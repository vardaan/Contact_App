package com.example.vardansharma.contact_app.ui.contactDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vardansharma.contact_app.ContactsApp;
import com.example.vardansharma.contact_app.R;
import com.example.vardansharma.contact_app.data.models.Contact;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactDetailActivity extends AppCompatActivity implements ContactDetailContract.Screen {

    private static final String EXTRA_CONTACT = "contact";

    @Inject
    ContactDetailPresenter contactDetailPresenter;
    @BindView (R.id.contact_detail_toolbar)
    Toolbar toolbar;
    @BindView (R.id.contact_detail_user_image)
    ImageView userImage;
    @BindView (R.id.contact_detail_phone_btn)
    ImageView phoneBtn;
    @BindView (R.id.contact_detail_phone_num_text)
    TextView phoneNumText;
    @BindView (R.id.contact_detail_message_btn)
    ImageView messageBtn;
    @BindView (R.id.contact_detail_email_btn)
    ImageView emailBtn;
    @BindView (R.id.contact_detail_email_text)
    TextView emailText;
    @BindView (R.id.contact_detail_user_name)
    TextView userName;
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
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_detail_menu, menu);
        return true;
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

    @Override
    public void copyToClipboard(String input) {

    }

    @Override
    public void showCopyToKeyBoardMessage() {

    }

    @Override
    public void shareContact(Contact contact) {

    }

}
