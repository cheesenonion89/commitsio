package bot4jdeployment.rest.bot.model;

public class TelegramSpecPayload {

    String platformName = "Telegram";

    String accessToken;
    String webhookUrl;

    public TelegramSpecPayload(String accessToken, String webhookUrl) {
        this.accessToken = accessToken;
        this.webhookUrl = webhookUrl;
    }
}
