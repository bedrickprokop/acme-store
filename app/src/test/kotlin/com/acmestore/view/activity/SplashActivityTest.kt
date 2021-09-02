package com.acmestore.view.activity

import android.content.Intent
import android.view.View
import com.acmestore.R
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
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
    private lateinit var tvVersion: MaterialTextView
    private lateinit var tvStore: MaterialTextView
    private lateinit var tvComeIn: MaterialTextView
    private lateinit var ivLogo: ShapeableImageView

    @Before
    fun setup() {
        activity = Robolectric.buildActivity(SplashActivity::class.java)
            .create().start().resume().visible().get()

        tvVersion = activity.findViewById(R.id.tv_app_version)
        tvStore = activity.findViewById(R.id.tv_store)
        tvComeIn = activity.findViewById(R.id.tv_come_in)
        ivLogo = activity.findViewById(R.id.iv_logo)
    }

    @Test
    fun whenThisActivityLoad_shouldDisplaySomeElements() {
        assertEquals(View.INVISIBLE, tvVersion.visibility)
        assertEquals(View.VISIBLE, tvStore.visibility)
        assertEquals(View.VISIBLE, tvComeIn.visibility)
        assertEquals(View.VISIBLE, ivLogo.visibility)
    }

    @Test
    fun whenTouch3TimesOnLogo_shouldDisplayAppVersion() {
        assertEquals(View.INVISIBLE, tvVersion.visibility)
        ivLogo.performClick()
        ivLogo.performClick()
        ivLogo.performClick()
        assertEquals(View.VISIBLE, tvVersion.visibility)

        ivLogo.performClick()

        //You might need a shadowOf(getMainLooper()).idle() call.
        //shadowOf(Looper.getMainLooper()).idle()
        assertEquals(View.INVISIBLE, tvVersion.visibility)
    }

    @Test
    fun whenTouchOnComeInButton_shouldStartMainActivity() {
        tvComeIn.performClick()
        val expectedIntent = Intent(activity, MainActivity::class.java)
        val actualIntent = shadowOf(RuntimeEnvironment.application).nextStartedActivity
        assertEquals(expectedIntent.component, actualIntent.component)
    }
}