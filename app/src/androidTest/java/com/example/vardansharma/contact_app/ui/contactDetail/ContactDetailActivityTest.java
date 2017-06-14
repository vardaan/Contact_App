package com.example.vardansharma.contact_app.ui.contactDetail;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.example.vardansharma.contact_app.FakeContactData;
import com.example.vardansharma.contact_app.R;
import com.example.vardansharma.contact_app.RxIdlingResource;
import com.example.vardansharma.contact_app.TestComponentRule;
import com.example.vardansharma.contact_app.data.models.Contact;
import com.example.vardansharma.contact_app.utils.Utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import io.reactivex.Observable;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
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

        launchActivity();

        onView(withId(R.id.contact_detail_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.action_edit)).check(matches(isDisplayed()));
        onView(withId(R.id.action_favourite)).check(matches(isDisplayed()));
    }

    private void launchActivity() {
        final Intent startIntent = new Intent();
        startIntent.putExtra("contact", FakeContactData.angeline);
        mActivityRule.launchActivity(startIntent);
    }

    @Test
    public void shouldFinishOnBackPress() {
        when(component.getMockDataManager()
                .getContactDetails(anyString()))
                .thenReturn(Observable.just(FakeContactData.angeline));
        launchActivity();

        onView(withContentDescription(R.string.navigate_up)).perform(click());
        assertTrue(mActivityRule.getActivity().isFinishing());
    }

    @Test
    public void shouldShowCorrectDataWhenDataInSuccess() throws Exception {
        final Contact angeline = FakeContactData.angeline;
        when(component.getMockDataManager()
                .getContactDetails(anyString()))
                .thenReturn(Observable.just(angeline));

        launchActivity();

        onView(withId(R.id.contact_detail_phone_num_text)).check(matches(withText(angeline.getPhoneNumber())));
        onView(withId(R.id.contact_detail_user_name)).check(matches(withText(Utils.getDisplayName(angeline))));
        onView(withId(R.id.contact_detail_email_text)).check(matches(withText(angeline.getEmail())));
    }


    @Test
    public void shouldCopyPhoneNumberWhenClickedOnPhoneText() {
        final Contact angeline = FakeContactData.angeline;
        when(component.getMockDataManager()
                .getContactDetails(anyString()))
                .thenReturn(Observable.just(angeline));

        launchActivity();

        onView(withId(R.id.contact_detail_phone_num_text)).perform(longClick());


        getInstrumentation().runOnMainSync(() -> {
            ClipboardManager clipboardManager = (ClipboardManager) mActivityRule.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            assertEquals(clipboardManager.getPrimaryClip().getItemAt(0).getText(), angeline.getPhoneNumber());
        });


    }

//
//    @Test
//    public void shouldCopyEmailWhenClickedOnEmailText() {
//        final Contact angeline = FakeContactData.angeline;
//        when(component.getMockDataManager()
//                .getContactDetails(anyString()))
//                .thenReturn(Observable.just(angeline));
//
//        launchActivity();
//
//        onView(withId(R.id.contact_detail_email_text)).perform(longClick());
//
//
//        getInstrumentation().runOnMainSync(() -> {
//            ClipboardManager clipboardManager = (ClipboardManager) mActivityRule.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
//            assertEquals(clipboardManager.getPrimaryClip().getItemAt(0).getText(), angeline.getEmail());
//            mActivityRule.getActivity().finish();
//
//        });
//
//
//    }
//
//
//    @Test
//    public void shouldLaunchPhoneAppWhenClickedOnPhoneButton() {
//        final Contact angeline = FakeContactData.angeline;
//        when(component.getMockDataManager()
//                .getContactDetails(anyString()))
//                .thenReturn(Observable.just(angeline));
//
//        launchActivity();
//
//        Intents.init();
//
//        onView(withId(R.id.contact_detail_phone_btn)).perform(click());
//
//        intended(hasAction(Intent.ACTION_DIAL));
//        intended(IntentMatchers.hasData(Uri.parse("tel:" + angeline.getPhoneNumber())));
//        Intents.release();
//        mActivityRule.getActivity().finish();
//
//    }
//
//
//    @Test
//    public void shouldLaunchEmailAppWhenClickedOnEmailButton() {
//        final Contact angeline = FakeContactData.angeline;
//        when(component.getMockDataManager()
//                .getContactDetails(anyString()))
//                .thenReturn(Observable.just(angeline));
//
//        launchActivity();
//        Intents.init();
//
//
//        onView(withId(R.id.contact_detail_email_btn)).perform(click());
//
//        intended(hasAction(Intent.ACTION_SENDTO));
//        Intents.release();
//        mActivityRule.getActivity().finish();
//    }
//
//    @Test
//    public void shouldLaunchMessageAppWhenClickedOnMessageButton() {
//        final Contact angeline = FakeContactData.angeline;
//        when(component.getMockDataManager()
//                .getContactDetails(anyString()))
//                .thenReturn(Observable.just(angeline));
//
//        launchActivity();
//        Intents.init();
//
//
//        onView(withId(R.id.contact_detail_message_btn)).perform(click());
//
//        intended(hasAction(Intent.ACTION_SEND));
//        intended(IntentMatchers.hasData(Uri.parse("smsto:" + angeline.getPhoneNumber())));
//        Intents.release();
//        mActivityRule.getActivity().finish();
//
//    }

    @After
    public void tearDown() throws Exception {
        unregisterIdlingResources(rxIdlingResource);

    }
}