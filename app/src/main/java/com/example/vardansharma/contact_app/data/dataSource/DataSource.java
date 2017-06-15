package com.example.vardansharma.contact_app.data.dataSource;

import com.example.vardansharma.contact_app.data.models.Contact;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by vardansharma on 06/06/17.
 */

public interface DataSource {
    Observable<List<Contact>> getAllContact();

    Observable<Contact> getContactDetails(String id);

    Observable<Contact> updateFavourite(String contactId, boolean favourite);

    Observable<Contact> createContact(Contact contact);
}
