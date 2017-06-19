package com.example.vardansharma.contact_app.ui.contactlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vardansharma.contact_app.R;
import com.example.vardansharma.contact_app.data.models.Contact;
import com.example.vardansharma.contact_app.utils.MaterialLetterIcon;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vardansharma on 11/06/17.
 */

class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactVH> {

    private List<Contact> contacts;
    private onContactClicked callback;

    ContactListAdapter(List<Contact> contacts, onContactClicked callback) {
        this.contacts = contacts;
        this.callback = callback;
    }

    @Override
    public ContactVH onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactVH(view);
    }

    @Override
    public void onBindViewHolder(ContactVH vh, int position) {
        final Contact contact = contacts.get(position);
        vh.contactName.setText(getFormattedName(contact));
        vh.contactType.setVisibility(position == 0 ? View.VISIBLE : View.INVISIBLE);
        vh.letterIcon.setLetter(contact.getFirstName(), position);
        vh.itemView.setOnClickListener(v -> callback.onContactClicked(contact));
    }

    @NonNull
    private String getFormattedName(Contact contact) {
        return contact.getFirstName() + " " + contact.getLastName();
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setData(List<Contact> contacts) {
        this.contacts = contacts;
    }

    static class ContactVH extends RecyclerView.ViewHolder {
        @BindView (R.id.contact_name)
        TextView contactName;
        @BindView (R.id.contact_type_indicator)
        ImageView contactType;
        @BindView (R.id.contact_letter_icon)
        MaterialLetterIcon letterIcon;
        @BindView (R.id.contact_first_letter)
        TextView contactFirstLetter;

        public ContactVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    interface onContactClicked {
        void onContactClicked(Contact contact);
    }
}
