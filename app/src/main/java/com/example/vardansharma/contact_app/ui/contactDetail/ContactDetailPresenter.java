package com.example.vardansharma.contact_app.ui.contactDetail;

import com.example.vardansharma.contact_app.data.dataSource.DataSource;

import javax.inject.Inject;

/**
 * Created by vardansharma on 12/06/17.
 */

public class ContactDetailPresenter implements ContactDetailContract.Presenter {
    private final DataSource dataSource;
    private final ContactDetailContract.Screen view;

    @Inject
    public ContactDetailPresenter(ContactDetailContract.Screen screen, DataSource dataSource) {
        this.view = screen;
        this.dataSource = dataSource;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void detachView() {

    }

    public void getContactDetail(String contactId) {
        view.showLoading();
    }
}
