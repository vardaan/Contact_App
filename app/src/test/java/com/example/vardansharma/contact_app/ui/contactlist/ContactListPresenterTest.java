package com.example.vardansharma.contact_app.ui.contactlist;

import com.example.vardansharma.contact_app.data.dataSource.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Created by vardansharma on 06/06/17.
 */
public class ContactListPresenterTest {

    /**
     * Contacts are fetched from the API
     * Favorites are returned first in alphabetical order
     * Rest of the Contacts are returned in alphabetical Order
     * Locally cache (Database/Custom cache) the contacts retrieved
     * For performance reason, invalidate the cache only on adding any new contact
     * On Editing any contact (favorites, name etc.) update cache
     * On loading the contacts for first time, show a non-cancellable progress bar with message “Please wait”
     * On Network Error, show an Alert Dialog with Title and Message “Network Error”, “Unable to contact the server” respectively
     * If there are no contacts on server, display a message “No Contacts Found” instead of the empty list page
     */


    @Mock
    private DataSource dataSource;

    @Mock
    ContactListContract.Screen screen;
    private ContactListPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new ContactListPresenter(dataSource, screen);
    }

    @Test
    public void shouldShowLoadingWhenContactsAreFetched() {
        presenter.getAllContacts();

        Mockito.verify(screen).showLoading();
    }

    @After
    public void tearDown() throws Exception {

    }

}