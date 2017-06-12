package com.example.vardansharma.contact_app.ui.contactlist;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vardansharma.contact_app.ContactsApp;
import com.example.vardansharma.contact_app.R;
import com.example.vardansharma.contact_app.base.BaseActivity;
import com.example.vardansharma.contact_app.data.models.Contact;
import com.example.vardansharma.contact_app.ui.contactDetail.ContactDetailActivity;
import com.example.vardansharma.contact_app.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactListActivity extends BaseActivity implements ContactListContract.Screen, ContactListAdapter.onContactClicked {
    @Inject
    ContactListContract.Presenter presenter;// private fields won't be injected
    @BindView (R.id.contact_list_no_Data)
    TextView contactListNoData;
    @BindView (R.id.contact_list_toolbar)
    Toolbar toolbar;
    @BindView (R.id.contact_list_rv)
    RecyclerView recyclerView;
    @BindView (R.id.contact_list_add_contact)
    FloatingActionButton addContact;

    private ContactListComponent component;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDI();
        setContentView(R.layout.activity_contact_list);
        ButterKnife.bind(this);

        initToolBar();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_msg));

        presenter.attachView();

        presenter.getAllContacts();
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(Utils.dpToPixel(4, this));
        }
    }

    private void initDI() {
        ContactsApp contactApp = (ContactsApp) getApplication();
        contactApp.getAppComponent();
        component = DaggerContactListComponent.builder()
                .applicationComponent(contactApp.getAppComponent())
                .contactListModule(new ContactListModule(this))
                .build();
        component.inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_list_menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        component = null;
        presenter.detachView();
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.hide();
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showEmptyScreen() {
        contactListNoData.setVisibility(View.VISIBLE);
    }

    @Override
    public void showData(List<Contact> contacts) {
        recyclerView.setAdapter(new ContactListAdapter(contacts, this));
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(this, R.string.error_msg_unknown_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNetworkError() {
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setMessage(R.string.error_msg_unable_to_contact)
                .setTitle(R.string.error_msg_network_error_title).show();
    }

    @Override
    public void launchContactDetail(Contact contact) {
        startActivity(ContactDetailActivity.createIntent(this, contact));
    }

    @Override
    public void onContactClicked(Contact contact) {
        presenter.onContactClicked(contact);
    }
}
