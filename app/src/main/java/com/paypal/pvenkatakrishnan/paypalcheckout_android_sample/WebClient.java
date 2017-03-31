package com.paypal.pvenkatakrishnan.paypalcheckout_android_sample;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.util.Log;

import com.paypal.paypal_xo_android.paypalxo;

/**
 * Created by pvenkatakrishnan on 10/13/16.
 */

public class WebClient extends WebViewClient {
    private AppCompatActivity activity;

    public WebClient(AppCompatActivity activity) {
        this.activity = activity;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        final Uri uri = Uri.parse(url);
        return paypalxo.getInstance().handleIfPaypalXO(url, this.activity);

    }

    public void onLoadResource(WebView view, String url) {
        paypalxo.getInstance().setupxojs();
    }

}