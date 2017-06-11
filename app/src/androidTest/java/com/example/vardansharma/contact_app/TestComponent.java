package com.example.vardansharma.contact_app;

import com.example.vardansharma.contact_app.di.ApplicationComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = TestApplicationModule.class)
public interface TestComponent extends ApplicationComponent {
    // Empty because extends ApplicationComponent
}
