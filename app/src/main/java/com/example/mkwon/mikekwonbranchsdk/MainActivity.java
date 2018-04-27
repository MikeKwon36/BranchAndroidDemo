package com.example.mkwon.mikekwonbranchsdk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import org.json.JSONObject;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.SharingHelper;
import io.branch.referral.util.LinkProperties;
import io.branch.referral.util.ShareSheetStyle;

public class MainActivity extends AppCompatActivity {

    Button mButtonA, mButtonB;
    Branch branch;
    FloatingActionButton fab;
    BranchUniversalObject buo, buo2;
    LinkProperties linkProperties;
    ShareSheetStyle shareSheetStyle;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        mButtonA = (Button) findViewById(R.id.bShareContentA);
        mButtonB = (Button) findViewById(R.id.bShareContentB);
        Branch.enablePlayStoreReferrer(1000L);
        branch = Branch.getInstance(getApplicationContext());
        branch.setDebug();
        branch.initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {
                    branch.setIdentity("The Kwon");
                } else {
                    Log.i("MyApp", error.getMessage());
                }
            }
        }, this.getIntent().getData(), this);

        buo = new BranchUniversalObject();
        buo2 = new BranchUniversalObject();
        linkProperties = new LinkProperties();

        shareSheetStyle = new ShareSheetStyle(MainActivity.this, "Check this out!", "This is the first share: ")
                .setCopyUrlStyle(getResources().getDrawable(android.R.drawable.ic_menu_send), "Copy", "Added to clipboard")
                .setMoreOptionStyle(getResources().getDrawable(android.R.drawable.ic_menu_search), "Show more")
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.FACEBOOK)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.EMAIL)
                .setAsFullWidthStyle(true)
                .setSharingTitle("Share With");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        mButtonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buo.setCanonicalIdentifier("monster/12345")
                        .setTitle("Meet Mr. Squiggles")
                        .setContentDescription("This is the first share test")
                        .setContentImageUrl("http://s7d9.scene7.com/is/image/JCPenney/DP1215201617102221M")
                        .addContentMetadata("userId", "12345")
                        .addContentMetadata("userName", "Josh")
                        .addContentMetadata("monsterName", "Mr. Squiggles");

                linkProperties.setChannel("facebook").setFeature("sharing");

                buo.showShareSheet(MainActivity.this, linkProperties, shareSheetStyle,
                        new Branch.BranchLinkShareListener() {
                            @Override
                            public void onShareLinkDialogLaunched() {
                            }

                            @Override
                            public void onShareLinkDialogDismissed() {
                            }

                            @Override
                            public void onLinkShareResponse(String sharedLink, String sharedChannel, BranchError error) {
                            }

                            @Override
                            public void onChannelSelected(String channelName) {
                            }
                        });
            }
        });

        mButtonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buo2.setCanonicalIdentifier("monster/12345")
                        .setTitle("Meet Mr. kwon")
                        .setContentDescription("This is the second share test")
                        .setContentImageUrl("http://s7d9.scene7.com/is/image/JCPenney/DP1215201617102221M")
                        .addContentMetadata("userId", "12345")
                        .addContentMetadata("userName", "Josh")
                        .addContentMetadata("kwon", "kwon");

                LinkProperties linkProperties = new LinkProperties()
                        .setChannel("facebook")
                        .setFeature("sharing");

                buo2.showShareSheet(MainActivity.this, linkProperties, shareSheetStyle,
                        new Branch.BranchLinkShareListener() {
                            @Override
                            public void onShareLinkDialogLaunched() {
                            }

                            @Override
                            public void onShareLinkDialogDismissed() {
                            }

                            @Override
                            public void onLinkShareResponse(String sharedLink, String sharedChannel, BranchError error) {
                            }

                            @Override
                            public void onChannelSelected(String channelName) {
                            }
                        });
            }
        });

        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        branch.logout();
        branch.closeSession();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
