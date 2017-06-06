package com.example.vardansharma.contact_app.ui.contactlist;

import com.example.vardansharma.contact_app.data.dataSource.DataSource;
import com.example.vardansharma.contact_app.data.models.Contact;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


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
        dataSource.getAllContact()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Contact>() {
                    @Override
                    public void onNext(@NonNull Contact contact) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
