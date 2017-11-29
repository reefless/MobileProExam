package com.egci428.qz0926

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_new_route.*

class newRoute : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_route)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        startBtn.setOnClickListener{
            val routename :String = routeNameEdt.text.toString()
            if(routename.isEmpty()){
                routeNameEdt.error = "Please input the route name"
            }else{
                val intent = Intent(this, routeMap::class.java)
                intent.putExtra("routename", routename)
                startActivity(intent)
            }
        }

    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.getItemId()
        if(id == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
