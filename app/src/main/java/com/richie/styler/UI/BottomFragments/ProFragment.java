package com.richie.styler.UI.BottomFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.richie.styler.R;
import com.richie.styler.UI.Base.BaseFragment;
import com.richie.styler.UTILS.Constants;
import com.richie.styler.util.IabHelper;
import com.richie.styler.util.IabResult;
import com.richie.styler.util.Inventory;
import com.richie.styler.util.Purchase;

/**
 * Created by User on 22-05-2017.
 */

public class ProFragment extends BaseFragment {
    Button Purchasebutton,RestoreButton;
    IabHelper mHelper;
    static final int RC_REQUEST = 10001;

    boolean mIsPremium = false;
    static final String SKU_PREMIUM = "premium";
    String TAG="PRO_FRAGMENT";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.prolayout, null);
        initUI(v);
        setclicks();
        hideProgress();

        return v;
    }
    private void setclicks() {
        Purchasebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String payload = "Styler"+getPref(Constants.USER_ID);

                mHelper.launchPurchaseFlow(getActivity(), SKU_PREMIUM, RC_REQUEST,
                        mPurchaseFinishedListener, payload);
            }
        });
        RestoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    void complain(String message) {
        Log.e(TAG, "**** TrivialDrive Error: " + message);
        alert("Error: " + message);
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(getActivity());
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

    private void initUI(View v) {
        Purchasebutton= (Button) v.findViewById(R.id.buttonPurchasePro);
        RestoreButton= (Button) v.findViewById(R.id.buttonRestorePro);
    }
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener=new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inv) {
            Log.d(TAG, "Query inventory finished.");
// Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) {
                return;
            }
            if (result.isFailure()) {
                Toast.makeText(getActivity(),
                        "Failed to query inventory: " + result,
                        Toast.LENGTH_LONG).show();
                return;
            }
            Log.d(TAG, "Query inventory was successful.");
            Purchase premiumPurchase = inv.getPurchase(SKU_PREMIUM);
            mIsPremium = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));
            Log.d(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));
        }
    };
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase)
        {
            if (result.isFailure()) {
                complain("Error purchasing: " + result);
                Log.d(TAG, "Error purchasing: " + result);
                return;
            }

            else if (purchase.getSku().equals(SKU_PREMIUM)) {
                alert("Thank you for upgrading to premium!");
                mIsPremium = true;
                // give user access to premium content and update the UI
            }
        }
    };
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null) return;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();

        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */

        return true;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnGBxTd0aShxOWFFlp15W6i0dPKFDzkUDywx55LrKg7zfDIOC2dFAwkaxtH8PQATMIQ153Z+CTyHc6IupqMs49MXST8SaWh87wCkwgdUovKx+mvQOkA68sqttzfo/884QiEJ1k2kjt+LdMfZhpLbQHSyOveAox8Vv6FdZRjRnW18zWTBe64b+BQ6WT6Bu35zMDSM9Frawt7uR6x8gXHN46digAGqX+qWASwL8XosgXJpMOoo4Lsm0yM8j63Q6EV3eB1OtzRZ8lWnN+ONwN3p8RT6KOEMXCpDO6N3jdrRRfNdkhTSn3TTJzohiF/I6vAQ0zDl1JUumKLvTYZ8ML6ykmwIDAQAB";
        mHelper = new IabHelper(getActivity(), base64EncodedPublicKey);
        mHelper.enableDebugLogging(true);
        Log.d(TAG, "Starting setup.");

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");
                if (!result.isSuccess()) {
// Oh noes, there was a problem.
                    Toast.makeText(getActivity(),
                            "Problem setting up in-app billing: " + result,
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (mHelper == null)
                    return;

// IAP is fully set up. Now, let's get an inventory of stuff we
// own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });


    }
}






