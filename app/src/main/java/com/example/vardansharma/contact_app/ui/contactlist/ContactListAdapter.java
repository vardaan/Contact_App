package com.example.vardansharma.contact_app.ui.contactlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vardansharma.contact_app.R;
import com.example.vardansharma.contact_app.data.models.Contact;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vardansharma on 11/06/17.
 */

class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactVH> {

    private final List<Contact> contacts;


    ContactListAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }


    @Override
    public ContactVH onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactVH(view);
    }

    @Override
    public void onBindViewHolder(ContactVH holder, int position) {
        final Contact contact = contacts.get(position);
        holder.contactName.setText(contact.getFirstName());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    static class ContactVH extends RecyclerView.ViewHolder {
        @BindView (R.id.contact_name)
        TextView contactName;

        public ContactVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
