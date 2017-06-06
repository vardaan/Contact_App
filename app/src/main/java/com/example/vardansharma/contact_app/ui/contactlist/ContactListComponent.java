package com.example.vardansharma.contact_app.ui.contactlist;

import com.example.vardansharma.contact_app.di.ActivityScope;
import com.example.vardansharma.contact_app.di.ApplicationComponent;

import dagger.Component;

@ActivityScope
@Component (dependencies = ApplicationComponent.class, modules = ContactListModule.class)
public interface ContactListComponent {
    void inject(ContactListActivity target);
}
