package com.example.vardansharma.contact_app.ui.contactDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.vardansharma.contact_app.ContactsApp;
import com.example.vardansharma.contact_app.R;
import com.example.vardansharma.contact_app.data.models.Contact;

import javax.inject.Inject;

public class ContactDetailActivity extends AppCompatActivity implements ContactDetailContract.Screen {

    private static final String EXTRA_CONTACT = "contact";

    @Inject
    ContactDetailPresenter contactDetailPresenter;
    private ContactDetailComponent component;

    public static Intent createIntent(Context context, Contact contact) {
        Intent intent = new Intent(context, ContactDetailActivity.class);
        intent.putExtra(EXTRA_CONTACT, contact);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initDI();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
    }

    private void initDI() {
        ContactsApp contactApp = (ContactsApp) getApplication();
        contactApp.getAppComponent();

        component = DaggerContactDetailComponent.builder()
                .applicationComponent(contactApp.getAppComponent())
                .contactDetailModule(new ContactDetailModule(this))
                .build();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        component = null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showErrorMessage() {

    }

    @Override
    public void showNetworkError() {

    }

    @Override
    public void showContactDetail(Contact contact) {

    }

    @Override
    public void launchPhoneApp(String phoneNumber) {

    }

    @Override
    public void launchEmailApp(String email) {

    }

    @Override
    public void launchMessageApp(String capture) {
    }

}
