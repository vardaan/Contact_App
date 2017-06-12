package com.example.vardansharma.contact_app.ui.contactDetail;

import com.example.vardansharma.contact_app.data.dataSource.DataSource;
import com.example.vardansharma.contact_app.data.models.Contact;

import java.io.IOException;

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

    @Override
    public void getContactDetail(String contactId) {
        view.showLoading();
        dataSource.getContactDetails(contactId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Contact>() {
                    @Override
                    public void onNext(@NonNull Contact contact) {
                        view.hideLoading();
                        view.showContactDetail(contact);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.hideLoading();
                        if (e instanceof IOException) {
                            view.showNetworkError();
                        }
                        else {
                            view.showErrorMessage();
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void onPhoneButtonClicked(String phoneNum) {
        view.launchPhoneApp(phoneNum);
    }

    @Override
    public void onEmailButtonClicked(String email) {
        view.launchEmailApp(email);

    }

    @Override
    public void onMessageButtonClicked(String phoneNum) {
        view.launchMessageApp(phoneNum);
    }
}
