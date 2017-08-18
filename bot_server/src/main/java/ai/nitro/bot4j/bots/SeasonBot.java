
package ai.nitro.bot4j.bots;

import ai.nitro.bot4j.bot.impl.BotImpl;
import ai.nitro.bot4j.middle.domain.Participant;
import ai.nitro.bot4j.middle.domain.receive.payload.PostbackReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.TextReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.UrlAttachmentReceivePayload;
import ai.nitro.bot4j.rest.ApiProviderService;
import ai.nitro.bot4j.rest.api.ImageApi;
import ai.nitro.bot4j.rest.domain.Base64ImageSendPayload;
import ai.nitro.bot4j.rest.domain.ImageNetResult;
import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;


public class SeasonBot extends BotImpl {

    private static final String BUTTON = "button";

    private final static int NR_RETURN_LABELS = 3;

    protected final static Logger LOG = LogManager.getLogger(InceptionBot.class);

    ImageApi imageApi = ApiProviderService.createService(ImageApi.class);

    @Override
    protected void onReceiveText(final TextReceivePayload receiveTextPayload, final Participant sender)
            throws Exception {
        final Participant recipient = sender;
        final String text = receiveTextPayload.getText();

        LOG.info("ON RECEIVE TEXT");
        LOG.info("received {}", text);

        sendText("processing your message...", recipient);

    }


    @Override
    protected void onReceiveAttachment(final UrlAttachmentReceivePayload payload, final Participant sender, Long botId) {
        final Participant recipient = sender;
        ImageNetResult imageNetResult = null;

        Call<String> call = imageApi.postBase64Image(
                Long.toString(botId),
                getBase64ImageSendPayload(
                        0,
                        payload.getTitle(),
                        payload.getUrl())
        );
        try {
            Response<String> response = call.execute();
            LOG.warn(response.body());
            Gson gson = new Gson();
            imageNetResult = gson.fromJson(response.body(), ImageNetResult.class);
        } catch (IOException e) {
            LOG.warn(e);
        }

        if (imageNetResult != null) {
            List<String> labels = imageNetResult.getLabels().subList(0, NR_RETURN_LABELS);
            List<String> probabilities = imageNetResult.getProbabilities().subList(0, NR_RETURN_LABELS);

            String label = labels.get(0);
            label = label.substring(0, 1).toUpperCase() + label.substring(1); //Capitalize first letter

            String reply = String.format("I am %s%s sure, that this image has been taken in %s.", (Float.parseFloat(probabilities.get(0)) * 100), '%', label);
            sendText(reply, recipient);
        } else {
            sendText("Something went wrong, please try again with another image.", recipient);
        }


        LOG.info("RECEIVED AN ATTACHMENT");

    }

    @Override
    protected void onReceivePostback(final PostbackReceivePayload postback, final Participant sender) throws Exception {

        LOG.info("ON RECEIVE POSTBACK");
        final Participant recipient = sender;

        final String name = postback.getName();
        final String[] payload = postback.getPayload();

        switch (postback.getName()) {
            case BUTTON:
                final String joinedPayload = StringUtils.join(payload, ", ");
                sendText(joinedPayload, recipient);
                break;
            default:
                LOG.warn("Unknown postback {}", name);
        }
    }

    private Base64ImageSendPayload getBase64ImageSendPayload(final int messageId, String title, final String url) {
        InputStream inputStream = null;
        Base64ImageSendPayload result = null;

        if (title == null) {
            title = "";
        }

        try {

            inputStream = new URL(url).openStream();
            final String base64Image = Base64.encodeBase64String(IOUtils.toByteArray(inputStream));

            result = new Base64ImageSendPayload(messageId, title, base64Image);

        } catch (final Exception e) {
            LOG.warn(e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (final IOException e) {
                LOG.warn(e);
            }

        }
        return result;
    }

}