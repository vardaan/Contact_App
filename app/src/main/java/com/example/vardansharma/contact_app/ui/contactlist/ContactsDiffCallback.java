package com.example.vardansharma.contact_app.ui.contactlist;

import android.support.v7.util.DiffUtil;

import com.example.vardansharma.contact_app.data.models.Contact;

import java.util.List;

public class ContactsDiffCallback extends DiffUtil.Callback {
    private final List<Contact> oldContacts;
    private final List<Contact> newContacts;

    public ContactsDiffCallback(List<Contact> oldContacts, List<Contact> newContacts) {
        this.oldContacts = oldContacts;
        this.newContacts = newContacts;
    }

    @Override
    public int getOldListSize() {
        return oldContacts.size();
    }

    @Override
    public int getNewListSize() {
        return newContacts.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldContacts.get(oldItemPosition).getId() == newContacts.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldContacts.get(oldItemPosition).equals(newContacts.get(newItemPosition));
    }
}
