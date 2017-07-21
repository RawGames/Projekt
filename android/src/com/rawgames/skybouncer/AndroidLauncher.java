package com.rawgames.skybouncer;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.rawgames.skybouncer.utils.AdHandler;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;




public class AndroidLauncher extends AndroidApplication implements AdHandler, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback,DialogInterface, Window.Callback, KeyEvent.Callback, View.OnCreateContextMenuListener {//, GoogleApiClient.ServerAuthCodeCallbacks {

	private GoogleApiClient myClient;


	private static final String TAG = "AndroidLauncher";
	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;
	protected AdView adView;

	private static final int REQUEST_RESOLVE_ERROR = 1001;
	private ProgressDialog mProgressDialog;
	private static final String DIALOG_ERROR = "dialog_error";

	public Dialog showErrorDialog(int errorCode) {
		return null;
	}


	private GameHelper gameHelper;
	private final static int requestCode = 1;
	private boolean mResolvingError = false;
	private static final String STATE_RESOLVING_ERROR = "resolving_error";

	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
				case SHOW_ADS:
					adView.setVisibility(View.VISIBLE);
					break;
				case HIDE_ADS:
					adView.setVisibility(View.GONE);
					break;
			}
		}

	};

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(STATE_RESOLVING_ERROR, mResolvingError);
	}


	@Override
	protected void onStart() {
		super.onStart();

//		gameHelper.onStart(this);
		myClient.connect();

	}

	@Override
	protected void onStop() {
		super.onStop();
		myClient.disconnect();
		gameHelper.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_RESOLVE_ERROR) {
			mResolvingError = false;
			if (resultCode == RESULT_OK) {
				if (!myClient.isConnecting() &&
						!myClient.isConnected()) {
					myClient.connect();
				}
			}
		}

	}

	@Override
	protected void onCreate (Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);


		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestEmail()
				.build();
		myClient = new GoogleApiClient.Builder(this)
				.addApi(Drive.API)
				.addScope(Drive.SCOPE_FILE)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.build();
		mResolvingError = savedInstanceState != null
				&& savedInstanceState.getBoolean(STATE_RESOLVING_ERROR, false);
/*		myClient = new GoogleApiClient.Builder(this).addOnConnectionFailedListener(this)
				.addApi(Plus.API, Plus.PlusOptions.builder().build())
				.addScope(Plus.SCOPE_PLUS_LOGIN).build();*/



		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(false);

		GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
		{
			@Override
			public void onSignInFailed(){ }

			@Override
			public void onSignInSucceeded(){ }



		};

		gameHelper.setup(gameHelperListener);

		// layout sak
		final RelativeLayout layout = new RelativeLayout(this);

		// config sak för spelet antar jag
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		// immersive af
		config.hideStatusBar = true;
		config.useImmersiveMode = true;

		// Game view
		View gameView = initializeForView(new Game(this), config);
		layout.addView(gameView);

		// reklam skit
		adView = new AdView(this);
		adView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				int visibility = adView.getVisibility();
				adView.setVisibility(AdView.GONE);
				adView.setVisibility(visibility);
				Log.i(TAG, "Ad loaded...");
			}
		});
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
		// test key (ca-app-pub-3940256099942544/6300978111)
		// release key (ca-app-pub-7220882176968020/2582474393)

		AdRequest.Builder builder = new AdRequest.Builder();
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
		);

		layout.addView(adView, adParams);
		adView.loadAd(builder.build());

		setContentView(layout);
	}

	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}

	@Override
	public void signIn() {
		try
		{

			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut() {
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestEmail()
				.build();
		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.signOut();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}

	}

	@Override
	public void submitScore(int highScore) {
		if (isSignedIn() == true)
		{
			Games.Leaderboards.submitScore(gameHelper.getApiClient(),
					getString(R.string.leaderboard_high_score), highScore);
		}
	}


	@Override
	public void showScore() {
		if (isSignedIn() == true)
		{

			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
					getString(R.string.leaderboard_high_score)), requestCode);
		}
		else
		{
			signIn();
		}
	}

	@Override
	public boolean isSignedIn() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void onConnected(@Nullable Bundle bundle) {

	}

	@Override
	public void onConnectionSuspended(int i) {

	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (mResolvingError) {
			// Already attempting to resolve an error.
			return;
		} else if (result.hasResolution()) {
			try {
				mResolvingError = true;
				result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
			} catch (IntentSender.SendIntentException e) {
				// There was an error with the resolution intent. Try again.
				myClient.connect();
			}
		} else {
			showErrorDialog(result.getErrorCode());
			mResolvingError = true;
		}
	}

	@Override
	public void cancel() {

	}

	@Override
	public void dismiss() {

	}
}
