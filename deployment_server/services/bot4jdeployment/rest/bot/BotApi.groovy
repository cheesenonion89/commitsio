package bot4jdeployment.rest.bot

import bot4jdeployment.rest.bot.model.BotSendPayload
import retrofit2.Call
import retrofit2.http.*


interface BotApi {

    @DELETE("/deploy")
    Call<String> deleteBot(@Query("bot_id") botId)

    @PUT("/deploy")
    Call<String> deployBot(@Body BotSendPayload botSendPayload)



}
