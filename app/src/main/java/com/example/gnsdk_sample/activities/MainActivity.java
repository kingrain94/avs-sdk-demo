package com.example.gnsdk_sample.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.amazon.identity.auth.device.api.authorization.AuthorizationManager;
import com.amazon.identity.auth.device.api.authorization.AuthorizeRequest;
import com.amazon.identity.auth.device.api.authorization.ScopeFactory;
import com.amazon.identity.auth.device.api.workflow.RequestContext;
import com.example.gnsdk_sample.R;
import com.example.gnsdk_sample.listeners.AuthorizeListenerImpl;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static final String PRODUCT_ID = "INSERT YOUR PRODUCT ID FROM AMAZON DEVELOPER CONSOLE";
    private static final String PRODUCT_DSN = "INSERT UNIQUE DSN FOR YOUR DEVICE";
    private static final String CODE_CHALLENGE = "INSERT CODE CHALLENGE FROM DEVICE FOR THIS REQUEST";
    private static final String CODE_CHALLENGE_METHOD = "S256";

    private RequestContext mRequestContext;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestContext = RequestContext.create(this);
        mRequestContext.registerListener(new AuthorizeListenerImpl());

        // Find the button with the login_with_amazon ID
        // and set up a click handler
        mLoginButton = (Button) findViewById(R.id.login_with_amazon);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final JSONObject scopeData = new JSONObject();
                final JSONObject productInstanceAttributes = new JSONObject();

                try {
                    productInstanceAttributes.put("deviceSerialNumber", PRODUCT_DSN);
                    scopeData.put("productInstanceAttributes", productInstanceAttributes);
                    scopeData.put("productID", PRODUCT_ID);

                    AuthorizationManager.authorize(new AuthorizeRequest.Builder(mRequestContext)
                            .addScopes(ScopeFactory.scopeNamed("alexa:voice_service:pre_auth"),
                                    ScopeFactory.scopeNamed("alexa:all", scopeData))
                            .forGrantType(AuthorizeRequest.GrantType.AUTHORIZATION_CODE)
                            .withProofKeyParameters(CODE_CHALLENGE, CODE_CHALLENGE_METHOD)
                            .build());
                } catch (JSONException e) {
                    // handle exception here
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRequestContext.onResume();
    }
}
