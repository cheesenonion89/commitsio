import multiprocessing
from multiprocessing import Manager

import os
import tensorflow as tf

from cnn_server.server import file_service as dirs
from slim.datasets import dataset_utils
from slim.nets import nets_factory as network_factory
from slim.preprocessing import preprocessing_factory as preprocessing_factory

slim = tf.contrib.slim


def map_predictions_to_labels(protobuf_dir, predictions, return_labels=None):

    labels = []

    if not return_labels:
        return_labels = len(predictions) - 1

    for line in open(os.path.join(protobuf_dir, 'labels.txt')):
        labels.append(line.split(':')[1].replace('\n', ''))

    # Get the indices of the n predictions with highest score
    top_n = predictions.argsort()[-return_labels:][::-1]

    lbls = [labels[ndx] for ndx in top_n]
    probabilities = predictions[top_n].tolist()
    return lbls, probabilities


def inference_on_image(bot_id, image_file, network_name='inception_v4', return_labels=None):

    manager = Manager()
    prediction_dict = manager.dict()
    process = multiprocessing.Process(target=infere,
                                      args=(bot_id, image_file, network_name, return_labels, prediction_dict))
    process.start()
    process.join()
    print(prediction_dict)
    return prediction_dict['predictions']


def infere(bot_id, image_file, network_name='inception_v4', return_labels=None, prediction_dict=[]):


    model_path = dirs.get_model_data_dir(bot_id)

    protobuf_dir = dirs.get_protobuf_dir(bot_id)
    number_of_classes = dataset_utils.get_number_of_classes_by_labels(protobuf_dir)

    if not return_labels:
        return_labels = number_of_classes

    preprocessing_fn = preprocessing_factory.get_preprocessing(network_name, is_training=False)
    network_fn = network_factory.get_network_fn(network_name, number_of_classes)

    image_tensor = tf.gfile.FastGFile(image_file, 'rb').read()
    image_tensor = tf.image.decode_image(image_tensor, channels=0)

    network_default_size = network_fn.default_image_size
    image_tensor = preprocessing_fn(image_tensor, network_default_size, network_default_size)

    input_batch = tf.reshape(image_tensor, [1, 299, 299, 3])

    logits, endpoints = network_fn(input_batch)

    restorer = tf.train.Saver()

    with tf.Session() as sess:
        tf.global_variables_initializer().run()

        restorer.restore(sess, tf.train.latest_checkpoint(model_path))
        sess.run(endpoints)

        predictions = endpoints['Predictions'].eval()[0]
        sess.close()
        prediction_dict['predictions'] = map_predictions_to_labels(protobuf_dir, predictions, return_labels)
