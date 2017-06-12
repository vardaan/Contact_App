package com.example.vardansharma.contact_app.ui.contactDetail;

import com.example.vardansharma.contact_app.base.BasePresenter;
import com.example.vardansharma.contact_app.base.BaseScreen;
import com.example.vardansharma.contact_app.data.models.Contact;

/**
 * Created by vardansharma on 12/06/17.
 */

public interface ContactDetailContract {

    interface Screen extends BaseScreen {

        void showErrorMessage();

        void showNetworkError();

        void showContactDetail(Contact contact);

        void launchPhoneApp(String phoneNumber);
    }

    interface Presenter extends BasePresenter {

        void onPhoneButtonClicked(String phoneNum);
    }
}
