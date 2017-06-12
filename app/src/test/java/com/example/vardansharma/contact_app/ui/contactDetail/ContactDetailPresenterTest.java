package com.example.vardansharma.contact_app.ui.contactDetail;

import com.example.vardansharma.contact_app.data.dataSource.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by vardansharma on 12/06/17.
 */
public class ContactDetailPresenterTest {
    @Mock
    private DataSource dataSource;

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
        when(dataSource.getAllContact()).thenReturn(Observable.empty());

        presenter.getContactDetail("1");

        verify(screen).showLoading();
    }

    @After
    public void tearDown() throws Exception {

    }

}