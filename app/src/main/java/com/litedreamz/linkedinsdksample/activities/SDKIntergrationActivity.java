package com.litedreamz.linkedinsdksample.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.AccessToken;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.litedreamz.linkedinsdksample.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Dulan Dissanayake on 3/6/2015.
 */
public class SDKIntergrationActivity extends ActionBarActivity {

    private static final String TAG = SDKIntergrationActivity.class.getCanonicalName();

    private static final int TAG_BUTTON_GENERATE_HASH = 0;
    private static final int TAG_BUTTON_LOGIN = 1;
    private static final int TAG_BUTTON_GET_CURRENT_USER = 2;
    private static final int TAG_BUTTON_GET_USER_CONNECTIONS = 3;

    private LISessionManager liSessionManager;
    private Activity currentActivity;

    private TextView tvContent;
    private Button btnGenerateHash;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentActivity = this;

        liSessionManager = LISessionManager.getInstance(getApplicationContext());

        initUI();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        liSessionManager.onActivityResult(this, requestCode, resultCode, data);
    }

    private void initUI(){

        tvContent = (TextView)findViewById(R.id.tv_content);

        btnGenerateHash = (Button)findViewById(R.id.btn_hashAndPackage);
        btnGenerateHash.setTag(TAG_BUTTON_GENERATE_HASH);
        btnGenerateHash.setOnClickListener(btn_OnClickListener);

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setTag(TAG_BUTTON_LOGIN);
        btnLogin.setOnClickListener(btn_OnClickListener);


    }

    View.OnClickListener btn_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int iTag = (Integer) view.getTag();

            switch (iTag){
                case TAG_BUTTON_GENERATE_HASH:
                    generateHashAndPackage();
                    break;

                case TAG_BUTTON_LOGIN:
                    loginToLinkedIn();
                    break;

                case TAG_BUTTON_GET_CURRENT_USER:
                    //getCurrentUser();
                    break;

                case TAG_BUTTON_GET_USER_CONNECTIONS:
                    //getUserConnections();
                    break;

            }

        }
    };

    //Step 01 ) Step 01) Generate Hash
    private void generateHashAndPackage(){

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                String outPut = getString(R.string.info_message_keyhash_and_package)+"\nPACKAGE NAME = "+info.packageName+"\nHASH KEY = "+Base64.encodeToString(md.digest(), Base64.NO_WRAP);

                Log.d(TAG,outPut);

                tvContent.setText(outPut);

            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            Log.d(TAG, e.getMessage(), e);
        }
    }


    //Step 02 ) Login to Linkedin
    private void loginToLinkedIn(){
//        LocalSharedPreferenceStorage localSharedPreferenceStorage = LocalSharedPreferenceStorage.getInstance(context);
//        Token prevToken = localSharedPreferenceStorage.getLinkedinAccessToken();
//        if(prevToken != null){
//            AccessToken token = AccessToken.buildAccessToken(prevToken.getToken());
//
//            liSessionManager.init(token);
//        }

        liSessionManager.init(currentActivity, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                Toast.makeText(getApplicationContext(), "success" + LISessionManager.getInstance(getApplicationContext()).getSession().getAccessToken().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAuthError(LIAuthError error) {
                Log.e("","Error="+error.toString());
            }
        }, true);
    }

    private static Scope buildScope() {
        return Scope.build(Scope.R_FULLPROFILE, Scope.R_EMAILADDRESS);
    }


}
