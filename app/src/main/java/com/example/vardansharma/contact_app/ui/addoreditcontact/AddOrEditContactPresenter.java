package com.example.vardansharma.contact_app.ui.addoreditcontact;

import com.example.vardansharma.contact_app.data.dataSource.DataSource;
import com.example.vardansharma.contact_app.data.models.Contact;

import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

class AddOrEditContactPresenter implements AddOrEditCotactContract.Presenter {
    private static final int VALID_FIRST_NAME_LENGTH = 3;
    private static final int VALID_PHONE_NUMBER_LENGTH = 9;
    private final DataSource dataSource;
    private final AddOrEditCotactContract.Screen screen;
    private CompositeDisposable compositeDisposable;
    private Contact previousContactData;


    @Inject
    public AddOrEditContactPresenter(AddOrEditCotactContract.Screen screen, DataSource dataSource) {
        this.screen = screen;
        this.dataSource = dataSource;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView() {

    }

    @Override
    public void detachView() {
        compositeDisposable.clear();
    }

    @Override
    public void onSubmit(String firstName, String phone, String email) {
        screen.hideAllErrors();
        if (firstName == null || firstName.trim().length() <= VALID_FIRST_NAME_LENGTH) {
            screen.showInvalidFirstNameError();
        }
        else if (!isValidEmail(email)) {// let's keep this simple for now not using android pattern
            screen.showInvalidEmailError();
        }
        else if (phone == null || phone.trim().length() <= VALID_PHONE_NUMBER_LENGTH) {
            screen.showInvalidPhoneNumberError();
        }
        else {
            updateOrAddContact(firstName, phone, email);
        }
    }

    private void updateOrAddContact(String firstName, String phone, String email) {
        final Contact contact;
        if (previousContactData != null) {
            previousContactData.setFirstName(firstName);
            previousContactData.setPhoneNumber(phone);
            previousContactData.setEmail(email);
            contact = previousContactData;
        }
        else {
            contact = new Contact.Builder()
                    .phoneNumber(phone)
                    .email(email)
                    .firstName(firstName)
                    .build();
        }

        screen.showLoading();

        final Observable<Contact> createOrUpdateContact
                = (previousContactData != null) ? dataSource.updateContact(contact) : dataSource.createContact(contact);

        compositeDisposable.add(createOrUpdateContact
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Contact>() {
                    @Override
                    public void onNext(Contact contact) {
                        handleContactAddOrUpdateSuccess();

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleContactAddOrUpdateError(e);
                    }

                    @Override
                    public void onComplete() {
                        Timber.i("hello");
                    }
                }));
    }

    private void handleContactAddOrUpdateError(Throwable e) {
        Timber.e(e);
        screen.hideLoading();
        screen.showContactFailToSaveError();
    }

    private void handleContactAddOrUpdateSuccess() {
        screen.showContactSavedMessage();
        screen.hideLoading();
        screen.finishScreen();
    }

    private static boolean isValidEmail(CharSequence target) {
        return target != null && EMAIL_ADDRESS.matcher(target).matches();
    }

    private static final Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public void setContactData(Contact contact) {
        this.previousContactData = contact;
        screen.prefillData(contact);
    }
}
