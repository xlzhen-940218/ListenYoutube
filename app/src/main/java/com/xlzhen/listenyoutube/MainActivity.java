package com.xlzhen.listenyoutube;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private BackgroundWebView webView;
    private static final String VIDEO_REPLACE_AUDIO = "for(var i=0;i<document.getElementsByTagName('video').length;i++){\n" +
            "    var parent = document.getElementsByTagName('video')[i].parentElement;\n" +
            "    var video = parent.innerHTML;\n" +
            "    var audio = video.replace(/video/g, 'audio');\n" +
            "    parent.innerHTML = audio;\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.evaluateJavascript(VIDEO_REPLACE_AUDIO, null);
            }
        });
        webView.loadUrl("https://m.youtube.com");
        webView.postDelayed(changeVideoToAudioTagRunnable,5000);
    }

    private Runnable changeVideoToAudioTagRunnable = new Runnable() {
        @Override
        public void run() {
            webView.evaluateJavascript(VIDEO_REPLACE_AUDIO, null);
            webView.postDelayed(changeVideoToAudioTagRunnable,2000);
        }
    };


}