package com.example.vardansharma.contact_app.ui.contactDetail;

import android.support.annotation.Nullable;

import com.example.vardansharma.contact_app.data.dataSource.DataSource;
import com.example.vardansharma.contact_app.data.models.Contact;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vardansharma on 12/06/17.
 */

public class ContactDetailPresenter implements ContactDetailContract.Presenter {
    private final DataSource dataSource;
    private final ContactDetailContract.Screen view;
    private CompositeDisposable compositeDisposable;
    @Nullable
    private Contact contact;


    @Inject
    public ContactDetailPresenter(ContactDetailContract.Screen screen, DataSource dataSource) {
        this.view = screen;
        this.dataSource = dataSource;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView() {

    }


    @Override
    public void getContactDetail(String contactId) {
        view.showLoading();
        compositeDisposable.add(dataSource.getContactDetails(contactId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Contact>() {

                    @Override
                    public void onNext(@NonNull Contact contact) {
                        view.hideLoading();
                        view.showContactDetail(contact);
                        ContactDetailPresenter.this.contact = contact;
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
                }));
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

    @Override
    public void onPhoneNumberLongPress(String phoneNum) {
        view.copyToClipboard(phoneNum);
        view.showCopyToClipBoardMessage();
    }

    @Override
    public void onEmailLongPress(String email) {
        view.copyToClipboard(email);
        view.showCopyToClipBoardMessage();
    }

    @Override
    public void onShareButtonClicked() {
        if (contact != null) {
            view.shareContact(contact);
        }
    }

    @Override
    public void onEditButtonClicked() {
        if (contact != null) {
            view.launchEditContactScreen(contact);
        }
    }

    @Override
    public void detachView() {
        compositeDisposable.clear();
    }
}
