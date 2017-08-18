import os

import slim.transfer_image_classifier as trainer
from cnn_server.server import file_service as dirs


def train():
    protobuf_dir = dirs.get_protobuf_dir('root') 
    labels = 0
    if not os.path.isfile(os.path.join(protobuf_dir, 'labels.txt')):
        print('Missing labels in %s' % os.path.join(protobuf_dir, 'labels.txt'))
        return None

    for _ in open(os.path.join(protobuf_dir, 'labels.txt')):
        labels += 1

    if not labels == 12:
        print('Wrong number of labels: %s in: %s' % (labels, protobuf_dir))
        return None
    bot_model_dir = dirs.get_model_data_dir('root')
    if not os.path.isdir(bot_model_dir):
        os.mkdir(bot_model_dir)

    trainer.train(bot_model_dir=bot_model_dir,
                  protobuf_dir=protobuf_dir,
                  max_train_time_sec=(60 * 60 * 24 * 7),  
                  optimization_params=None,
                  log_every_n_steps=10)
