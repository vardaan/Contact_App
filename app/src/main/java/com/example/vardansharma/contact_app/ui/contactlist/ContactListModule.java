package com.example.vardansharma.contact_app.ui.contactlist;

import com.example.vardansharma.contact_app.data.dataSource.DataSource;
import com.example.vardansharma.contact_app.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vardansharma on 06/06/17.
 */

@Module
public class ContactListModule {
    public ContactListModule(ContactListContract.Screen screen) {
        this.screen = screen;
    }

    private ContactListContract.Screen screen;

    @Provides
    @ActivityScope
    public ContactListContract.Screen screen() {
        return screen;
    }


    @Provides
    @ActivityScope
    public ContactListContract.Presenter presenter(DataSource dataSource, ContactListContract.Screen screen) {
        return new ContactListPresenter(dataSource, screen);
    }
}
