from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import sys

import os
import tarfile
import tensorflow as tf
from six.moves import urllib

LABELS_FILENAME = 'labels.txt'


def int64_feature(values):
    if not isinstance(values, (tuple, list)):
        values = [values]
    return tf.train.Feature(int64_list=tf.train.Int64List(value=values))


def bytes_feature(values):

    return tf.train.Feature(bytes_list=tf.train.BytesList(value=[values]))


def image_to_tfexample(image_data, image_format, height, width, class_id):
    return tf.train.Example(features=tf.train.Features(feature={
        'image/encoded': bytes_feature(image_data),
        'image/format': bytes_feature(image_format),
        'image/class/label': int64_feature(class_id),
        'image/height': int64_feature(height),
        'image/width': int64_feature(width),
    }))


def download_and_uncompress_tarball(tarball_url, dataset_dir):
    filename = tarball_url.split('/')[-1]
    filepath = os.path.join(dataset_dir, filename)

    def _progress(count, block_size, total_size):
        sys.stdout.write('\r>> Downloading %s %.1f%%' % (
            filename, float(count * block_size) / float(total_size) * 100.0))
        sys.stdout.flush()

    filepath, _ = urllib.request.urlretrieve(tarball_url, filepath, _progress)
    print()
    statinfo = os.stat(filepath)
    print('Successfully downloaded', filename, statinfo.st_size, 'bytes.')
    tarfile.open(filepath, 'r:gz').extractall(dataset_dir)


def write_label_file(labels_to_class_names, dataset_dir,
                     filename=LABELS_FILENAME):
    labels_filename = os.path.join(dataset_dir, filename)
    with tf.gfile.Open(labels_filename, 'w') as f:
        for label in labels_to_class_names:
            class_name = labels_to_class_names[label]
            f.write('%d:%s\n' % (label, class_name))


def has_labels(dataset_dir, filename=LABELS_FILENAME):
    return tf.gfile.Exists(os.path.join(dataset_dir, filename))


def read_label_file(dataset_dir, filename=LABELS_FILENAME):
    labels_filename = os.path.join(dataset_dir, filename)
    with tf.gfile.Open(labels_filename, 'r') as f:
        # lines = f.read().decode()
        lines = f.read()
    lines = lines.split('\n')
    lines = filter(None, lines)

    labels_to_class_names = {}
    for line in lines:
        index = line.index(':')
        labels_to_class_names[int(line[:index])] = line[index + 1:]
    return labels_to_class_names


def get_number_of_classes_by_subfolder(training_data_dir):
    labels = 0

    for ndx, dir in enumerate(os.listdir(training_data_dir)):
        labels = ndx
    return labels + 1


def get_number_of_classes_by_labels(protobuf_dir):
    labels = 0
    if not os.path.exists(protobuf_dir):
        return None
    if not os.path.isfile(os.path.join(protobuf_dir, 'labels.txt')):
        return None

    with open(os.path.join(protobuf_dir, 'labels.txt')) as f:
        for ndx, ln in enumerate(f):
            pass
        return ndx + 1


def get_split_size(training_data_dir, split_name, frac_validation=0.0):
    split_fracs = {
        'train': (1 - frac_validation),
        'validation': frac_validation
    }
    if split_name not in split_fracs:
        print('Name of dataset split split_name %s is unknown' % split_name)
        return None
    dataset_size = sum([len(files) for r, d, files in os.walk(training_data_dir)])
    return int(dataset_size * split_fracs[split_name])
