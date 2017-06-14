package com.example.vardansharma.contact_app;

import com.example.vardansharma.contact_app.data.models.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vardansharma on 06/06/17.
 */

public class FakeContactData {

    public static List<Contact> EMPTY_CONTACTS = new ArrayList<>();
    public static final Contact angeline = new Contact.Builder()
            .favorite(false)
            .firstName("angeline")
            .lastName("jolie")
            .id(1)
            .email("angeline@gmail.com")
            .profilePic("")
            .phoneNumber("9612121212")
            .url("")
            .build();
    public static final Contact brad = new Contact.Builder()
            .favorite(false)
            .firstName("brad")
            .lastName("pit")
            .id(2)
            .url("")
            .profilePic("")
            .build();

    public static final Contact hugh = new Contact.Builder()
            .favorite(false)
            .firstName("hugh")
            .lastName("jackman")
            .id(3)
            .url("")
            .profilePic("")
            .build();
    public static final Contact vardan = new Contact.Builder()
            .favorite(false)
            .firstName("vardan")
            .lastName("sharma")
            .id(4)
            .url("")
            .profilePic("")
            .build();

    public static final Contact bella = new Contact.Builder()
            .favorite(false)
            .firstName("bella")
            .lastName("bulluci")
            .id(5)
            .profilePic("")
            .url("")
            .build();

    public static List<Contact> getContactList() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(angeline);
        contacts.add(brad);
        contacts.add(hugh);
        contacts.add(vardan);
        contacts.add(bella);

        return contacts;
    }


}
