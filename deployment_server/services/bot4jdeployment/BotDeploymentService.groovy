package bot4jdeployment

import bot4jdeployment.rest.bot.BotApi
import bot4jdeployment.rest.bot.BotApiGenerator
import bot4jdeployment.rest.bot.model.BotSendPayload
import bot4jdeployment.rest.bot.model.FacebookSpecPayload
import bot4jdeployment.rest.bot.model.SlackSpecPayload
import bot4jdeployment.rest.bot.model.TelegramSpecPayload
import grails.transaction.Transactional
import retrofit2.Call
import retrofit2.Response

@Transactional
class BotDeploymentService {

    private final botApi = BotApiGenerator.createService(BotApi.class)

    def deleteBot(Bot bot) {
        Call<String> call = botApi.deleteBot(bot.getId())
        Response<String> response = call.execute();
        println(response.body())
        return response.body()
    }

    def deployBot(Bot bot) {

        Call<String> call = botApi.deployBot(domainToRestModel(bot))
        Response<String> response = call.execute()
        println(response)
    }

    def domainToRestModel(Bot bot) {

        FacebookSpecPayload facebookSpecPayload = null
        SlackSpecPayload slackSpecPayload = null
        TelegramSpecPayload telegramSpecPayload = null

        if (bot.facebookSpec) {
            facebookSpecPayload = new FacebookSpecPayload(
                    bot.facebookSpec.accessToken
            )
        }

        if (bot.slackSpec) {
            slackSpecPayload = new SlackSpecPayload(
                    bot.slackSpec.accessToken,
                    bot.slackSpec.clientId,
                    bot.slackSpec.clientSecret,
                    bot.slackSpec.userName
            )
        }

        if (bot.telegramSpec) {
            telegramSpecPayload = new TelegramSpecPayload(
                    bot.telegramSpec.accessToken,
                    bot.telegramSpec.webhookUrl
            )
        }

        BotSendPayload botSendPayload = new BotSendPayload(
                bot.id,
                bot.name,
                bot.botType,
                facebookSpecPayload,
                slackSpecPayload,
                telegramSpecPayload
        )
        return botSendPayload
    }

}
