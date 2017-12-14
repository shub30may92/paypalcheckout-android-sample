package com.paypal.pvenkatakrishnan.paypalcheckout_android_sample;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.loopj.android.http.*;
import com.paypal.paypal_xo_android.paypalxo;

import android.view.View;

import android.content.res.Resources;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

import cz.msebera.android.httpclient.entity.mime.Header;

import static java.security.AccessController.getContext;

public class DirectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Resources res = getResources();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct);
        paypalxo.getInstance().setIntegration(paypalxo.IntegrationType.DIRECT, null, this).setDeepLink(res.getString(R.string.paypalxo_deep_link));
    }

    public void handleClick(View view) {
        System.out.println("In button click");
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get("https://paypalmerchant.herokuapp.com/getPPCheckoutURL", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    JSONObject content =new JSONObject(new String(responseBody));
                    String url = content.getString("checkoutURL");
                    paypalxo.getInstance().handleIfPaypalXO(url, DirectActivity.this);
                } catch (JSONException e) {

                    System.out.println("JSON not parsed" + e.getMessage());
                }

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
            }

        });

    }

    public void handlePPXoResult(Uri data) {
        System.out.println("Results: " + data);
    }

    @Override
    protected void onNewIntent(Intent intent){
        String PPCheckoutUrl = intent.getStringExtra("PYPLCheckoutFinishUrl");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //payment completed
        builder.setMessage("Paypal callback from Payment happened: " + PPCheckoutUrl)
                .setTitle("The End");

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();

        dialog.show();
    }

}
