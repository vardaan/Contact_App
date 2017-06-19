package com.example.vardansharma.contact_app.data;

import com.example.vardansharma.contact_app.TestContactData;
import com.example.vardansharma.contact_app.TrampolineSchedulerRule;
import com.example.vardansharma.contact_app.data.dataSource.InMemoryDataSource;
import com.example.vardansharma.contact_app.data.dataSource.RemoteDataSource;
import com.example.vardansharma.contact_app.data.models.Contact;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by vardansharma on 07/06/17.
 */
public class DataManagerTest {

    @Mock
    InMemoryDataSource inMemoryDataSource;

    @Mock
    RemoteDataSource remoteDataSource;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @Captor
    ArgumentCaptor<Boolean> booleanArgumentCaptor;

    @Captor
    ArgumentCaptor<Contact> contactArgumentCaptor;

    private DataManager dataManager;

    @Rule
    public TrampolineSchedulerRule trampolineSchedulerRule = new TrampolineSchedulerRule();


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        dataManager = new DataManager(inMemoryDataSource, remoteDataSource);
    }


    @Test
    public void shouldGetContactListFromInMemoryIfInMemoryHasData() {
        when(inMemoryDataSource.hasData()).thenReturn(true);

        dataManager.getAllContact();

        verify(inMemoryDataSource).getAllContact();
    }


    @Test
    public void shouldGetContactListFromRemoteIfInMemoryIsEmpty() {
        when(inMemoryDataSource.hasData()).thenReturn(false);
        when(remoteDataSource.getAllContact()).thenReturn(Observable.just(TestContactData.getContactList()));
        final TestObserver<List<Contact>> test = dataManager.getAllContact().test();
        test.awaitTerminalEvent();


        verify(remoteDataSource).getAllContact();
        verify(inMemoryDataSource).updateContacts(anyList());
    }

    @Test
    public void shouldGetContactDetailsFromInMemoryIfInMemoryHasData() {
        final String id = "1";
        when(inMemoryDataSource.hasFullContactData(id)).thenReturn(true);
        when(inMemoryDataSource.getContactDetails(id)).thenReturn(Observable.just(TestContactData.angeline));

        final TestObserver<Contact> testObserver = dataManager.getContactDetails(id).test();
        testObserver.awaitTerminalEvent();

        verify(inMemoryDataSource).getContactDetails(stringArgumentCaptor.capture());
        Assert.assertEquals(stringArgumentCaptor.getValue(), id);
    }

    @Test
    public void shouldUpdateBothDataSourceWhenUpdateFavoriteIsCalled() {
        final String id = "1";
        when(remoteDataSource.updateFavourite(anyString(), anyBoolean())).thenReturn(Observable.just(TestContactData.angeline));

        final TestObserver<Contact> testObserver = dataManager.updateFavourite(id, false).test();
        testObserver.awaitTerminalEvent();

        testObserver.assertNoErrors();
        testObserver.assertValue(TestContactData.angeline);


        verify(inMemoryDataSource).updateSingleContact(contactArgumentCaptor.capture());
        Assert.assertEquals(TestContactData.angeline, contactArgumentCaptor.getValue());

        verify(remoteDataSource).updateFavourite(stringArgumentCaptor.capture(), booleanArgumentCaptor.capture());
        Assert.assertEquals(booleanArgumentCaptor.getValue(), false);
        Assert.assertEquals(stringArgumentCaptor.getValue(), id);

    }

    @Test
    public void shouldCallRemoteRepoWhenCreateContactIsCalled() throws Exception {
        when(remoteDataSource.createContact(any())).thenReturn(Observable.just(TestContactData.angeline));

        final TestObserver<Contact> testObserver = dataManager.createContact(TestContactData.angeline).test();
        testObserver.awaitTerminalEvent();

        verify(remoteDataSource).createContact(contactArgumentCaptor.capture());
        Assert.assertEquals(TestContactData.angeline, contactArgumentCaptor.getValue());

        verify(inMemoryDataSource).addContact(contactArgumentCaptor.capture());
        Assert.assertEquals(TestContactData.angeline, contactArgumentCaptor.getValue());
    }

    @Test
    public void shouldCallRemoteRepoWhenUpdateContactIsCalled() throws Exception {
        when(remoteDataSource.updateContact(any())).thenReturn(Observable.just(TestContactData.angeline));

        final TestObserver<Contact> testObserver = dataManager.updateContact(TestContactData.angeline).test();
        testObserver.awaitTerminalEvent();

        verify(remoteDataSource).updateContact(contactArgumentCaptor.capture());
        Assert.assertEquals(TestContactData.angeline, contactArgumentCaptor.getValue());

        verify(inMemoryDataSource).updateContact(contactArgumentCaptor.capture());
        Assert.assertEquals(TestContactData.angeline, contactArgumentCaptor.getValue());
    }
}