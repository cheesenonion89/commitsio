from flask import request
from flask_restful import Resource

import cnn_server.classification.classification_receive_handler as handler


class Classifier(Resource):
    def post(self, bot_id):

        response = request.get_json(force=True)
        base64_image = response['base64Image']

        return handler.handle_post(bot_id, base64_image)
