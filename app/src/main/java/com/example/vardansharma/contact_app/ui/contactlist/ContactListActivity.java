package com.example.vardansharma.contact_app.ui.contactlist;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.vardansharma.contact_app.ContactsApp;
import com.example.vardansharma.contact_app.R;
import com.example.vardansharma.contact_app.base.BaseActivity;
import com.example.vardansharma.contact_app.data.models.Contact;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactListActivity extends BaseActivity implements ContactListContract.Screen {
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

        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_msg));

        presenter.attachView();

        presenter.getAllContacts();
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

    }

    @Override
    public void showErrorScreen() {

    }
}
