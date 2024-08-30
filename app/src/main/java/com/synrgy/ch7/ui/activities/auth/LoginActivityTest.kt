package com.synrgy.ch7.ui.activities.auth

import android.content.Intent
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.synrgy.ch7.R
import com.synrgy.ch7.ui.viewmodel.LoginViewModel
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.shadows.ShadowToast
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class LoginActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(LoginActivity::class.java, true, true)

    private val viewModel: LoginViewModel = mock(LoginViewModel::class.java)

    @Test
    fun testLoginButtonClickNavigatesToMainActivityOnSuccess() {
        val activity = activityRule.activity
        val username = "testuser"
        val password = "password"

        `when`(viewModel.loginResult).thenReturn(MutableLiveData(true))

        val etName: EditText = activity.findViewById(R.id.etName)
        val etPassword: EditText = activity.findViewById(R.id.etPassword)
        etName.setText(username)
        etPassword.setText(password)

        val loginButton: Button = activity.findViewById(R.id.btnlogin)
        loginButton.performClick()

        val expectedIntent = Intent(activity, MainActivity::class.java)
        val actualIntent = shadowOf(activity).peekNextStartedActivity()
        assertEquals(expectedIntent.component, actualIntent.component)
    }

    @Test
    fun testRegisterButtonClickNavigatesToRegisterActivity() {
        val activity = activityRule.activity

        // Perform register button click
        val registerButton: Button = activity.findViewById(R.id.btnRegisterInLogin)
        registerButton.performClick()

        // Verify that RegisterActivity is started
        val expectedIntent = Intent(activity, RegisterActivity::class.java)
        val actualIntent = shadowOf(activity).peekNextStartedActivity()
        assertEquals(expectedIntent.component, actualIntent.component)
    }

    @Test
    fun testLoginFailureShowsToast() {
        val activity = activityRule.activity


        `when`(viewModel.loginResult).thenReturn(MutableLiveData(false))

        val etName: EditText = activity.findViewById(R.id.etName)
        val etPassword: EditText = activity.findViewById(R.id.etPassword)
        etName.setText("wronguser")
        etPassword.setText("wrongpassword")

        val loginButton: Button = activity.findViewById(R.id.btnlogin)
        loginButton.performClick()

        val expectedToastMessage = "Login gagal"
        assertTrue(ShadowToast.getTextOfLatestToast().contains(expectedToastMessage))
    }
}
