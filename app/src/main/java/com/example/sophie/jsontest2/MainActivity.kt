package com.example.sophie.jsontest2


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper


class MainActivity : AppCompatActivity() {

    val url = "http://4m.to/apps/tesco/log.json"

    val mapper = jacksonObjectMapper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }


    override fun onStart() {
        super.onStart()
        /*val logToFile = LogToFile(this)
        logToFile.write("Hello")
        val networkClient = NetworkClient("tag", this)
        //networkClient.postOverlayOn(url,{ tag -> Log.d("xyz", tag)}, {tag2 -> Log.e("xyz", tag2)})
        networkClient.getRequest(url)*/
        createJsonObject("12.12", "device", "build", null)

    }
    fun createJsonObject(date: String, device: String, buildVersion: String, tx: String?){
        val overlayJsonObject = mapper.createObjectNode()
        if(tx != null){
            overlayJsonObject.put("tx", "activating tx")
        }
        overlayJsonObject.put("device", device)
        overlayJsonObject.put("datetime", date)
        overlayJsonObject.put("version", buildVersion)
        val string = overlayJsonObject.toString()
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()

    }

}