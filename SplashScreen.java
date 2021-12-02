package com.org.nic.ts;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.org.nic.ts.custom.Utility;
//import com.org.nic.ts.tmatsya.force_close.ExceptionHandler;

public class SplashScreen extends AppCompatActivity {

    public static final int seconds = 8;
    public static final int milliseconds = seconds * 500;
    public static final int delay = 2;
    NetworkInfo netInfo;
    private ProgressBar pbprogress;
    private final String  TAG = SplashScreen.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

    /*    Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));    */

        if (Utility.unique_id=="")//not unique if restarted or factory reset is done it will change. Some companies don ot give this.
            Utility.unique_id= Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        if (Utility.showLogs==0)
            Log.d(TAG,"unique_id: "+Utility.unique_id);

        //code that displays the content in full screen mode
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//int flag, int mask
        setContentView(R.layout.activity_splash_screen);
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pbprogress = (ProgressBar) findViewById(R.id.progressBar);
        pbprogress.setMax(maximum_progress());

        displayanimation();
    }

    public void displayanimation() {
        new CountDownTimer(milliseconds, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                pbprogress.setProgress(establisher_progress(millisUntilFinished));
            }

            @Override
            public void onFinish() {

                if (netInfo != null && netInfo.isConnected() == true) {

                   /* if (Utility.getSharedPreferences(SplashScreen.this).getString("lang_selection", "").equalsIgnoreCase("")){
                        Intent yes = new Intent(SplashScreen.this, LanguageActivity.class);
//                    Intent yes = new Intent(SplashScreen.this, CameraNougatActivity.class);
                        startActivity(yes);
                        finish();
                    }else{*/
//                    Intent yes = new Intent(SplashScreen.this, FishSeedStockingNavigation.class);
//                    Intent yes = new Intent(SplashScreen.this, MainActivity.class);
                    Intent yes = new Intent(SplashScreen.this, LoginEmastya.class);
//                    Intent yes = new Intent(SplashScreen.this, MainNavigation.class);
                    startActivity(yes);
                    finish();
//                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Network Not Available", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }.start();
    }

    public int maximum_progress() {
        return seconds - delay;
    }

    public int establisher_progress(long miliseconds) {
        return (int) ((milliseconds - miliseconds) / 1000);
    }
}
