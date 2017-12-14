package com.paypal.pvenkatakrishnan.paypalcheckout_android_sample;
import com.paypal.paypal_xo_android.paypalxo;


import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.content.res.Resources;
import android.webkit.WebViewClient;


import java.util.logging.Logger;

public class CheckoutjsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Resources res = getResources();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkoutjs);

        final AppCompatActivity selfActivity = this;
        WebView browser = (WebView) findViewById(R.id.webview);
//        browser.setWebViewClient(new WebClient(this));
        browser.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                final Uri uri = Uri.parse(url);
                return paypalxo.getInstance().handleIfPaypalXO(url, selfActivity);

            }

            //the following ONLY if your web integration uses the new checkoutjs for Paypal Checkout
            public void onLoadResource(WebView view, String url) {
                paypalxo.getInstance().setupxojs();
            }
        });
        browser.getSettings().setLoadWithOverviewMode(true);

        browser.getSettings().setUseWideViewPort(true);
        browser.loadUrl("https://paypalmerchant.herokuapp.com/cartv2");
//        browser.loadUrl("https://google.com");
        paypalxo.getInstance().setIntegration(paypalxo.IntegrationType.WEBVIEW, browser, this).setDeepLink(res.getString(R.string.paypalxo_deep_link));

    }
}





