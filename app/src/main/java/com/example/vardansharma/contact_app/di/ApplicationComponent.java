package com.example.vardansharma.contact_app.di;

import android.content.Context;

import com.example.vardansharma.contact_app.data.DataModule;
import com.example.vardansharma.contact_app.data.dataSource.DataSource;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vardansharma on 06/06/17.
 */

@Singleton
@Component (modules = {ApplicationModule.class, DataModule.class})
public interface ApplicationComponent {

    DataSource provideDataManager();

    Context provideContext();

}

