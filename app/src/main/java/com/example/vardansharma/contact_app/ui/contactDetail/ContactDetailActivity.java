package com.example.vardansharma.contact_app.ui.contactDetail;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vardansharma.contact_app.ContactsApp;
import com.example.vardansharma.contact_app.R;
import com.example.vardansharma.contact_app.base.BaseActivity;
import com.example.vardansharma.contact_app.data.models.Contact;
import com.example.vardansharma.contact_app.ui.addoreditcontact.AddOrEditContactActivity;
import com.example.vardansharma.contact_app.utils.Utils;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactDetailActivity extends BaseActivity implements ContactDetailContract.Screen {

    private static final String EXTRA_CONTACT = "contact";

    @Inject
    ContactDetailPresenter contactDetailPresenter;
    @BindView(R.id.contact_detail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.contact_detail_user_image)
    ImageView userImage;
    @BindView(R.id.contact_detail_phone_btn)
    ImageView phoneBtn;
    @BindView(R.id.contact_detail_phone_num_text)
    TextView phoneNumText;
    @BindView(R.id.contact_detail_message_btn)
    ImageView messageBtn;
    @BindView(R.id.contact_detail_email_btn)
    ImageView emailBtn;
    @BindView(R.id.contact_detail_email_text)
    TextView emailText;
    @BindView(R.id.contact_detail_user_name)
    TextView userName;
    @BindView(R.id.contact_detail_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.user_info_container)
    CardView infoContainer;
    private ContactDetailComponent component;
    private Menu menu;
    private boolean isFavourite;

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
        infoContainer.setVisibility(View.GONE);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setTitle("");
        getSupportActionBar().setTitle("");
        contactDetailPresenter.attachView();

        final Contact contact = getIntent().getParcelableExtra(EXTRA_CONTACT);
        contactDetailPresenter.getContactDetail(String.valueOf(contact.getId()));


    }

    private void initDI() {
        ContactsApp contactApp = (ContactsApp) getApplication();
        contactApp.getAppComponent();

        component = DaggerContactDetailComponent.builder()
                .applicationComponent(contactApp.getAppComponent())
                .contactDetailModule(new ContactDetailModule(this))
                .build();
        component.inject(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_detail_menu, menu);
        @DrawableRes int favouriteIcon = (isFavourite) ?
                R.drawable.ic_start_white : R.drawable.ic_star_border;
        menu.getItem(0).setIcon(favouriteIcon);
        this.menu = menu;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                contactDetailPresenter.onShareButtonClicked();
                break;
            case R.id.action_edit:
                contactDetailPresenter.onEditButtonClicked();
                break;
            case R.id.action_favourite:
                contactDetailPresenter.onFavouriteButtonClicked();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        component = null;
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(this, R.string.error_msg_unable_to_contact, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(this, R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showContactDetail(Contact contact) {
        infoContainer.setVisibility(View.VISIBLE);
        phoneNumText.setText(contact.getPhoneNumber());
        emailText.setText(contact.getEmail());
        userName.setText(Utils.getDisplayName(contact));
        Picasso.with(this)
                .load(Utils.getProfileUrl(contact.getProfilePic()))
                .placeholder(R.drawable.ic_profile_placeholder)
                .into(userImage);
        phoneNumText.setOnLongClickListener(v -> {
            contactDetailPresenter.onPhoneNumberLongPress(contact.getPhoneNumber());
            return true;
        });
        emailText.setOnLongClickListener(v -> {
            contactDetailPresenter.onEmailLongPress(contact.getEmail());
            return true;
        });
        this.isFavourite = contact.isFavorite();
        invalidateOptionsMenu();
        phoneBtn.setOnClickListener(v -> contactDetailPresenter.onPhoneButtonClicked(contact.getPhoneNumber()));
        messageBtn.setOnClickListener(v -> contactDetailPresenter.onMessageButtonClicked(contact.getPhoneNumber()));
        emailBtn.setOnClickListener(v -> contactDetailPresenter.onEmailButtonClicked(contact.getEmail()));
    }

    @Override
    public void launchPhoneApp(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    @Override
    public void launchEmailApp(String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null));
        startActivity(emailIntent);
    }

    @Override
    public void launchMessageApp(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("smsto:" + phoneNumber));
        intent.setType("vnd.android-dir/mms-sms");
        intent.putExtra("address", phoneNumber);
        startActivity(intent);
    }

    @Override
    public void copyToClipboard(String input) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText("text", input));
    }

    @Override
    public void showCopyToClipBoardMessage() {
        Toast.makeText(this, R.string.copied_to_clip_board, Toast.LENGTH_LONG).show();
    }

    @Override
    public void shareContact(Contact contact) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, contact.getPhoneNumber());
        startActivity(sharingIntent);

    }

    @Override
    public void launchEditContactScreen(Contact contact) {
        startActivity(AddOrEditContactActivity.createIntent(this, contact));
    }

    @Override
    public void showUnableToUpdateFavoriteError() {
        Toast.makeText(this, R.string.error_msg_unknown_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateFavourite(Contact contact) {
        this.isFavourite = contact.isFavorite();
        invalidateOptionsMenu();
    }

}
