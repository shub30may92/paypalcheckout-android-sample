package com.paypal.pvenkatakrishnan.paypalcheckout_android_sample;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ResponseTypeValues;

/**
 * Created by shubgupta on 12/14/17.
 */

public class NativeXoActivity extends AppCompatActivity{

    public static String MY_CLIENT_ID = "ARcshMywpyPeIC38POXa2JuUseTcYRjsVcdtnTrB64KV0R_MhqfFPzcV76ajQPmEUDKo8jw7zJS423ch";
    public static Uri MY_REDIRECT_URI = Uri.parse("https://paypalmerchant.herokuapp.com/thankyou");

    AuthorizationServiceConfiguration serviceConfig =
            new AuthorizationServiceConfiguration(
                    Uri.parse("https://www.sandbox.paypal.com/signin/authorize"), // authorization endpoint
                    Uri.parse("https://www.sandbox.paypal.com/v1/oauth2/token")); // token endpoint

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_xo);
        AuthorizationResponse resp = AuthorizationResponse.fromIntent(getIntent());
        AuthorizationException ex = AuthorizationException.fromIntent(getIntent());
        if (resp != null) {
            // authorization completed
        } else {
            // authorization failed, check ex for more details
        }
    }

    AuthorizationRequest.Builder authRequestBuilder =
            new AuthorizationRequest.Builder(
                    serviceConfig, // the authorization service configuration
                    MY_CLIENT_ID, // the client ID, typically pre-registered and static
                    ResponseTypeValues.CODE, // the response_type value: we want a code
                    MY_REDIRECT_URI); // the redirect URI to which the auth response is sent

    AuthorizationRequest authRequest = authRequestBuilder
            .setScope("openid email https://uri.paypal.com/web/experience/incontextxo")
            .setLoginHint("jdoe@user.example.com")
            .build();


    public void executeRequest(View view) {

        AuthorizationService authService = new AuthorizationService(this);

        Log.i("AUTH REQUEST", authRequest.toUri().toString());
        authService.performAuthorizationRequest(
        authRequest,
                PendingIntent.getActivity(this,0,new Intent(this,CheckoutjsActivity.class), 0),
                PendingIntent.getActivity(this,0,new Intent(this,SimpleActivity.class), 0));
    }
}
