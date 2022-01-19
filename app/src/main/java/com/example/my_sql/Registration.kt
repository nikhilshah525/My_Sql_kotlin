package com.example.my_sql

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.util.HashMap

class Registration : AppCompatActivity() {

    private val url = "http://192.168.151.250/nikhil_api/register.php"

    lateinit var name: EditText
    lateinit var email:EditText
    lateinit var password:EditText

    lateinit var registration_button: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        registration_button = findViewById(R.id.registration_button)


        registration_button.setOnClickListener {
            val nametxt = name.text.toString()
            val emailtxt = email.text.toString()
            val passwordtxt = password.text.toString()
            registration(nametxt, emailtxt, passwordtxt)
        }


    }

    private fun registration(nametxt: String, emailtxt: String, passwordtxt: String) {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                Toast.makeText(applicationContext, response, Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val map: MutableMap<String, String> = HashMap()
                map["name"] = nametxt
                map["email"] = emailtxt
                map["password"] = passwordtxt
                return map
            }
        }

        val requestQueue = Volley.newRequestQueue(this)
        stringRequest.retryPolicy = DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        requestQueue.add(stringRequest)
    }


    fun loginn(view: android.view.View) {
        onBackPressed()
    }
}