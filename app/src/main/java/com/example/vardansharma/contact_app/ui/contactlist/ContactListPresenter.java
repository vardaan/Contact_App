package com.example.vardansharma.contact_app.ui.contactlist;

import com.example.vardansharma.contact_app.data.dataSource.DataSource;
import com.example.vardansharma.contact_app.data.models.Contact;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
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
        //nothing to do here
    }


    @Override
    public void getAllContacts() {
        screen.showLoading();
        compositeDisposable.add(dataSource.getAllContact()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Contact>>() {
                    @Override
                    public void onNext(@NonNull List<Contact> contacts) {
                        screen.hideLoading();
                        if (contacts == null || contacts.size() == 0) {
                            screen.showEmptyScreen();
                            return;
                        }
                        screen.showData(contacts);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        screen.hideLoading();

                        if(e instanceof IOException){
                            screen.showNetworkError();
                        }
                        else{
                            screen.showErrorMessage();
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                }));

    }

    @Override
    public void detachView() {
        compositeDisposable.clear();
    }
}
