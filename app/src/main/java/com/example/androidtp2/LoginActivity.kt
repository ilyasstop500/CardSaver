package com.example.androidtp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    public fun registerNewAccount(view: View)
    {
        val intentLoginRegister = Intent(this, RegisterActivity::class.java);
        startActivity(intentLoginRegister);
    }

    fun login(view: View) {
        val mailText = findViewById<EditText>(R.id.loginMail)
        val passwordText = findViewById<EditText>(R.id.loginPassword)

        val mail = mailText.text.toString()
        val password = passwordText.text.toString()

        val loginData = LoginData(mail, password)

        Api().post<LoginData, String>(
            "https://esicards.lesmoulinsdudev.com/auth",
            loginData,
            { responseCode, token ->
                when (responseCode) {
                    200 -> {
                        // Login successfull
                        val intentLoginCard = Intent(this, CardActivity::class.java)
                        intentLoginCard.putExtra("token", token)
                        startActivity(intentLoginCard)
                    }
                    404 -> {
                        // User not found
                        Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                    }
                    500 -> {
                        // Server error
                        Toast.makeText(this, "Server error occurred", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        // Other errors
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }



}