package com.example.vardansharma.contact_app.ui.contactDetail;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.example.vardansharma.contact_app.FakeContactData;
import com.example.vardansharma.contact_app.R;
import com.example.vardansharma.contact_app.RxIdlingResource;
import com.example.vardansharma.contact_app.TestComponentRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import io.reactivex.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by vardansharma on 13/06/17.
 */
public class ContactDetailActivityTest {
    @Rule
    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());

    @Rule
    public ActivityTestRule<ContactDetailActivity> mActivityRule = new ActivityTestRule<>(
            ContactDetailActivity.class, true, false);// needed to make activity launch after setting the application component


    @Rule
    public final TestRule chain = RuleChain.outerRule(component).around(mActivityRule);
    private RxIdlingResource rxIdlingResource;

    @Before
    public void setUp() throws Exception {
        rxIdlingResource = new RxIdlingResource();
        registerIdlingResources(rxIdlingResource);
    }

    @Test
    public void shouldDisplayToolbarCorrectly() {
        when(component.getMockDataManager()
                .getContactDetails(anyString()))
                .thenReturn(Observable.just(FakeContactData.monica));

        mActivityRule.launchActivity(new Intent());

        onView(withId(R.id.contact_detail_toolbar)).check(matches(isDisplayed()));

        onView(withId(R.id.action_edit)).check(matches(isDisplayed()));
        onView(withId(R.id.action_favourite)).check(matches(isDisplayed()));
    }

}