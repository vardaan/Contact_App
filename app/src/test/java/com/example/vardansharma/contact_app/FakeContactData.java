package com.example.vardansharma.contact_app;

import com.example.vardansharma.contact_app.data.models.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vardansharma on 06/06/17.
 */

public class FakeContactData {
    public static final Contact angeline = new Contact.Builder()
            .favorite(false)
            .firstName("angeline")
            .lastName("jolie")
            .id(1).profilePic("")
            .build();
    public static final Contact brad = new Contact.Builder()
            .favorite(false)
            .firstName("brad")
            .lastName("pit")
            .id(2).profilePic("")
            .build();

    public static final Contact hugh = new Contact.Builder()
            .favorite(false)
            .firstName("hugh")
            .lastName("jackman")
            .id(3).profilePic("")
            .build();
    public static final Contact vardan = new Contact.Builder()
            .favorite(false)
            .firstName("vardan")
            .lastName("sharma")
            .id(4).profilePic("")
            .build();

    public static final Contact monica = new Contact.Builder()
            .favorite(false)
            .firstName("monica")
            .lastName("bulluci")
            .id(5).profilePic("")
            .build();

    public static List<Contact> getContactList() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(angeline);
        contacts.add(brad);
        contacts.add(hugh);
        contacts.add(vardan);
        contacts.add(monica);

        return contacts;
    }


}
