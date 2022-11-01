package com.lmslsbe.ui.main.dashboard.detail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.lmslsbe.databinding.FragmentContentAndVideoBinding
import com.lmslsbe.utils.ChromeWebView

class ContentAndVideoSubFragment(val isContent:Boolean, val contentText:String, var videoLink:String):Fragment() {
    private lateinit var binding: FragmentContentAndVideoBinding
    val dummyVideo = "https://www.youtube.com/watch?v=fu3JcXBxxxY&ab_channel=ilmkidunya"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentContentAndVideoBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this

        if (isContent){
            binding.nestedscroll.visibility=View.VISIBLE
            binding.clVideo.visibility=View.GONE
            binding.tvDetail.text=getHtmlText(contentText)
        } else{
            binding.nestedscroll.visibility=View.GONE
            binding.clVideo.visibility=View.VISIBLE
            setWebView()
            if (videoLink.isNullOrEmpty()) videoLink= dummyVideo
            binding.webViewLecture.loadUrl(dummyVideo)
        }


        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView() {
        val myChrome = ChromeWebView(requireContext(), requireActivity())
        binding.webViewLecture.webChromeClient = myChrome
        val webSettings: WebSettings = binding.webViewLecture.getSettings()
        webSettings.javaScriptEnabled = true
        webSettings.allowFileAccess = true
        webSettings.setAppCacheEnabled(true)
        webSettings.javaScriptCanOpenWindowsAutomatically = true;
        webSettings.setPluginState(WebSettings.PluginState.ON);
        binding.webViewLecture.webViewClient = Browser_home()
    }

    inner class Browser_home : WebViewClient() {
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            binding.progressbar.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            binding.progressbar.visibility = View.GONE
            binding.webViewLecture.loadUrl("javascript:(function() { document.getElementsByTagName('video')[0].play(); })()");
        }

        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            super.onReceivedError(view, request, error)
            // Toast.makeText(requireContext(), "Error:$error", Toast.LENGTH_SHORT).show()
            // progressDialog.dismiss()
        }
    }

    private fun getHtmlText(description: String): CharSequence {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT).trim()
        } else {
            HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT).trim()
        }
    }
}