package com.example.vardansharma.contact_app.data.dataSource;

import com.example.vardansharma.contact_app.data.ContactsApiService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

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

}