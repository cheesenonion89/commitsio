package bot4jdeployment

class SlackSpec {

    String platformName = 'Slack'

    String accessToken
    String clientId
    String clientSecret
    String userName

    static belongsTo = [bot: Bot]

    static constraints = {
        platformName editable: false
    }
}
