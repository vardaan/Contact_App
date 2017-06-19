package com.example.vardansharma.contact_app.ui.addoreditcontact;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vardansharma.contact_app.ContactsApp;
import com.example.vardansharma.contact_app.R;
import com.example.vardansharma.contact_app.base.BaseActivity;
import com.example.vardansharma.contact_app.data.models.Contact;
import com.example.vardansharma.contact_app.utils.Utils;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddOrEditContactActivity extends BaseActivity implements AddOrEditCotactContract.Screen {

    public static final String EXTRA_CONTACT = "contact";

    @BindView (R.id.add_edit_contact_toolbar)
    Toolbar toolbar;
    @BindView (R.id.add_edit_contact_camera_btn)
    ImageView cameraBtn;
    @BindView (R.id.add_edit_contact_name)
    TextInputEditText name;
    @BindView (R.id.add_edit_contact_phone)
    TextInputEditText phone;
    @BindView (R.id.add_edit_contact_email)
    TextInputEditText email;
    @BindView (R.id.add_edit_contact_name_input_layout)
    TextInputLayout nameInputLayout;
    @BindView (R.id.add_edit_contact_phone_input_layout)
    TextInputLayout phoneInputLayout;
    @BindView (R.id.add_edit_contact_email_input_layout)
    TextInputLayout emailInputLayout;
    @BindView (R.id.add_edit_contact_image)
    ImageView contactImage;


    @Inject
    AddOrEditContactPresenter presenter;


    private AddOrEditContactComponent component;
    private ProgressDialog progressDialog;

    public static Intent createIntent(Context context, Contact contact) {
        Intent intent = new Intent(context, AddOrEditContactActivity.class);
        if (contact != null) {
            intent.putExtra(EXTRA_CONTACT, contact);
        }
        return intent;
    }

    public static Intent createIntent(Context context) {
        return createIntent(context, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initDI();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_contact);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        Contact contact = getIntent().getParcelableExtra(EXTRA_CONTACT);
        if (contact != null) {
            presenter.setContactData(contact);
        }

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        email.setOnEditorActionListener((v, keyCode, event) -> {
            switch (keyCode) {
                case EditorInfo.IME_ACTION_DONE:
                    sendDataToPresenter();
                    return true;
            }
            return false;
        });

        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void prefillData(Contact contact) {
        email.setText(contact.getEmail());
        name.setText(contact.getFirstName());
        phone.setText(contact.getPhoneNumber());
        Picasso.with(this)
                .load(Utils.getProfileUrl(contact.getProfilePic()))
                .placeholder(R.drawable.ic_profile_placeholder)
                .error(R.drawable.ic_profile_placeholder)
                .into(contactImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_or_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_done) {
            sendDataToPresenter();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendDataToPresenter() {
        final String userName = name.getText().toString();
        final String userEmail = email.getText().toString();
        final String userPhone = phone.getText().toString();
        presenter.onSubmit(userName, userPhone, userEmail);
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
    public void showLoading() {
        progressDialog.setMessage(getString(R.string.saving_contact));
        progressDialog.show();
    }


    @Override
    public void hideLoading() {
        progressDialog.hide();
    }

    @Override
    public void showInvalidFirstNameError() {
        nameInputLayout.setError(getString(R.string.error_msg_name_invalid));
    }

    @Override
    public void showInvalidEmailError() {
        emailInputLayout.setError(getString(R.string.error_msg_email_invalid));
    }

    @Override
    public void hideAllErrors() {
        emailInputLayout.setError(null);
        phoneInputLayout.setError(null);
        nameInputLayout.setError(null);
    }

    @Override
    public void showInvalidPhoneNumberError() {
        phoneInputLayout.setError(getString(R.string.error_msg_phone_invalid));
    }

    @Override
    public void showContactSavedMessage() {
        Toast.makeText(this, R.string.contacts_saved_successfully, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showContactFailToSaveError() {
        Toast.makeText(this, R.string.error_msg_unknown_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishScreen() {
        this.onBackPressed();
    }
}
