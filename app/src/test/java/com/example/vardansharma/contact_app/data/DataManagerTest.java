package com.example.vardansharma.contact_app.data;

import com.example.vardansharma.contact_app.data.dataSource.InMemoryDataSource;
import com.example.vardansharma.contact_app.data.dataSource.RemoteDataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

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

}