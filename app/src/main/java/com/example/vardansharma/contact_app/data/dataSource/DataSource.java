package com.example.vardansharma.contact_app.data.dataSource;

import com.example.vardansharma.contact_app.data.models.Contact;

import rx.Observable;

/**
 * Created by vardansharma on 06/06/17.
 */

public interface DataSource {
    Observable<Contact> getAllContact();
}
