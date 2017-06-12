package com.example.vardansharma.contact_app.ui.contactDetail;

import com.example.vardansharma.contact_app.data.dataSource.DataSource;
import com.example.vardansharma.contact_app.data.models.Contact;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

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
        dataSource.getContactDetails(contactId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Contact>() {
                    @Override
                    public void onNext(@NonNull Contact contact) {
                        view.hideLoading();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.hideLoading();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
