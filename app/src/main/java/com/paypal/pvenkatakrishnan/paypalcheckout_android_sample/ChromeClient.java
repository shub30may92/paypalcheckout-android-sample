package com.paypal.pvenkatakrishnan.paypalcheckout_android_sample;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by pvenkatakrishnan on 10/13/16.
 */

public class ChromeClient extends WebChromeClient {
    @Override
    public void onProgressChanged(WebView view, int progress) {
        //...
    }
}