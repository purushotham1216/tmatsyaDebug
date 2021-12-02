package com.org.nic.ts;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//import com.org.nic.ts.tmatsya.custom.ScrollTextView;
import com.org.nic.ts.custom.ScrollTextView;
//import com.org.nic.ts.tmatsya.custom.Utility;
//import com.org.nic.ts.tmatsya.force_close.ExceptionHandler;
import com.org.nic.ts.custom.Utility;
import com.org.nic.ts.webservice.Authenticate;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

public class LoginEmastya extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private ScrollTextView txtFooter, scrolling_text;
    private final String  TAG=LoginEmastya.class.getSimpleName();
    private EditText mobNoEdt, pwdEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_emastya);
       // Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        requestStoragePermission();

        initializeViews();
    }

    private void initializeViews() {

        mobNoEdt = findViewById(R.id.mob_no_edt);
        pwdEdt = findViewById(R.id.password_edt);

        txtFooter = (ScrollTextView) this.findViewById(R.id.scrolltext);
        txtFooter.setSelected(true);
        txtFooter.startScroll();

        mobNoEdt.setText("Dfo_niz");
        pwdEdt.setText("Bad@54321");

    }

    public void loginClick(View v) {

        if (allPermissionsGranted) {

            Utility.callHideKeyBoard(LoginEmastya.this);

            String mobNoStr = mobNoEdt.getText().toString().trim();
            String pwdStr = pwdEdt.getText().toString().trim();

            if (TextUtils.isEmpty(mobNoStr)) {
//            mobNoEdt.setError("Mobile number required");
                mobNoEdt.setError("User Id required");
                return;
            }
           /* if (mobNoStr.length() < 5) {
//            mobNoEdt.setError("Valid Mobile number required");
                mobNoEdt.setError("Valid user id required");
                return;
            }*/
            if (TextUtils.isEmpty(pwdStr)) {
                pwdEdt.setError("Password required");
                return;
            }

            int activityVal;
            // if (loginSelectoinStr.equalsIgnoreCase("RB"))
            activityVal = 0;
           /* else
                activityVal = 1;*/

            progressDialog = new ProgressDialog(LoginEmastya.this, R.style.MyAlertDialogStyle);
            progressDialog.setMessage("Authenticating...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();
            new Authenticate(LoginEmastya.this, activityVal).execute(
                    mobNoStr, pwdStr,
                    Utility.GetIMEINO(LoginEmastya.this),
                    Utility.getVersionNameCode(LoginEmastya.this));

        }else
            requestStoragePermission();
    }

    public void parsingLoginResp(SoapObject response) {
        int i = 0;

//        banks.clear();
//        for (int i = 0; i < response.getPropertyCount(); i++) {
        if (Utility.showLogs == 0)
            Log.d("response: ", "response: " + response.toString());

        PropertyInfo pi = new PropertyInfo();
        response.getPropertyInfo(i, pi);
        Object property = response.getProperty(i);
        if (pi.name.equals("LoginVasData") && property instanceof SoapObject) {
            SoapObject s = (SoapObject) property;

            if (Utility.showLogs == 0) {
                Log.d("response: ", "SucessFlag: " + s.getProperty("SucessFlag").toString().trim());
                Log.d("response: ", "SucessMsg: " + s.getProperty("SucessMsg").toString().trim());
            }

            if (s.getProperty("SucessFlag").toString().trim().equalsIgnoreCase("1")) {

                progressDialog.dismiss(); //dismiss dialog after all

                if (Utility.showLogs == 0) {
                    Log.d("response: ", "UserId: " + s.getProperty("UserId").toString().trim());
                    Log.d("response: ", "UsrName: " + s.getProperty("UsrName").toString().trim());
//                    Log.d("response: ", "StateCode: " + s.getProperty("StateCode").toString().trim());
//                Log.d("response: ", "DistCode: " + s.getProperty("StateCd").toString().trim());
                Log.d("response: ", "DistCode: " + s.getProperty("DistCode").toString().trim());
                    Log.d("response: ", "Role: " + s.getProperty("Role").toString().trim());
//                Log.d("response: ", "DistName: " + s.getProperty("DistName").toString().trim());
//                Log.d("response: ", "MandName: " + s.getProperty("MandName").toString().trim());
//                Log.d("response: ", "DivisionCode: " + s.getProperty("DivisionCode").toString().trim());
//                Log.d("response: ", "DivisionName: " + s.getProperty("DivisionName").toString().trim());
//                Log.d("response: ", "ClusterCode: " + s.getProperty("ClusterCode").toString().trim());
//                Log.d("response: ", "ClusterName: " + s.getProperty("ClusterName").toString().trim());

                }

                SharedPreferences.Editor editor = Utility.getSharedPreferences(LoginEmastya.this).edit();
                editor.putString("UserId", s.getProperty("UserId").toString().trim());
                editor.putString("UsrName", s.getProperty("UsrName").toString().trim());
//                editor.putString("StateCd", s.getProperty("StateCode").toString().trim());

                if (!s.getProperty("DistCode").toString().trim().equalsIgnoreCase(""))
                    editor.putString("DistCode", s.getProperty("DistCode").toString().trim());

                if (!s.getProperty("MandCode").toString().trim().equalsIgnoreCase(""))
                    editor.putString("MandCode", s.getProperty("MandCode").toString().trim());

                editor.putString("Role", s.getProperty("Role").toString().trim());

                if (!s.getProperty("Districtname").toString().trim().equalsIgnoreCase(""))
                    editor.putString("DistName", s.getProperty("Districtname").toString().trim());

                if (!s.getProperty("mandalname").toString().trim().equalsIgnoreCase(""))
                    editor.putString("MandName", s.getProperty("mandalname").toString().trim());

             /*   if (!s.getProperty("DivisionCode").toString().trim().equalsIgnoreCase(""))
                    editor.putString("DivisionCode", s.getProperty("DivisionCode").toString().trim());

                if (!s.getProperty("DivisionName").toString().trim().equalsIgnoreCase(""))
                    editor.putString("DivisionName", s.getProperty("DivisionName").toString().trim());

                if (!s.getProperty("ClusterCode").toString().trim().equalsIgnoreCase(""))
                    editor.putString("ClusterCode", s.getProperty("ClusterCode").toString().trim());

                if (!s.getProperty("ClusterName").toString().trim().equalsIgnoreCase(""))
                    editor.putString("ClusterName", s.getProperty("ClusterName").toString().trim());
                editor.putString("DashboardList", "0");

                if (!s.getProperty("BankCode").toString().trim().equalsIgnoreCase(""))
                    editor.putString("BankCode", s.getProperty("BankCode").toString().trim());*/

                editor.commit();

                if (Utility.showLogs == 0)
                    Log.d(TAG, "IMEI No: " + Utility.GetIMEINO(LoginEmastya.this));

                Toast.makeText(this, s.getProperty("SucessMsg").toString().trim(), Toast.LENGTH_SHORT).show();

//                startActivity(new Intent(LoginEmastya.this, SelectionPage.class));
//                startActivity(new Intent(LoginEmastya.this, HarvestingNavigation.class));
//                startActivity(new Intent(LoginEmastya.this, MainNavigation.class));
//                finish();

                //Component Grounding
                if (s.getProperty("Role").toString().trim().equalsIgnoreCase("8")){
                    startActivity(new Intent(LoginEmastya.this, SelectionPage.class));
//                    startActivity(new Intent(LoginEmastya.this, MainNavigation.class));
//                    startActivity(new Intent(LoginEmastya.this, PrawnSeedStockingNavigation.class));
                    finish();
                }else if (s.getProperty("Role").toString().trim().equalsIgnoreCase("15")){
                    //Harvesting Details
                    /*startActivity(new Intent(LoginEmastya.this, HarvestingNavigation.class));
                    finish();*/
                }

               /* if (pwdEdt.getText().toString().trim().equals("Test@12345")) {

                    SharedPreferences.Editor editor1 = Utility.getSharedPreferences(Login.this).edit();
                    editor1.putString("chq_pwd", "login");
                    editor1.commit();

                    Intent intent;


                    if (Utility.showLogs == 0) {
                        if (loginSelectoinStr.equalsIgnoreCase("RB"))
                            intent = new Intent(LoginActivity.this, NavigationMenu.class);
                        else
                            intent = new Intent(LoginActivity.this, LICNavigationMenu.class);
                    } else
                        intent = new Intent(LoginActivity.this, ChangePassword.class);

                    startActivity(intent);
                    finish();
                } else {

                    if (loginSelectoinStr.equalsIgnoreCase("RB"))
                        startActivity(new Intent(LoginActivity.this, NavigationMenu.class));
                    else
                        startActivity(new Intent(LoginActivity.this, LICNavigationMenu.class));
//                    startActivity(new Intent(LoginActivity.this, NavigationMenu.class));
//                startActivity(new Intent(LoginActivity.this, MenuActivity.class));
//                    startActivity(new Intent(LoginActivity.this, ChangePassword.class));
                    finish();
                }*/


            } else {
                progressDialog.dismiss(); //dismiss dialog after all
                try {

//                    if (loginSelectoinStr.equalsIgnoreCase("RB"))

                    if (s.getProperty("SucessFlag").toString().trim().equalsIgnoreCase("2")) {

                    /*if (s.getProperty("SuccessMsg").toString().trim().
                            equalsIgnoreCase("Update With New Mobile App Version( Goto PlayStore App Update)")) {*/

                        Utility.showAlertDialogToCallPlayStore(LoginEmastya.this, "Info",
                                "" + s.getProperty("SucessMsg").toString().trim(), true);
                    } else {
//                        Toast.makeText(this, s.getProperty("SuccessMsg").toString().trim(), Toast.LENGTH_SHORT).show();

                        Utility.showAlertDialog(LoginEmastya.this, "Info", s.getProperty("SucessMsg").toString().trim(), true);

                    }

                    //

                } catch (Exception e) {
                    progressDialog.dismiss();
                }
            }

               /* BankInfo entity = new BankInfo();
                entity.setBankCode(bankCode);
                entity.setBankName(fullName);

                // add to list after all
                banks.add(entity);*/
        } else {
            progressDialog.dismiss();
        }
//        }

//        setDataToLayout(); //set data to view after finished parsing.

    }

    public void onError(String error) {
        progressDialog.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private static final int STORAGE_PERMISSION_CODE = 123;
    private static final int CAMERA = 23;
    private static final int READ_SMS = 23;
    private static final int INTERNET = 23;
    private static final int ACCESS_NETWORK_STATE = 23;

    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.INTERNET)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
            Toast.makeText(this, "You need to allow the permissions asked in order to work with this application", Toast.LENGTH_LONG).show();
        }
//Manifest.permission.RECEIVE_SMS,
//        Manifest.permission.READ_SMS,READ_SMS
//        Manifest.permission.READ_PHONE_STATE,
        //And finally ask for the permission

        ActivityCompat.requestPermissions(
                this,
                new String[]{

                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                },
                STORAGE_PERMISSION_CODE);
    }

    boolean allPermissionsGranted = true;

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (permissions.length == 0) {
            return;
        }
        allPermissionsGranted = true;
        if (grantResults.length > 0) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }
        }
        if (!allPermissionsGranted) {
            boolean somePermissionsForeverDenied = false;
            for (String permission : permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    //denied
                    Log.e("denied", permission);
                } else {
                    if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                        //allowed
                        Log.e("allowed", permission);
                    } else {
                        //set to never ask again
                        Log.e("set to never ask again", permission);
                        somePermissionsForeverDenied = true;
                    }
                }
            }
            if (somePermissionsForeverDenied) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Permissions Required")
                        .setMessage("You have forcefully denied some of the required permissions " +
                                "for this action. Please open settings, go to permissions and allow them.")
                        .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", getPackageName(), null));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }
        } else {
            switch (requestCode) {
                //act according to the request code used while requesting the permission(s).
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Utility.callSignOutAlert(LoginEmastya.this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                || keyCode == KeyEvent.KEYCODE_HOME) {

            Utility.callSignOutAlert(LoginEmastya.this);
        }

        return super.onKeyDown(keyCode, event);
    }
}
