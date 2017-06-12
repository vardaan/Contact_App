package com.example.vardansharma.contact_app.ui.contactDetail;

import com.example.vardansharma.contact_app.FakeContactData;
import com.example.vardansharma.contact_app.data.dataSource.DataSource;
import com.example.vardansharma.contact_app.data.models.Contact;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by vardansharma on 12/06/17.
 */
public class ContactDetailPresenterTest {
    @Mock
    private DataSource dataSource;

    @Captor
    ArgumentCaptor<String> argumentCaptor;

    @Captor
    ArgumentCaptor<Contact> contactArgumentCaptor;

    @Mock
    ContactDetailContract.Screen screen;
    private ContactDetailPresenter presenter;

    @BeforeClass
    public static void setUpScheduler() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                __ -> Schedulers.trampoline());
    }


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new ContactDetailPresenter(screen, dataSource);

    }

    @Test
    public void shouldShowLoadingWhenContactDetailIsFetched() {
        when(dataSource.getContactDetails(anyString())).thenReturn(Observable.empty());

        presenter.getContactDetail("1");

        verify(screen).showLoading();
    }

    @Test
    public void shouldCallDataSourceWhenContactsDetailsFetched() {
        when(dataSource.getContactDetails(anyString())).thenReturn(Observable.empty());
        final String contactId = "1";
        presenter.getContactDetail(contactId);

        verify(dataSource).getContactDetails(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(), contactId);
    }


    @Test
    public void shouldHideLoadingInCaseOfDataFetchedSuccess() {
        when(dataSource.getContactDetails(anyString())).thenReturn(Observable.just(FakeContactData.monica));

        presenter.getContactDetail("1");

        TestObserver testObserver = dataSource.getContactDetails("1").test();

        testObserver.awaitTerminalEvent();

        verify(screen).hideLoading();
    }

    @Test
    public void shouldHideLoadingInCaseOfFailure() {
        when(dataSource.getContactDetails(anyString())).thenReturn(Observable.error(new Exception()));

        presenter.getContactDetail("1");

        TestObserver testObserver = dataSource.getContactDetails("1").test();

        testObserver.awaitTerminalEvent();

        verify(screen).hideLoading();
    }


    @Test
    public void shouldShowErrorInCaseOfFailure() {
        when(dataSource.getContactDetails(anyString())).thenReturn(Observable.error(new Exception()));

        presenter.getContactDetail("1");

        TestObserver testObserver = dataSource.getContactDetails("1").test();

        testObserver.awaitTerminalEvent();

        verify(screen).showErrorMessage();

        verify(screen, never()).showNetworkError();
    }


    @Test
    public void shouldShowNetworkErrorInCaseOfNetworkFailure() {
        when(dataSource.getContactDetails(anyString())).thenReturn(Observable.error(new IOException()));

        presenter.getContactDetail("1");

        TestObserver testObserver = dataSource.getContactDetails("1").test();

        testObserver.awaitTerminalEvent();

        verify(screen).showNetworkError();

        verify(screen, never()).showErrorMessage();

        verify(screen).showNetworkError();

    }

    @Test
    public void shouldShowContactDataInCaseOfSuccess() throws Exception {
        final String contactId = "1";
        when(dataSource.getContactDetails(contactId)).thenReturn(Observable.just(FakeContactData.monica));

        presenter.getContactDetail(contactId);

        TestObserver testObserver = dataSource.getContactDetails(contactId).test();

        testObserver.awaitTerminalEvent();

        verify(screen).showContactDetail(contactArgumentCaptor.capture());

        assertEquals(contactArgumentCaptor.getValue(), FakeContactData.monica);
    }


    @Test
    public void shouldLaunchPhoneAppWhenPhoneButtonIsClicked() throws Exception {
        final String phoneNum = "9501168453";
        presenter.onPhoneButtonClicked(phoneNum);

        verify(screen).launchPhoneApp(argumentCaptor.capture());
        assertEquals(phoneNum, argumentCaptor.getValue());

    }

    @After
    public void tearDown() throws Exception {

    }

}