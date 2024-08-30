package com.synrgy.ch7.ui.activities.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.ch7.databinding.ActivityRegisterBinding
import com.synrgy.ch7.ui.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        observeViewModel()
    }

    private fun setupClickListeners() = with(binding) {
        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val username = etName.text.toString()
            val password = etPassword.text.toString()

            // Validate input fields
            if (email.isNotBlank() && username.isNotBlank() && password.isNotBlank()) {
                viewModel.register(email, username, password)
            } else {
                showError("All fields are required.")
            }
        }
        topAppBar.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }

    private fun observeViewModel() {
        viewModel.registrationResult.observe(this) { isSuccess ->
            if (isSuccess) {
                // Registration successful, navigate to LoginActivity
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                // Registration failed, show error message
                showError("Registration failed. Please try again.")
            }
        }
    }


    private fun showError(message: String) {
        // Display an error message to the user
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
