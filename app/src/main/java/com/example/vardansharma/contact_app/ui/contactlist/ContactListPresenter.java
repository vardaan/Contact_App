package com.example.vardansharma.contact_app.ui.contactlist;

import com.example.vardansharma.contact_app.data.dataSource.DataSource;

/**
 * Created by vardansharma on 06/06/17.
 */

public class ContactListPresenter implements ContactListContract.Presenter {

    private final DataSource dataSource;
    private final ContactListContract.Screen screen;

    public ContactListPresenter(DataSource dataSource, ContactListContract.Screen screen) {
        this.dataSource = dataSource;
        this.screen = screen;
    }

    @Override
    public void attachView() {
        throw new IllegalStateException("No yet implemented");
    }

    @Override
    public void detachView() {
        throw new IllegalStateException("No yet implemented");
    }

    public void getAllContacts() {

        screen.showLoading();

    }
}
