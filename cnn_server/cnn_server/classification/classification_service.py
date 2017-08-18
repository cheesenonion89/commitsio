import base64
import os
import tempfile

import slim.inference_image_classifier as classifier


def classify_image(bot_id, image, return_labels=None):
    temp_file = tempfile.NamedTemporaryFile()
    temp_file.write(
        base64.b64decode(image)
    )

    labels, probabilities = classifier.inference_on_image(bot_id, os.path.join(tempfile.gettempdir(), temp_file.name),
                                                          network_name='inception_v4', return_labels=return_labels)

    temp_file.close()

    return labels, probabilities
