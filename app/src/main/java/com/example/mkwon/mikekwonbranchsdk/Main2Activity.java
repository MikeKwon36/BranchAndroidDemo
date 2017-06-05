package com.example.mkwon.mikekwonbranchsdk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;

public class Main2Activity extends AppCompatActivity {

    TextView mTextview;
    Button mButton;
    String mkwon;
    Branch branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mTextview = (TextView) findViewById(R.id.tvTest);
        mButton = (Button) findViewById(R.id.bBack);

        Log.i("***kwon***", "App's second screen onCreate called");


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("***KWON***", "2nd screen onStart called");

        branch = Branch.getInstance(getApplicationContext());
        Log.i("***kwon***", "Branch object initialized in 2nd activity at memory = " + branch.toString());

        branch.initSession(new Branch.BranchReferralInitListener(){
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {
                    Log.i("***KWON***", "Json value for 'channel' key = " + referringParams.optString("~channel"));
                    Log.i("***KWON***", "Json value for 'marketing title' key = " + referringParams.optString("$marketing_title"));
                    mkwon = referringParams.optString("kwon", "No Deep link data received");
                    Log.i("***KWON***", "mkwon string value = " + mkwon);
                    mTextview.setText(mkwon);
                }
                else {
                    mkwon = "no deep link data received";
                    mTextview.setText(mkwon);
                    Log.i("***kwon***", error.getMessage());
                }
            }
        }, this.getIntent().getData(), this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        branch.closeSession();
    }
}
