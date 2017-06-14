package com.example.vardansharma.contact_app.ui.addoreditcontact;

import com.example.vardansharma.contact_app.di.ActivityScope;
import com.example.vardansharma.contact_app.di.ApplicationComponent;

import dagger.Component;

@ActivityScope
@Component(modules = AddOrEditContactModule.class, dependencies = ApplicationComponent.class)
public interface AddOrEditContactComponent {
    void inject(AddOrEditContactActivity target);
}
