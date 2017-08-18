package bot4jdeployment.rest.bot.model;


public class FacebookSpecPayload {

    String platformName = "Facebook";

    String accessToken;

    public FacebookSpecPayload(String accessToken) {
        this.accessToken = accessToken;
    }
}
