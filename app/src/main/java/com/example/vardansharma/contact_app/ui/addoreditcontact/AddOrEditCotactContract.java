package com.example.vardansharma.contact_app.ui.addoreditcontact;

import com.example.vardansharma.contact_app.base.BasePresenter;
import com.example.vardansharma.contact_app.base.BaseScreen;
import com.example.vardansharma.contact_app.data.models.Contact;

public interface AddOrEditCotactContract {

    interface Presenter extends BasePresenter {
        void onSubmit(String firstName, String phone, String email);
    }

    interface Screen extends BaseScreen {
        void showInvalidFirstNameError();

        void showInvalidEmailError();

        void hideAllErrors();

        void showInvalidPhoneNumberError();

        void showContactSavedMessage();

        void showContactFailToSaveError();

        void finishScreen();

        void prefillData(Contact contact);
    }
}
