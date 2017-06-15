package com.example.vardansharma.contact_app.ui.addoreditcontact;

import com.example.vardansharma.contact_app.TestContactData;
import com.example.vardansharma.contact_app.TrampolineSchedulerRule;
import com.example.vardansharma.contact_app.data.dataSource.DataSource;
import com.example.vardansharma.contact_app.data.models.Contact;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddOrEditContactPresenterTest {
    @Mock
    private DataSource dataSource;

    @Rule
    public TrampolineSchedulerRule trampolineSchedulerRule = new TrampolineSchedulerRule();

    private static final String INVALID_LENGTH_FIRST_NAME = "ab";
    private static final String INVALID_LENGTH_PHONE_NUMBER = "9501168";
    private static final String INVALID_EMAIL_ADDRESS = "xxjs22";


    private static final String VALID_FIRST_NAME = "Angelina";
    private static final String VALID_PHONE_NUMBER = "9501168453";
    private static final String VALID_EMAIL_ADDRESS = "angelina@gmail.com";


    @Mock
    AddOrEditCotactContract.Screen screen;
    private AddOrEditContactPresenter presenter;

//    @BeforeClass
//    public static void setUpScheduler() {
//        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
//                __ -> Schedulers.trampoline());
//    }


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
        verify(screen, never()).showInvalidPhoneNumberError();
    }

    @Test
    public void shouldShowErrorInCaseOfInvalidEmail() throws Exception {
        presenter.onSubmit(VALID_FIRST_NAME, VALID_PHONE_NUMBER, INVALID_EMAIL_ADDRESS);

        verify(screen).showInvalidEmailError();
        verify(screen, never()).showInvalidFirstNameError();
        verify(screen, never()).showInvalidPhoneNumberError();
    }


    @Test
    public void shouldShowErrorInCaseOfInvalidPhoneNumber() throws Exception {
        presenter.onSubmit(VALID_FIRST_NAME, INVALID_LENGTH_PHONE_NUMBER, VALID_EMAIL_ADDRESS);

        verify(screen).showInvalidPhoneNumberError();
        verify(screen, never()).showInvalidFirstNameError();
        verify(screen, never()).showInvalidEmailError();

    }


    @Test
    public void shouldCallDataSourceToAddContactOnSubmit() throws Exception {
        when(dataSource.createContact(any(Contact.class))).thenReturn(Observable.just(TestContactData.bella));
        presenter.onSubmit(VALID_FIRST_NAME, VALID_PHONE_NUMBER, VALID_EMAIL_ADDRESS);

        verify(screen, never()).showInvalidFirstNameError();
        verify(screen, never()).showInvalidEmailError();
        verify(screen, never()).showInvalidPhoneNumberError();

        verify(dataSource).createContact(any(Contact.class));

    }

    @Test
    public void shouldShowErrorWhenErrorCreatingContact() {
        when(dataSource.createContact(any(Contact.class))).thenReturn(Observable.error(new Exception()));

        presenter.onSubmit(VALID_FIRST_NAME, VALID_PHONE_NUMBER, VALID_EMAIL_ADDRESS);
//        TestObserver testObserver = dataSource.createContact(TestContactData.angeline).test();
//        testObserver.awaitTerminalEvent();

        verify(screen).hideLoading();
        verify(screen).showContactFailToSaveError();

    }


    @After
    public void tearDown() throws Exception {
    }

}