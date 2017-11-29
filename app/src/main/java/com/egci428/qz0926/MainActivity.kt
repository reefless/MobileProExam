package com.egci428.qz0926

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar

import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toolbar
import com.egci428.qz0926.models.MessageAdapter
import com.egci428.qz0926.models.mapInfo
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var dataReference: DatabaseReference
    var msgList = ArrayList<mapInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSupportActionBar()!!.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar()!!.setCustomView(R.layout.actionbar)
        listView.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this, routeView::class.java)
            var latArray = msgList.get(i).latitudeArray
            var lngArray = msgList.get(i).longtitudeArray
            intent.putExtra("lat", latArray)
            intent.putExtra("long", lngArray)
            intent.putExtra("color", msgList.get(i).colorArray)
            startActivity(intent)
        }
        addDrivingBtn.setOnClickListener{
            val intent = Intent(this, newRoute::class.java)
            startActivity(intent)
        }
        dataReference = FirebaseDatabase.getInstance().getReference("UserInfo")

        dataReference.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot?) {
                if (p0!!.exists()) {
                    msgList.clear()
                    for (i in p0.children) {
                        val message = i.getValue(mapInfo::class.java) // get the data base on Class object and put in message
                        msgList.add(message!!) // add the data that stores in message to the msgList(Arraylist)
                    }
                    val count = msgList.size
                    var x = 0
                    val adapter = MessageAdapter(applicationContext, R.layout.listview, msgList) // add row template for listview (R.layout.listview) and also msgList(ArrayList) as an adapter for listview
                    listView.adapter = adapter // set adapter for a listview
                }

            }
        })
    }



}
