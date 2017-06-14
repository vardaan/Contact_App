package com.example.vardansharma.contact_app.ui.addoreditcontact;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.vardansharma.contact_app.ContactsApp;
import com.example.vardansharma.contact_app.R;
import com.example.vardansharma.contact_app.data.models.Contact;

public class AddOrEditContactActivity extends AppCompatActivity implements AddOrEditCotactContract.Screen {

    public static final String EXTRA_CONTACT = "contact";
    private AddOrEditContactComponent component;

    public static Intent createIntent(Context context, Contact contact) {
        Intent intent = new Intent(context, AddOrEditContactActivity.class);
        intent.putExtra(EXTRA_CONTACT, contact);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initDI();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_contact);
    }

    private void initDI() {
        ContactsApp contactApp = (ContactsApp) getApplication();
        component = DaggerAddOrEditContactComponent.builder()
                .applicationComponent(contactApp.getAppComponent())
                .addOrEditContactModule(new AddOrEditContactModule(this))
                .build();
        component.inject(this);
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
}
