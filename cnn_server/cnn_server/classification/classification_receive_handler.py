import json

import cnn_server.classification.classification_service as service


class ClassificationResult:

    def __init__(self, labels, probabilities):
        self.labels = labels
        self.probabilities = probabilities

    def to_json(self):
        return json.dumps(self, default=lambda o: o.__dict__, sort_keys=True, indent=4)


def handle_post(bot_id, image, return_labels=None):

    labels, probabilities = service.classify_image(bot_id, image, return_labels)

    if not labels or not probabilities:
        return "Error processing the input image", 400
    elif len(labels) == 0 or len(probabilities) == 0:
        return "Classification Result is empty", 500
    else:
        return ClassificationResult(labels, probabilities).to_json(), 200
