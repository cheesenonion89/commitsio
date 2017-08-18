from cnn_server.training_data import training_data_service




def handle_put(bot_id: int, training_data_file: str):

    if not training_data_service.validate_training_data(training_data_file):
        print()
        return "Training Data is invalid", 400

    if not training_data_service.create_training_data_dir(bot_id, training_data_file):
        return "Failed to create training data directory.", 400

    if not training_data_service.write_to_protobuffer(bot_id):
        return "Failed to convert training data to protobuffer format", 400

    return "Training Data created", 200


def handle_delete(bot_id):

    return training_data_service.delete_bot_data(bot_id)
