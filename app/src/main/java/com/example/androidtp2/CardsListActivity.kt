package com.example.androidtp2


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CardAdapter(
    private val context: Context,
    private val cardList: ArrayList<Card>

) : BaseAdapter() {

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return cardList[position]
    }

    override fun getCount(): Int {
        return cardList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.card_list_item, parent, false)

        val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
        val dataTextView = view.findViewById<TextView>(R.id.dataTextView)

        val card = getItem(position) as Card
        nameTextView.text = card.name
        dataTextView.text = card.data

        return view
    }


}
