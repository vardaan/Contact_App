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

        void launchEmailApp(String email);

        void launchMessageApp(String capture);

        void copyToClipboard(String input);

        void showCopyToClipBoardMessage();

        void shareContact(Contact contact);

        void launchEditContactScreen(Contact contact);
    }

    interface Presenter extends BasePresenter {

        void getContactDetail(String contactId);

        void onPhoneButtonClicked(String phoneNum);

        void onEmailButtonClicked(String email);

        void onMessageButtonClicked(String phoneNum);

        void onPhoneNumberLongPress(String phoneNum);

        void onEmailLongPress(String email);

        void onShareButtonClicked();

        void onEditButtonClicked();
    }
}
