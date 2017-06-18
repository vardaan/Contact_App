package com.example.vardansharma.contact_app.data.dataSource;

import android.text.TextUtils;

import com.example.vardansharma.contact_app.data.models.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;


/**
 * Created by vardansharma on 06/06/17.
 */

public class InMemoryDataSource implements DataSource {
    private List<Contact> contacts = new ArrayList<>();

    @Override
    public Observable<List<Contact>> getAllContact() {
        return Observable.just(contacts);
    }

    @Override
    public Observable<Contact> getContactDetails(String id) {
        for (Contact contact : contacts) {
            if (TextUtils.equals(String.valueOf(contact.getId()), id)) {
                return Observable.just(contact);
            }
        }
        throw new IllegalStateException("Not correct id");
    }

    @Override
    public Observable<Contact> updateFavourite(String contactId, boolean favourite) {
        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            if (TextUtils.equals(String.valueOf(contact.getId()), contactId)) {
                contact.setFavorite(favourite);
                contacts.set(i, contact);
                return Observable.just(contact);
            }
        }
        throw new IllegalStateException("Not found in the local cache");
    }

    @Override
    public Observable<Contact> createContact(Contact contact) {
        throw new IllegalStateException("not yet implemented");
    }

    public void updateContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public void updateSingleContact(Contact contact) {
        for (int i = 0; i < contacts.size(); i++) {
            Contact contact1 = contacts.get(i);
            if (contact.getId() == contact1.getId()) {
                contact.setHasFullContactDetails(true);
                contacts.set(i, contact);
                sortList();
            }
        }
    }

    private void sortList() {
        Collections.sort(contacts, (left, right) -> {
            if (left.isFavorite() && !right.isFavorite()) {
                return -1;
            }
            else if (!left.isFavorite() && right.isFavorite()) {
                return 1;
            }
            return left.getFirstName().compareTo(right.getFirstName());
        });
    }

    public boolean hasFullContactData(String id) {
        for (Contact contact : contacts) {
            if (TextUtils.equals(String.valueOf(contact.getId()), id)) {
                return contact.isHasFullContactDetails();
            }
        }
        return false;
    }

    public boolean hasData() {
        return contacts.size() > 0;
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        sortList();
    }
}
