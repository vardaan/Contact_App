package com.example.vardansharma.contact_app.ui.contactDetail;

import com.example.vardansharma.contact_app.data.dataSource.DataSource;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vardansharma on 12/06/17.
 */

@Module
public class ContactDetailModule {
    private ContactDetailContract.Screen screen;

    public ContactDetailModule(ContactDetailContract.Screen screen) {
        this.screen = screen;
    }


    @Provides
    public ContactDetailContract.Screen provideScreen(){
        return screen;
    }
    @Provides
    public ContactDetailContract.Presenter providePresenter(DataSource dataSource,
                                                            ContactDetailContract.Screen screen) {
        return new ContactDetailPresenter(screen, dataSource);
    }
}
