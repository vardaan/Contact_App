package com.example.vardansharma.contact_app.data;

import com.example.vardansharma.contact_app.data.dataSource.DataSource;
import com.example.vardansharma.contact_app.data.dataSource.InMemoryDataSource;
import com.example.vardansharma.contact_app.data.dataSource.RemoteDataSource;
import com.example.vardansharma.contact_app.data.models.Contact;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by vardansharma on 06/06/17.
 */

public class DataManager implements DataSource {
    private final RemoteDataSource remoteDataSource;
    private final InMemoryDataSource inMemoryDataSource;
    private static final String TAG = "DataManager";

    public DataManager(InMemoryDataSource inMemoryDataSource, RemoteDataSource remoteDataSource) {
        this.inMemoryDataSource = inMemoryDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Observable<List<Contact>> getAllContact() {
        if (inMemoryDataSource.hasData()) {
            return inMemoryDataSource.getAllContact();
        }
        return remoteDataSource.getAllContact()
                .doOnNext(inMemoryDataSource:: updateContacts);// update cache
    }

    @Override
    public Observable<Contact> getContactDetails(String id) {
        if (inMemoryDataSource.hasFullContactData(id)) {
            return inMemoryDataSource.getContactDetails(id);
        }
        return remoteDataSource.getContactDetails(id)
                .doOnNext(inMemoryDataSource:: updateSingleContact);// update cache
    }

    @Override
    public Observable<Contact> updateFavourite(String contactId, boolean favourite) {
        return remoteDataSource.updateFavourite(contactId, favourite)
                .doOnNext(inMemoryDataSource:: updateSingleContact);
    }

    @Override
    public Observable<Contact> createContact(Contact contact) {
        return remoteDataSource.createContact(contact).doOnNext(inMemoryDataSource:: addContact);
    }

    @Override
    public Observable<Contact> updateContact(Contact contact) {
        return remoteDataSource.updateContact(contact).doOnNext(inMemoryDataSource:: updateContact);
    }
}
