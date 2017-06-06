package com.example.vardansharma.contact_app;

import android.app.Application;

import com.example.vardansharma.contact_app.di.ApplicationComponent;
import com.example.vardansharma.contact_app.di.ApplicationModule;
import com.example.vardansharma.contact_app.di.DaggerApplicationComponent;
import com.facebook.stetho.Stetho;

import timber.log.Timber;

public class ContactsApp extends Application {
    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(this);
        }
        else {
            // send to crashlytics or something similar
        }

        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getAppComponent() {
        return appComponent;
    }
}
