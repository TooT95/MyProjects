package com.example.helloworldkotlin

/**
 * Created by MrJ
 */
import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class ParseApplications {
    private val TAG = "ParseApplications"
    val applications = ArrayList<CatalogEntry>()
    fun parse(xmlData: String): Boolean {
        Log.d(TAG, "parse called with $xmlData")
        var status = true
        var isEntry = false
        var textValue = ""
        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()
            xpp.setInput(xmlData.reader())
            var eventType = xpp.eventType
            var currentRecord = CatalogEntry()
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = xpp.name?.toLowerCase()
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        Log.d(TAG, "parse: Starting tag for $tagName")
                        if (tagName == "cd") {
                            isEntry = true
                        }
                    }
                    XmlPullParser.TEXT -> textValue = xpp.text
                    XmlPullParser.END_TAG -> {
                        Log.d(TAG, "parse: Ending tag for $tagName")
                        if (isEntry) {
                            when (tagName) {
                                "cd" -> {
                                    applications.add(currentRecord)
                                    isEntry = false
                                    currentRecord = CatalogEntry()
                                }
                                "title" -> currentRecord.Title = textValue
                                "artist" -> currentRecord.Artist = textValue
                                "country" -> currentRecord.Country = textValue
                                "company" -> currentRecord.Company = textValue
                                "price" -> currentRecord.Price = textValue.toDouble()
                                "year" -> currentRecord.Year = textValue.toInt()
                            }
                        }
                    }
                }
                eventType = xpp.next()
            }
            for (app in applications) {
                Log.d(TAG, "***************")
                Log.d(TAG, app.toString())
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            status = false
        }
        return status
    }
}