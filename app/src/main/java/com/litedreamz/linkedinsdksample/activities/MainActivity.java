package com.litedreamz.linkedinsdksample.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.litedreamz.linkedinsdksample.R;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private static final int TAG_BUTTON_GET_CURRENT_USER = 1;
    private static final int TAG_BUTTON_GET_USER_CONNECTIONS = 2;

    private TextView tvDescription;
    private Button btnGetCurrentUser;
    private Button btnUserConnections;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    public void initUI(){
        tvDescription = (TextView)findViewById(R.id.tv_content);

        btnGetCurrentUser = (Button)findViewById(R.id.btn_getCurrentUser);
        btnGetCurrentUser.setTag(TAG_BUTTON_GET_CURRENT_USER);
        btnGetCurrentUser.setOnClickListener(btn_OnClickListener);

        btnUserConnections = (Button)findViewById(R.id.btn_getUserConnections);
        btnUserConnections.setTag(TAG_BUTTON_GET_USER_CONNECTIONS);
        btnUserConnections.setOnClickListener(btn_OnClickListener);

    }

    View.OnClickListener btn_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int iTag = (Integer) view.getTag();

            switch (iTag){
                case TAG_BUTTON_GET_CURRENT_USER:

                    break;

                case TAG_BUTTON_GET_USER_CONNECTIONS:

                    break;

            }

        }
    };


}
