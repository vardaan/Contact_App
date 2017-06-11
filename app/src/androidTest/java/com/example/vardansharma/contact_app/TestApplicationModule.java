package com.example.vardansharma.contact_app;

import android.app.Application;
import android.content.Context;

import com.example.vardansharma.contact_app.data.DataManager;
import com.example.vardansharma.contact_app.data.dataSource.DataSource;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestApplicationModule {
    private Application application;

    public TestApplicationModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public Context providesContext() {
        return application;
    }

    @Provides
    @Singleton
    public DataSource provideDataSource() {
        return Mockito.mock(DataManager.class);
    }
}