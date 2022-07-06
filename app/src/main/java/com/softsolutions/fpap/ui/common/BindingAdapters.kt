package com.softsolutions.fpap.ui.common

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.mikelau.views.shimmer.ShimmerRecyclerViewX

import de.hdodenhof.circleimageview.CircleImageView
//
//@BindingAdapter("imageTint")
//fun setImage(view: ImageView, colorResource: Int) {
//    view.setColorFilter(
//        ContextCompat.getColor
//            (view.context, colorResource), android.graphics.PorterDuff.Mode.SRC_IN
//    )
//
//}
//
@BindingAdapter("imageUri")
fun loadImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.context).load(imageUrl).into(view)
}

@BindingAdapter(value = ["imageUrl", "default"], requireAll = false)
fun loadImage(view: ImageView, imageUrl: String?, default: Drawable?) {
    if (default == null) {
        Glide.with(view.context).load(imageUrl).into(view)
    } else {
        Glide.with(view.context).load(imageUrl).placeholder(default).into(view)
    }
}

@BindingAdapter(value = ["imageUrl", "default", "borderColor"], requireAll = false)
fun loadImage(
    view: CircleImageView,
    imageUrl: String?,
    default: Drawable?,
    borderColor: Int = Color.WHITE
) {
    if (default == null) {
        Glide.with(view.context).load(imageUrl).into(view)
    } else {
        Glide.with(view.context).load(imageUrl).placeholder(default).into(view)
    }
    view.borderColor = borderColor
}

////@BindingAdapter("imagesList")
////fun setImages(view: ImageSlider, imagesList: List<SlideModel>) {
////    view.setImageList(imagesList)
////}
//
//@BindingAdapter("html")
//fun setHtml(view: TextView, html: String) {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//        view.text = Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
//    } else {
//        view.text = Html.fromHtml(html)
//    }
//}
//
//@BindingAdapter("error")
//fun setError(view: EditText, error: String?) {
//    if (!error.isNullOrEmpty())
//        view.error = error
//}

//@BindingAdapter(value = ["isLoading", "newText"], requireAll = false)
//fun setLoading(view: Button, isLoading: Boolean, newText: String?) {
//    view.attachTextChangeAnimator()
//    if (isLoading) {
//        view.isEnabled = false
//    } else {
//        view.isEnabled = true
//        view.hideProgress(newText)
//    }
//}

//@BindingAdapter(
//    value = ["itemsList", "itemLayout", "itemClickListener", "hasFixSize", "onItemViewClick"],
//    requireAll = false
//)
//fun <T> setItems(
//    view: RecyclerView, itemsList: List<T>, layout: Int,
//    itemClickListener: OnListItemClickListener<T>?, hasFixSize: Boolean = false,
//    onItemViewClick: OnItemViewClickListener<T>?
//) {
//    val mAdapter = GenericRecyclerViewAdapter(itemsList, layout)
//    view.isNestedScrollingEnabled = false
//    view.setHasFixedSize(hasFixSize)
//    view.adapter = mAdapter
//    mAdapter.setItemClickListener(itemClickListener)
//    mAdapter.onItemViewClick = onItemViewClick
//}

@BindingAdapter(
    value = ["itemsList", "itemLayout", "itemClickListener", "hasFixSize", "onItemViewClick"],
    requireAll = false
)
fun <T> setItems(
    view: ShimmerRecyclerViewX, itemsList: List<T>, layout: Int,
    itemClickListener: OnListItemClickListener<T>?, hasFixSize: Boolean = false,
    onItemViewClick: OnItemViewClickListener<T>?
) {
    val mAdapter = GenericRecyclerViewAdapter(itemsList, layout)
    view.isNestedScrollingEnabled = false
    view.setHasFixedSize(hasFixSize)
    view.adapter = mAdapter
    mAdapter.setItemClickListener(itemClickListener)
    mAdapter.onItemViewClick = onItemViewClick
    if (itemsList.isEmpty()){
        view.showShimmerAdapter()
    }
}

@BindingAdapter(value = ["itemsList", "isSpinner"], requireAll = false)
fun <T> setSpinnerItems(view: MaterialAutoCompleteTextView, items: List<T>, isSpinner: Boolean = false) {
    val adapter = ArrayAdapter<T>(
        view.context,
        android.R.layout.simple_spinner_dropdown_item, items
    )
    view.setAdapter(adapter)
    if (isSpinner) {
        view.setOnClickListener { view.showDropDown() } //show drop down like spinner
    } else if (view is MultiAutoCompleteTextView) {
        view.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
    }
}

//@BindingAdapter(value = ["spItemsList", "isSmall"], requireAll = false)
//fun <T> setSpinnerItems(view: MaterialSpinner, spItemsList: List<T>, isSmall: Boolean = false) {
//    val headerLayout: Int
//    val spinnerItem: Int
//    if (isSmall) {
//        headerLayout = R.layout.item_spinner_header_small
//        spinnerItem = R.layout.item_spinner_small
//    } else {
//        headerLayout = R.layout.item_spinner_header
//        spinnerItem = R.layout.item_spinner
//    }
//    val adapter = ArrayAdapter(
//        view.context,
//        headerLayout, R.id.text1, spItemsList
//    )
//    adapter.setDropDownViewResource(spinnerItem)
//    view.adapter = adapter
//}
//
////    For spinner
//@BindingAdapter("spItemsList")
//fun <T> setSpinnerItems(view: Spinner, spItemsList: List<T>) {
//    val adapter = ArrayAdapter(
//        view.context,
//        R.layout.item_spinner_header, R.id.text1, spItemsList
//    )
//    adapter.setDropDownViewResource(R.layout.item_spinner)
//    view.adapter = adapter
//}
//
////    For spinner with white text
//@BindingAdapter("spItemsListWhite")
//fun <T> setSpinnerItemsWhite(view: Spinner, spItemsList: List<T>) {
//    Log.d("spinner","******** data setting " + spItemsList.size )
//    val adapter = ArrayAdapter(
//        view.context,
//        R.layout.item_spinner_header_white, R.id.text1, spItemsList
//    )
//    adapter.setDropDownViewResource(R.layout.item_spinner)
//    view.adapter = adapter
//}
//
//@BindingAdapter("currentItem")
//fun setCurrentItem(view: Spinner, currentItem: Int) {
//    if (currentItem == -1) return
//    view.setSelection(currentItem)
//}
//
//@BindingAdapter("selectedPos")
//fun setSelectedItem(view: Spinner, selectedPos: Int) {
//    view.setSelection(selectedPos)
//}
//
@BindingAdapter("navigation")
fun navigateUp(view: AppCompatImageView, isEnabled: Boolean) {
    view.setOnClickListener {
        it.findNavController().navigateUp()
    }
}

@BindingAdapter("navigation")
fun navigateBack(view: MaterialButton, isEnabled: Boolean) {
    view.setOnClickListener {
        it.findNavController().navigateUp()
    }
}
//@BindingAdapter("isSelected")
//fun select(view: View, isSelected: Boolean) {
//    view.isSelected = isSelected
//}

//@BindingAdapter(value = ["isLoading", "newText"], requireAll = false)
//fun setLoading(view: Button, isLoading: Boolean, newText: String?) {
//    view.attachTextChangeAnimator()
//    if (isLoading) {
//        view.isEnabled = false
//    } else {
//        view.isEnabled = true
//        view.hideProgress(newText)
//    }
//}


//        For swip to refresh
//@BindingAdapter("isRefreshing")
//fun setRefreshing(view: SwipeRefreshLayout, isRefreshing: Boolean) {
//    view.isRefreshing = isRefreshing
//}

@BindingAdapter(value = ["webUrl", "webViewClient"], requireAll = false)
fun loadWebUrl(view: WebView, webUrl: String? = null, webViewClient: WebViewClient? = WebViewClient()){
    view.loadUrl(webUrl!!)
    view.settings.javaScriptEnabled = true
    view.webViewClient = webViewClient!!
}

//@BindingAdapter(value = ["listDataHeader", "listChildData", "headerLayout", "childLayout"])
//fun <T, U> setListItems(
//    view: ExpandableListView,
//    listDataHeader: List<T>,
//    listChildData: HashMap<T, List<U>>,
//    headerLayout: Int,
//    childLayout: Int
//) {
//    val adapter =
//        GenericExpandableListAdapter(headerLayout, childLayout, listDataHeader, listChildData)
//    view.setAdapter(adapter)
//}
