package denisefurlong.com.budgetapp;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.plus.Plus;
import denisefurlong.com.budgetapp.ui.CategoryFragment;

public abstract class BaseConnection extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        CategoryFragment.OnFragmentInteractionListener{

    protected static GoogleApiClient mApiClient;
    protected String mAccountName;

    @Override
    protected void onResume() {
        super.onResume();
        connectClient();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectClient();
    }

    protected void connectClient(){
        if (mApiClient == null) {
            Scope sheets = new Scope(ConstantValues.FEEDS_SCOPE);

            mApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addScope(Drive.SCOPE_APPFOLDER)
                    .addScope(sheets)
                    .addConnectionCallbacks(this)
                    .addApi(Plus.API)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        mApiClient.connect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            mApiClient.connect();
        }
    }

    @Override
    protected void onPause() {
        if (mApiClient != null) {
            mApiClient.disconnect();
        }
        super.onPause();
    }

    /**
     * Called when Google API Client is connected.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(ConstantValues.APP_TAG, "Google Api Client connected");
        mAccountName = Plus.AccountApi.getAccountName(mApiClient);
    }

    /**
     * Called when Google API Client is disconnected.
     */
    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(ConstantValues.APP_TAG, "Google Api Client connection suspended");
    }

    /**
     * Called when Google API Client is trying to connect but failed.
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(ConstantValues.APP_TAG, "Google Api Client connection failed: " + result.toString());
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
            return;
        }
        try {
            result.startResolutionForResult(this, 1);
        } catch (IntentSender.SendIntentException e) {
            Log.e(ConstantValues.APP_TAG, "Exception while starting resolution activity: ", e);
        }
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Getter for the Google API Client.
     */
    public GoogleApiClient getGoogleApiClient() {
        return mApiClient;
    }
}
