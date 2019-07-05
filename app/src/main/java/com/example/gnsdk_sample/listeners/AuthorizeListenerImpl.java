package com.example.gnsdk_sample.listeners;

import com.amazon.identity.auth.device.AuthError;
import com.amazon.identity.auth.device.api.authorization.AuthCancellation;
import com.amazon.identity.auth.device.api.authorization.AuthorizeListener;
import com.amazon.identity.auth.device.api.authorization.AuthorizeResult;

public class AuthorizeListenerImpl extends AuthorizeListener {
    /* Authorization was completed successfully. */
    @Override
    public void onSuccess(final AuthorizeResult authorizeResult) {
        final String authorizationCode = authorizeResult.getAuthorizationCode();
        final String redirectUri = authorizeResult.getRedirectURI();
        final String clientId = authorizeResult.getClientId();

        System.out.println("authorizationCode:" + authorizationCode + "\nredirectUri:" + redirectUri + "\nclientId:" + clientId);
    }

    /* There was an error during the attempt to authorize the application. */
    @Override
    public void onError(final AuthError authError) {
    }

    /* Authorization was cancelled before it could be completed. */
    @Override
    public void onCancel(final AuthCancellation authCancellation) {
    }
}
