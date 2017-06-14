package com.example.vardansharma.contact_app.ui.addoreditcontact;

import com.example.vardansharma.contact_app.data.dataSource.DataSource;

class AddOrEditContactPresenter implements AddOrEditCotactContract.Presenter {
    private static final int VALID_FIRST_NAME_LENGTH = 3;
    private final DataSource dataSource;
    private final AddOrEditCotactContract.Screen screen;

    public AddOrEditContactPresenter(AddOrEditCotactContract.Screen screen, DataSource dataSource) {
        this.screen = screen;
        this.dataSource = dataSource;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void onSubmit(String firstName, String phone, String email) {
        if (firstName == null || firstName.trim().length() <= VALID_FIRST_NAME_LENGTH) {
            screen.showInvalidFirstNameError();
        }

    }
}
