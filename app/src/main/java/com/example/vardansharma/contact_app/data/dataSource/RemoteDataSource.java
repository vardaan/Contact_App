package com.example.vardansharma.contact_app.data.dataSource;

import com.example.vardansharma.contact_app.data.ContactsApiService;
import com.example.vardansharma.contact_app.data.models.Contact;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * Created by vardansharma on 06/06/17.
 */

public class RemoteDataSource implements DataSource {
    private final ContactsApiService contactsApiService;

    @Inject
    public RemoteDataSource(ContactsApiService contactsApiService) {
        this.contactsApiService = contactsApiService;
    }

    @Override
    public Observable<List<Contact>> getAllContact() {
         contactsApiService.getAllContacts();
        return null;
    }
}
