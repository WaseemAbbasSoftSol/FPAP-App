package com.softsolutions.fpap.ui.main.dashboard.detail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.softsolutions.fpap.databinding.FragmentContentAndVideoBinding
import com.softsolutions.fpap.utils.ChromeWebView

class ContentAndVideoSubFragment(val isContent:Boolean):Fragment() {
    private lateinit var binding:FragmentContentAndVideoBinding
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
        } else{
            binding.nestedscroll.visibility=View.GONE
            binding.clVideo.visibility=View.VISIBLE
        }

        setWebView()
        binding.webViewLecture.loadUrl(dummyVideo)
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
}