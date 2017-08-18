package bot4jdeployment.rest.bot.model;


public class BotSendPayload {
    private Long id;
    private String name;
    private String botType;

    private FacebookSpecPayload facebookSpec;
    private SlackSpecPayload slackSpecPayload;
    private TelegramSpecPayload telegramSpecPayload;

    public BotSendPayload(Long id, String name, String botType, FacebookSpecPayload facebookSpec, SlackSpecPayload slackSpecPayload, TelegramSpecPayload telegramSpecPayload) {
        this.id = id;
        this.name = name;
        this.botType = botType;
        this.facebookSpec = facebookSpec;
        this.slackSpecPayload = slackSpecPayload;
        this.telegramSpecPayload = telegramSpecPayload;
    }
}
