package com.example.vardansharma.contact_app.ui.contactlist;

import com.example.vardansharma.contact_app.data.dataSource.DataSource;
import com.example.vardansharma.contact_app.data.models.Contact;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by vardansharma on 06/06/17.
 */

public class ContactListPresenter implements ContactListContract.Presenter {

    private final DataSource dataSource;
    private final ContactListContract.Screen screen;

    private CompositeDisposable compositeDisposable;

    public ContactListPresenter(DataSource dataSource, ContactListContract.Screen screen) {
        this.dataSource = dataSource;
        this.screen = screen;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView() {
        throw new IllegalStateException("No yet implemented");
    }

    @Override
    public void detachView() {
        compositeDisposable.clear();
    }

    public void getAllContacts() {
        screen.showLoading();
        compositeDisposable.add(dataSource.getAllContact()
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<List<Contact>>() {
                    @Override
                    public void onNext(@NonNull List<Contact> contacts) {
                        if (contacts == null || contacts.size() == 0) {
                            screen.showEmptyScreen();
                            return;
                        }
                        screen.showData(contacts);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        screen.hideLoading();
                        screen.showErrorScreen();
                    }

                    @Override
                    public void onComplete() {
                        screen.hideLoading();
                    }
                }));

    }
}
