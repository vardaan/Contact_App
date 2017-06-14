package com.example.vardansharma.contact_app.ui.addoreditcontact;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.vardansharma.contact_app.ContactsApp;
import com.example.vardansharma.contact_app.R;
import com.example.vardansharma.contact_app.base.BaseActivity;
import com.example.vardansharma.contact_app.data.models.Contact;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddOrEditContactActivity extends BaseActivity implements AddOrEditCotactContract.Screen {

    public static final String EXTRA_CONTACT = "contact";
    @BindView(R.id.add_edit_contact_toolbar)
    Toolbar toolbar;
    @BindView(R.id.add_edit_contact_camera_btn)
    ImageView cameraBtn;
    @BindView(R.id.add_edit_contact_name)
    TextInputEditText name;
    @BindView(R.id.add_edit_contact_phone)
    TextInputEditText phone;
    @BindView(R.id.add_edit_contact_email)
    TextInputEditText email;
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
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_or_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_done) {
            //do something here
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void showInvalidPhoneNumberError() {

    }
}
