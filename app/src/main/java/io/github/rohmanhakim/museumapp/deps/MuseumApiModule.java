package io.github.rohmanhakim.museumapp.deps;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.rohmanhakim.museumapp.R;
import io.github.rohmanhakim.museumapp.api.service.MuseumApiService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rohmanhakim <rohmanhakim@live.com> on 9/4/16 13:33.
 */

@Module
public class MuseumApiModule {

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public Gson providesGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor providesHttpLoggingInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    @Named("baseOkhtpp")
    public OkHttpClient providesOkHttpClient3(HttpLoggingInterceptor interceptor) {
            return new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    @Named("baseRetrofit")
    public Retrofit providesBaseRetrofit(Context context, @Named("baseOkhtpp") OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(context.getString(R.string.app_base_url))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public MuseumApiService providesMuseumApiService(@Named("baseRetrofit") Retrofit retrofit){
        MuseumApiService museumApiService = retrofit.create(MuseumApiService.class);
        return  museumApiService;
    }
}
