package com.example.vardansharma.contact_app.ui.contactlist;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.vardansharma.contact_app.FakeContactData;
import com.example.vardansharma.contact_app.R;
import com.example.vardansharma.contact_app.RxIdlingResource;
import com.example.vardansharma.contact_app.TestComponentRule;
import com.example.vardansharma.contact_app.assertions.RecyclerViewItemCountAssertion;
import com.example.vardansharma.contact_app.data.models.Contact;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;

/**
 * Created by vardansharma on 08/06/17.
 */
@LargeTest
@RunWith (AndroidJUnit4.class)
public class ContactListActivityTest {

    @Rule
    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());

    @Rule
    public ActivityTestRule<ContactListActivity> mActivityRule = new ActivityTestRule<>(
            ContactListActivity.class, true, false);// needed to make activity launch after setting the application component


    @Rule
    public final TestRule chain = RuleChain.outerRule(component).around(mActivityRule);
    private RxIdlingResource rxIdlingResource;

    @Before
    public void setUp() throws Exception {
        rxIdlingResource = new RxIdlingResource();
        registerIdlingResources(rxIdlingResource);
    }


    @Test
    public void shouldShowEmptyViewInCaseOfNoData() {
        when(component.getMockDataManager()
                .getAllContact())
                .thenReturn(Observable.just(FakeContactData.EMPTY_CONTACTS));

        mActivityRule.launchActivity(new Intent());
        onView(withId(R.id.contact_list_no_Data)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplayToolbarCorrectly() {
        when(component.getMockDataManager()
                .getAllContact())
                .thenReturn(Observable.just(FakeContactData.EMPTY_CONTACTS));

        mActivityRule.launchActivity(new Intent());

        onView(withId(R.id.contact_list_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.contact_list_toolbar)).check(matches(hasDescendant(withText(R.string.contacts))));
        onView(withId(R.id.menu_search)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldShowFAB() {
        when(component.getMockDataManager()
                .getAllContact())
                .thenReturn(Observable.just(FakeContactData.EMPTY_CONTACTS));

        mActivityRule.launchActivity(new Intent());

        onView(withId(R.id.contact_list_add_contact)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldShowCorrectNumberOfContacts() {
        final List<Contact> contactList = FakeContactData.getContactList();
        when(component.getMockDataManager()
                .getAllContact())
                .thenReturn(Observable.just(contactList));

        mActivityRule.launchActivity(new Intent());

        onView(withId(R.id.contact_list_rv)).check(new RecyclerViewItemCountAssertion(contactList.size()));
    }

    @After
    public void tearDown() throws Exception {
        unregisterIdlingResources(rxIdlingResource);
    }
}