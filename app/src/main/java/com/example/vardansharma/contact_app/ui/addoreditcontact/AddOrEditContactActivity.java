package com.example.vardansharma.contact_app.ui.addoreditcontact;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.vardansharma.contact_app.R;
import com.example.vardansharma.contact_app.data.models.Contact;

public class AddOrEditContactActivity extends AppCompatActivity {

    public static final String EXTRA_CONTACT = "contact";

    public static Intent createIntent(Context context, Contact contact) {
        Intent intent = new Intent(context, AddOrEditContactActivity.class);
        intent.putExtra(EXTRA_CONTACT, contact);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_contact);
    }
}
