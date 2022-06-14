package com.softsolutions.fpap.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat
import com.softsolutions.fpap.data.PrefRepository
import com.softsolutions.fpap.ui.common.isUrduMedium
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

fun setTextViewFont(textView:TextView,font:Int,context:Context,textSize:Int=0){
    val typeface = ResourcesCompat.getFont(context, font)
    textView.typeface = typeface
    //if (textSize!=0) textView.textSize=textSize.toFloat()
}

fun setTextViewFont(button:AppCompatButton,font:Int,context:Context,textSize:Int=0){
    val typeface = ResourcesCompat.getFont(context, font)
    button.typeface = typeface
}

