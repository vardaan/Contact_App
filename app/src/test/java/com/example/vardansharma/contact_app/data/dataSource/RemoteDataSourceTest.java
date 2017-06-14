package com.example.vardansharma.contact_app.data.dataSource;

import com.example.vardansharma.contact_app.TestContactData;
import com.example.vardansharma.contact_app.data.ContactsApiService;
import com.example.vardansharma.contact_app.data.models.Contact;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Observable;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by vardansharma on 07/06/17.
 */
public class RemoteDataSourceTest {

    @Mock
    ContactsApiService contactsApiService;
    private RemoteDataSource remoteDataSource;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        remoteDataSource = new RemoteDataSource(contactsApiService);
    }


    @Test
    public void shouldCallContactsApiWhenDataRequested() {
        remoteDataSource.getAllContact();
        verify(contactsApiService).getAllContacts();
    }

    @Test
    public void shouldReturnCorrectContactsWhenDataRequested() {
        remoteDataSource.getAllContact();

        final List<Contact> contactList = TestContactData.getContactList();

        final Observable<List<Contact>> value = Observable.just(contactList);
        when(contactsApiService.getAllContacts()).thenReturn(value);

        assertEquals(remoteDataSource.getAllContact(), value);
    }

}