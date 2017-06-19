package com.example.vardansharma.contact_app.data;

import com.example.vardansharma.contact_app.data.models.Contact;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by vardansharma on 06/06/17.
 */

public interface ContactsApiService {

    @GET ("contacts.json")
    Observable<List<Contact>> getAllContacts();

    @GET ("contacts/{id}.json")
    Observable<Contact> getContactDetail(@Path ("id") String id);

    @PUT ("contacts/{id}.json")
    Observable<Contact> updateFavourite(@Body UpdateFavouriteRequest updateFavourite,
                                        @Path ("id") String contactId);

    @POST ("contacts.json")
    Observable<Contact> createContact(@Body CreateUserRequest createUserRequest);

    @PUT ("contacts/{id}.json")
    Observable<Contact> updateContact(@Body UpdateUserRequest updateFavourite,
                                      @Path ("id") String contactId);
}
