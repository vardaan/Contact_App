package com.example.vardansharma.contact_app.ui.addoreditcontact;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.vardansharma.contact_app.ContactsApp;
import com.example.vardansharma.contact_app.R;
import com.example.vardansharma.contact_app.base.BaseActivity;
import com.example.vardansharma.contact_app.data.models.Contact;

public class AddOrEditContactActivity extends BaseActivity implements AddOrEditCotactContract.Screen {

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        component = null;
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
    public void showInvalidFirstNameError() {

    }

    @Override
    public void showInvalidEmailError() {

    }
}
