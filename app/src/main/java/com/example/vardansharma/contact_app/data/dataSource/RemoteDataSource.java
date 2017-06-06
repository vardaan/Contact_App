package com.example.vardansharma.contact_app.data.dataSource;

import com.example.vardansharma.contact_app.data.ContactsApiService;
import com.example.vardansharma.contact_app.data.models.Contact;

import rx.Observable;

/**
 * Created by vardansharma on 06/06/17.
 */

public class RemoteDataSource implements DataSource {
    private final ContactsApiService contactsApiService;

    public RemoteDataSource(ContactsApiService contactsApiService) {
        this.contactsApiService = contactsApiService;
    }

    @Override
    public Observable<Contact> getAllContact() {
        throw new IllegalStateException("not yet implemented");
    }
}
