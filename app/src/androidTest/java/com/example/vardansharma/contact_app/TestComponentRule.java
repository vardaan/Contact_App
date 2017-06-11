package com.example.vardansharma.contact_app;

import android.content.Context;

import com.example.vardansharma.contact_app.data.dataSource.DataSource;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TestComponentRule implements TestRule {
    private final TestComponent mTestComponent;
    private final Context context;

    public TestComponentRule(Context context) {
        this.context = context;
        ContactsApp application = (ContactsApp) context.getApplicationContext();
        mTestComponent = DaggerTestComponent.builder()
                .testApplicationModule(new TestApplicationModule(application))
                .build();
    }

    public DataSource getMockDataManager() {
        return mTestComponent.provideDataManager();
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                ContactsApp application = (ContactsApp) context.getApplicationContext();
                // Set the TestComponent before the test runs
                application.setComponent(mTestComponent);
                base.evaluate();
                // Clears the component once the tets finishes so it would use the default one. 
                application.setComponent(null);
            }
        };
    }
}