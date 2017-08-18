package bot4jdeployment

import bot4jdeployment.rest.cnn_server.CNNServerApi
import bot4jdeployment.rest.cnn_server.CNNServerApiGenerator
import grails.transaction.Transactional
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.springframework.web.multipart.MultipartFile
import retrofit2.Call
import retrofit2.Response

@Transactional
class TrainingDataService {


    private final fileApi = CNNServerApiGenerator.createService(CNNServerApi.class)


    Bot postTrainingData(TrainingDataCommand cmd) {
        println("UPLOAD SERVICE")
        println("SIZE:" + cmd.trainingDataFile.getSize())
        println("CONTENT TYPE: " + cmd.trainingDataFile.getContentType())


        File file = multiPartFileToFile(cmd.trainingDataFile)
        print("TEMPORARY FILE LENGHT")
        print(file.length())


        RequestBody requestBody = RequestBody.create(
                MediaType.parse(cmd.trainingDataFile.getContentType()),
                file

        )

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", cmd.trainingDataFile.getOriginalFilename(), requestBody)

        String stringId = cmd.id.toString()

        Call<String> call = fileApi.putTrainingData(stringId, body)
        Response<String> response = call.execute()

        println(response.body())

        Bot.get(cmd.id)
    }

    Bot deleteTrainingData(Bot bot) {
        Call<String> call = fileApi.deleteTrainingData(bot.id.toString())
        Response<String> response = call.execute()
        println(response.body())
        return bot
    }

    private File multiPartFileToFile(MultipartFile multipartFile) {
        File file = File.createTempFile(multipartFile.getOriginalFilename(), '.tmp')
        multipartFile.transferTo(file)
        file
    }
}

