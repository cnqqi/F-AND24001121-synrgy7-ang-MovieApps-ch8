package com.synrgy.ch7.ui.activities.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.ch7.BuildConfig
import com.synrgy.ch7.R
import com.synrgy.ch7.databinding.ActivityLoginBinding
import com.synrgy.ch7.ui.activities.MainActivity
import com.synrgy.ch7.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()

        val checkButton: Button = findViewById(R.id.btnCheck)
        checkButton.setOnClickListener {
            checkFlavor()
        }
    }

    private fun checkFlavor() {
        val flavorName = BuildConfig.FLAVOR_NAME
        Toast.makeText(this, "Flavor: $flavorName", Toast.LENGTH_LONG).show()
    }

    private fun setupClickListeners() = with(binding) {
        btnlogin.setOnClickListener {
            val username = etName.text.toString()
            val password = etPassword.text.toString()
            viewModel.login(username, password)
        }

        btnRegisterInLogin.setOnClickListener {
            // Navigate to RegisterActivity
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        viewModel.loginResult.observe(this@LoginActivity) { isSuccess ->
            if (isSuccess) {
                val username = binding.etName.text.toString()

                // Save username to SharedPreferences
                val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putString("username", username)
                    apply()
                }

                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this@LoginActivity, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
