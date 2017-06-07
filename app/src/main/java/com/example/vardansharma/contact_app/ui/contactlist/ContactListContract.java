package com.example.vardansharma.contact_app.ui.contactlist;

import com.example.vardansharma.contact_app.base.BasePresenter;
import com.example.vardansharma.contact_app.base.BaseScreen;

/**
 * Created by vardansharma on 06/06/17.
 */

public interface ContactListContract {

    interface Screen extends BaseScreen {

        void showEmptyScreen();
    }

    interface Presenter extends BasePresenter {

    }
}
