package com.example.my_sql

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class Login : AppCompatActivity() {

    lateinit var register: TextView
    lateinit var login: LinearLayout
    lateinit var email: EditText
    lateinit var password: EditText

    private val url = "http://192.168.151.250/nikhil_api/login.php"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        register = findViewById(R.id.register)
        login = findViewById(R.id.login_button)

        register.setOnClickListener {
            startActivity(
                Intent(applicationContext, Registration::class.java)
            )
        }


        login.setOnClickListener {
            val emailtxt = email.text.toString()
            val passwordtxt = password.text.toString()
            login(emailtxt, passwordtxt)
        }


    }


    private fun login(emailtxt: String, passwordtxt: String) {
        val qry = "?email=$emailtxt&password=$passwordtxt"


        class dbprocess : AsyncTask<String, Void, String>() {

            override fun onPostExecute(result: String) {
                if (result == "exist") {
                    Toast.makeText(applicationContext, "sucessfully login", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, DashBoard::class.java))
                } else if (result == "wrong password") {
                    Toast.makeText(
                        applicationContext,
                        "Please enter correct password.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "$emailtxt is not register.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun doInBackground(vararg params: String): String {
                val furl: String = params[0]

                return try {
                    val url = URL(furl)

                    val conn = url.openConnection() as HttpURLConnection
                    val br = BufferedReader(InputStreamReader(conn.inputStream))
                    br.readLine()
                } catch (e: Exception) {
                    e.printStackTrace()
                    e.message!!
                }
            }


        }

        val obj = dbprocess()
        obj.execute(url + qry)
    }


}