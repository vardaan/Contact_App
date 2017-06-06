package com.example.vardansharma.contact_app.data;

import android.content.Context;

import com.example.vardansharma.contact_app.data.dataSource.DataSource;
import com.example.vardansharma.contact_app.data.dataSource.InMemoryDataSource;
import com.example.vardansharma.contact_app.data.dataSource.RemoteDataSource;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.moshi.Moshi;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;


@Module
public class DataModule {
    private static final String BASE_URL = "http://gojek-contacts-app.herokuapp.com";

    @Singleton
    @Provides
    public Moshi provideMoshi() {
        return new Moshi.Builder().build();
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkhttp() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor()).build();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(Moshi moshi, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// execution mechanism
                .addConverterFactory(MoshiConverterFactory.create(moshi))// how the objects will be converted
                .client(okHttpClient)// will make the underlying calls
                .baseUrl(BASE_URL)// base url for our requests
                .build();
    }

    @Singleton
    @Provides
    public OkHttp3Downloader provideOkHttp3Downloader(OkHttpClient client) {
        return new OkHttp3Downloader(client);

    }

    @Singleton
    @Provides
    public Picasso providePicasso(OkHttp3Downloader okHttp3Downloader, Context context) {
        return new Picasso.Builder(context).downloader(okHttp3Downloader).build();
    }

    @Singleton
    @Provides
    public ContactsApiService provideContactsApi(Retrofit retrofit) {
        return retrofit.create(ContactsApiService.class);
    }


    @Singleton
    @Provides
    public RemoteDataSource provideRemoteDataSource(ContactsApiService contactsApiService) {
        return new RemoteDataSource(contactsApiService);
    }

    @Singleton
    @Provides
    public InMemoryDataSource provideInMemoryDataSource() {
        return new InMemoryDataSource();
    }

    @Singleton
    @Provides
    public DataSource providesDataManager(InMemoryDataSource inMemoryDataSource,
                                          RemoteDataSource remoteDataSource) {
        return new DataManager(inMemoryDataSource, remoteDataSource);
    }
}
