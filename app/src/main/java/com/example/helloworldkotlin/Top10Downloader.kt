package com.example.helloworldkotlin

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.top10downloader.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import kotlin.properties.Delegates

class CatalogEntry {
    var Title: String = ""
    var Artist: String = ""
    var Country: String = ""
    var Company: String = ""
    var Price: Double = 0.0
    var Year: Int = 0
    override fun toString(): String {
        return """
            Title = $Title
            Artist = $Artist
            Country = $Country
            Company = $Company
            Price = $Price
            Year = $Year
            """.trimIndent()
    }
}

class Top10Downloader : AppCompatActivity() {
    private val Tag = "top10Downloader"

    private var downloadData: DownloadData? = null

    private var feedUrl: String = "http://okitgo.ru/misc/xml/cd_catalog.xml"
    private var feedLimit = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.top10downloader)
        downloadUrl("http://okitgo.ru/misc/xml/cd_catalog.xml")
        Log.d(Tag,"onCreate done")
    }

    private fun downloadUrl(feedUrl: String) {
        Log.d(Tag, "downloadUrl starting AsyncTask")
        downloadData = DownloadData(this,xmlListView)
        if(feedUrl.isNotEmpty()){
            downloadData?.execute(feedUrl)
        }
        Log.d(Tag, "downloadUrl done")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(Tag, "Oncreate destroy")
        downloadData?.cancel(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?):Boolean {
        menuInflater.inflate(R.menu.catalog_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var feedUrl: String = ""
        when(item.itemId){
            R.id.mnuFree ->
                feedUrl = "http://okitgo.ru/misc/xml/cd_catalog.xml"
            R.id.mnuPaid ->
                feedUrl = "http://okitgo.ru/misc/xml/cd_catalog.xml"
            R.id.mnuSongs ->
                feedUrl = "http://okitgo.ru/misc/xml/cd_catalog.xml"
            R.id.mnutop10,R.id.mnutop25 ->
                {if(!item.isChecked){
                    item.isChecked = true
                    feedLimit = 35-feedLimit
                    Log.d(Tag,"onoptionstItemSelected ${item.title} changed")
                } else{
                    Log.d(Tag,"onoptionstItemSelected ${item.title} unchanged")
                }
                }
        }
        downloadUrl(feedUrl)
        return true
    }

    companion object {
        private class DownloadData(context: Context, listView: ListView) :
            AsyncTask<String, Void, String>() {
            private val Tag = "DownloadData"
            var propContext: Context by Delegates.notNull()
            var propListView: ListView by Delegates.notNull()

            init {
                propContext = context
                propListView = listView
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
//                Log.d(Tag, "Download data sync: $result")
                val parseApplications = ParseApplications()
                parseApplications.parse(result)

//                val arrayAdapter = ArrayAdapter<CatalogEntry>(propContext,R.layout.list_item_catalog,parseApplications.applications)
//                propListView.adapter = arrayAdapter
                val feedAdapter =
                    FeedAdapter(propContext, R.layout.list_record, parseApplications.applications)
                propListView.adapter = feedAdapter
            }

            override fun doInBackground(vararg params: String?): String {
                Log.d(Tag, "Sync is data: ${params[0]}")
                val rssFeed = downloadXML(params[0])
                if (rssFeed.isEmpty()) {
                    Log.e(Tag, "doinbackground: Error downloading")
                }
                return rssFeed
            }

            private fun downloadXML(url: String?): String {
                Log.d(Tag, "downloadXML: The response code ${URL(url).readText()}")
                return URL(url).readText()
            }
        }
    }

}
