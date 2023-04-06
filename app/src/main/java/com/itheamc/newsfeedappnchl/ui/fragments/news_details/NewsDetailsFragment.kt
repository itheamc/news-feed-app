package com.itheamc.newsfeedappnchl.ui.fragments.news_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.itheamc.newsfeedappnchl.databinding.FragmentNewsDetailsBinding

class NewsDetailsFragment : Fragment() {
    // FragmentNewsDetailsBinding
    private var _binding: FragmentNewsDetailsBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initializing FragmentNewsDetailsBinding
        _binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webView.webViewClient = AWebViewClient()

        /**
         * Getting web url from the arguments that we have passed from NewsFragment
         * with key ---> 'webUrl'
         */
        arguments?.let {
            val webUrl = it.get("webUrl") as String?

            webUrl?.let { url ->
                binding.webView.loadUrl(url)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Inner class to customize the web view behaviour
     */
    inner class AWebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.webProgressBar.visibility = View.GONE
        }
    }
}