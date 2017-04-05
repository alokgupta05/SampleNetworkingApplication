package sample.codetest.com.musicapplication.activity;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import sample.codetest.com.musicapplication.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TrackDetailLandingTest {

    @Rule
    public ActivityTestRule<MusicHomeActivity> mActivityTestRule = new ActivityTestRule<>(MusicHomeActivity.class);

    @Test
    public void trackDetailLandingTest() {
        ViewInteraction searchAutoComplete = onView(
                allOf(withId(R.id.search_src_text),
                        withParent(allOf(withId(R.id.search_plate),
                                withParent(withId(R.id.search_edit_frame)))),
                        isDisplayed()));
        searchAutoComplete.perform(click());

        ViewInteraction searchAutoComplete2 = onView(
                allOf(withId(R.id.search_src_text),
                        withParent(allOf(withId(R.id.search_plate),
                                withParent(withId(R.id.search_edit_frame)))),
                        isDisplayed()));
        searchAutoComplete2.perform(click());

        ViewInteraction searchAutoComplete3 = onView(
                allOf(withId(R.id.search_src_text),
                        withParent(allOf(withId(R.id.search_plate),
                                withParent(withId(R.id.search_edit_frame)))),
                        isDisplayed()));
        searchAutoComplete3.perform(replaceText("micha"), closeSoftKeyboard());

        ViewInteraction searchAutoComplete4 = onView(
                allOf(withId(R.id.search_src_text), withText("micha"),
                        withParent(allOf(withId(R.id.search_plate),
                                withParent(withId(R.id.search_edit_frame)))),
                        isDisplayed()));
        searchAutoComplete4.perform(pressImeActionButton());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler_view), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(3, click()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView), withText("Track Name"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content_main),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("Track Name")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textView), withText("Track Name"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content_main),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.textView5), withText("Album Name")));
        textView3.check(matches(withText("Album Name")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.textView5), withText("Album Name")));
        textView4.check(matches(isDisplayed()));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction textView5 = onView(
                allOf(withId(R.id.textView3), withText("Artist Name")));
        textView5.check(matches(withText("Artist Name")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.textView3), withText("Artist Name")));
        textView6.check(matches(isDisplayed()));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.textView7), withText("Price")));
        textView7.check(matches(withText("Price")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.textView7), withText("Price")));
        textView8.check(matches(isDisplayed()));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.textView10), withText("ReleaseDate")));
        textView9.check(matches(withText("ReleaseDate")));

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.textView10), withText("ReleaseDate")));
        textView10.check(matches(isDisplayed()));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.imageViewArt)));
        imageView.check(matches(isDisplayed()));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
