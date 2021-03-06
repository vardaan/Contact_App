package com.example.vardansharma.contact_app.ui.contactlist;

import com.example.vardansharma.contact_app.TestContactData;
import com.example.vardansharma.contact_app.TrampolineSchedulerRule;
import com.example.vardansharma.contact_app.data.dataSource.DataSource;
import com.example.vardansharma.contact_app.data.models.Contact;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Captor
    ArgumentCaptor<List<Contact>> captor;


    @Captor
    ArgumentCaptor<Contact> contactArgumentCaptor;


    @Mock
    private DataSource dataSource;

    @Rule
    public TrampolineSchedulerRule trampolineSchedulerRule = new TrampolineSchedulerRule();

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
        when(dataSource.getAllContact()).thenReturn(Observable.empty());

        presenter.getAllContacts();

        verify(screen).showLoading();
    }

    @Test
    public void shouldCallDataSourceWhenContactsAreFetched() {
        when(dataSource.getAllContact()).thenReturn(Observable.empty());
        presenter.getAllContacts();

        verify(dataSource).getAllContact();
    }

    @Test
    public void shouldHideLoadingInCaseOfDataFetchedSuccess() {
        when(dataSource.getAllContact()).thenReturn(Observable.just(TestContactData.getContactList()));

        presenter.getAllContacts();

        verify(screen).hideLoading();
    }

    @Test
    public void shouldHideLoadingInCaseOfFailure() {
        when(dataSource.getAllContact()).thenReturn(Observable.error(new Exception()));

        presenter.getAllContacts();

        verify(screen).hideLoading();
    }

    @Test
    public void shouldShowEmptyScreenInCaseOfEmptyData() {
        when(dataSource.getAllContact()).thenReturn(Observable.just(new ArrayList<>()));

        presenter.getAllContacts();

        verify(screen).showEmptyScreen();
    }

    @Test
    public void shouldDisplayDataInCaseOfSuccess() {
        final List<Contact> contactList = TestContactData.getContactList();

        when(dataSource.getAllContact()).thenReturn(Observable.just(contactList));

        presenter.getAllContacts();

        verify(screen, never()).showEmptyScreen();

        verify(screen).showData(captor.capture());

        final List<Contact> contacts = captor.getValue();

        Assert.assertEquals(contactList.size(), contacts.size());
    }

    @Test
    public void shouldShowErrorInCaseOfFailure() {
        when(dataSource.getAllContact()).thenReturn(Observable.error(new Exception()));

        presenter.getAllContacts();

//        TestObserver testObserver = dataSource.getAllContact().test();
//
//        testObserver.awaitTerminalEvent();

        verify(screen).showErrorMessage();

        verify(screen, never()).showNetworkError();
    }


    @Test
    public void shouldShowNetworkErrorInCaseOfNetworkFailure() {
        when(dataSource.getAllContact()).thenReturn(Observable.error(new IOException()));

        presenter.getAllContacts();

        verify(screen).showNetworkError();

        verify(screen, never()).showErrorMessage();

        verify(screen).showNetworkError();

    }

    @Test
    public void shouldGoToContactDetailWhenContactItemIsClicked() {
        Contact contact = TestContactData.vardan;
        presenter.onContactClicked(contact);

        verify(screen).launchContactDetail(contactArgumentCaptor.capture());
        Assert.assertEquals(contactArgumentCaptor.getValue(), contact);
    }

}