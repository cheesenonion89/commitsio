package bot4jdeployment

class Bot {

    String name
    String botType

    FacebookSpec facebookSpec
    SlackSpec slackSpec
    TelegramSpec telegramSpec

    static hasOne = [
            facebookSpec: FacebookSpec,
            telegramSpec: TelegramSpec,
            slackSpec   : SlackSpec
    ]

    static constraints = {
        facebookSpec(nullable: true, unique: true)
        slackSpec(nullable: true, unique: true)
        telegramSpec(nullable: true, unique: true)
    }
}
