package bot4jdeployment

class FacebookSpec {

    String platformName = 'Facebook'

    String accessToken

    static belongsTo = [bot: Bot]


    static constraints = {
        platformName editable: false
    }
}
