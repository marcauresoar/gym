package com.gymproject.app.restful;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gymproject.app.models.Exercicio;
import com.gymproject.app.models.Serie;
import com.gymproject.app.models.UpdateExercicio;
import com.gymproject.app.models.UpdateFicha;
import com.gymproject.app.models.Ficha;
import com.gymproject.app.models.UpdateSerie;
import com.gymproject.app.models.Usuario;
import com.gymproject.app.restful.converters.LenientGsonConverterFactory;
import com.gymproject.app.serializers.ExercicioSerializer;
import com.gymproject.app.serializers.FichaSerializer;
import com.gymproject.app.serializers.SerieSerializer;
import com.gymproject.app.serializers.UpdateExercicioSerializer;
import com.gymproject.app.serializers.UpdateFichaSerializer;
import com.gymproject.app.serializers.UpdateSerieSerializer;
import com.gymproject.app.serializers.UsuarioSerializer;

import io.realm.ExercicioRealmProxy;
import io.realm.FichaRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.SerieRealmProxy;
import io.realm.UpdateExercicioRealmProxy;
import io.realm.UpdateFichaRealmProxy;
import io.realm.UpdateSerieRealmProxy;
import io.realm.UsuarioRealmProxy;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class RestfulAPI {
    //public static final String BASE_URL = "http://10.0.2.2:8080/gymwebserver/";
    //public static final String BASE_URL = "http://192.168.0.106:8080/gymwebserver/";
    public static final String BASE_URL = "http://gym-marcauresoar.rhcloud.com/";
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
                        .registerTypeAdapter(Exercicio.class, new ExercicioSerializer())
                        .registerTypeAdapter(ExercicioRealmProxy.class, new ExercicioSerializer())
                        .registerTypeAdapter(Serie.class, new SerieSerializer())
                        .registerTypeAdapter(SerieRealmProxy.class, new SerieSerializer())
                        .registerTypeAdapter(UpdateFicha.class, new UpdateFichaSerializer())
                        .registerTypeAdapter(UpdateFichaRealmProxy.class, new UpdateFichaSerializer())
                        .registerTypeAdapter(UpdateExercicio.class, new UpdateExercicioSerializer())
                        .registerTypeAdapter(UpdateExercicioRealmProxy.class, new UpdateExercicioSerializer())
                        .registerTypeAdapter(UpdateSerie.class, new UpdateSerieSerializer())
                        .registerTypeAdapter(UpdateSerieRealmProxy.class, new UpdateSerieSerializer())
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
