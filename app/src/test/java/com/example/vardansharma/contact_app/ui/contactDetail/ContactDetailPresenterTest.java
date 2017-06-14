package com.example.vardansharma.contact_app.ui.contactDetail;

import com.example.vardansharma.contact_app.TestContactData;
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
import static org.mockito.Matchers.anyBoolean;
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

    @Captor
    ArgumentCaptor<Boolean> booleanArgumentCaptor;

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
        when(dataSource.getContactDetails(anyString())).thenReturn(Observable.just(TestContactData.bella));

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
        when(dataSource.getContactDetails(contactId)).thenReturn(Observable.just(TestContactData.bella));

        presenter.getContactDetail(contactId);

        TestObserver testObserver = dataSource.getContactDetails(contactId).test();

        testObserver.awaitTerminalEvent();

        verify(screen).showContactDetail(contactArgumentCaptor.capture());

        assertEquals(contactArgumentCaptor.getValue(), TestContactData.bella);
    }


    @Test
    public void shouldLaunchPhoneAppWhenPhoneButtonIsClicked() throws Exception {
        final String phoneNum = "9501168453";
        presenter.onPhoneButtonClicked(phoneNum);

        verify(screen).launchPhoneApp(argumentCaptor.capture());
        assertEquals(phoneNum, argumentCaptor.getValue());

    }

    @Test
    public void shouldLaunchEmailAppWhenEmailButtonIsClicked() throws Exception {
        final String email = "vardaan1992sharma@gmail.com";
        presenter.onEmailButtonClicked(email);

        verify(screen).launchEmailApp(argumentCaptor.capture());
        assertEquals(email, argumentCaptor.getValue());

    }

    @Test
    public void shouldLaunchMessageAppWhenEmailButtonIsClicked() throws Exception {
        final String phoneNum = "9501168453";

        presenter.onMessageButtonClicked(phoneNum);

        verify(screen).launchMessageApp(argumentCaptor.capture());
        assertEquals(phoneNum, argumentCaptor.getValue());

    }

    @Test
    public void shouldCopyPhoneNumberToClipBoardOnLongPressPhoneNumber() {
        final String phoneNum = "9501168453";

        presenter.onPhoneNumberLongPress(phoneNum);

        verify(screen).copyToClipboard(argumentCaptor.capture());
        verify(screen).showCopyToClipBoardMessage();
        assertEquals(phoneNum, argumentCaptor.getValue());
    }

    @Test
    public void shouldCopyEmailToClipBoardOnLongPressEmail() {
        final String email = "vardaan1992sharma@gmail.com";

        presenter.onEmailLongPress(email);

        verify(screen).copyToClipboard(argumentCaptor.capture());
        verify(screen).showCopyToClipBoardMessage();
        assertEquals(email, argumentCaptor.getValue());
    }


    @Test
    public void shouldShareContactOnShareButtonClick() throws Exception {
        presenter.onShareButtonClicked();
        verify(screen).shareContact(TestContactData.vardan);

    }

    @Test
    public void shouldLaunchEditScreenOnEditButtonClick() {

        final String contactId = "1";
        final Contact monica = TestContactData.bella;
        when(dataSource.getContactDetails(contactId)).thenReturn(Observable.just(monica));

        presenter.getContactDetail(contactId);

        TestObserver testObserver = dataSource.getContactDetails(contactId).test();

        testObserver.awaitTerminalEvent();
        presenter.onEditButtonClicked();
        verify(screen).launchEditContactScreen(contactArgumentCaptor.capture());
        assertEquals(contactArgumentCaptor.getValue(), monica);
    }


    @Test
    public void shouldUpdateDataSourceWhenClickedOnFavouriteButton() {
        final String contactId = "1";
        final Contact bella = TestContactData.bella;
        when(dataSource.getContactDetails(contactId)).thenReturn(Observable.just(bella));
        presenter.onFavouriteButtonClicked();

        verify(dataSource).updateFavourite(argumentCaptor.capture(), booleanArgumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(), String.valueOf(bella.getId()));
        assertEquals(booleanArgumentCaptor.getValue(), true);

    }

    @Test
    public void shouldShowErrorMessageWhenUpdateFavouriteFails() {
        when(dataSource.getContactDetails(anyString())).thenReturn(Observable.just(TestContactData.bella));
        when(dataSource.updateFavourite(anyString(), anyBoolean())).thenReturn(Observable.error(new Exception()));

        presenter.getContactDetail("1");
        TestObserver testObserver = dataSource.getContactDetails(anyString()).test();

        testObserver.awaitTerminalEvent();

        presenter.onFavouriteButtonClicked();

        TestObserver testObserver1 = dataSource.updateFavourite("121", true).test();
        testObserver1.awaitTerminalEvent();

        verify(screen).showUnableToUpdateFavoriteError();
    }


    @Test
    public void shouldUpdateUIWhenUpdateFavouriteSuccedds() {
        when(dataSource.getContactDetails(anyString())).thenReturn(Observable.just(TestContactData.bella));
        when(dataSource.updateFavourite(anyString(), anyBoolean())).thenReturn(Observable.just(TestContactData.bella));

        presenter.getContactDetail("1");
        TestObserver testObserver = dataSource.getContactDetails(anyString()).test();

        testObserver.awaitTerminalEvent();

        presenter.onFavouriteButtonClicked();

        TestObserver testObserver1 = dataSource.updateFavourite("121", true).test();
        testObserver1.awaitTerminalEvent();

        verify(screen).updateFavourite(contactArgumentCaptor.capture());
        assertEquals(contactArgumentCaptor.getValue(), TestContactData.bella);
    }


//    @Test
//    public void shouldShowErrorMessageWhenUpdateFavouriteFails() {
//        when(dataSource.updateFavourite(anyString(), anyBoolean())).thenReturn(Observable.error(new Exception()));
//        presenter.onFavouriteButtonClicked(true);
//
//        verify(screen).showUnableToUpdateFavoriteError();
//    }


    @After
    public void tearDown() throws Exception {

    }

}