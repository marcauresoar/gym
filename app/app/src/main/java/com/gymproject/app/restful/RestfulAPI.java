package com.gymproject.app.restful;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gymproject.app.models.UpdateFicha;
import com.gymproject.app.models.Ficha;
import com.gymproject.app.models.Usuario;
import com.gymproject.app.restful.converters.LenientGsonConverterFactory;
import com.gymproject.app.serializers.FichaSerializer;
import com.gymproject.app.serializers.UpdateFichaSerializer;
import com.gymproject.app.serializers.UsuarioSerializer;

import io.realm.FichaRealmProxy;
import io.realm.RealmObject;
import io.realm.UpdateFichaRealmProxy;
import io.realm.UsuarioRealmProxy;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class RestfulAPI {
    //public static final String BASE_URL = "http://10.0.2.2:8080/gymwebserver/";
    public static final String BASE_URL = "http://192.168.254.83:8080/gymwebserver/";
    //public static final String BASE_URL = "http://gym-marcauresoar.rhcloud.com/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            Gson gson = new GsonBuilder()
                        .setExclusionStrategies(new ExclusionStrategy() {
                            @Override
                            public boolean shouldSkipField(FieldAttributes f) {
                                return f.getDeclaringClass().equals(RealmObject.class);
                            }

                            @Override
                            public boolean shouldSkipClass(Class<?> clazz) {
                                return false;
                            }
                        })
                        .registerTypeAdapter(Usuario.class, new UsuarioSerializer())
                        .registerTypeAdapter(UsuarioRealmProxy.class, new UsuarioSerializer())
                        .registerTypeAdapter(Ficha.class, new FichaSerializer())
                        .registerTypeAdapter(FichaRealmProxy.class, new FichaSerializer())
                        .registerTypeAdapter(UpdateFicha.class, new UpdateFichaSerializer())
                        .registerTypeAdapter(UpdateFichaRealmProxy.class, new UpdateFichaSerializer())
                        .create();


            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(LenientGsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
