package com.paypal.pvenkatakrishnan.paypalcheckout_android_sample;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ResponseTypeValues;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

/**
 * Created by shubgupta on 12/14/17.
 */

public class NativeXoActivity extends AppCompatActivity{

    public static String MY_CLIENT_ID = "AX93NErgg-F0VeBQ6pNLwa2VKQdw3BnKDvBnasIe_pKoprQyz6NiSf6XS7I1Qtro-VD4GP-AJdjT0Uz4"; //SandBox
//    public static String MY_CLIENT_ID = "ARcshMywpyPeIC38POXa2JuUseTcYRjsVcdtnTrB64KV0R_MhqfFPzcV76ajQPmEUDKo8jw7zJS423ch"; //LIVE
    public static Uri MY_REDIRECT_URI = Uri.parse("https://paypalmerchant.herokuapp.com/thankyou");
//    public static Uri MY_REDIRECT_URI = Uri.parse("http://myhost.com");

    private String mECToken;
    AuthorizationRequest authRequest;
    AuthorizationRequest.Builder authRequestBuilder;
    AuthorizationServiceConfiguration serviceConfig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createECToken();
        setContentView(R.layout.activity_native_xo);

        AuthorizationResponse resp = AuthorizationResponse.fromIntent(getIntent());
        AuthorizationException ex = AuthorizationException.fromIntent(getIntent());
        if (resp != null) {
            // authorization completed
        } else {
            // authorization failed, check ex for more details
        }
    }

    public void createECToken() {
        mECToken = "";
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("USER", "test-bus-01_api1.pp.com");
        params.add("PWD", "74NG2HJTV6Y7PSWE");
        params.add("SIGNATURE", "APreiuMSbjU.2DGNjIKidvhGFbj6Ag5lbprphYB4thQ9J.XBZTWCQiWU");
        params.add("METHOD", "SetExpressCheckout");
        params.add("VERSION", "93");
        params.add("PAYMENTREQUEST_0_PAYMENTACTION", "SALE");
        params.add("PAYMENTREQUEST_0_AMT", "10.95");
        params.add("PAYMENTREQUEST_0_CURRENCYCODE", "USD");
        params.add("RETURNURL", "https://example.com/success");
        params.add("CANCELURL", "https://example.com/cancel");
        httpClient.setConnectTimeout(50);

        httpClient.post("https://api-3t.sandbox.paypal.com/nvp", params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Log.i("SUCCESS", new String(responseBody, "UTF-8"));
                    String response = new String(responseBody, "UTF-8");
                    final String regex = "(?<==)(.*)(?=&TIMESTAMP)";

                    final Pattern pattern = Pattern.compile(regex);
                    final Matcher matcher = pattern.matcher(new String(responseBody, "UTF-8"));
                    while (matcher.find()) {
                        mECToken = matcher.group(0);
                        mECToken = mECToken.replace("%2d", "-");
                        System.out.println("Full match:  " + mECToken);
                    }
                } catch (UnsupportedEncodingException e) {
                    Log.i("EXCEPTION", "Unable to parse");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("FAILED", error.toString());
            }
        });
    }

    public void  executeRequest(View view) {
        serviceConfig =
                new AuthorizationServiceConfiguration(
                        Uri.parse("https://www.sandbox.paypal.com/signin/authorize"), // authorization endpoint
                        Uri.parse("https://www.sandbox.paypal.com/v1/oauth2/token")); // token endpoint

        authRequestBuilder =
                new AuthorizationRequest.Builder(
                        serviceConfig, // the authorization service configuration
                        MY_CLIENT_ID, // the client ID, typically pre-registered and static
                        ResponseTypeValues.CODE, // the response_type value: we want a code
                        MY_REDIRECT_URI); // the redirect URI to which the auth response is sent

        authRequestBuilder.setPrompt("login");

        Map<String, String> additionalParameters = new HashMap<String, String>();
        additionalParameters.put("flowId", mECToken);
        authRequestBuilder.setAdditionalParameters(additionalParameters);

        authRequest = authRequestBuilder
                .setScope("openid email https://uri.paypal.com/web/experience/incontextxo")
                .setLoginHint("jdoe@user.example.com")
                .build();

        AuthorizationService authService = new AuthorizationService(this);

        Log.i("AUTH REQUEST", authRequest.toUri().toString());
        authService.performAuthorizationRequest(
        authRequest,
                PendingIntent.getActivity(this,0,new Intent(this,CheckoutjsActivity.class), 0),
                PendingIntent.getActivity(this,0,new Intent(this,SimpleActivity.class), 0));
    }
}
