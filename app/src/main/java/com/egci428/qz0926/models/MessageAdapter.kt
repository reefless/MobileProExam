package com.egci428.qz0926.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.egci428.qz0926.R

/**
 * Created by 6272user on 11/29/17.
 */
class MessageAdapter(val mContext: Context, val layoutResId: Int, val messageList: List<mapInfo>): ArrayAdapter<mapInfo>(mContext, layoutResId, messageList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutInflator: LayoutInflater = LayoutInflater.from(mContext) // set a layoutInflater for that context
        val view: View = layoutInflator.inflate(layoutResId, null) // inflate template layout to the listview
        val msgTextView = view.findViewById<TextView>(R.id.userList) // set msgTextView to be TextView of id userList
        val msg = messageList[position] // retrieve data from messageList according to the position

        msgTextView.text = msg.routeName // set id.userList = msg.username
        return view
    }
}