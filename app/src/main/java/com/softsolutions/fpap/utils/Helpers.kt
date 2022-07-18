package com.softsolutions.fpap.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.view.View
import android.view.WindowManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.softsolutions.fpap.R
import com.softsolutions.fpap.data.PrefRepository
import com.softsolutions.fpap.ui.common.isUrduMedium
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


fun setLocate(Lang: String,activity: Activity) {
    val locale = Locale(Lang)
    Locale.setDefault(locale)
    val config = Configuration()
    config.locale = locale
    activity.baseContext.resources.updateConfiguration(config, activity.baseContext.resources.displayMetrics)
    val prefRepository=PrefRepository(activity.application)
    prefRepository.saveMedium(Lang)
    isUrduMedium = prefRepository.getMedium() == "ur"
}
 fun loadLocate(activity: Activity) {
     val prefRepository=PrefRepository(activity.application)
     if (prefRepository.getMedium()!=null){
         setLocate(prefRepository.getMedium()!!, activity)
     }

}

fun createSelectedFileCopy(uri: Uri, context: Context): File? {
    val parcelFileDescriptor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        context.contentResolver.openFileDescriptor(uri, "r", null)
    } else {
        null
    }
    parcelFileDescriptor?.let {
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(context.cacheDir, context.contentResolver.getFileName(uri))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()
        return file
    }
    return null
}

private fun ContentResolver.getFileName(fileUri: Uri): String {

    var name = ""
    val returnCursor = this.query(fileUri, null, null, null, null)
    if (returnCursor != null) {
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        name = returnCursor.getString(nameIndex)
        returnCursor.close()
    }

    return name
}

fun setTextViewFont(textView:TextView,font:Int,context:Context,textSize:Int=0){
    val typeface = ResourcesCompat.getFont(context, font)
    textView.typeface = typeface
    //if (textSize!=0) textView.textSize=textSize.toFloat()
}

fun setTextViewFont(button:AppCompatButton,font:Int,context:Context,textSize:Int=0){
    val typeface = ResourcesCompat.getFont(context, font)
    button.typeface = typeface
}

fun makeProgressOnButton(view: Button, loadingTextRes: Int) {
    view.showProgress {
        buttonTextRes = loadingTextRes
        progressColor = view.currentTextColor
    }
}
fun hideProgressOnButton(view: Button, text:String) {
    view.hideProgress(text)
}

fun splitDateAndTime(dateTime: String): String {
    val date = dateTime.substring(0, dateTime.indexOf('T'))
    val time = dateTime.substring(dateTime.indexOf('T') + 1)
    val parser: DateFormat = SimpleDateFormat("yyyy-MM-dd")
    val formater: DateFormat = SimpleDateFormat("dd MMM, yyyy")
    val convertedDate: Date = parser.parse(date)
    return formater.format(convertedDate)
}

@SuppressLint("SetJavaScriptEnabled")
fun setWebView(context:Context,activity: Activity,webview:WebView) {
    val myChrome = ChromeWebView(context, activity)
    webview.webChromeClient = myChrome
    val webSettings: WebSettings = webview.getSettings()
    webSettings.javaScriptEnabled = true
    webSettings.allowFileAccess = true
    webSettings.setAppCacheEnabled(true)
    webSettings.javaScriptCanOpenWindowsAutomatically = true;
    webSettings.setPluginState(WebSettings.PluginState.ON);
}

fun Activity.makeStatusBarTransparent() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            statusBarColor = Color.TRANSPARENT
            setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                        or WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            )
        }
    }

}
@RequiresApi(Build.VERSION_CODES.R)
fun Activity.exitFullScreenMode() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.apply {
            this.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE)
            statusBarColor = ContextCompat.getColor(context, R.color.primary)
        }
    }

}
