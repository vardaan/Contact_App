package com.example.vardansharma.contact_app.ui.contactlist;

import com.example.vardansharma.contact_app.base.BasePresenter;
import com.example.vardansharma.contact_app.base.BaseScreen;
import com.example.vardansharma.contact_app.data.models.Contact;

import java.util.List;

/**
 * Created by vardansharma on 06/06/17.
 */

public interface ContactListContract {

    interface Screen extends BaseScreen {

        void showEmptyScreen();

        void showData(List<Contact> contacts);

        void showErrorScreen();
    }

    interface Presenter extends BasePresenter {

        void getAllContacts();

    }
}
