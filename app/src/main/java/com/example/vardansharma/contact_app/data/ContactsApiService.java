package com.example.vardansharma.contact_app.data;

import com.example.vardansharma.contact_app.data.models.Contact;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by vardansharma on 06/06/17.
 */

public interface ContactsApiService {

    @GET ("contacts.json")
    Observable<List<Contact>> getAllContacts();

    @GET ("contacts/{id}.json")
    Observable<Contact> getContactDetail(@Path ("id") String id);
}
