package com.example.sophie.jsontest2


import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

public class NetworkClient(val myTag: String?, val context: Context) {
    /**variables */
    private var currentDate: String? = null
    private var device: String? = null
    private var buildVersion: String? = null

    /**create a queue */
    val queue = Volley.newRequestQueue(context)
    /**create mapper*/
    val mapper = jacksonObjectMapper()

    data class Experiment(val success: String)

    /**
     * function to retrieve device info
     */
    private fun getDeviceInfo() {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val date = Date()
        currentDate = dateFormat.format(date)
        device = Build.SERIAL
        val i = BuildConfig.VERSION_CODE
        buildVersion = i.toString()
    }

    /**
     * function to POST if overlay is on
     */
    fun postOverlayOn(URL: String, onComplete: (String) -> Unit, onFailure: (String) -> Unit) {
        /**gat date and time */
        getDeviceInfo()
        /**create post request */
        val postRequest = object : JsonObjectRequest(

                Request.Method.POST, URL, null, Response.Listener { response ->
            try {
                val data = response.getBoolean("success")
                /**
                 * on success
                 */
                if (data == true) {
                    onComplete("")
                } else {
                    onFailure("")
                }
            } catch (e: Exception) {
                Log.e("xyz", e.toString())
                onFailure("")
            }
        }, Response.ErrorListener { error ->
            /**
             * on failure
             */
            onFailure("")
        }) {
            override
                    /**
                     * post these json pairs
                     */
            fun getParams(): Map<String, String?> {
                val params = HashMap<String, String?>()
                params["tx"] = "activating tx"
                params["device"] = device
                params["datetime"] = currentDate
                params["version"] = buildVersion
                return params
            }
        }
        queue.add(postRequest)
    }

    /**
     * function to POST if overlay is off
     */
    fun postOverlayOff(URL: String, onComplete: (String) -> Unit, onFailure: (String) -> Unit) {
        /**gat date and time */
        getDeviceInfo()
        /**create post request */
        val postRequest = object : JsonObjectRequest(

                Request.Method.POST, URL, null, Response.Listener { response ->
            try {
                val data = response.getBoolean("success")
                /**
                 * on success
                 */
                if (data == true) {
                    onComplete("Overlay on POST failure ${device}, ${currentDate}, ${buildVersion}")
                } else {
                    onFailure("Overlay off POST failure ${device}, ${currentDate}, ${buildVersion}")
                }
            } catch (e: Exception) {
                val error = e.toString()
                onFailure("Overlay off POST failure ${device}, ${currentDate}, " +
                        "${buildVersion}, Error: ${error}")
            }
        }, Response.ErrorListener { error ->
            /**
             * on failure
             */
            onFailure("Overlay off POST failure ${device}, ${currentDate}, ${buildVersion}")
        }) {
            override
                    /**
                     * post these json pairs
                     */
            fun getParams(): Map<String, String?> {
                val params = HashMap<String, String?>()
                params["device"] = device
                params["datetime"] = currentDate
                params["version"] = buildVersion
                return params
            }
        }
        queue.add(postRequest)
    }
    fun getRequest(URL: String) {
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, URL, null,
                Response.Listener<JSONObject> { response ->
                    /**
                     * On success
                     */
                    /**map my response*/
                    try{
                        val data = response.getBoolean("success")
                        /**print my response in a Toast*/
                        Toast.makeText(context, "Response: ${data}", Toast.LENGTH_LONG).show()}
                    catch (e: Exception){
                        Log.e("xyz", e.toString())
                        Toast.makeText(context, "Response: ${e}", Toast.LENGTH_LONG).show()
                    }

                },
                Response.ErrorListener {
                    /**
                     * On error
                     * inform me in a Toast
                     */
                    Toast.makeText(context, "That didn't work!", Toast.LENGTH_LONG).show()
                })
        jsonObjectRequest.tag = myTag

        queue.add(jsonObjectRequest)
    }
/*
    fun test(context: Context) {
        val client = NetworkClient("", context)
        client.postOverlayOn("", { str ->
            Log.d("Success", str)
        }, { err ->
            Log.e("Error", "Error messgae $err")
        })
    }*/
}







