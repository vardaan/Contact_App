package com.example.vardansharma.contact_app;

import android.app.Application;

import com.facebook.stetho.Stetho;

import timber.log.Timber;

/**
 * Created by vardansharma on 06/06/17.
 */

public class ContactsApp extends Application {
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
    }
}
