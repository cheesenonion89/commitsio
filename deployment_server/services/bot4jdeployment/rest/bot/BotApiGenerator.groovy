package bot4jdeployment.rest.bot

import com.google.gson.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BotApiGenerator {

    private final static String LOCAL_NETWORK_URL = "http://192.168.0.2:5000/";
    private final static String LOCALHOST_URL ="http://localhost:4567"

    static Gson gson = new GsonBuilder().setLenient().create()

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(LOCALHOST_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    static <S> S createService(
            Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
