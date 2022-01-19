package com.example.my_sql

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class DashBoard : AppCompatActivity() {

    private val url = "http://192.168.151.250/nikhil_api/listdata.php"

    var textView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        textView = findViewById(R.id.textView)

        listdata()

    }


    private fun listdata() {
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->


                try {
                    val jsonObject = JSONObject(response)
                    val jsonArray = jsonObject.getJSONArray("students")
                    var data = ""
                    for (i in 0 until jsonArray.length()) {
                        val `object` = jsonArray.getJSONObject(i)
                        data = """${data}Id:${`object`.getString("id")}
                            Name:${`object`.getString("name")}
                            Email:${`object`.getString("email")}
                            Password:${`object`.getString("password")}
                            
                                 
                            """

                    }
                    textView!!.text = data
                } catch (e: JSONException) {
                    e.printStackTrace()
                }


            }
        ) { error ->
            Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
        }
        val requestQueue = Volley.newRequestQueue(this)
        stringRequest.retryPolicy =
            DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        requestQueue.add(stringRequest)
    }
}