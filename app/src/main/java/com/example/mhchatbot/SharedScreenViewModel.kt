package com.example.mhchatbot

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel

class SharedScreenViewModel: ViewModel() {
    fun openBrowser(context: Context, url:String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            context.startActivity(intent)
        } catch(e: Exception){
            Toast.makeText(context, "Browser Can't be opened",Toast.LENGTH_SHORT).show()
        }
    }
    fun openGmailApp(uri: String,context: Context) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(uri)

        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        if (activities.isNotEmpty()){
            context.startActivity(intent)
        } else{
            Toast.makeText(context,"Gmail is not installed ", Toast.LENGTH_SHORT).show()
        }
    }
    fun shareApp(context: Context, msg:String){
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT,msg)
        }
        val chooserIntent = Intent.createChooser(shareIntent,null)
        context.startActivity(shareIntent)
    }
}