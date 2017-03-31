package com.paypal.pvenkatakrishnan.paypalcheckout_android_sample;
import com.paypal.paypal_xo_android.paypalxo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import com.paypal.pvenkatakrishnan.paypalcheckout_android_sample.WebClient;
import android.content.res.Resources;



import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Resources res = getResources();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
    }

    public void handleClick(View view) {
        System.out.println("In button click");

        switch (view.getId()) {
            case  R.id.simple:
            default: {
                Intent i = new Intent(getBaseContext(), SimpleActivity.class);
                startActivity(i);
                break;
            }

            case R.id.direct: {
                Intent i = new Intent(getBaseContext(), DirectActivity.class);
                startActivity(i);
                break;
            }

            case R.id.checkoutjs: {
                Intent i = new Intent(getBaseContext(), CheckoutjsActivity.class);
                startActivity(i);
                break;
            }
        }
    }
}





