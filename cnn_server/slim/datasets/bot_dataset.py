from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import os
import tensorflow as tf

from cnn_server.server import file_service as dirs
from slim.datasets import dataset_utils

slim = tf.contrib.slim

_FILE_PATTERN = 'sample_%s_*.tfrecord'

_ITEMS_TO_DESCRIPTIONS = {
    'image': 'A color image of varying size.',
    'label': 'A single integer between 0 and nr of classes'
}

_SPLIT_FRAC = 0.1 


def get_split(split_name, dataset_dir, file_pattern=None, reader=None):
    if split_name not in ['train', 'validation']:
        raise ValueError('illegal split name %s ' % split_name)

    num_classes = dataset_utils.get_number_of_classes_by_labels(dataset_dir)

    if not num_classes:
        raise FileNotFoundError('Dataset in %s not Found' % dataset_dir)

    if not file_pattern:
        file_pattern = _FILE_PATTERN
    file_pattern = os.path.join(dataset_dir, file_pattern % split_name)

    if reader is None:
        reader = tf.TFRecordReader

    keys_to_features = {
        'image/encoded': tf.FixedLenFeature((), tf.string, default_value=''),
        'image/format': tf.FixedLenFeature((), tf.string, default_value='png'),
        'image/class/label': tf.FixedLenFeature(
            [], tf.int64, default_value=tf.zeros([], dtype=tf.int64)),
    }

    items_to_handlers = {
        'image': slim.tfexample_decoder.Image(),
        'label': slim.tfexample_decoder.Tensor('image/class/label'),
    }

    decoder = slim.tfexample_decoder.TFExampleDecoder(
        keys_to_features, items_to_handlers)

    labels_to_names = None
    if dataset_utils.has_labels(dataset_dir):
        labels_to_names = dataset_utils.read_label_file(dataset_dir)

    bot_id = dirs.get_bot_id_from_dir(dataset_dir)

    training_data_dir = dirs.get_training_data_dir(bot_id)

    if not bot_id:
        raise ValueError('bot id not recognized from dataset_dir %s' % dataset_dir)

    split_size = dataset_utils.get_split_size(
        training_data_dir, split_name, _SPLIT_FRAC
    )

    return slim.dataset.Dataset(
        data_sources=file_pattern,
        reader=reader,
        decoder=decoder,
        num_samples=split_size,
        items_to_descriptions=_ITEMS_TO_DESCRIPTIONS,
        num_classes=num_classes,
        labels_to_names=labels_to_names)
