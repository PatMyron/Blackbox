package com.patmyron.blackbox;


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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatImageButton = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.ll),
                                childAtPosition(
                                        withClassName(is("com.jrummyapps.android.widget.TwoDScrollView")),
                                        0)),
                        12),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        pressBack();

        ViewInteraction appCompatImageButton2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.ll),
                                childAtPosition(
                                        withClassName(is("com.jrummyapps.android.widget.TwoDScrollView")),
                                        0)),
                        18),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        pressBack();

        ViewInteraction appCompatImageButton3 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.ll),
                                childAtPosition(
                                        withClassName(is("com.jrummyapps.android.widget.TwoDScrollView")),
                                        0)),
                        24),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        pressBack();

        ViewInteraction appCompatImageButton4 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.ll),
                                childAtPosition(
                                        withClassName(is("com.jrummyapps.android.widget.TwoDScrollView")),
                                        0)),
                        25),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        pressBack();

        ViewInteraction appCompatImageButton5 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.ll),
                                childAtPosition(
                                        withClassName(is("com.jrummyapps.android.widget.TwoDScrollView")),
                                        0)),
                        19),
                        isDisplayed()));
        appCompatImageButton5.perform(click());

        pressBack();

        ViewInteraction appCompatImageButton6 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.ll),
                                childAtPosition(
                                        withClassName(is("com.jrummyapps.android.widget.TwoDScrollView")),
                                        0)),
                        13),
                        isDisplayed()));
        appCompatImageButton6.perform(click());

        pressBack();

        ViewInteraction appCompatImageButton7 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.ll),
                                childAtPosition(
                                        withClassName(is("com.jrummyapps.android.widget.TwoDScrollView")),
                                        0)),
                        20),
                        isDisplayed()));
        appCompatImageButton7.perform(click());

        pressBack();

        ViewInteraction appCompatImageButton8 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.ll),
                                childAtPosition(
                                        withClassName(is("com.jrummyapps.android.widget.TwoDScrollView")),
                                        0)),
                        32),
                        isDisplayed()));
        appCompatImageButton8.perform(click());

        pressBack();

        ViewInteraction appCompatImageButton9 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.ll),
                                childAtPosition(
                                        withClassName(is("com.jrummyapps.android.widget.TwoDScrollView")),
                                        0)),
                        33),
                        isDisplayed()));
        appCompatImageButton9.perform(click());

        pressBack();

        ViewInteraction appCompatImageButton10 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.ll),
                                childAtPosition(
                                        withClassName(is("com.jrummyapps.android.widget.TwoDScrollView")),
                                        0)),
                        22),
                        isDisplayed()));
        appCompatImageButton10.perform(click());

        pressBack();

        ViewInteraction appCompatImageButton11 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.ll),
                                childAtPosition(
                                        withClassName(is("com.jrummyapps.android.widget.TwoDScrollView")),
                                        0)),
                        16),
                        isDisplayed()));
        appCompatImageButton11.perform(click());

        pressBack();

        ViewInteraction appCompatImageButton12 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.ll),
                                childAtPosition(
                                        withClassName(is("com.jrummyapps.android.widget.TwoDScrollView")),
                                        0)),
                        17),
                        isDisplayed()));
        appCompatImageButton12.perform(click());

        pressBack();

        ViewInteraction appCompatImageButton13 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.ll),
                                childAtPosition(
                                        withClassName(is("com.jrummyapps.android.widget.TwoDScrollView")),
                                        0)),
                        5),
                        isDisplayed()));
        appCompatImageButton13.perform(click());

        pressBack();

        ViewInteraction appCompatImageButton14 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.ll),
                                childAtPosition(
                                        withClassName(is("com.jrummyapps.android.widget.TwoDScrollView")),
                                        0)),
                        4),
                        isDisplayed()));
        appCompatImageButton14.perform(click());

        pressBack();

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
