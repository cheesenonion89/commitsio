from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

from slim.datasets import cifar10
from slim.datasets import flowers
from slim.datasets import imagenet
from slim.datasets import mnist
from slim.datasets import bot_dataset

datasets_map = {
    'cifar10': cifar10,
    'flowers': flowers,
    'imagenet': imagenet,
    'mnist': mnist,
    'bot': bot_dataset
}


def get_dataset(name, split_name, dataset_dir, file_pattern=None, reader=None):
  if name not in datasets_map:
    raise ValueError('Name of dataset unknown %s' % name)
  return datasets_map[name].get_split(
      split_name,
      dataset_dir,
      file_pattern,
      reader)
