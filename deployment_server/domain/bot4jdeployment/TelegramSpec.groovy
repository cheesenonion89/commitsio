package bot4jdeployment

class TelegramSpec {

    String platformName = 'Telegram'

    String accessToken
    String webhookUrl

    static belongsTo = [bot: Bot]

    static constraints = {
        platformName editable: false
    }
}
