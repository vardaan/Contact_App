package com.example.vardansharma.contact_app.ui.contactDetail;

import com.example.vardansharma.contact_app.di.ActivityScope;
import com.example.vardansharma.contact_app.di.ApplicationComponent;

import dagger.Component;

/**
 * Created by vardansharma on 12/06/17.
 */

@ActivityScope
@Component (modules = ContactDetailModule.class, dependencies = ApplicationComponent.class)
public interface ContactDetailComponent {

    void inject(ContactDetailActivity target);
}
