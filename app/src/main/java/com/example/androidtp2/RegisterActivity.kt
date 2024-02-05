package com.example.androidtp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    public fun goToLogin(view: View)
    {
        finish();
    }
    public fun register(view: View) {
        val name = findViewById<EditText>(R.id.registerName).text.toString()
        val mail = findViewById<EditText>(R.id.registerMail).text.toString()
        val password = findViewById<EditText>(R.id.registerPassword).text.toString()

        val registerData = RegisterData(name, mail, password)

        Api().post<RegisterData, String>(
            "https://esicards.lesmoulinsdudev.com/register",
            registerData,
            { responseCode, response ->
                when (responseCode) {
                    200 -> {
                        // Registration successful
                        val intent_register_login = Intent(this, LoginActivity::class.java)
                        startActivity(intent_register_login)
                    }
                    409 -> {
                        // Account with the same email already exists
                        Toast.makeText(this, "Account with this email already exists", Toast.LENGTH_SHORT).show()
                    }
                    500 -> {
                        // Server error
                        Toast.makeText(this, "Server error occurred", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        // other errors
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }


    public fun registerSuccess(responseCode: Int) {
        if (responseCode == 200)
            finish()

    }



}