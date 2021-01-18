package com.example.moviecatalogue.ui.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.moviecatalogue.core.utils.gone
import com.example.moviecatalogue.core.utils.visible
import com.example.moviecatalogue.databinding.FragmentWebViewBinding

@SuppressLint("SetJavaScriptEnabled")
class WebViewFragment : Fragment() {
    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher
            ?.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.webview.canGoBack()) {
                        binding.webview.goBack()
                    } else {
                        super.remove()
                        view?.findNavController()?.popBackStack()
                    }
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = WebViewFragmentArgs.fromBundle(arguments as Bundle).url

        binding.webview.settings.javaScriptEnabled = true
        binding.webview.settings.cacheMode = WebSettings.LOAD_NO_CACHE

        binding.webview.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                binding.progress.visible()
            }

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("SSL Certificate Error")
                builder.setPositiveButton("continue") { _, _ -> handler?.proceed() }
                builder.setNegativeButton("cancel") { _, _ -> handler?.cancel() }
                val dialog = builder.create()
                dialog.show()
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                binding.progress.gone()
            }
        }

        binding.webview.loadUrl(url)
        binding.progress.progress = 1
        binding.progress.max = 100

        binding.webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                binding.progress.progress = newProgress
            }
        }
    }
}