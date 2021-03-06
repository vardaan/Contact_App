package com.example.vardansharma.contact_app.ui.contactlist;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.vardansharma.contact_app.R;
import com.example.vardansharma.contact_app.RxIdlingResource;
import com.example.vardansharma.contact_app.TestComponentRule;
import com.example.vardansharma.contact_app.TestContactData;
import com.example.vardansharma.contact_app.assertions.RecyclerViewItemCountAssertion;
import com.example.vardansharma.contact_app.data.models.Contact;
import com.example.vardansharma.contact_app.ui.addoreditcontact.AddOrEditContactActivity;
import com.example.vardansharma.contact_app.ui.contactDetail.ContactDetailActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by vardansharma on 08/06/17.
 */
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
                .thenReturn(Observable.just(TestContactData.EMPTY_CONTACTS));

        mActivityRule.launchActivity(new Intent());
        onView(withId(R.id.contact_list_no_Data)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplayToolbarCorrectly() {
        when(component.getMockDataManager()
                .getAllContact())
                .thenReturn(Observable.just(TestContactData.EMPTY_CONTACTS));

        mActivityRule.launchActivity(new Intent());

        onView(withId(R.id.contact_list_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.contact_list_toolbar)).check(matches(hasDescendant(withText(R.string.contacts))));
        onView(withId(R.id.menu_search)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldShowFAB() {
        when(component.getMockDataManager()
                .getAllContact())
                .thenReturn(Observable.just(TestContactData.EMPTY_CONTACTS));

        mActivityRule.launchActivity(new Intent());

        onView(withId(R.id.contact_list_add_contact)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldShowCorrectNumberOfContacts() {
        final List<Contact> contactList = TestContactData.getContactList();
        when(component.getMockDataManager()
                .getAllContact())
                .thenReturn(Observable.just(contactList));

        mActivityRule.launchActivity(new Intent());

        onView(withId(R.id.contact_list_rv)).check(new RecyclerViewItemCountAssertion(contactList.size()));
    }


    @Test
    public void shouldShowNetworkErrorMessageInCaseOfNetworkFailure() {
        when(component.getMockDataManager()
                .getAllContact())
                .thenReturn(Observable.error(new IOException()));

        mActivityRule.launchActivity(new Intent());

        onView(withText(R.string.error_msg_network_error_title)).check(matches(isDisplayed()));

    }

    @Test
    public void shouldLaunchContactDetailWhenContactItemIsClicked() {
        when(component.getMockDataManager()
                .getAllContact())
                .thenReturn(Observable.just(TestContactData.getContactList()));


        when(component.getMockDataManager()
                .getContactDetails(anyString()))
                .thenReturn(Observable.just(TestContactData.bella));
        mActivityRule.launchActivity(new Intent());

        Intents.init();

        onView(withId(R.id.contact_list_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        intended(hasComponent(ContactDetailActivity.class.getName()));
        intended(hasComponent(ContactDetailActivity.class.getName()), times(1));

        Intents.release();
    }

    @Test
    public void shouldLaunchAddContactScreenWhenAddContactBtnIsClicked() throws Exception {
        when(component.getMockDataManager()
                .getAllContact())
                .thenReturn(Observable.just(TestContactData.getContactList()));

        when(component.getMockDataManager()
                .getContactDetails(anyString()))
                .thenReturn(Observable.just(TestContactData.bella));

        mActivityRule.launchActivity(new Intent());
        Intents.init();

        onView(withId(R.id.contact_list_add_contact))
                .perform(click());

        intended(hasComponent(AddOrEditContactActivity.class.getName()));
        intended(hasComponent(AddOrEditContactActivity.class.getName()), times(1));

        Intents.release();
    }

    @After
    public void tearDown() throws Exception {
        unregisterIdlingResources(rxIdlingResource);
    }
}