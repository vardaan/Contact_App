package com.example.vardansharma.contact_app.data;

import android.util.Log;

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
            Log.d(TAG, "getAllContact() called");
            return inMemoryDataSource.getAllContact();
        }
        return remoteDataSource.getAllContact()
                .doOnNext(inMemoryDataSource::updateContacts);// update cache
    }

    @Override
    public Observable<Contact> getContactDetails(String id) {
        if (inMemoryDataSource.hasFullContactData(id)) {
            Log.d(TAG, "getContactDetails() called with: id = [" + id + "]");
            return inMemoryDataSource.getContactDetails(id);
        }
        return remoteDataSource.getContactDetails(id)
                .doOnNext(inMemoryDataSource::updateSingleContact);// update cache
    }

    @Override
    public Observable<Contact> updateFavourite(String contactId, boolean favourite) {
        return inMemoryDataSource.updateFavourite(contactId, favourite);
    }
}
