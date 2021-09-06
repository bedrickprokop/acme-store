package com.acmestore.view.activity

import android.content.Intent
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.acmestore.R
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf

@RunWith(RobolectricTestRunner::class)
class SplashActivityTest {

    private lateinit var activity: SplashActivity
    private lateinit var actvVersion: AppCompatTextView
    private lateinit var actvStore: AppCompatTextView
    private lateinit var actvComeIn: AppCompatTextView
    private lateinit var acivLogo: AppCompatImageView

    @Before
    fun setup() {
        activity = Robolectric.buildActivity(SplashActivity::class.java)
            .create().start().resume().visible().get()

        actvVersion = activity.findViewById(R.id.actv_app_version)
        actvStore = activity.findViewById(R.id.actv_store)
        actvComeIn = activity.findViewById(R.id.actv_come_in)
        acivLogo = activity.findViewById(R.id.aciv_logo)
    }

    @Test
    fun whenThisActivityLoad_shouldDisplaySomeElements() {
        assertEquals(View.INVISIBLE, actvVersion.visibility)
        assertEquals(View.VISIBLE, actvStore.visibility)
        assertEquals(View.VISIBLE, actvComeIn.visibility)
        assertEquals(View.VISIBLE, acivLogo.visibility)
    }

    @Test
    fun whenTouch3TimesOnLogo_shouldDisplayAppVersion() {
        assertEquals(View.INVISIBLE, actvVersion.visibility)
        acivLogo.performClick()
        acivLogo.performClick()
        acivLogo.performClick()
        assertEquals(View.VISIBLE, actvVersion.visibility)

        acivLogo.performClick()

        //You might need a shadowOf(getMainLooper()).idle() call.
        //shadowOf(Looper.getMainLooper()).idle()
        assertEquals(View.INVISIBLE, actvVersion.visibility)
    }

    @Test
    fun whenTouchOnComeInButton_shouldStartMainActivity() {
        actvComeIn.performClick()
        val expectedIntent = Intent(activity, MainActivity::class.java)
        val actualIntent = shadowOf(RuntimeEnvironment.application).nextStartedActivity
        assertEquals(expectedIntent.component, actualIntent.component)
    }
}