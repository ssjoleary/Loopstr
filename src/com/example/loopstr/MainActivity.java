package com.example.loopstr;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import com.facebook.*;
import com.facebook.model.*;
import com.facebook.widget.LoginButton;

import java.util.Arrays;

public class MainActivity extends Activity {

	LoginButton loginButton;
	Session session;
	private UiLifecycleHelper uiHelper;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (LoginButton) findViewById(R.id.authButton);
        loginButton.setReadPermissions(Arrays.asList("user_likes", "user_status", "read_stream"));
        session = Session.getActiveSession();
        
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
    }

    @Override
    public void onResume(){

        super.onResume();
        if (session == null) {
            Log.d("Loopstr", "Session is null");
        } else {
            // CHECK IF USER LOGGED IN
            if (session.isOpened()){
                Log.e("here", "ha");
                Intent i = new Intent(this, FeedActivity.class);
                startActivity(i);
            }
        }
    	Bundle b = getIntent().getExtras();
    	if (b!=null) {
    	int loggingIn = b.getInt("logging_in");
    	if (loggingIn == 0){
    		Log.e("logginIn",loggingIn + "");
    	} else if(loggingIn == 1)
    		Log.e("logginIn",loggingIn + "");
    	}
    	uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }
    
    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
        	Intent i = new Intent(this, FeedActivity.class);
       	 	startActivity(i);
            Log.i("a", "Logged in...");
        } else if (state.isClosed()) {
            Log.i("a", "Logged out...");
        }
    }
    
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
    

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
}
