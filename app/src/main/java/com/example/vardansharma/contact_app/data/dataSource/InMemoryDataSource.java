package com.example.vardansharma.contact_app.data.dataSource;

import com.example.vardansharma.contact_app.data.models.Contact;

import io.reactivex.Observable;


/**
 * Created by vardansharma on 06/06/17.
 */

public class InMemoryDataSource implements DataSource {
    @Override
    public Observable<Contact> getAllContact() {
        throw new IllegalStateException("Not yet implemented");
    }
}
