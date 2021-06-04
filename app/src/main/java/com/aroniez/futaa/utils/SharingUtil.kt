package com.aroniez.futaa.utils

import android.content.Context
import android.content.Intent


object SharingUtil {
    fun shareArticle(context: Context, title: String, url: String) {
        val appPackageName = context.packageName
        val toShare = "$title\nRead more at $url\n\nCheck out our app here \nhttps://play.google.com/store/apps/details?id=$appPackageName"
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, toShare)
        context.startActivity(Intent.createChooser(shareIntent, "Share using ..."))
    }
}