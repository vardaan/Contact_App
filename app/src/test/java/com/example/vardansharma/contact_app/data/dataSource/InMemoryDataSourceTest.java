package com.example.vardansharma.contact_app.data.dataSource;

import com.example.vardansharma.contact_app.TestContactData;
import com.example.vardansharma.contact_app.data.models.Contact;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InMemoryDataSourceTest {

    private InMemoryDataSource inMemoryDataSource;

    @Before
    public void setUp() throws Exception {
        inMemoryDataSource = new InMemoryDataSource();
    }


    @Test
    public void shouldHaveCorrectContactListWhenSingleContactIsAdded() throws Exception {
        inMemoryDataSource.addContact(TestContactData.angeline);

        List<Contact> contacts = inMemoryDataSource.getAllContact().blockingFirst();

        List<Contact> expectedContacts = new ArrayList<>();
        expectedContacts.add(TestContactData.angeline);
        assertEquals(contacts, expectedContacts);
    }


    @Test
    public void shouldShowFavoriteContactsInHigherPosition() throws Exception {
        inMemoryDataSource.addContact(TestContactData.angeline);// not so :(
        inMemoryDataSource.addContact(TestContactData.brad);// is favorite

        List<Contact> contacts = inMemoryDataSource.getAllContact().blockingFirst();

        assertEquals(contacts.get(0), TestContactData.brad);
        assertEquals(contacts.get(1), TestContactData.angeline);
    }

    @Test
    public void shouldDecideAlphaBeticallyIfBothContactsAreFavourite() {
        inMemoryDataSource.addContact(TestContactData.hugh);// also favorite
        inMemoryDataSource.addContact(TestContactData.brad);// is favorite

        List<Contact> contacts = inMemoryDataSource.getAllContact().blockingFirst();

        assertEquals(contacts.get(0), TestContactData.brad);
        assertEquals(contacts.get(1), TestContactData.hugh);
    }


    @Test
    public void shouldDecideAlphabeticallyIfBothBothContactsAreNotFavorite() {
        inMemoryDataSource.addContact(TestContactData.angeline);// not favorite
        inMemoryDataSource.addContact(TestContactData.vardan);// I am  also not favorite

        List<Contact> contacts = inMemoryDataSource.getAllContact().blockingFirst();

        assertEquals(contacts.get(0), TestContactData.angeline);
        assertEquals(contacts.get(1), TestContactData.vardan);
    }

    @Test
    public void shouldShowCorrectOrderAfterUpdatingAContact() {
        Contact angeline = TestContactData.angeline;
        inMemoryDataSource.addContact(angeline);// not so :(
        inMemoryDataSource.addContact(TestContactData.brad);// is favorite

        angeline.setFavorite(true);// fav angeline

        List<Contact> contacts = inMemoryDataSource.getAllContact().blockingFirst();


        inMemoryDataSource.updateSingleContact(angeline);
        assertEquals(contacts.get(0), TestContactData.angeline);
        assertEquals(contacts.get(1), TestContactData.brad);


    }
}