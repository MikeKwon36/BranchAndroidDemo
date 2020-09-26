package com.example.mkwon.mikekwonbranchsdk;

import android.content.Intent;
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
import io.branch.referral.util.BRANCH_STANDARD_EVENT;
import io.branch.referral.util.BranchContentSchema;
import io.branch.referral.util.BranchEvent;
import io.branch.referral.util.ContentMetadata;
import io.branch.referral.util.CurrencyType;
import io.branch.referral.util.LinkProperties;
import io.branch.referral.util.ProductCategory;
import io.branch.referral.util.ShareSheetStyle;

public class MainActivity extends AppCompatActivity {

    Button mButtonA, mButtonB;
    Branch branch;
    FloatingActionButton fab;
    BranchUniversalObject buo, buo2;
    LinkProperties linkProperties;
    ShareSheetStyle shareSheetStyle;
    Toolbar toolbar;
    Boolean isThisAnInstantApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MyApp", "onCreate() called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        mButtonA = (Button) findViewById(R.id.bShareContentA);
        mButtonB = (Button) findViewById(R.id.bShareContentB);

        buo = new BranchUniversalObject();
        buo2 = new BranchUniversalObject();
        linkProperties = new LinkProperties();

        shareSheetStyle = new ShareSheetStyle(MainActivity.this, "Check this out!", "This is the first share: ")
                .setCopyUrlStyle(getResources().getDrawable(android.R.drawable.ic_menu_send), "Copy", "Added to clipboard")
                .setMoreOptionStyle(getResources().getDrawable(android.R.drawable.ic_menu_search), "Show more")
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.MESSAGE)
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

                linkProperties.setChannel("Kwon456").setFeature("sharing").addTag("test1").addTag("test2");

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
                        .setCanonicalUrl("https://www.test.com")
                        .setTitle("Meet Mr. kwon")
                        .setContentDescription("This is the second share test")
                        .setContentImageUrl("http://s7d9.scene7.com/is/image/JCPenney/DP1215201617102221M");

                LinkProperties linkProperties = new LinkProperties()
                        .setChannel("Kwon123")
                        .addTag("test1")
                        .addTag("test2")
                        .setFeature("sharing");

                buo2.showShareSheet(MainActivity.this,linkProperties,shareSheetStyle,null);
            }
        });

    }

    @Override
    protected void onStart() {
        Log.i("MyApp", "onStart() called");
        super.onStart();
        branch = Branch.getInstance(this);
        branch.setRequestMetadata("$marketing_cloud_visitor_id","test");

        // Setting custom intent with deep link to simulate a deep linked session
        Intent unitTest= new Intent(this, MainActivity.class);
        unitTest.putExtra("branch","https://kwon36.app.link/6OGwuG1st5");
        this.setIntent(unitTest);

        branch.initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                Log.i("MyApp", "Branch SDK callback received");
                if (error == null) {
                    branch.setIdentity("The Kwon");
                    Log.i("MyApp", referringParams.toString());
                    if(referringParams.optBoolean("+match_guaranteed",false)){
                        Log.i("MyApp","session deep linked!");
                    }
                } else {
                    Log.i("MyApp", error.getMessage());
                }
                BranchUniversalObject buo = new BranchUniversalObject()
                        .setCanonicalIdentifier("myprod/1234")
                        .setCanonicalUrl("https://test_canonical_url")
                        .setTitle("test_title")
                        .setContentMetadata(
                                new ContentMetadata()
                                        .addCustomMetadata("sub1", "buo set")
                                        .addCustomMetadata("custom_metadata_key1", "custom_metadata_val1")
                                        .addImageCaptions("image_caption_1", "image_caption2", "image_caption3")
                                        .setAddress("Street_Name", "test city", "test_state", "test_country", "test_postal_code")
                                        .setLocation(-151.67, -124.0)
                                        .setPrice(10.0, CurrencyType.USD)
                                        .setProductBrand("test_prod_brand")
                                        .setProductCategory(ProductCategory.APPAREL_AND_ACCESSORIES)
                                        .setProductName("test_prod_name")
                                        .setProductCondition(ContentMetadata.CONDITION.EXCELLENT)
                                        .setProductVariant("test_prod_variant")
                                        .setQuantity(1.5)
                                        .setSku("test_sku")
                                        .setContentSchema(BranchContentSchema.COMMERCE_PRODUCT))
                        .addKeyWord("keyword1")
                        .addKeyWord("keyword2");
                new BranchEvent(BRANCH_STANDARD_EVENT.PURCHASE)
                        .setAffiliation("test_affiliation")
                        .setCustomerEventAlias("my_custom_alias")
                        .setCoupon("Coupon Code")
                        .setCurrency(CurrencyType.USD)
                        .setDescription("Customer added item to cart")
                        .setShipping(0.0)
                        .setTax(9.75)
                        .setRevenue(1.5)
                        .setSearchQuery("Test Search query")
                        .addCustomDataProperty("sub1", "branchevent set")
                        .addCustomDataProperty("Custom_Event_Property_Key2", "Custom_Event_Property_val2")
                        .addContentItems(buo)
                        .logEvent(getApplicationContext());
            }
        }, this.getIntent().getData(), this);
    }

    @Override
    protected void onResume() {
        Log.i("MyApp", "onResume() called");
        super.onResume();
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
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
