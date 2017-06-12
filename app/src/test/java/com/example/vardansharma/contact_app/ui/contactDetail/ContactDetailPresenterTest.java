package com.example.vardansharma.contact_app.ui.contactDetail;

import com.example.vardansharma.contact_app.FakeContactData;
import com.example.vardansharma.contact_app.data.dataSource.DataSource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Matchers.anyString;
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
        Assert.assertEquals(argumentCaptor.getValue(), contactId);
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

    @After
    public void tearDown() throws Exception {

    }

}