package com.example.helloworldkotlin

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.list_record.*
import kotlin.properties.Delegates

/**
 * Created by MrJ
 */

class ViewHolder(v: View) {
    val title = v.findViewById<TextView>(R.id.txt_Title)
    val artist = v.findViewById<TextView>(R.id.txt_Artist)
    val company = v.findViewById<TextView>(R.id.txt_Company)
    val country = v.findViewById<TextView>(R.id.txt_Country)
    val price = v.findViewById<TextView>(R.id.txt_Price)
    val year = v.findViewById<TextView>(R.id.txt_Year)
}

class FeedAdapter(
    context: Context,
    private val resource: Int,
    val applications: List<CatalogEntry>
) : ArrayAdapter<CatalogEntry>(context, resource) {
    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = inflater.inflate(resource, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
//        val txt_Title = view.findViewById<TextView>(R.id.txt_Title)
//        val txt_Artist = view.findViewById<TextView>(R.id.txt_Artist)
//        val txt_Company = view.findViewById<TextView>(R.id.txt_Company)
//        val txt_County = view.findViewById<TextView>(R.id.txt_Country)
//        val txt_Price = view.findViewById<TextView>(R.id.txt_Price)
//        val txt_Year = view.findViewById<TextView>(R.id.txt_Year)

        val currentApp = applications[position]

        viewHolder.title.text = currentApp.Title
        viewHolder.artist.text = currentApp.Artist
        viewHolder.company.text = currentApp.Company
        viewHolder.country.text = currentApp.Country
        viewHolder.price.text = currentApp.Price.toString()
        viewHolder.year.text = currentApp.Year.toString()
        return view
    }

    override fun getCount(): Int {
        return applications.size
    }

}