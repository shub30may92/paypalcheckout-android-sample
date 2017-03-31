package com.paypal.pvenkatakrishnan.paypalcheckout_android_sample;
import com.paypal.paypal_xo_android.paypalxo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.content.res.Resources;

public class SimpleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Resources res = getResources();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        WebView browser = (WebView) findViewById(R.id.webview);
        browser.setWebViewClient(new WebClient(this));
        browser.getSettings().setLoadWithOverviewMode(true);
        browser.getSettings().setUseWideViewPort(true);
        browser.loadUrl("https://paypalmerchant.herokuapp.com/cart");
        paypalxo.getInstance().setIntegration(paypalxo.IntegrationType.WEBVIEW, browser, this).setDeepLink(res.getString(R.string.paypalxo_deep_link));
    }

}





