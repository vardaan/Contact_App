package com.example.vardansharma.contact_app.ui.addoreditcontact;

import com.example.vardansharma.contact_app.data.dataSource.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class AddOrEditContactPresenterTest {
    @Mock
    private DataSource dataSource;


    public static final String INVALID_LENGTH_FIRST_NAME = "ab";
    public static final String INVALID_LENGTH_PHONE_NUMBER = "9501168";
    public static final String INVALID_EMAIL_ADDRESS = "xxjs22";


    public static final String VALID_FIRST_NAME = "Angelina";
    public static final String VALID_PHONE_NUMBER = "9501168453";
    public static final String VALID_EMAIL_ADDRESS = "angelina@gmail.com";


    @Mock
    AddOrEditCotactContract.Screen screen;
    private AddOrEditContactPresenter presenter;

    @BeforeClass
    public static void setUpScheduler() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                __ -> Schedulers.trampoline());
    }


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new AddOrEditContactPresenter(screen, dataSource);

    }

    @Test
    public void shouldShowErrorInCaseOfInvalidFirstName() throws Exception {
        presenter.onSubmit(INVALID_LENGTH_FIRST_NAME, VALID_PHONE_NUMBER, VALID_EMAIL_ADDRESS);

        verify(screen).showInvalidFirstNameError();
        verify(screen, never()).showInvalidEmailError();
    }

    @Test
    public void shouldShowErrorInCaseOfInvalidEmail() throws Exception {
        presenter.onSubmit(VALID_FIRST_NAME, VALID_PHONE_NUMBER, INVALID_EMAIL_ADDRESS);

        verify(screen).showInvalidEmailError();
        verify(screen, never()).showInvalidFirstNameError();
    }

    @After
    public void tearDown() throws Exception {
    }

}