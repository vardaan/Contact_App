package com.example.vardansharma.contact_app.data;

import com.example.vardansharma.contact_app.TestContactData;
import com.example.vardansharma.contact_app.data.dataSource.InMemoryDataSource;
import com.example.vardansharma.contact_app.data.dataSource.RemoteDataSource;
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
public class DataManagerTest {

    @Mock
    InMemoryDataSource inMemoryDataSource;

    @Mock
    RemoteDataSource remoteDataSource;

    private DataManager dataManager;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        dataManager = new DataManager(inMemoryDataSource, remoteDataSource);
    }


    @Test
    public void shouldInvokeRemoteRepoWhenContactListIsRequested() {
        dataManager.getAllContact();

        verify(remoteDataSource).getAllContact();
    }

    @Test
    public void shouldReturnCorrectDataWhenContactListIsRequested() {
        dataManager.getAllContact();

        final Observable<List<Contact>> value = Observable.just(TestContactData.getContactList());
        when(remoteDataSource.getAllContact()).thenReturn(value);

        assertEquals(dataManager.getAllContact(), value);
    }

}