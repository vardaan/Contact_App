package com.example.vardansharma.contact_app.data.dataSource;

import com.example.vardansharma.contact_app.data.ContactsApiService;
import com.example.vardansharma.contact_app.data.CreateUserRequest;
import com.example.vardansharma.contact_app.data.UpdateFavouriteRequest;
import com.example.vardansharma.contact_app.data.UpdateUserRequest;
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
        return contactsApiService.getAllContacts();
    }

    @Override
    public Observable<Contact> getContactDetails(String id) {
        return contactsApiService.getContactDetail(id);
    }

    @Override
    public Observable<Contact> updateFavourite(String contactId, boolean favourite) {
        return contactsApiService.updateFavourite(new UpdateFavouriteRequest(favourite), contactId);
    }

    @Override
    public Observable<Contact> createContact(Contact contact) {
        final CreateUserRequest createUserRequest = new CreateUserRequest.Builder()
                .email(contact.getEmail())
                .firstName(contact.getFirstName())
                .lastName(contact.getFirstName())
                .phoneNumber(contact.getPhoneNumber())
                .build();
        return contactsApiService.createContact(createUserRequest);
    }

    @Override
    public Observable<Contact> updateContact(Contact contact) {
        final UpdateUserRequest updateUserRequest = new UpdateUserRequest.Builder()
                .email(contact.getEmail())
                .firstName(contact.getFirstName())
                .lastName(contact.getFirstName())
                .phoneNumber(contact.getPhoneNumber())
                .build();

        return contactsApiService.updateContact(updateUserRequest, String.valueOf(contact.getId()));
    }

}
