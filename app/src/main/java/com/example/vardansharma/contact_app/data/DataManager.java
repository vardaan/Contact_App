package com.example.vardansharma.contact_app.data;

import com.example.vardansharma.contact_app.data.dataSource.DataSource;
import com.example.vardansharma.contact_app.data.dataSource.InMemoryDataSource;
import com.example.vardansharma.contact_app.data.dataSource.RemoteDataSource;
import com.example.vardansharma.contact_app.data.models.Contact;

import rx.Observable;

/**
 * Created by vardansharma on 06/06/17.
 */

public class DataManager implements DataSource {
    private final RemoteDataSource remoteDataSource;
    private final InMemoryDataSource inMemoryDataSource;

    public DataManager(InMemoryDataSource inMemoryDataSource, RemoteDataSource remoteDataSource) {
        this.inMemoryDataSource = inMemoryDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Observable<Contact> getAllContact() {
        throw new IllegalStateException("Not yet implemented");
    }
}
