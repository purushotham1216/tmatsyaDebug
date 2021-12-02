package com.org.nic.ts;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.org.nic.ts.custom.DatePickerFragmentCustom;
import com.org.nic.ts.custom.Utility;
import com.org.nic.ts.helper.DatabaseHelper;
import com.org.nic.ts.model.fish_seed_stocking.FishSeedStockingEntryJSON;
import com.org.nic.ts.model.harvesting.CultureTypeBean;
import com.org.nic.ts.Holder.FishSeedStockingHolder1;
//import com.org.nic.ts.tmatsya.custom.CommonUtils;
import com.org.nic.ts.custom.DatePickerFragmentCustom;
import com.org.nic.ts.custom.PermissionUtils;
import com.org.nic.ts.custom.Utility;
//import com.org.nic.ts.force_close.ExceptionHandler;
import com.org.nic.ts.helper.DatabaseHelper;
import com.org.nic.ts.model.GetSubComponentMstBean;
import com.org.nic.ts.model.fish_seed_stocking.FishSeedStockingEntryBean;
import com.org.nic.ts.model.fish_seed_stocking.FishSeedStockingEntryBean1;
import com.org.nic.ts.model.fish_seed_stocking.FishSeedStockingEntryJSON;
import com.org.nic.ts.model.harvesting.CultureTypeBean;
import com.org.nic.ts.model.harvesting.District;
import com.org.nic.ts.model.harvesting.FinYearBean;
import com.org.nic.ts.model.harvesting.Mandal;
import com.org.nic.ts.model.harvesting.SeasonalityBean;
import com.org.nic.ts.model.harvesting.SpeciesBean;
import com.org.nic.ts.model.harvesting.UpdateSpeciesBean;
import com.org.nic.ts.model.harvesting.Village;
import com.org.nic.ts.model.harvesting.WaterBodyBean;
import com.org.nic.ts.webservice.GetMastersJSONData;
//import com.org.nic.ts.webservice.seed_stocking.InsertSeedstockingData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FishSeedStockingNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String ddMMyyyyStr = "";
    public static String MMddyyyyStr = "";
    public static String yyyyMMddStr = "";

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private final String TAG = FishSeedStockingNavigation.class.getSimpleName();
    private final Handler handler = new Handler();
    View itemView;
    private ProgressDialog progressDialog;
    private View navHeader;
    private TextView welcomeTxt;
    private String FarmerId4mServerStr = "",
            Aadhaarid4mServerStr = "",
            SchemeId4mServerStr = "",
            ApplicantTypeCode4mServerStr = "",
            ComponentCode4mServerStr = "",
            SubComponentCode4mServerStr = "",
            SubSchemeid4mServerStr = "";

    // Database Helper
    private DatabaseHelper db;
    private String mastersFlag = "";

    private boolean chqSelectedOrNot = false;
    private String cultureTypeCode = "";
    private String Tot_HarvestedStr = "0";
    private HashMap<String, String> speciesQtyHashMap = new LinkedHashMap<>();
    private boolean quantityEnteredBol = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_seed_stocking_navigation);
       // Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        isLocationServicesThere();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>tMastya "
                + getResources().getString(R.string.mob_version) + "</font>"));

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navHeader = navigationView.getHeaderView(0);
        Menu nav_Menu = navigationView.getMenu();
//        nav_Menu.findItem(R.id.nav_dd_nominee_details).setVisible(false);

        welcomeTxt = navHeader.findViewById(R.id.textView);
        welcomeTxt.setText("Welcome "
                + Utility.getSharedPreferences(FishSeedStockingNavigation.this).getString("UsrName", "")
                + "...");

        db = new DatabaseHelper(getApplicationContext());

        if (Utility.showLogs == 0) {
            Log.d(TAG, "Dist. Count: " + db.getTableCount("DISTRICT_MST"));
            Log.d(TAG, "Mand. Count: " + db.getTableCount("MANDAL_MST"));
            Log.d(TAG, "Vill. Count: " + db.getTableCount("VILLAGE_MST"));
        }

//        callGetJSONStr();

        initializeViews();
    }

    //private List speiceLsit = new ArrayList();
    // private Set<String> hashSetData = new HashSet<String>();
    private Map<String, FishSeedStockingEntryJSON> vehicleSpicesDataMaps
            = new HashMap<String, FishSeedStockingEntryJSON>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Utility.callSignOutAlert(FishSeedStockingNavigation.this);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            // Handle the camera action
        } else if (id == R.id.nav_component_capture) {

        } else if (id == R.id.nav_sync) {
            callSyncMasters();

        } else if (id == R.id.nav_logout) {
            Utility.callSignOutAlert(FishSeedStockingNavigation.this);
        }
        /*else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void callSyncMasters() {
        db.deleteTableData("DISTRICT_MST");
        db.deleteTableData("MANDAL_MST");
        db.deleteTableData("VILLAGE_MST");
        db.deleteTableData("SeasonalityMst");
        db.deleteTableData("CultureTypeMst");
        db.deleteTableData("SpeciesMst");
        db.deleteTableData("FinYearMST");

        callMastersData();
    }

    private Spinner fin_year_spin, seasonality_spin, district_spin,
            mandal_spin, village_spin,
            culture_type_spin, water_body_spin, intended_supplier_spin;
    private LinearLayout hide_linear_layout_mandal_spin,
            hide_linear_layout_village_spin,
            hide_linear_layout_water_body_spin, hide_linear_layout_fish_seed_stocking_waterbody_rv, hide_linear_layout_intended_supplier_spin;

    private Button date_fish_seed_stocking_btn, enter_seed_stocking_details_btn;
    private LinearLayout hide_linear_layout_harvesting_data_details, hide_linear_layout_supplier_seed_indented,
            hide_linear_layout_leftover_stocking_qty;
    private TextView error_msg_txt, supplier_seed_indented_txt, leftover_stocking_qty_txt;

    private RecyclerView fish_seed_stocking_waterbody_recyclerview;

    private LinearLayout hide_linear_enter_seed_stocking_dtls_btn, hide_linear_capture_stocking_certificate, hide_linear_stocking_certificate_submit_btn;
    private ImageView stocking_certificate_imageview;
    private Button stocking_certificate_btn, submit_data_btn;

    private void initializeViews() {

        fin_year_spin = findViewById(R.id.fin_year_spin);
        seasonality_spin = findViewById(R.id.seasonality_spin);
        district_spin = findViewById(R.id.district_spin);
        mandal_spin = findViewById(R.id.mandal_spin);
        village_spin = findViewById(R.id.village_spin);
        culture_type_spin = findViewById(R.id.culture_type_spin);
        water_body_spin = findViewById(R.id.water_body_name_spin);
        intended_supplier_spin = findViewById(R.id.intended_supplier_spin);

        supplier_seed_intended_waterbody_spin = findViewById(R.id.supplier_seed_intended_waterbody_spin);

        date_fish_seed_stocking_btn = findViewById(R.id.date_fish_seed_stocking_btn);
        date_fish_seed_stocking_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerOnclick(v);
            }
        });

        enter_seed_stocking_details_btn = findViewById(R.id.enter_seed_stocking_details_btn);
        enter_seed_stocking_details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                validateWaterBodySelection(1);
//                alertEnteredSpiecesTotalFLG = 0;
                alertFishSeedStockingEntry();
            }
        });

        fish_seed_stocking_waterbody_recyclerview = findViewById(R.id.fish_seed_stocking_waterbody_recyclerview);
        fish_seed_stocking_waterbody_recyclerview.setHasFixedSize(true);

        supplier_seed_indented_txt = findViewById(R.id.supplier_seed_indented_txt);
        hide_linear_layout_supplier_seed_indented = findViewById(R.id.hide_linear_layout_supplier_seed_indented);

        leftover_stocking_qty_txt = findViewById(R.id.leftover_stocking_qty_txt);
        hide_linear_layout_leftover_stocking_qty = findViewById(R.id.hide_linear_layout_leftover_stocking_qty);

        hide_linear_enter_seed_stocking_dtls_btn = findViewById(R.id.hide_linear_enter_seed_stocking_dtls_btn);

        hide_linear_capture_stocking_certificate = findViewById(R.id.hide_linear_capture_stocking_certificate);
        hide_linear_stocking_certificate_submit_btn = findViewById(R.id.hide_linear_stocking_certificate_submit_btn);

        stocking_certificate_imageview = findViewById(R.id.stocking_certificate_imageview);
        stocking_certificate_btn = findViewById(R.id.stocking_certificate_btn);

        submit_data_btn = findViewById(R.id.submit_data_btn);

      /*  imageFilePath = CommonUtils.getFilename();
        if (Utility.showLogs == 0)
            Log.d("Image Path===", imageFilePath);*/


        stocking_certificate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*chqSelectedOrNot = false;
                if (!validateChqSelectedOrNot()) {

                    Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info", "Check at-least one Sub-component.", true);
                    return;
                }*/

                // callCamera(TAKE_PICTURE_ONE2, "");
                filename2 = "tMatsya_seed_certificate" + System.currentTimeMillis();

                callCameraNew(TAKE_PICTURE_ONE2, filename2);
            }
        });

        submit_data_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // prepareJSONObj();

             /*   showInsertSeedStockDataAlertDialog(FishSeedStockingNavigation.this, "Info",
                        "Are you sure?\nData will be submitted to tMatsya application.", true);*/
            }
        });

        error_msg_txt = findViewById(R.id.error_msg_txt);
        error_msg_txt.setVisibility(View.GONE);

        hide_linear_layout_harvesting_data_details = findViewById(R.id.linear_layout_harvesting_data_details);
        hide_linear_layout_fish_seed_stocking_waterbody_rv = findViewById(R.id.hide_linear_layout_fish_seed_stocking_waterbody_rv);
        hide_linear_layout_intended_supplier_spin = findViewById(R.id.hide_linear_layout_intended_supplier_spin);

        hide_linear_layout_mandal_spin = findViewById(R.id.hide_linear_layout_mandal_spin);
        hide_linear_layout_village_spin = findViewById(R.id.hide_linear_layout_village_spin);
        hide_linear_layout_water_body_spin = findViewById(R.id.hide_linear_layout_water_body_spin);

        hide_linear_layout_harvesting_data_details.setVisibility(View.GONE);
        hide_linear_layout_fish_seed_stocking_waterbody_rv.setVisibility(View.GONE);
        hide_linear_layout_intended_supplier_spin.setVisibility(View.GONE);

         /*hide_linear_layout_mandal_spin.setVisibility(View.GONE);
        hide_linear_layout_village_spin.setVisibility(View.GONE);
        hide_linear_layout_water_body_spin.setVisibility(View.GONE);*/

        hide_linear_layout_supplier_seed_indented.setVisibility(View.GONE);
        hide_linear_layout_leftover_stocking_qty.setVisibility(View.GONE);
        hide_linear_enter_seed_stocking_dtls_btn.setVisibility(View.GONE);

        hide_linear_stocking_certificate_submit_btn.setVisibility(View.GONE);

        callMastersData();

      //  testViews();
    }

    public class DecimalDigitsInputFilterB4A4 implements InputFilter {
        Pattern pattern;

        public DecimalDigitsInputFilterB4A4(int digitsBeforeDecimal, int digitsAfterDecimal) {
            pattern = Pattern.compile("(([1-9]{1}[0-9]{0," + (digitsBeforeDecimal - 1) + "})?||[0]{1})((\\.[0-9]{0," + digitsAfterDecimal + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int sourceStart, int sourceEnd, Spanned destination, int destinationStart, int destinationEnd) {
            // Remove the string out of destination that is to be replaced.
            String newString = destination.toString().substring(0, destinationStart) + destination.toString().substring(destinationEnd, destination.toString().length());

            // Add the new string in.
            newString = newString.substring(0, destinationStart) + source.toString() + newString.substring(destinationStart, newString.length());

            // Now check if the new string is valid.
            Matcher matcher = pattern.matcher(newString);

            if (matcher.matches()) {
                // Returning null indicates that the input is valid.
                return null;
            }

            // Returning the empty string indicates the input is invalid.
            return "";
        }
    }


    public void datePickerOnclick(View view) {
        DialogFragment newFragment = new DatePickerFragmentCustom();
        newFragment.show(getFragmentManager(), "Date Picker");

    }

    private String cultureTypeNameSelectedStr = "Fish";


    private void callAssignSpinners() {
        finYearMstSpinnerValuesList(fin_year_spin);
    }

    private List<String> cultureTypeNamesList = new ArrayList<>(),
            cultureTypeCodeList = new ArrayList<>();
    private List<CultureTypeBean> cultureTypeDataList = new ArrayList<>();

    private int culture_type_spinPosition = 0;
    private String culture_type_spinSelectedCode = "0", culture_type_spinSelectedName = "",
            culture_type_spinSelected_d_LgCode = "";

    private void cultureTypeMstSpinnerValuesList(Spinner spinner) {

        try {
            cultureTypeNamesList = new ArrayList<>();
            cultureTypeCodeList = new ArrayList<>();

            cultureTypeDataList = new ArrayList<>();

            cultureTypeNamesList.add("Select");
            cultureTypeCodeList.add("0");

//            cultureTypeDataList = db.getAllCultureTypeData();
            cultureTypeDataList = db.getAllCultureTypeData();

            if (Utility.showLogs == 0)
                Log.d(TAG, "cultureTypeDataList. Count: " + cultureTypeDataList.size());

            for (int i = 0; i < cultureTypeDataList.size(); i++) {
                cultureTypeNamesList.add(cultureTypeDataList.get(i).getFishculture_Name());
                cultureTypeCodeList.add(cultureTypeDataList.get(i).getFishculture_cd());

                if (Utility.showLogs == 0) {
                    Log.d(TAG, "Fishculture_Name: " + cultureTypeDataList.get(i).getFishculture_Name());
                    Log.d(TAG, "Fishculture_cd: " + cultureTypeDataList.get(i).getFishculture_cd());
                }
            }

            if (Utility.showLogs == 0) {
                Log.d(TAG, "cultureTypeNamesList. Count: " + cultureTypeNamesList.size());
                Log.d(TAG, "cultureTypeCodeList. Count: " + cultureTypeCodeList.size());
            }

            Utility.assignArrayAdpListToSpin(FishSeedStockingNavigation.this, cultureTypeNamesList, spinner);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    culture_type_spinPosition = position;

                    if (culture_type_spinPosition > 0) {
                        culture_type_spinSelectedName = cultureTypeNamesList.get(culture_type_spinPosition);
                        culture_type_spinSelectedCode = cultureTypeCodeList.get(culture_type_spinPosition);


                        if (Utility.showLogs == 0) {
                            Log.d(TAG, "cultureType Name: " + culture_type_spinSelectedName + "\n" +
                                    "cultureType Code: " + culture_type_spinSelectedCode);
                        }

                    } else {
                        culture_type_spinSelectedCode = "0";
                        culture_type_spinSelectedName = "";
                    }

                    /*if (culture_type_spinPosition > 0) {
                        culture_type_spinSelectedName = cultureTypeNamesList.get(culture_type_spinPosition);
                        culture_type_spinSelectedCode = cultureTypeCodeList.get(culture_type_spinPosition);


                        if (Utility.showLogs == 0) {
                            Log.d(TAG, "cultureType Name: " + culture_type_spinSelectedName + "\n" +
                                    "cultureType Code: " + culture_type_spinSelectedCode);
                        }

                        error_msg_txt.setVisibility(View.GONE);
                        hide_linear_layout_harvesting_data_details.setVisibility(View.GONE);

                        //  validateWaterBodySelection(0);


                    } else {
                        culture_type_spinSelectedCode = "0";
                        hide_linear_layout_water_body_spin.setVisibility(View.GONE);
                        error_msg_txt.setVisibility(View.GONE);
                        hide_linear_layout_harvesting_data_details.setVisibility(View.GONE);
                    }*/
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            if (Utility.showLogs == 0)
                Log.d(TAG, "Exception culturetype spin: " + e.toString());
        }

        // addListenerRadioGroup();

        culture_type_spin.setSelection(1);
        culture_type_spin.setClickable(false);
        culture_type_spin.setEnabled(false);

        //  waterBodyMstSpinnerValuesList(water_body_spin);

    }

    private List<FinYearBean> finYearBeanDataList = new ArrayList<>();
    private List<String> yearCodeList = new ArrayList<>(),
            yearDescList = new ArrayList<>();
    private int fin_year_spinPosition = 0;
    private String fin_year_spinSelectedCode = "0", fin_year_spinSelectedName = "";

    private void finYearMstSpinnerValuesList(Spinner spinner) {
        yearDescList = new ArrayList<>();
        yearCodeList = new ArrayList<>();

        yearDescList.add("Select");
        yearCodeList.add("0");

        finYearBeanDataList = db.getAllFinYearData();

        for (int i = 0; i < finYearBeanDataList.size(); i++) {

            if (Utility.showLogs == 0) {
                Log.d(TAG, "getYear_Desc " + finYearBeanDataList.get(i).getYear_Desc());
                Log.d(TAG, "getYear_Code " + finYearBeanDataList.get(i).getYear_Code());
            }

            yearDescList.add(finYearBeanDataList.get(i).getYear_Desc());
            yearCodeList.add(finYearBeanDataList.get(i).getYear_Code());
        }

        Utility.assignArrayAdpListToSpin(FishSeedStockingNavigation.this, yearDescList, spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fin_year_spinPosition = position;


                if (fin_year_spinPosition > 0) {

                    fin_year_spinSelectedName = yearDescList.get(fin_year_spinPosition);
                    fin_year_spinSelectedCode = yearCodeList.get(fin_year_spinPosition);

                    if (Utility.showLogs == 0) {
                        Log.d(TAG, "finYear Name: " + fin_year_spinSelectedName + "\n" +
                                "Code: " + fin_year_spinSelectedCode);
                    }

                   /* if (district_spinPosition > 0 &&
                            Mandal_spinPosition > 0 &&
                            village_spinPosition > 0 &&
                            culture_type_spinPosition > 0)
                        validateWaterBodySelection(0);*/

                    //  hide_linear_layout_water_body_spin.setVisibility(View.GONE);

                } else {
                    fin_year_spinSelectedCode = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        seasonalityMstSpinnerValuesList(seasonality_spin);

        fin_year_spin.setSelection(1);
        fin_year_spin.setEnabled(false);
        fin_year_spin.setClickable(false);


    }

    private List<String> seasonalityNamesList = new ArrayList<>(),
            seasonalityCodesList = new ArrayList<>();
    private List<SeasonalityBean> seasonalityDataList = new ArrayList<SeasonalityBean>();
    private int seasonality_spinPosition = 0;
    private String seasonality_spinSelectedCode = "0", seasonality_spinSelectedName = "",
            seasonality_spinSelected_d_LgCode = "";
    private boolean seasonalBol = false;

    private void seasonalityMstSpinnerValuesList(Spinner spinner) {

        seasonalityNamesList = new ArrayList<String>();
        seasonalityCodesList = new ArrayList<String>();


        seasonalityDataList = new ArrayList<SeasonalityBean>();

        seasonalityNamesList.add("Select");
        seasonalityCodesList.add("0");


        seasonalityDataList = db.getAllSeasonalityData();

        for (int i = 0; i < seasonalityDataList.size(); i++) {

            seasonalityNamesList.add(seasonalityDataList.get(i).getSeasionality());
            seasonalityCodesList.add(seasonalityDataList.get(i).getSNo());

        }

        Utility.assignArrayAdpListToSpin(FishSeedStockingNavigation.this, seasonalityNamesList, spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seasonality_spinPosition = position;


                if (seasonality_spinPosition > 0) {
                    seasonality_spinSelectedName = seasonalityNamesList.get(seasonality_spinPosition);
                    seasonality_spinSelectedCode = seasonalityCodesList.get(seasonality_spinPosition);


                    if (Utility.showLogs == 0) {
                        Log.d(TAG, "seasonality Name: " + seasonality_spinSelectedName + "\n" +
                                "seasonality Code: " + seasonality_spinSelectedCode);
                    }

                    error_msg_txt.setVisibility(View.GONE);
                    // hide_linear_layout_harvesting_data_details.setVisibility(View.GONE);

                    // validateWaterBodySelection(0);
                    if (seasonality_spinSelectedName.equalsIgnoreCase("Seasonal"))
                        seasonalBol = true;
                    else
                        seasonalBol = false;


                } else {
                    seasonality_spinSelectedCode = "0";
                    //  hide_linear_layout_water_body_spin.setVisibility(View.GONE);
                    error_msg_txt.setVisibility(View.GONE);
                    //  hide_linear_layout_harvesting_data_details.setVisibility(View.GONE);
                    seasonalBol = false;
                }


                mandal_spin.setSelection(0, true);
                village_spin.setSelection(0, true);
                water_body_spin.setSelection(0, true);
                intended_supplier_spin.setSelection(0, true);
                supplier_seed_indented_txt.setText("");
                leftover_stocking_qty_txt.setText("");
                ddMMyyyyStr = "";
                date_fish_seed_stocking_btn.setText("Click to Select");

                hide_linear_enter_seed_stocking_dtls_btn.setVisibility(View.GONE);

                hide_linear_layout_village_spin.setVisibility(View.GONE);
                hide_linear_layout_water_body_spin.setVisibility(View.GONE);
                hide_linear_layout_intended_supplier_spin.setVisibility(View.GONE);
                hide_linear_layout_leftover_stocking_qty.setVisibility(View.GONE);
                hide_linear_layout_supplier_seed_indented.setVisibility(View.GONE);
                hide_linear_layout_harvesting_data_details.setVisibility(View.GONE);

                vehicleNoHashSet = new HashSet<String>();
                if (fishSeedStockingEntryBeansList1.size() > 0)
                    fishSeedStockingEntryBeansList1.clear();

                if (fishSeedStockingEntrRecyclerList1.size() > 0)
                    fishSeedStockingEntrRecyclerList1.clear();

                alertEnteredSpiecesTotalFLG = 0;

                hide_linear_layout_fish_seed_stocking_waterbody_rv.setVisibility(View.GONE);
                hide_linear_stocking_certificate_submit_btn.setVisibility(View.GONE);
                stocking_certificate_imageview.setVisibility(View.GONE);
                photoBase64Str3 = "";

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        seasonality_spin.setSelection(1);
//        seasonality_spin.setEnabled(false);
//        seasonality_spin.setClickable(false);

        districtMstSpinnerValuesList(district_spin);
    }

    private List<String> districtNamesList = new ArrayList<>(),
            districtCodesList = new ArrayList<>(),
            distName_TelList = new ArrayList<>(),
            d_LgCodeList = new ArrayList<>();
    private List<District> districtDataList = new ArrayList<>();
    private int district_spinPosition = 0;
    private String district_spinSelectedCode = "0", district_spinSelectedName = "",
            district_spinSelected_d_LgCode = "";

    private void districtMstSpinnerValuesList(Spinner spinner) {

        districtNamesList = new ArrayList<>();
        districtCodesList = new ArrayList<>();
        distName_TelList = new ArrayList<>();
        d_LgCodeList = new ArrayList<>();

        districtDataList = new ArrayList<>();

        districtNamesList.add("Select");
        districtCodesList.add("0");
        distName_TelList.add("0");
        d_LgCodeList.add("0");

        districtDataList = db.getAllDistrictData();

        for (int i = 0; i < districtDataList.size(); i++) {

            districtNamesList.add(districtDataList.get(i).getDistName());
            districtCodesList.add(districtDataList.get(i).getDistCode());
            distName_TelList.add(districtDataList.get(i).getDistName_Tel());
            d_LgCodeList.add(districtDataList.get(i).getDistCode_Lg());

            if (Utility.showLogs == 0) {
                Log.d(TAG, "getDistCode_Lg: " + districtDataList.get(i).getDistCode_Lg());
            }

        }

        Utility.assignArrayAdpListToSpin(FishSeedStockingNavigation.this, districtNamesList, spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                district_spinPosition = position;


                if (district_spinPosition > 0) {
                    district_spinSelectedName = districtNamesList.get(district_spinPosition);
                    district_spinSelectedCode = districtCodesList.get(district_spinPosition);
//                    district_spinSelected_d_LgCode = d_LgCodeList.get(district_spinPosition);

                    if (Utility.showLogs == 0) {
                        Log.d(TAG, "district_spinSelectedName: " + district_spinSelectedName + "\n" +
                                "district_spinSelectedCode: " + district_spinSelectedCode);
                        /*+ "\n" +
                                "d_LgCode: " + district_spinSelected_d_LgCode);*/
                    }


                    mandalMstSpinnerValuesList(mandal_spin);

                    hide_linear_layout_mandal_spin.setVisibility(View.VISIBLE);
                    hide_linear_layout_village_spin.setVisibility(View.GONE);
                    //  hide_linear_layout_water_body_spin.setVisibility(View.GONE);


                } else {
                    district_spinSelectedCode = "0";

                    hide_linear_layout_mandal_spin.setVisibility(View.GONE);
                    hide_linear_layout_village_spin.setVisibility(View.GONE);
                    //   hide_linear_layout_water_body_spin.setVisibility(View.GONE);
                    mandal_spin.setSelection(0);
                    village_spin.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //fixing logged in user district
        /*spinner.setSelection(districtCodesList.indexOf("23")
        ,true);*/
        spinner.setSelection(districtCodesList.indexOf(
                Utility.getSharedPreferences(FishSeedStockingNavigation.this).getString("DistCode", "")
        ), true);

        if (Utility.showLogs == 0) {
            Log.d(TAG, "DistCode: " + Utility.getSharedPreferences(FishSeedStockingNavigation.this).getString("DistCode", ""));
            Log.d(TAG, "DistCode index: " +
                    districtCodesList.indexOf(
                            Utility.getSharedPreferences(FishSeedStockingNavigation.this).getString("DistCode", ""))
            );
        }



      /*  spinner.setSelection(d_LgCodeList.indexOf(
                Utility.getSharedPreferences(FishSeedStockingNavigation.this).getString("DistCode", "")
        ),true);*/

        spinner.setEnabled(false);
        spinner.setClickable(false);

        //spinner.setSelection(1, true);

        cultureTypeMstSpinnerValuesList(culture_type_spin);


    }

    private List<String> mandalNamesList = new ArrayList<>(),
            mandalCodesList = new ArrayList<>(),
            mandName_TelList = new ArrayList<>(),
            m_LgCodeList = new ArrayList<>(),
            mandCode_LList = new ArrayList<>();
    private List<Mandal> mandalDataList = new ArrayList<>();
    private int Mandal_spinPosition = 0;
    private String Mandal_spinSelectedCode = "0", Mandal_spinSelectedName = "",
            Mandal_spinSelected_m_LgCode = "0", Mandal_spinSelected_mandCode_L = "";

    private void mandalMstSpinnerValuesList(Spinner spinner) {

        mandalNamesList = new ArrayList<>();
        mandalCodesList = new ArrayList<>();
        mandName_TelList = new ArrayList<>();
        m_LgCodeList = new ArrayList<>();
        mandCode_LList = new ArrayList<>();

        mandalDataList = new ArrayList<>();

        mandalNamesList.add("Select");
        mandalCodesList.add("0");
        m_LgCodeList.add("0");
        mandCode_LList.add("0");

        mandalDataList = db.getAllMandalData(district_spinSelectedCode);

        for (int i = 0; i < mandalDataList.size(); i++) {

            mandalNamesList.add(mandalDataList.get(i).getMandName());
            mandalCodesList.add(mandalDataList.get(i).getMandCode());
            mandName_TelList.add(mandalDataList.get(i).getMandName_Tel());
//            m_LgCodeList.add(mandalDataList.get(i).get);
            mandCode_LList.add(mandalDataList.get(i).getMandCode_LG());
        }

        Utility.assignArrayAdpListToSpin(FishSeedStockingNavigation.this, mandalNamesList, spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Mandal_spinPosition = position;


                if (Mandal_spinPosition > 0) {
                    Mandal_spinSelectedName = mandalNamesList.get(Mandal_spinPosition);
                    Mandal_spinSelectedCode = mandalCodesList.get(Mandal_spinPosition);
//                    Mandal_spinSelected_m_LgCode = m_LgCodeList.get(Mandal_spinPosition);
//                    Mandal_spinSelected_mandCode_L = mandCode_LList.get(Mandal_spinPosition);

                    if (Utility.showLogs == 0) {
                        Log.d(TAG, "Crop Name: " + Mandal_spinSelectedName + "\n" +
                                "Code: " + Mandal_spinSelectedCode);
                    }

                    villageMstSpinnerValuesList(village_spin);


                    hide_linear_layout_village_spin.setVisibility(View.VISIBLE);

                } else {
                    Mandal_spinSelectedCode = "0";
                    village_spin.setSelection(0);
                    water_body_spin.setSelection(0);
                    intended_supplier_spin.setSelection(0);
                    supplier_seed_indented_txt.setText("");
                    leftover_stocking_qty_txt.setText("");
                    ddMMyyyyStr = "";
                    date_fish_seed_stocking_btn.setText("Click to Select");

                    hide_linear_layout_village_spin.setVisibility(View.GONE);

                }
                hide_linear_enter_seed_stocking_dtls_btn.setVisibility(View.GONE);
                hide_linear_layout_water_body_spin.setVisibility(View.GONE);
                hide_linear_layout_intended_supplier_spin.setVisibility(View.GONE);
                hide_linear_layout_leftover_stocking_qty.setVisibility(View.GONE);
                hide_linear_layout_supplier_seed_indented.setVisibility(View.GONE);
                hide_linear_layout_harvesting_data_details.setVisibility(View.GONE);

                if (fishSeedStockingEntryBeansList1.size() > 0)
                    fishSeedStockingEntryBeansList1.clear();

                if (fishSeedStockingEntrRecyclerList1.size() > 0)
                    fishSeedStockingEntrRecyclerList1.clear();

                vehicleNoHashSet = new HashSet<String>();

                alertEnteredSpiecesTotalFLG = 0;

                hide_linear_layout_fish_seed_stocking_waterbody_rv.setVisibility(View.GONE);
                hide_linear_stocking_certificate_submit_btn.setVisibility(View.GONE);
                stocking_certificate_imageview.setVisibility(View.GONE);
                photoBase64Str3 = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private List<String> villageNamesList = new ArrayList<>(),
            villageCodesList = new ArrayList<>(),
            villageName_TeList = new ArrayList<>();
    private List<Village> villageDataList = new ArrayList<>();
    private int village_spinPosition = 0;
    private String village_spinSelectedCode = "0", village_spinSelectedName = "",
            village_spinSelected_m_LgCode = "0", village_spinSelected_mandCode_L = "";

    private void villageMstSpinnerValuesList(Spinner spinner) {

        villageNamesList = new ArrayList<>();
        villageCodesList = new ArrayList<>();
        villageName_TeList = new ArrayList<>();
        // m_LgCodeList = new ArrayList<String>();
        //mandCode_LList = new ArrayList<String>();

        villageDataList = new ArrayList<>();

        villageNamesList.add("Select");
        villageCodesList.add("0");
        //  m_LgCodeList.add("0");
        //  mandCode_LList.add("0");

        villageDataList = db.getAllVillageData(district_spinSelectedCode, Mandal_spinSelectedCode);

        for (int i = 0; i < villageDataList.size(); i++) {

            villageNamesList.add(villageDataList.get(i).getVillName());
            villageCodesList.add(villageDataList.get(i).getVillCode());
//            villageName_TeList.add(villageDataList.get(i).getVillName_Tel());
//            m_LgCodeList.add(villageDataList.get(i).get);
            //  mandCode_LList.add(villageDataList.get(i).getMandCode_LG());
        }

        Utility.assignArrayAdpListToSpin(FishSeedStockingNavigation.this, villageNamesList, spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                village_spinPosition = position;


                if (village_spinPosition > 0) {
                    village_spinSelectedName = villageNamesList.get(village_spinPosition);
                    village_spinSelectedCode = villageCodesList.get(village_spinPosition);
                    // village_spinSelected_m_LgCode = m_LgCodeList.get(village_spinPosition);
                    // village_spinSelected_mandCode_L = mandCode_LList.get(village_spinPosition);

                    if (Utility.showLogs == 0) {
                        Log.d(TAG, "Crop Name: " + village_spinSelectedName + "\n" +
                                "Code: " + village_spinSelectedCode);
                    }

                    /*if (culture_type_spinPosition > 0)*/
                    validateWaterBodySelection(0);

//                    hide_linear_layout_water_body_spin.setVisibility(View.VISIBLE);


                } else {
                    village_spinSelectedCode = "0";
                    hide_linear_layout_water_body_spin.setVisibility(View.GONE);

                }
                water_body_spin.setSelection(0);
                intended_supplier_spin.setSelection(0);
                supplier_seed_indented_txt.setText("");
                leftover_stocking_qty_txt.setText("");
                ddMMyyyyStr = "";
                date_fish_seed_stocking_btn.setText("Click to Select");

                hide_linear_enter_seed_stocking_dtls_btn.setVisibility(View.GONE);
                hide_linear_layout_intended_supplier_spin.setVisibility(View.GONE);
                hide_linear_layout_leftover_stocking_qty.setVisibility(View.GONE);
                hide_linear_layout_supplier_seed_indented.setVisibility(View.GONE);
                hide_linear_layout_harvesting_data_details.setVisibility(View.GONE);

                if (fishSeedStockingEntryBeansList1.size() > 0)
                    fishSeedStockingEntryBeansList1.clear();

                if (fishSeedStockingEntrRecyclerList1.size() > 0)
                    fishSeedStockingEntrRecyclerList1.clear();

                vehicleNoHashSet = new HashSet<String>();
                alertEnteredSpiecesTotalFLG = 0;

                hide_linear_layout_fish_seed_stocking_waterbody_rv.setVisibility(View.GONE);
                hide_linear_stocking_certificate_submit_btn.setVisibility(View.GONE);
                stocking_certificate_imageview.setVisibility(View.GONE);
                photoBase64Str3 = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private List<String> waterBodyNamesList = new ArrayList<>(),
            waterBodyCodesList = new ArrayList<>();
    private List<WaterBodyBean> waterBodyDataDataList = new ArrayList<>();
    private int water_body_spinPosition = 0;
    private String water_body_spinSelectedCode = "0", water_body_spinSelectedName = "";

    private void waterBodyMstSpinnerValuesList(Spinner spinner) {

      /*  waterBodyNamesList = new ArrayList<>();
        waterBodyCodesList = new ArrayList<>();

        waterBodyDataDataList = new ArrayList<>();

        waterBodyNamesList.add("Select");
        waterBodyCodesList.add("0");*/
//        waterBodyNamesList.add("Pedda cheruvu");
//        waterBodyCodesList.add("0");

        /*for (int i = 0; i < waterBodyDataDataList.size(); i++) {
            waterBodyNamesList.add(waterBodyDataDataList.get(i).getVillName());
            waterBodyCodesList.add(waterBodyDataDataList.get(i).getVillCode());
        }*/

        Utility.assignArrayAdpListToSpin(FishSeedStockingNavigation.this, waterBodyNamesList, spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                water_body_spinPosition = position;

                error_msg_txt.setVisibility(View.GONE);
                if (water_body_spinPosition > 0) {
                    water_body_spinSelectedName = waterBodyNamesList.get(water_body_spinPosition);
                    water_body_spinSelectedCode = waterBodyCodesList.get(water_body_spinPosition);

                    if (Utility.showLogs == 0) {
                        Log.d(TAG, "water body Name: " + water_body_spinSelectedName + "\n" +
                                "water body Code: " + water_body_spinSelectedCode);
                    }

                    // supplier_seed_indented_txt.setText(water_spread_areaList.get(water_body_spinPosition));


                    callGetIntendedSupplierListJSONMastersData("WaterbodySupplierlist");
//                    hide_linear_layout_intended_supplier_spin.setVisibility(View.VISIBLE);


                } else {
                    water_body_spinSelectedCode = "0";
                    hide_linear_layout_intended_supplier_spin.setVisibility(View.GONE);

                }

                intended_supplier_spin.setSelection(0);
                supplier_seed_indented_txt.setText("");
                leftover_stocking_qty_txt.setText("");
                ddMMyyyyStr = "";
                date_fish_seed_stocking_btn.setText("Click to Select");

                hide_linear_enter_seed_stocking_dtls_btn.setVisibility(View.GONE);
                hide_linear_layout_leftover_stocking_qty.setVisibility(View.GONE);
                hide_linear_layout_supplier_seed_indented.setVisibility(View.GONE);

                hide_linear_layout_harvesting_data_details.setVisibility(View.GONE);
                hide_linear_layout_fish_seed_stocking_waterbody_rv.setVisibility(View.GONE);

                if (fishSeedStockingEntryBeansList1.size() > 0)
                    fishSeedStockingEntryBeansList1.clear();

                if (fishSeedStockingEntrRecyclerList1.size() > 0)
                    fishSeedStockingEntrRecyclerList1.clear();

                vehicleNoHashSet = new HashSet<String>();
                alertEnteredSpiecesTotalFLG = 0;

                hide_linear_stocking_certificate_submit_btn.setVisibility(View.GONE);
                stocking_certificate_imageview.setVisibility(View.GONE);
                photoBase64Str3 = "";

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        hide_linear_layout_water_body_spin.setVisibility(View.VISIBLE);

        error_msg_txt.setText("");
        error_msg_txt.setVisibility(View.GONE);
        hide_linear_layout_harvesting_data_details.setVisibility(View.VISIBLE);

        // intendedSupplierMstSpinnerValuesList(intended_supplier_spin);
    }

    private void callGetIntendedSupplierListJSONMastersData(String masterName) {
        progressDialog = new ProgressDialog(FishSeedStockingNavigation.this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Getting " + masterName + " Data...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        new GetMastersJSONData(FishSeedStockingNavigation.this, masterName).execute(
                water_body_spinSelectedCode,
                fin_year_spinSelectedName,
                culture_type_spinSelectedCode
        );

    }

    private List<String> intendedSupplierNamesList = new ArrayList<>(),
            intendedSupplierCodesList = new ArrayList<>();
    // private List<intendedSupplierBean> intendedSupplierDataDataList = new ArrayList<>();
    private int intended_supplier_spinPosition = 0;
    private String intended_supplier_spinSelectedCode = "0", intended_supplier_spinSelectedName = "";

    private void intendedSupplierMstSpinnerValuesList(Spinner spinner) {

      /*  intendedSupplierNamesList = new ArrayList<>();
        intendedSupplierCodesList = new ArrayList<>();

        // intendedSupplierDataDataList = new ArrayList<>();

        intendedSupplierNamesList.add("Select");
        intendedSupplierCodesList.add("0");
        intendedSupplierNamesList.add("Ramesh Seed farm");
        intendedSupplierCodesList.add("0");*/

        /*for (int i = 0; i < intendedSupplierDataDataList.size(); i++) {
            intendedSupplierNamesList.add(intendedSupplierDataDataList.get(i).getVillName());
            intendedSupplierCodesList.add(intendedSupplierDataDataList.get(i).getVillCode());
        }*/

        Utility.assignArrayAdpListToSpin(FishSeedStockingNavigation.this, intendedSupplierNamesList, spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intended_supplier_spinPosition = position;
                error_msg_txt.setVisibility(View.GONE);

                if (intended_supplier_spinPosition > 0) {
                    intended_supplier_spinSelectedName = intendedSupplierNamesList.get(intended_supplier_spinPosition);
                    intended_supplier_spinSelectedCode = intendedSupplierCodesList.get(intended_supplier_spinPosition);


                    if (Utility.showLogs == 0) {
                        Log.d(TAG, "water body Name: " + intended_supplier_spinSelectedName + "\n" +
                                "water body Code: " + intended_supplier_spinSelectedCode);
                    }

                    // supplier_seed_indented_txt.setText(water_spread_areaList.get(intended_supplier_spinPosition));

                    ddMMyyyyStr = "";
                    date_fish_seed_stocking_btn.setText("Click to Select");

                    callGetWaterbodySupplierIndentingData("SupplierIndentingData");

                   /* supplier_seed_indented_txt.setText("1000000");
                    leftover_stocking_qty_txt.setText("1000000");

                    hide_linear_layout_supplier_seed_indented.setVisibility(View.VISIBLE);
                    hide_linear_layout_leftover_stocking_qty.setVisibility(View.VISIBLE);
                    hide_linear_layout_harvesting_data_details.setVisibility(View.VISIBLE);*/

                } else {
                    intended_supplier_spinSelectedCode = "0";

                    supplier_seed_indented_txt.setText("");
                    leftover_stocking_qty_txt.setText("");
                    ddMMyyyyStr = "";
                    date_fish_seed_stocking_btn.setText("Click to Select");


                    hide_linear_layout_leftover_stocking_qty.setVisibility(View.GONE);
                    hide_linear_layout_supplier_seed_indented.setVisibility(View.GONE);
                    hide_linear_layout_harvesting_data_details.setVisibility(View.GONE);

                }

                hide_linear_enter_seed_stocking_dtls_btn.setVisibility(View.GONE);
                hide_linear_layout_fish_seed_stocking_waterbody_rv.setVisibility(View.GONE);

                if (fishSeedStockingEntryBeansList1.size() > 0)
                    fishSeedStockingEntryBeansList1.clear();

                if (fishSeedStockingEntrRecyclerList1.size() > 0)
                    fishSeedStockingEntrRecyclerList1.clear();

                vehicleNoHashSet = new HashSet<String>();
                alertEnteredSpiecesTotalFLG = 0;

                hide_linear_stocking_certificate_submit_btn.setVisibility(View.GONE);
                stocking_certificate_imageview.setVisibility(View.GONE);
                photoBase64Str3 = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        hide_linear_layout_intended_supplier_spin.setVisibility(View.VISIBLE);

        error_msg_txt.setText("");
        error_msg_txt.setVisibility(View.GONE);
        hide_linear_layout_harvesting_data_details.setVisibility(View.VISIBLE);

        // supplier_seed_indented_txt.setText("1000000");
        //  leftover_stocking_qty_txt.setText("1000000");
    }

    private void callGetWaterbodySupplierIndentingData(String masterName) {
        progressDialog = new ProgressDialog(FishSeedStockingNavigation.this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Getting " + masterName + " Data...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        new GetMastersJSONData(FishSeedStockingNavigation.this, masterName).execute(
                water_body_spinSelectedCode,
                intended_supplier_spinSelectedCode,
                fin_year_spinSelectedName,
                culture_type_spinSelectedCode
        );

    }

    private Spinner supplier_seed_intended_waterbody_spin;
    private int supplier_seed_intended_waterbody_spinPosition = 0;

    private void waterBodySupplierSeedIntendedSpnList(Spinner spinner) {

        Utility.assignArrayAdpListToSpin(FishSeedStockingNavigation.this, fishSeedIndentedList, spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                supplier_seed_intended_waterbody_spinPosition = position;

                error_msg_txt.setVisibility(View.GONE);

                if (supplier_seed_intended_waterbody_spinPosition > 0) {

                 //   error_msg_txt.setVisibility(View.GONE);

                    StackingIdStr = stackingIdList.get(supplier_seed_intended_waterbody_spinPosition);
                    String supplier_seed_intended_waterbody_spinSelected_stocking_Left =
                            stocking_LeftList.get(supplier_seed_intended_waterbody_spinPosition);

                    Stocking_LeftSpinStr = supplier_seed_intended_waterbody_spinSelected_stocking_Left;


                    if (Utility.showLogs == 0) {
                        Log.d(TAG, "StackingIdStr: " + StackingIdStr + "\n" +
                                "stocking_Left: " + supplier_seed_intended_waterbody_spinSelected_stocking_Left + "\n" +
                                "fishSeedIndented: " +
                                supplier_seed_intended_waterbody_spin.getSelectedItem().toString().trim());

                        Log.d(TAG, "Stocking_LeftSpinStr: " +Stocking_LeftSpinStr);
                    }


                    if (!supplier_seed_intended_waterbody_spinSelected_stocking_Left.equalsIgnoreCase("0")) {
                        ddMMyyyyStr = "";
                        date_fish_seed_stocking_btn.setText("Click to Select");

                        supplier_seed_indented_txt.setText("" + supplier_seed_intended_waterbody_spin.getSelectedItem().toString().trim());
                        leftover_stocking_qty_txt.setText("" + supplier_seed_intended_waterbody_spinSelected_stocking_Left);

                        hide_linear_layout_supplier_seed_indented.setVisibility(View.VISIBLE);
                        hide_linear_layout_leftover_stocking_qty.setVisibility(View.VISIBLE);
                        hide_linear_layout_harvesting_data_details.setVisibility(View.VISIBLE);
                    } else {

                        supplier_seed_indented_txt.setText("");
                        leftover_stocking_qty_txt.setText("");
                        ddMMyyyyStr = "";
                        date_fish_seed_stocking_btn.setText("Click to Select");

                        hide_linear_layout_leftover_stocking_qty.setVisibility(View.GONE);
//                        hide_linear_layout_supplier_seed_indented.setVisibility(View.GONE);
                        hide_linear_layout_harvesting_data_details.setVisibility(View.GONE);

                        error_msg_txt.setText("Entry not allowed as Leftover Stocking Quantity for supplier= " + Stocking_LeftSpinStr);
                        error_msg_txt.setVisibility(View.VISIBLE);
                    }

                } else {
                    StackingIdStr = "0";

                    supplier_seed_indented_txt.setText("");
                    leftover_stocking_qty_txt.setText("");
                    ddMMyyyyStr = "";
                    date_fish_seed_stocking_btn.setText("Click to Select");

                    hide_linear_layout_leftover_stocking_qty.setVisibility(View.GONE);
//                    hide_linear_layout_supplier_seed_indented.setVisibility(View.GONE);
                    hide_linear_layout_harvesting_data_details.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void callMastersData() {

        if (db.getTableCount("DISTRICT_MST") == 0) {

            if (Utility.showLogs == 0)
                Log.d(TAG, "B4 calling ws_dist_data Dist. Count: " + db.getTableCount("DISTRICT_MST"));

            mastersFlag = "DISTRICT_MST";
            callGetJSONMastersData(mastersFlag);
        } else if (db.getTableCount("MANDAL_MST") == 0) {

            if (Utility.showLogs == 0)
                Log.d(TAG, "B4 calling ws_mand_data Mand. Count: " + db.getTableCount("MANDAL_MST"));

            mastersFlag = "MANDAL_MST";
            callGetJSONMastersData(mastersFlag);


        } else if (db.getTableCount("VILLAGE_MST") == 0) {

            if (Utility.showLogs == 0)
                Log.d(TAG, "B4 calling ws_vill_data Vill. Count: " + db.getTableCount("VILLAGE_MST"));

            mastersFlag = "VILLAGE_MST";
            callGetJSONMastersData(mastersFlag);
        } else if (db.getTableCount("SeasonalityMst") == 0) {

            if (Utility.showLogs == 0)
                Log.d(TAG, "B4 calling SeasonalityMst  Count: " + db.getTableCount("SeasonalityMst"));

            mastersFlag = "SeasonalityMst";
            callGetJSONMastersData(mastersFlag);
        } else if (db.getTableCount("CultureTypeMst") == 0) {

            if (Utility.showLogs == 0)
                Log.d(TAG, "B4 calling CultureTypeMst Count: " + db.getTableCount("CultureTypeMst"));

            mastersFlag = "CultureTypeMst";
            callGetJSONMastersData(mastersFlag);


        } else if (db.getTableCount("SpeciesMst") == 0) {

            if (Utility.showLogs == 0)
                Log.d(TAG, "B4 calling SpeciesMst Count: " + db.getTableCount("SpeciesMst"));

            mastersFlag = "SpeciesMst";
            callGetJSONMastersData(mastersFlag);
        } else if (db.getTableCount("FinYearMST") == 0) {

            if (Utility.showLogs == 0)
                Log.d(TAG, "B4 calling SpeciesMst Count: " + db.getTableCount("FinYearMST"));

            mastersFlag = "FinYearMST";
            callGetJSONMastersData(mastersFlag);
        } else {
            callAssignSpinners();
        }
    }

    //    private int activityVal = 0;
    private void callGetJSONMastersData(String masterName) {
        progressDialog = new ProgressDialog(FishSeedStockingNavigation.this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Getting " + masterName + " Data...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        new GetMastersJSONData(FishSeedStockingNavigation.this, masterName).execute(

                district_spinSelectedCode,
                Mandal_spinSelectedCode,
                village_spinSelectedCode,
                seasonality_spinSelectedCode,
                fin_year_spinSelectedName,
                culture_type_spinSelectedCode


        );//culture_type_spinSelectedCode

    }

    public void parsingGetMastersDataJSONResp(String response) {
        if (Utility.showLogs == 0)
            Log.d("response: ", "parsingGetMastersDataJSONResp response: " + response.toString());

        // progressDialog.dismiss();

        try {
            JSONObject jsonObject = new JSONObject(response);

            if (Utility.showLogs == 0) {
                Log.d(TAG, "SuccessFlag " + jsonObject.getString("SuccessFlag"));
                Log.d(TAG, "SuccessMsg " + jsonObject.getString("SuccessMsg"));
                // Log.d(TAG, "Data " + jsonObject.getString("Data"));
            }


            if (jsonObject.getString("SuccessFlag").equalsIgnoreCase("1")) {

                JSONArray jarray = new JSONArray(jsonObject.getString("Data"));

                if (mastersFlag.equalsIgnoreCase("DISTRICT_MST")) {

                    String StateCodeStr = "",
                            DistCodeStr = "",
                            DistNameStr = "",
                            DistName_TelStr = "",
                            DistCode_LgStr = "";

                    for (int j = 0; j < jarray.length(); j++) {

                        if (jarray.getJSONObject(j).has("StateCode")) {
                            StateCodeStr = jarray.getJSONObject(j).getString("StateCode");
                            if (StateCodeStr.equalsIgnoreCase("") || StateCodeStr.equalsIgnoreCase("anyType{}"))
                                StateCodeStr = "";
                        }
                        if (jarray.getJSONObject(j).has("DistCode")) {
                            DistCodeStr = jarray.getJSONObject(j).getString("DistCode");
                            if (DistCodeStr.equalsIgnoreCase("") || DistCodeStr.equalsIgnoreCase("anyType{}"))
                                DistCodeStr = "";
                        }
                        if (jarray.getJSONObject(j).has("DistName")) {
                            DistNameStr = jarray.getJSONObject(j).getString("DistName");
                            if (DistNameStr.equalsIgnoreCase("") || DistNameStr.equalsIgnoreCase("anyType{}"))
                                DistNameStr = "";
                        }
                        if (jarray.getJSONObject(j).has("DistName_Tel")) {
                            DistName_TelStr = jarray.getJSONObject(j).getString("DistName_Tel");
                            if (DistName_TelStr.equalsIgnoreCase("") || DistName_TelStr.equalsIgnoreCase("anyType{}"))
                                DistName_TelStr = "";
                        }
                        if (jarray.getJSONObject(j).has("DistCode_Lg")) {
                            DistCode_LgStr = jarray.getJSONObject(j).getString("DistCode_Lg");
                            if (DistCode_LgStr.equalsIgnoreCase("") || DistCode_LgStr.equalsIgnoreCase("anyType{}"))
                                DistCode_LgStr = "";
                        }


                        District marketingMstGetCropMiscData
                                = new District(
                                "" + StateCodeStr.trim(),
                                "" + DistCodeStr.trim(),
                                "" + DistNameStr.trim(),
                                "" + DistName_TelStr.trim(),
                                "" + DistCode_LgStr.trim()
                        );

                        db.createDistrictData(marketingMstGetCropMiscData);
                    }

                } else if (mastersFlag.equalsIgnoreCase("MANDAL_MST")) {

                    String DistCodeStr = "",
                            MandCodeStr = "",
                            MandNameStr = "",
                            MandName_TelStr = "",
                            MandCode_LGStr = "";

                    for (int j = 0; j < jarray.length(); j++) {
                        if (jarray.getJSONObject(j).has("DistCode")) {
                            DistCodeStr = jarray.getJSONObject(j).getString("DistCode");
                            if (DistCodeStr.equalsIgnoreCase("") || DistCodeStr.equalsIgnoreCase("anyType{}"))
                                DistCodeStr = "";
                        }

                        if (jarray.getJSONObject(j).has("MandCode")) {
                            MandCodeStr = jarray.getJSONObject(j).getString("MandCode");
                            if (MandCodeStr.equalsIgnoreCase("") || MandCodeStr.equalsIgnoreCase("anyType{}"))
                                MandCodeStr = "";
                        }
                        if (jarray.getJSONObject(j).has("MandName")) {
                            MandNameStr = jarray.getJSONObject(j).getString("MandName");
                            if (MandNameStr.equalsIgnoreCase("") || MandNameStr.equalsIgnoreCase("anyType{}"))
                                MandNameStr = "";
                        }
                        if (jarray.getJSONObject(j).has("MandName_Tel")) {
                            MandName_TelStr = jarray.getJSONObject(j).getString("MandName_Tel");
                            if (MandName_TelStr.equalsIgnoreCase("") || MandName_TelStr.equalsIgnoreCase("anyType{}"))
                                MandName_TelStr = "";
                        }
                        if (jarray.getJSONObject(j).has("MandCode_LG")) {
                            MandCode_LGStr = jarray.getJSONObject(j).getString("MandCode_LG");
                            if (MandCode_LGStr.equalsIgnoreCase("") || MandCode_LGStr.equalsIgnoreCase("anyType{}"))
                                MandCode_LGStr = "";
                        }

                        Mandal marketingMstGetCropMiscData
                                = new Mandal(
                                "" + DistCodeStr.trim(),
                                "" + MandCodeStr.trim(),
                                "" + MandNameStr.trim(),
                                "" + MandName_TelStr.trim(),
                                "" + MandCode_LGStr.trim()
                        );

                        db.createMandalData(marketingMstGetCropMiscData);
                    }
                } else if (mastersFlag.equalsIgnoreCase("VILLAGE_MST")) {

                    String DistCodeStr = "",
                            MandCodeStr = "",
                            VillCodeStr = "",
                            VillNameStr = "",
                            VillName_TelStr = "",
                            VillCode_LGStr = "";

                    for (int j = 0; j < jarray.length(); j++) {
                        if (jarray.getJSONObject(j).has("DistCode")) {
                            DistCodeStr = jarray.getJSONObject(j).getString("DistCode");
                            if (DistCodeStr.equalsIgnoreCase("") || DistCodeStr.equalsIgnoreCase("anyType{}"))
                                DistCodeStr = "";
                        }

                        if (jarray.getJSONObject(j).has("MandCode")) {
                            MandCodeStr = jarray.getJSONObject(j).getString("MandCode");
                            if (MandCodeStr.equalsIgnoreCase("") || MandCodeStr.equalsIgnoreCase("anyType{}"))
                                MandCodeStr = "";
                        }
                        if (jarray.getJSONObject(j).has("VillCode")) {
                            VillCodeStr = jarray.getJSONObject(j).getString("VillCode");
                            if (VillCodeStr.equalsIgnoreCase("") || VillCodeStr.equalsIgnoreCase("anyType{}"))
                                VillCodeStr = "";
                        }
                        if (jarray.getJSONObject(j).has("VillName")) {
                            VillNameStr = jarray.getJSONObject(j).getString("VillName");
                            if (VillNameStr.equalsIgnoreCase("") || VillNameStr.equalsIgnoreCase("anyType{}"))
                                VillNameStr = "";
                        }
                        if (jarray.getJSONObject(j).has("VillName_Tel")) {
                            VillName_TelStr = jarray.getJSONObject(j).getString("VillName_Tel");
                            if (VillName_TelStr.equalsIgnoreCase("") || VillName_TelStr.equalsIgnoreCase("anyType{}"))
                                VillName_TelStr = "";
                        }
                        if (jarray.getJSONObject(j).has("VillCode_LG")) {
                            VillCode_LGStr = jarray.getJSONObject(j).getString("VillCode_LG");
                            if (VillCode_LGStr.equalsIgnoreCase("") || VillCode_LGStr.equalsIgnoreCase("anyType{}"))
                                VillCode_LGStr = "";
                        }


                        Village marketingMstGetCropMiscData
                                = new Village(
                                "" + DistCodeStr.trim(),
                                "" + MandCodeStr.trim(),
                                "" + VillCodeStr.trim(),
                                "" + VillNameStr.trim(),
                                "" + VillName_TelStr.trim(),
                                "" + VillCode_LGStr.trim()
                        );

                        db.createVillageData(marketingMstGetCropMiscData);
                    }
                } else if (mastersFlag.equalsIgnoreCase("SeasonalityMst")) {

                    String SNoStr = "",
                            SeasionalityStr = "";

                    for (int j = 0; j < jarray.length(); j++) {

                        if (jarray.getJSONObject(j).has("SNo")) {
                            SNoStr = jarray.getJSONObject(j).getString("SNo");
                            if (SNoStr.equalsIgnoreCase("") || SNoStr.equalsIgnoreCase("anyType{}"))
                                SNoStr = "";
                        }
                        if (jarray.getJSONObject(j).has("Seasionality")) {
                            SeasionalityStr = jarray.getJSONObject(j).getString("Seasionality");
                            if (SeasionalityStr.equalsIgnoreCase("") || SeasionalityStr.equalsIgnoreCase("anyType{}"))
                                SeasionalityStr = "";
                        }


                        SeasonalityBean marketingMstGetCropMiscData
                                = new SeasonalityBean(
                                "" + SNoStr.trim(),
                                "" + SeasionalityStr.trim()
                        );

                        db.createSeasonalityData(marketingMstGetCropMiscData);
                    }

                } else if (mastersFlag.equalsIgnoreCase("CultureTypeMst")) {

                    String Fishculture_cdStr = "",
                            Fishculture_NameStr = "";

                    for (int j = 0; j < jarray.length(); j++) {

                        if (jarray.getJSONObject(j).has("Fishculture_cd")) {
                            Fishculture_cdStr = jarray.getJSONObject(j).getString("Fishculture_cd");
                            if (Fishculture_cdStr.equalsIgnoreCase("") || Fishculture_cdStr.equalsIgnoreCase("anyType{}"))
                                Fishculture_cdStr = "";
                        }
                        if (jarray.getJSONObject(j).has("Fishculture_Name")) {
                            Fishculture_NameStr = jarray.getJSONObject(j).getString("Fishculture_Name");
                            if (Fishculture_NameStr.equalsIgnoreCase("") || Fishculture_NameStr.equalsIgnoreCase("anyType{}"))
                                Fishculture_NameStr = "";
                        }


                        CultureTypeBean marketingMstGetCropMiscData
                                = new CultureTypeBean(
                                "" + Fishculture_cdStr.trim(),
                                "" + Fishculture_NameStr.trim()
                        );

                        db.createCultureTypeData(marketingMstGetCropMiscData);
                    }

                } else if (mastersFlag.equalsIgnoreCase("SpeciesMst")) {
                    String LandTypecodeStr = "",
                            LandTypedescStr = "",
                            TypeStr = "";

                    for (int j = 0; j < jarray.length(); j++) {

                        if (jarray.getJSONObject(j).has("LandTypecode")) {
                            LandTypecodeStr = jarray.getJSONObject(j).getString("LandTypecode");
                            if (LandTypecodeStr.equalsIgnoreCase("") || LandTypecodeStr.equalsIgnoreCase("anyType{}"))
                                LandTypecodeStr = "";
                        }
                        if (jarray.getJSONObject(j).has("LandTypedesc")) {
                            LandTypedescStr = jarray.getJSONObject(j).getString("LandTypedesc");
                            if (LandTypedescStr.equalsIgnoreCase("") || LandTypedescStr.equalsIgnoreCase("anyType{}"))
                                LandTypedescStr = "";
                        }
                        if (jarray.getJSONObject(j).has("Type")) {
                            TypeStr = jarray.getJSONObject(j).getString("Type");
                            if (TypeStr.equalsIgnoreCase("") || TypeStr.equalsIgnoreCase("anyType{}"))
                                TypeStr = "";
                        }


                        SpeciesBean marketingMstGetCropMiscData
                                = new SpeciesBean(
                                "" + LandTypecodeStr.trim(),
                                "" + LandTypedescStr.trim(),
                                "" + TypeStr
                        );

                        db.createSpeciesData(marketingMstGetCropMiscData);
                    }
                } else if (mastersFlag.equalsIgnoreCase("FinYearMST")) {

                    String Year_CodeStr = "",
                            Year_DescStr = "";

                    for (int j = 0; j < jarray.length(); j++) {

                        if (jarray.getJSONObject(j).has("Year_Code")) {
                            Year_CodeStr = jarray.getJSONObject(j).getString("Year_Code");
                            if (Year_CodeStr.equalsIgnoreCase("") || Year_CodeStr.equalsIgnoreCase("anyType{}"))
                                Year_CodeStr = "";
                        }
                        if (jarray.getJSONObject(j).has("Year_Desc")) {
                            Year_DescStr = jarray.getJSONObject(j).getString("Year_Desc");
                            if (Year_DescStr.equalsIgnoreCase("") || Year_DescStr.equalsIgnoreCase("anyType{}"))
                                Year_DescStr = "";
                        }


                        FinYearBean marketingMstGetCropMiscData
                                = new FinYearBean(
                                "" + Year_CodeStr.trim(),
                                "" + Year_DescStr.trim()
                        );

                        db.createFinYearData(marketingMstGetCropMiscData);
                    }

                }

                new CountDownTimer(1500, 1000) {
                    //   getFarmerDetailsCropSurvey();


                    @Override
                    public void onTick(long millisUntilFinished) {

//                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFinish() {

                        progressDialog.dismiss();

                        callMastersData();
                    }
                }.start();

            } else {
                progressDialog.dismiss();
            }

        } catch (Exception e) {
            progressDialog.dismiss();
            if (Utility.showLogs == 0)
                Log.d(TAG, "parsingGetMastersDataJSONResp Exception: " + e.toString());
        }

    }

    private List<String> water_spread_areaList = new ArrayList<String>();
    private String water_spread_areaStr = "";

    public void parsingGetWaterBodyDataJSONResp(String response) {
        if (Utility.showLogs == 0)
            Log.d("response: ", "parsingGetWaterBodyDataJSONResp response: " + response.toString());

//        progressDialog.dismiss();

        try {
            JSONObject jsonObject = new JSONObject(response);

            if (Utility.showLogs == 0) {
                Log.d(TAG, "SuccessFlag " + jsonObject.getString("SuccessFlag"));
                Log.d(TAG, "SuccessMsg " + jsonObject.getString("SuccessMsg"));
                // Log.d(TAG, "Data " + jsonObject.getString("Data"));
            }


            if (jsonObject.getString("SuccessFlag").equalsIgnoreCase("1")) {

                JSONArray jarray = new JSONArray(jsonObject.getString("Data"));

                waterBodyNamesList = new ArrayList<String>();
                waterBodyCodesList = new ArrayList<String>();

                water_spread_areaList = new ArrayList<String>();

                waterBodyNamesList.add("Select");
                waterBodyCodesList.add("0");
                water_spread_areaList.add("0");


                currentDate4mServer = "";
                previousDate4mServer = "";

                String wb_codeStr = "",
                        wb_nameStr = "";

                for (int j = 0; j < jarray.length(); j++) {

                    if (jarray.getJSONObject(j).has("wb_code")) {
                        wb_codeStr = jarray.getJSONObject(j).getString("wb_code");
                        if (wb_codeStr.equalsIgnoreCase("") || wb_codeStr.equalsIgnoreCase("anyType{}"))
                            wb_codeStr = "";
                    }
                    if (jarray.getJSONObject(j).has("wb_name")) {
                        wb_nameStr = jarray.getJSONObject(j).getString("wb_name");
                        if (wb_nameStr.equalsIgnoreCase("") || wb_nameStr.equalsIgnoreCase("anyType{}"))
                            wb_nameStr = "";
                    }

                    if (jarray.getJSONObject(j).has("water_spread_area")) {
                        water_spread_areaStr = jarray.getJSONObject(j).getString("water_spread_area");
                        if (water_spread_areaStr.equalsIgnoreCase("") || water_spread_areaStr.equalsIgnoreCase("anyType{}"))
                            water_spread_areaStr = "--";
                    }

                    if (jarray.getJSONObject(0).has("CurrentDt")) {
                        currentDate4mServer = jarray.getJSONObject(0).getString("CurrentDt");
                        if (currentDate4mServer.equalsIgnoreCase("") || currentDate4mServer.equalsIgnoreCase("anyType{}"))
                            currentDate4mServer = "";
                    }

                    if (jarray.getJSONObject(0).has("PrevDate")) {
                        previousDate4mServer = jarray.getJSONObject(0).getString("PrevDate");
                        if (previousDate4mServer.equalsIgnoreCase("") || currentDate4mServer.equalsIgnoreCase("anyType{}"))
                            previousDate4mServer = "";
                    }

                    waterBodyNamesList.add(wb_nameStr);
                    waterBodyCodesList.add(wb_codeStr);
                    water_spread_areaList.add(water_spread_areaStr);

                }

                new CountDownTimer(1500, 1000) {
                    //   getFarmerDetailsCropSurvey();


                    @Override
                    public void onTick(long millisUntilFinished) {

//                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFinish() {

                        progressDialog.dismiss();

                        if (waterBodyNamesList.size() > 0) {
                            waterBodyMstSpinnerValuesList(water_body_spin);
                            hide_linear_layout_water_body_spin.setVisibility(View.VISIBLE);
                        } else {
                            hide_linear_layout_water_body_spin.setVisibility(View.GONE);
                        }
                    }
                }.start();

            } else {
                progressDialog.dismiss();

                //  supplier_seed_indented_txt.setText("--");
                hide_linear_layout_supplier_seed_indented.setVisibility(View.GONE);

                hide_linear_layout_water_body_spin.setVisibility(View.GONE);

                error_msg_txt.setText("Entry not allowed as no water body data found.");
                error_msg_txt.setVisibility(View.VISIBLE);
                hide_linear_layout_harvesting_data_details.setVisibility(View.GONE);

            }

        } catch (Exception e) {
            progressDialog.dismiss();
            hide_linear_layout_water_body_spin.setVisibility(View.GONE);

            //   supplier_seed_indented_txt.setText("--");
            hide_linear_layout_supplier_seed_indented.setVisibility(View.GONE);

            error_msg_txt.setText("Something went wrong!");
            error_msg_txt.setVisibility(View.VISIBLE);
            hide_linear_layout_harvesting_data_details.setVisibility(View.GONE);


            if (Utility.showLogs == 0)
                Log.d(TAG, "parsingGetWaterBodyDataJSONResp Exception: " + e.toString());
        }

    }

    public void parsingGetWaterbodySupplierlistDataJSONResp(String response) {
        if (Utility.showLogs == 0)
            Log.d("response: ", "parsingGetWaterbodySupplierlistDataJSONResp response: " + response.toString());

//        progressDialog.dismiss();

        try {
            JSONObject jsonObject = new JSONObject(response);

            if (Utility.showLogs == 0) {
                Log.d(TAG, "SuccessFlag " + jsonObject.getString("SuccessFlag"));
                Log.d(TAG, "SuccessMsg " + jsonObject.getString("SuccessMsg"));
                // Log.d(TAG, "Data " + jsonObject.getString("Data"));
            }


            if (jsonObject.getString("SuccessFlag").equalsIgnoreCase("1")) {

                JSONArray jarray = new JSONArray(jsonObject.getString("Data"));

                intendedSupplierNamesList = new ArrayList<>();
                intendedSupplierCodesList = new ArrayList<>();

                // intendedSupplierDataDataList = new ArrayList<>();

                intendedSupplierNamesList.add("Select");
                intendedSupplierCodesList.add("0");

                String Supplier_codeStr = "", Supplier_nameStr = "";

                for (int j = 0; j < jarray.length(); j++) {

                    if (jarray.getJSONObject(j).has("Supplier_code")) {
                        Supplier_codeStr = jarray.getJSONObject(j).getString("Supplier_code");
                        if (Supplier_codeStr.equalsIgnoreCase("") || Supplier_codeStr.equalsIgnoreCase("anyType{}") ||
                                Supplier_codeStr.equalsIgnoreCase("null"))
                            Supplier_codeStr = "0";
                    }
                    if (jarray.getJSONObject(j).has("Supplier_name")) {
                        Supplier_nameStr = jarray.getJSONObject(j).getString("Supplier_name");
                        if (Supplier_nameStr.equalsIgnoreCase("") ||
                                Supplier_nameStr.equalsIgnoreCase("anyType{}") ||
                                Supplier_nameStr.equalsIgnoreCase("null"))
                            Supplier_nameStr = "";
                    }

                    intendedSupplierNamesList.add(Supplier_nameStr);
                    intendedSupplierCodesList.add(Supplier_codeStr);

                }

                new CountDownTimer(1500, 1000) {
                    //   getFarmerDetailsCropSurvey();


                    @Override
                    public void onTick(long millisUntilFinished) {

//                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFinish() {

                        progressDialog.dismiss();

                        if (intendedSupplierNamesList.size() > 0) {
                            intendedSupplierMstSpinnerValuesList(intended_supplier_spin);
                            hide_linear_layout_intended_supplier_spin.setVisibility(View.VISIBLE);
                        } else {
                            hide_linear_layout_intended_supplier_spin.setVisibility(View.GONE);
                        }
                    }
                }.start();

            } else {
                progressDialog.dismiss();

                //  supplier_seed_indented_txt.setText("--");
                hide_linear_layout_supplier_seed_indented.setVisibility(View.GONE);

                error_msg_txt.setText("Entry not allowed as no water body data found.");
                error_msg_txt.setVisibility(View.VISIBLE);
                hide_linear_layout_harvesting_data_details.setVisibility(View.GONE);

            }

        } catch (Exception e) {
            progressDialog.dismiss();
            hide_linear_layout_water_body_spin.setVisibility(View.GONE);

            //   supplier_seed_indented_txt.setText("--");
            hide_linear_layout_supplier_seed_indented.setVisibility(View.GONE);

            error_msg_txt.setText("Something went wrong!");
            error_msg_txt.setVisibility(View.VISIBLE);
            hide_linear_layout_harvesting_data_details.setVisibility(View.GONE);


            if (Utility.showLogs == 0)
                Log.d(TAG, "parsingGetWaterBodyDataJSONResp Exception: " + e.toString());
        }

    }

    private String StackingIdStr = "", StackingIdLoopStr = "", FishSeedIndentedStr = "", Stocking_LeftStr = ""
            , Stocking_LeftSpinStr = "";
    private List<String> stackingIdList = new ArrayList<>(), fishSeedIndentedList = new ArrayList<>(),
            stocking_LeftList = new ArrayList<>();

    private int stockingLeftVal = 0;

    public void parsingGetWaterbodySupplierIndentingDataJSONResp(String response) {
        if (Utility.showLogs == 0)
            Log.d("response: ", "parsingGetWaterbodySupplierlistDataJSONResp response: " + response.toString());

//        progressDialog.dismiss();

        try {
            JSONObject jsonObject = new JSONObject(response);

            if (Utility.showLogs == 0) {
                Log.d(TAG, "SuccessFlag " + jsonObject.getString("SuccessFlag"));
                Log.d(TAG, "SuccessMsg " + jsonObject.getString("SuccessMsg"));
                // Log.d(TAG, "Data " + jsonObject.getString("Data"));
            }


            if (jsonObject.getString("SuccessFlag").equalsIgnoreCase("1")) {

                JSONArray jarray = new JSONArray(jsonObject.getString("Data"));

                stockingLeftVal = 0;

                stackingIdList = new ArrayList<>();
                fishSeedIndentedList = new ArrayList<>();
                stocking_LeftList = new ArrayList<>();

                stackingIdList.add("0");
                fishSeedIndentedList.add("Select");
                stocking_LeftList.add("0");

                for (int j = 0; j < jarray.length(); j++) {

                    if (jarray.getJSONObject(j).has("StackingId")) {
                        StackingIdLoopStr = jarray.getJSONObject(j).getString("StackingId");
                        if (StackingIdLoopStr.equalsIgnoreCase("") || StackingIdLoopStr.equalsIgnoreCase("anyType{}") ||
                                StackingIdLoopStr.equalsIgnoreCase("null"))
                            StackingIdLoopStr = "0";
                    }
                    if (jarray.getJSONObject(j).has("FishSeedIndented")) {
                        FishSeedIndentedStr = jarray.getJSONObject(j).getString("FishSeedIndented");
                        if (FishSeedIndentedStr.equalsIgnoreCase("") ||
                                FishSeedIndentedStr.equalsIgnoreCase("anyType{}") ||
                                FishSeedIndentedStr.equalsIgnoreCase("null"))
                            FishSeedIndentedStr = "0";
                    }
                    if (jarray.getJSONObject(j).has("Stocking_Left")) {
                        Stocking_LeftStr = jarray.getJSONObject(j).getString("Stocking_Left");
                        if (Stocking_LeftStr.equalsIgnoreCase("") || Stocking_LeftStr.equalsIgnoreCase("anyType{}") ||
                                Stocking_LeftStr.equalsIgnoreCase("null"))
                            Stocking_LeftStr = "0";
                    }

                    stackingIdList.add(StackingIdLoopStr);
                    fishSeedIndentedList.add(FishSeedIndentedStr);
                    stocking_LeftList.add(Stocking_LeftStr);

                    stockingLeftVal = stockingLeftVal +
                            Integer.parseInt(Stocking_LeftStr);

                    if (Utility.showLogs==0)
                        Log.d(TAG,"parsing stockingLeftVal "+stockingLeftVal);
/*
if (jarray.getJSONObject(0).has("StackingId")) {
                    StackingIdStr = jarray.getJSONObject(0).getString("StackingId");
                    if (StackingIdStr.equalsIgnoreCase("") || StackingIdStr.equalsIgnoreCase("anyType{}") ||
                            StackingIdStr.equalsIgnoreCase("null"))
                        StackingIdStr = "0";
                }
                if (jarray.getJSONObject(0).has("FishSeedIndented")) {
                    FishSeedIndentedStr = jarray.getJSONObject(0).getString("FishSeedIndented");
                    if (FishSeedIndentedStr.equalsIgnoreCase("") ||
                            FishSeedIndentedStr.equalsIgnoreCase("anyType{}") ||
                            FishSeedIndentedStr.equalsIgnoreCase("null"))
                        FishSeedIndentedStr = "0";
                }
                if (jarray.getJSONObject(0).has("Stocking_Left")) {
                    Stocking_LeftStr = jarray.getJSONObject(0).getString("Stocking_Left");
                    if (Stocking_LeftStr.equalsIgnoreCase("") || Stocking_LeftStr.equalsIgnoreCase("anyType{}") ||
                            Stocking_LeftStr.equalsIgnoreCase("null"))
                        Stocking_LeftStr = "0";
                }
*/

                }

                new CountDownTimer(1500, 1000) {
                    //   getFarmerDetailsCropSurvey();


                    @Override
                    public void onTick(long millisUntilFinished) {

//                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFinish() {

                        progressDialog.dismiss();

                        if (stockingLeftVal!=0) {
                            ddMMyyyyStr = "";
                            date_fish_seed_stocking_btn.setText("Click to Select");

                            waterBodySupplierSeedIntendedSpnList(supplier_seed_intended_waterbody_spin);
                            hide_linear_layout_supplier_seed_indented.setVisibility(View.VISIBLE);
                            /*supplier_seed_indented_txt.setText("" + FishSeedIndentedStr);
                            leftover_stocking_qty_txt.setText("" + Stocking_LeftStr);

                            hide_linear_layout_supplier_seed_indented.setVisibility(View.VISIBLE);
                            hide_linear_layout_leftover_stocking_qty.setVisibility(View.VISIBLE);
                            hide_linear_layout_harvesting_data_details.setVisibility(View.VISIBLE);
                            */

                            Stocking_LeftSpinStr = "0";

                        } else {
                            intended_supplier_spinSelectedCode = "0";

                            supplier_seed_indented_txt.setText("");
                            leftover_stocking_qty_txt.setText("");
                            ddMMyyyyStr = "";
                            date_fish_seed_stocking_btn.setText("Click to Select");

                            hide_linear_layout_leftover_stocking_qty.setVisibility(View.GONE);
                            hide_linear_layout_supplier_seed_indented.setVisibility(View.GONE);
                            hide_linear_layout_harvesting_data_details.setVisibility(View.GONE);

                            Stocking_LeftSpinStr = "0";

                            error_msg_txt.setText("Entry not allowed as Leftover Stocking Quantity for supplier= " + Stocking_LeftStr);
                            error_msg_txt.setVisibility(View.VISIBLE);
                        }
                    }
                }.start();

            } else {
                progressDialog.dismiss();

                error_msg_txt.setText("No indent pending for stocking for selected supplier");
                error_msg_txt.setVisibility(View.VISIBLE);
                intended_supplier_spinSelectedCode = "0";

                supplier_seed_indented_txt.setText("");
                leftover_stocking_qty_txt.setText("");
                ddMMyyyyStr = "";
                date_fish_seed_stocking_btn.setText("Click to Select");


                hide_linear_layout_leftover_stocking_qty.setVisibility(View.GONE);
                hide_linear_layout_supplier_seed_indented.setVisibility(View.GONE);
                hide_linear_layout_harvesting_data_details.setVisibility(View.GONE);

                Stocking_LeftSpinStr = "0";

            }

        } catch (Exception e) {
            progressDialog.dismiss();
            hide_linear_layout_water_body_spin.setVisibility(View.GONE);

            //   supplier_seed_indented_txt.setText("--");
            hide_linear_layout_supplier_seed_indented.setVisibility(View.GONE);

            error_msg_txt.setText("Something went wrong!");
            error_msg_txt.setVisibility(View.VISIBLE);
            hide_linear_layout_harvesting_data_details.setVisibility(View.GONE);

            Stocking_LeftSpinStr = "0";

            if (Utility.showLogs == 0)
                Log.d(TAG, "parsingGetWaterBodyDataJSONResp Exception: " + e.toString());
        }

    }

    private void callGetSeedHarvestingJSONData(String masterName) {
        progressDialog = new ProgressDialog(FishSeedStockingNavigation.this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Getting " + masterName + " Data...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        new GetMastersJSONData(FishSeedStockingNavigation.this, masterName).execute(

                water_body_spinSelectedCode,
                fin_year_spinSelectedName,
                cultureTypeCode

        );

    }

    private String stocking_noStr = "0", StockingIdStr = "0";

    public void parsingGetSeedHarvestingStockDataJSONResp(String response) {
        if (Utility.showLogs == 0)
            Log.d("response: ", "parsingGetSeedHarvestingStockDataJSONResp response: " + response.toString());

//        progressDialog.dismiss();

        try {
            JSONObject jsonObject = new JSONObject(response);

            if (Utility.showLogs == 0) {
                Log.d(TAG, "SuccessFlag " + jsonObject.getString("SuccessFlag"));
                Log.d(TAG, "SuccessMsg " + jsonObject.getString("SuccessMsg"));
                // Log.d(TAG, "Data " + jsonObject.getString("Data"));
            }


            if (jsonObject.getString("SuccessFlag").equalsIgnoreCase("1")) {

                JSONArray jarray = new JSONArray(jsonObject.getString("Data"));

                stocking_noStr = jarray.getJSONObject(0).getString("stocking_no").trim();
                StockingIdStr = jarray.getJSONObject(0).getString("StockingId").trim();

                /*waterBodyNamesList = new ArrayList<String>();
                waterBodyCodesList = new ArrayList<String>();

                waterBodyNamesList.add("Select");
                waterBodyCodesList.add("0");

                String wb_codeStr = "",
                        wb_nameStr = "";

                for (int j = 0; j < jarray.length(); j++) {

                    if (jarray.getJSONObject(j).has("wb_code")) {
                        wb_codeStr = jarray.getJSONObject(j).getString("wb_code");
                        if (wb_codeStr.equalsIgnoreCase("") || wb_codeStr.equalsIgnoreCase("anyType{}"))
                            wb_codeStr = "";
                    }
                    if (jarray.getJSONObject(j).has("wb_name")) {
                        wb_nameStr = jarray.getJSONObject(j).getString("wb_name");
                        if (wb_nameStr.equalsIgnoreCase("") || wb_nameStr.equalsIgnoreCase("anyType{}"))
                            wb_nameStr = "";
                    }

                    waterBodyNamesList.add(wb_nameStr);
                    waterBodyCodesList.add(wb_codeStr);


                }*/

                new CountDownTimer(1500, 1000) {
                    //   getFarmerDetailsCropSurvey();


                    @Override
                    public void onTick(long millisUntilFinished) {

//                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFinish() {

                        progressDialog.dismiss();

                        leftover_stocking_qty_txt.setText("" + stocking_noStr);
                        hide_linear_layout_leftover_stocking_qty.setVisibility(View.VISIBLE);
                    }
                }.start();

            } else {
                progressDialog.dismiss();

                hide_linear_layout_leftover_stocking_qty.setVisibility(View.VISIBLE);
                leftover_stocking_qty_txt.setText("--");

            }

        } catch (Exception e) {
            progressDialog.dismiss();
            hide_linear_layout_leftover_stocking_qty.setVisibility(View.GONE);
            leftover_stocking_qty_txt.setText("");
            if (Utility.showLogs == 0)
                Log.d(TAG, "parsingGetSeedHarvestingStockDataJSONResp Exception: " + e.toString());
        }

    }




   public void sumbitData(View v) {

        Utility.callHideKeyBoard(FishSeedStockingNavigation.this);

       // getQuantityList();

    }

    private void validateWaterBodySelection(int val) {

        if (culture_type_spinPosition == 0) {
            Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info", "Select Culture Type", true);
            return;
        }

        if (fin_year_spinPosition == 0) {
            Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info", "Select Fin. year", true);
            return;
        }
       /* if (district_spinPosition == 0) {
            Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info", "Select District", true);
            return;
        }*/

        if (Mandal_spinPosition == 0) {
            Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info", "Select Mandal", true);
            return;
        }

        if (village_spinPosition == 0) {
            Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info", "Select Village", true);
            return;
        }

        if (val == 0) {
            callGetJSONMastersData("WaterBodySSData");
        } else
            validateWaterDate(val);
    }

    private void validateWaterDate(int val) {
        if (water_body_spinPosition == 0) {
            Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info", "Select Water Body", true);
            return;
        }

        if (ddMMyyyyStr == null || ddMMyyyyStr == "") {
            Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info", "Date of Harvesting required.", true);
            return;
        }

        if (currentDate4mServer.isEmpty()) {
            Date today = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            currentDate4mServer = formatter.format(today);

            if (Utility.showLogs == 0) {
                Log.d(TAG, "Today Date is " + today);
                Log.d(TAG, "F-Today Date is " + currentDate4mServer);
            }
        }

        try {
            if (Utility.compareDates(currentDate4mServer, ddMMyyyyStr)) {
                Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info", "Date of Harvesting cannot be greater than today's date.", true);
                return;
            }
        } catch (Exception e) {

        }

        if (val == 0)
            validateOtherFields();
        else if (val == 2)
            validateOtherFields();
        else
            callFishSeedStockingInsert("ER", 2);
//            callFishSeedStockingInsert();
    }

    public static String currentDate4mServer = "", previousDate4mServer = "";

    private void validateOtherFields() {

        if (!quantityEnteredBol) {
            Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info", "Enter at-least one species quantity (Kg's)", true);
            return;
        }

      //  getTotalQuantity();

        if (Integer.valueOf(Tot_HarvestedStr) < 1) {
            Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info", "Enter at-least one species quantity > 0 (Kg's)", true);
            return;
        }

        if (updateDataFoundVal == 0)
            callFishSeedStockingInsert("I", 1);
        else
            callFishSeedStockingInsert("U", 1);

    }
    private int speciesSortingVal = 0;
    private List<SpeciesBean> SpeciesBeanDataList = new ArrayList<SpeciesBean>();
    private List<String> landTypedescList = new ArrayList<>(),
            landTypecodeList = new ArrayList<>(),
            typeList = new ArrayList<>();
    private List<SpeciesBean> speciesDataForRecyclerList = new ArrayList<>();

    private ArrayList<SpeciesBean> getModel(int val) {
        ArrayList<SpeciesBean> list = new ArrayList<>();
        speciesDataForRecyclerList = new ArrayList<>();

        SpeciesBeanDataList = db.getAllSpeciesData();

        landTypedescList = new ArrayList<String>();
        landTypecodeList = new ArrayList<String>();
        typeList = new ArrayList<String>();

        landTypedescList.add("Select");
        landTypecodeList.add("0");
        typeList.add("0");

        for (int i = 0; i < SpeciesBeanDataList.size(); i++) {

            if (Utility.showLogs == 0) {
                Log.d(TAG, "getLandTypedesc " + SpeciesBeanDataList.get(i).getLandTypedesc());
            }

            if (val == 1) {
                if (SpeciesBeanDataList.get(i).getLandTypedesc().equalsIgnoreCase(
                        "Prawn")) {

                    /*||
                        SpeciesBeanDataList.get(i).getLandTypedesc().equalsIgnoreCase(
                                "Others")*/
                    landTypedescList.add(SpeciesBeanDataList.get(i).getLandTypedesc());
                    landTypecodeList.add(SpeciesBeanDataList.get(i).getLandTypecode());
                    typeList.add(SpeciesBeanDataList.get(i).getType());

                    SpeciesBean sortedSpeciesBean = new SpeciesBean(
                            SpeciesBeanDataList.get(i).getLandTypecode(),
                            SpeciesBeanDataList.get(i).getLandTypedesc(),
                            SpeciesBeanDataList.get(i).getType()

                    );

                    speciesDataForRecyclerList.add(sortedSpeciesBean);
                }
            } else {

                if (!SpeciesBeanDataList.get(i).getLandTypedesc().equalsIgnoreCase(
                        "Prawn") &&
                        !SpeciesBeanDataList.get(i).getLandTypedesc().equalsIgnoreCase(
                                "Sea Bass") &&
                        !SpeciesBeanDataList.get(i).getLandTypedesc().equalsIgnoreCase(
                                "Paku")) {

                   /* if (SpeciesBeanDataList.get(i).getLandTypedesc().equalsIgnoreCase(
                            "Jalli Fish or Cat Fish") )
                    landTypedescList.add("Cat Fish (Wallago, Marpu(Indian) Jella etc)");
                    else*/
                    landTypedescList.add(SpeciesBeanDataList.get(i).getLandTypedesc());

                    landTypecodeList.add(SpeciesBeanDataList.get(i).getLandTypecode());
                    typeList.add(SpeciesBeanDataList.get(i).getType());

                    SpeciesBean sortedSpeciesBean = new SpeciesBean(
                            SpeciesBeanDataList.get(i).getLandTypecode(),
                            SpeciesBeanDataList.get(i).getLandTypedesc(),
                            SpeciesBeanDataList.get(i).getType()

                    );
                    speciesDataForRecyclerList.add(sortedSpeciesBean);
                }
            }
        }


        for (int i = 0; i < speciesDataForRecyclerList.size(); i++) {

            SpeciesBean model = new SpeciesBean();
            model.setLandTypedesc(speciesDataForRecyclerList.get(i).getLandTypedesc());
            model.setLandTypecode(speciesDataForRecyclerList.get(i).getLandTypecode());
            list.add(model);
        }
        return list;
    }

    private HashMap<String, String> speciesPriceHashMap = new LinkedHashMap<>();

    private void getQuantityList() {
        List<String> quantities = new ArrayList<>();
        speciesQtyHashMap = new LinkedHashMap<>();
        speciesPriceHashMap = new LinkedHashMap<>();

        quantityEnteredBol = false;

        /*for (int i = 0; i < fish_seed_stocking_waterbody_recyclerview.getChildCount(); i++) {

            String quantityVal = "0";
            String priceQtyPerKgVal = "0";
//            int quantityVal = 0;
            String speciesNameStr = "";

            TextView speciesNameTxt = (fish_seed_stocking_waterbody_recyclerview.getLayoutManager()).findViewByPosition(i).findViewById(R.id.species_harvesing_txt);
//            EditText tet = (fish_seed_stocking_waterbody_recyclerview.getLayoutManager()).findViewByPosition(i).findViewById(R.id.qty_species_harvesting_edt);
            TextView tet = (fish_seed_stocking_waterbody_recyclerview.getLayoutManager()).findViewByPosition(i).findViewById(R.id.qty_species_harvesting_edt);
            TextView priceQ = (fish_seed_stocking_waterbody_recyclerview.getLayoutManager()).findViewByPosition(i).findViewById(R.id.price_per_kg_species_harvesting_edt);

            speciesNameStr = speciesNameTxt.getText().toString();

            if (tet.getText().toString().isEmpty())
                quantities.add("" + quantityVal);
            else {
                quantityEnteredBol = true;
                quantityVal = tet.getText().toString().trim();
                priceQtyPerKgVal = priceQ.getText().toString().trim();
//                quantityVal = Integer.valueOf(tet.getText().toString());
                quantities.add("" + quantityVal);
            }

            speciesQtyHashMap.put(speciesNameStr, quantityVal);
            speciesPriceHashMap.put(speciesNameStr, priceQtyPerKgVal);

           *//* quantities.add(Integer.valueOf(((EditText) Objects.requireNonNull(
                    Objects.requireNonNull(fish_seed_stocking_waterbody_recyclerview.getLayoutManager()).findViewByPosition(i))
                    .findViewById(R.id.qty_species_harvesting_edt)).getText().toString()));*//*
        }*/

        if (Utility.showLogs == 0) {
            Log.d(TAG, "quantities " + quantities);
            Log.d(TAG, "speciesQtyHashMap " + speciesQtyHashMap);
            Log.d(TAG, "speciesPriceHashMap " + speciesPriceHashMap);
        }

        validateWaterBodySelection(2);
//        validateWaterBodySelection(1);
    }

    private String huidValStr = "0";
    private int updateDataFoundVal = 0;

    private void callFishSeedStockingInsert(String flag, int activityVal) {

//        prepareJSONObj();

//        getUpdateJSON();

    /*    progressDialog = new ProgressDialog(FishSeedStockingNavigation.this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Submitting Data...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        new InsertSeedstockingData(FishSeedStockingNavigation.this, activityVal).execute(
                getUpdateJSON(),
                StackingIdStr,
                ATotalofAllEnteredSpieces_str,
                Utility.getSharedPreferences(FishSeedStockingNavigation.this).getString("UserId", ""),
                water_body_spinSelectedCode,
                fin_year_spinSelectedName,
                culture_type_spinSelectedCode,
                photoBase64Str3,
                latitudeStr,
                longitudeStr
        );*/
    }//FishSeedIndentedStr

    private String ATotalofAllEnteredSpieces_str = "0";

    public void parsingInsertSeedstockingDataResp(String response) {

        int i = 0;
        if (Utility.showLogs == 0)
            Log.d("response: ", "response: " + response.toString());


        try {
            JSONObject jsonObject = new JSONObject(response);

            if (Utility.showLogs == 0) {
                Log.d(TAG, "SuccessFlag " + jsonObject.getString("SuccessFlag"));
                Log.d(TAG, "SuccessMsg " + jsonObject.getString("SuccessMsg"));
                // Log.d(TAG, "Data " + jsonObject.getString("Data"));
            }


            if (jsonObject.getString("SuccessFlag").equalsIgnoreCase("1")) {
                progressDialog.dismiss();

                JSONArray jsonArray = new JSONArray(jsonObject.getString("Data"));

                if (Utility.showLogs == 0)
                    Log.d(TAG, "Column1 " + jsonArray.getJSONObject(0).getString("Column1"));

                /*if (jsonArray.getJSONObject(0).getString("Column1").
                        equalsIgnoreCase("1")) {*/

                Toast.makeText(FishSeedStockingNavigation.this,
                        "" + jsonArray.getJSONObject(0).getString("Column1")
                        , Toast.LENGTH_LONG).show();

//                Utility.showAlertDialog(HarvestingNavigation.this, "Info",
//                        "" + s.getProperty("SucessMsg").toString().trim(), true);

                startActivity(new Intent(FishSeedStockingNavigation.this, FishSeedStockingNavigation.class));
                finish();
                /*} else if (jsonArray.getJSONObject(0).getString("Column1").
                        equalsIgnoreCase("2")) {

                    Utility.showAlertDialogToCallPlayStore(FishSeedStockingNavigation.this, "Info",
                            "Update with new version from play store", true);

                } else if (jsonArray.getJSONObject(0).getString("Column1").
                        equalsIgnoreCase("3")) {

                    Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info",
                            "Details Already Submitted for Harvesting Date", true);

                } else if (jsonArray.getJSONObject(0).getString("Column1").
                        equalsIgnoreCase("Details Submitted Successfully")) {

                    Toast.makeText(FishSeedStockingNavigation.this, "Details Submitted Successfully",
                            Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(FishSeedStockingNavigation.this, FishSeedStockingNavigation.class));
                    finish();

                } else {
                    Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info",
                            "Something went wrong.", true);
                }*/


            } else {
                progressDialog.dismiss();

                if (jsonObject.getString("SuccessFlag").
                        equalsIgnoreCase("2")) {

                    Utility.showAlertDialogToCallPlayStore(FishSeedStockingNavigation.this, "Info",
                            "Update with new version from play store", true);
                } else {
                    Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info",
                            "Something went wrong.", true);
                }
            }
        } catch (Exception e) {
            progressDialog.dismiss();
            hide_linear_layout_leftover_stocking_qty.setVisibility(View.GONE);
            leftover_stocking_qty_txt.setText("");
            if (Utility.showLogs == 0)
                Log.d(TAG, "parsingGetSeedHarvestingStockDataJSONResp Exception: " + e.toString());
        }
        /*PropertyInfo pi = new PropertyInfo();
        response.getPropertyInfo(i, pi);
        Object property = response.getProperty(i);
        if (pi.name.equals("SucessData") && property instanceof SoapObject) {
            SoapObject s = (SoapObject) property;

            if (Utility.showLogs == 0) {
                Log.d("response: ", "SucessFlag: " + s.getProperty("SucessFlag").toString().trim());
                Log.d("response: ", "SucessMsg: " + s.getProperty("SucessMsg").toString().trim());
            }

            if (s.getProperty("SucessFlag").toString().trim().equalsIgnoreCase("1")) {
                progressDialog.dismiss();

                Toast.makeText(HarvestingNavigation.this, "" + s.getProperty("SucessMsg").toString().trim(), Toast.LENGTH_SHORT).show();

//                Utility.showAlertDialog(HarvestingNavigation.this, "Info",
//                        "" + s.getProperty("SucessMsg").toString().trim(), true);

                startActivity(new Intent(HarvestingNavigation.this, MainNavigation.class));
                finish();

            } else {
                progressDialog.dismiss();

                if (s.getProperty("SucessFlag").toString().trim().
                        equalsIgnoreCase("2")) {

                    Utility.showAlertDialogToCallPlayStore(HarvestingNavigation.this, "Info",
                            "" + s.getProperty("SucessMsg").toString().trim(), true);
                } else {
                    Utility.showAlertDialog(HarvestingNavigation.this, "Info",
                            "" + s.getProperty("SucessMsg").toString().trim(), true);
                }
            }
        } else {
            progressDialog.dismiss();
        }*/
    }

    private List<UpdateSpeciesBean> updateSpeciesListBeans = new ArrayList<UpdateSpeciesBean>();

    public void parsingGetSpeciesDataInsertFishHarvestingResp(String response) {

        //int i = 0;
        if (Utility.showLogs == 0)
            Log.d("response: ", "response: " + response.toString());


        try {
            JSONObject jsonObject = new JSONObject(response);

            if (Utility.showLogs == 0) {
                Log.d(TAG, "SuccessFlag " + jsonObject.getString("SuccessFlag"));
                Log.d(TAG, "SuccessMsg " + jsonObject.getString("SuccessMsg"));
                // Log.d(TAG, "Data " + jsonObject.getString("Data"));
            }

            if (jsonObject.getString("SuccessFlag").equalsIgnoreCase("1")) {

                String HuidStr = "", DistCodeStr = "", MandCodeStr = "", VillCodeStr = "", DistNameStr = "",
                        MandNameStr = "", VillNameStr = "", Seasonality_codeStr = "",
                        Culture_typeStr = "", Wb_CodeStr = "", Harvesting_dtStr = "",
                        Tot_HarvestedStr = "", CatlaStr = "", RohuStr = "", MrigalaStr = "", CCStr = "",
                        MurrelStr = "", PrawnStr = "", Catfish_jelly_wallageStr = "",
                        OthersStr = "", Catla_PerKgStr = "", Rohu_PerKgStr = "",
                        Mrigala_PerKgStr = "", CC_PerKgStr = "", Murrel_PerKgStr = "",
                        Prawn_PerKgStr = "", Catfish_PerKgStr = "", Others_PerKgStr = "",
                        tilapiaFishStr1 = "",
                        pangasiusFishStr1 = "", tilapiaFish_kgStr = "", pangasiusFish_kgStr = "",
                        wb_nameStr = "", SeasionalityStr = "";

                JSONArray jarray = new JSONArray(jsonObject.getString("Data"));

                if (Utility.showLogs == 0)
                    Log.d(TAG, "HuidStr " + jarray.getJSONObject(0).getString("Huid"));

                updateSpeciesListBeans = new ArrayList<UpdateSpeciesBean>();

                for (int i = 0; i < jarray.length(); i++) {

                    if (jarray.getJSONObject(i).has("Huid")) {
                        HuidStr = jarray.getJSONObject(i).getString("Huid");
                        if (HuidStr.equalsIgnoreCase("") || HuidStr.equalsIgnoreCase("anyType{}") || HuidStr.equalsIgnoreCase("null"))
                            HuidStr = "0";
                    }

                    huidValStr = HuidStr;

                    if (jarray.getJSONObject(i).has("DistCode")) {
                        DistCodeStr = jarray.getJSONObject(i).getString("DistCode");
                        if (DistCodeStr.equalsIgnoreCase("") || DistCodeStr.equalsIgnoreCase("anyType{}") || DistCodeStr.equalsIgnoreCase("null"))
                            DistCodeStr = "";
                    }
                    if (jarray.getJSONObject(i).has("MandCode")) {
                        MandCodeStr = jarray.getJSONObject(i).getString("MandCode");
                        if (MandCodeStr.equalsIgnoreCase("") || MandCodeStr.equalsIgnoreCase("anyType{}") || MandCodeStr.equalsIgnoreCase("null"))
                            MandCodeStr = "";
                    }
                    if (jarray.getJSONObject(i).has("VillCode")) {
                        VillCodeStr = jarray.getJSONObject(i).getString("VillCode");
                        if (VillCodeStr.equalsIgnoreCase("") || VillCodeStr.equalsIgnoreCase("anyType{}") || VillCodeStr.equalsIgnoreCase("null"))
                            VillCodeStr = "";
                    }
                    if (jarray.getJSONObject(i).has("DistName")) {
                        DistNameStr = jarray.getJSONObject(i).getString("DistName");
                        if (DistNameStr.equalsIgnoreCase("") || DistNameStr.equalsIgnoreCase("anyType{}") || DistNameStr.equalsIgnoreCase("null"))
                            DistNameStr = "";
                    }
                    if (jarray.getJSONObject(i).has("MandName")) {
                        MandNameStr = jarray.getJSONObject(i).getString("MandName");
                        if (MandNameStr.equalsIgnoreCase("") || MandNameStr.equalsIgnoreCase("anyType{}") || MandNameStr.equalsIgnoreCase("null"))
                            MandNameStr = "";
                    }
                    if (jarray.getJSONObject(i).has("VillName")) {
                        VillNameStr = jarray.getJSONObject(i).getString("VillName");
                        if (VillNameStr.equalsIgnoreCase("") || VillNameStr.equalsIgnoreCase("anyType{}") || VillNameStr.equalsIgnoreCase("null"))
                            VillNameStr = "";
                    }
                    if (jarray.getJSONObject(i).has("Seasonality_code")) {
                        Seasonality_codeStr = jarray.getJSONObject(i).getString("Seasonality_code");
                        if (Seasonality_codeStr.equalsIgnoreCase("") || Seasonality_codeStr.equalsIgnoreCase("anyType{}") || Seasonality_codeStr.equalsIgnoreCase("null"))
                            Seasonality_codeStr = "";
                    }
                    if (jarray.getJSONObject(i).has("Culture_type")) {
                        Culture_typeStr = jarray.getJSONObject(i).getString("Culture_type");
                        if (Culture_typeStr.equalsIgnoreCase("") || Culture_typeStr.equalsIgnoreCase("anyType{}") || Culture_typeStr.equalsIgnoreCase("null"))
                            Culture_typeStr = "";
                    }
                    if (jarray.getJSONObject(i).has("Wb_Code")) {
                        Wb_CodeStr = jarray.getJSONObject(i).getString("Wb_Code");
                        if (Wb_CodeStr.equalsIgnoreCase("") || Wb_CodeStr.equalsIgnoreCase("anyType{}") || Wb_CodeStr.equalsIgnoreCase("null"))
                            Wb_CodeStr = "";
                    }
                    if (jarray.getJSONObject(i).has("Harvesting_dt")) {
                        Harvesting_dtStr = jarray.getJSONObject(i).getString("Harvesting_dt");
                        if (Harvesting_dtStr.equalsIgnoreCase("") || Harvesting_dtStr.equalsIgnoreCase("anyType{}") || Harvesting_dtStr.equalsIgnoreCase("null"))
                            Harvesting_dtStr = "";
                    }
                    if (jarray.getJSONObject(i).has("Tot_Harvested")) {
                        Tot_HarvestedStr = jarray.getJSONObject(i).getString("Tot_Harvested");
                        if (Tot_HarvestedStr.equalsIgnoreCase("") || Tot_HarvestedStr.equalsIgnoreCase("anyType{}") || Tot_HarvestedStr.equalsIgnoreCase("null"))
                            Tot_HarvestedStr = "";
                    }
                    if (jarray.getJSONObject(i).has("Catla")) {
                        CatlaStr = jarray.getJSONObject(i).getString("Catla");
                        if (CatlaStr.equalsIgnoreCase("") || CatlaStr.equalsIgnoreCase("anyType{}") || CatlaStr.equalsIgnoreCase("null"))
                            CatlaStr = "0";
                    }
                    if (jarray.getJSONObject(i).has("Rohu")) {
                        RohuStr = jarray.getJSONObject(i).getString("Rohu");
                        if (RohuStr.equalsIgnoreCase("") || RohuStr.equalsIgnoreCase("anyType{}") || RohuStr.equalsIgnoreCase("null"))
                            RohuStr = "0";
                    }
                    if (jarray.getJSONObject(i).has("Mrigala")) {
                        MrigalaStr = jarray.getJSONObject(i).getString("Mrigala");
                        if (MrigalaStr.equalsIgnoreCase("") || MrigalaStr.equalsIgnoreCase("anyType{}") || MrigalaStr.equalsIgnoreCase("null"))
                            MrigalaStr = "0";
                    }
                    if (jarray.getJSONObject(i).has("CC")) {
                        CCStr = jarray.getJSONObject(i).getString("CC");
                        if (CCStr.equalsIgnoreCase("") || CCStr.equalsIgnoreCase("anyType{}") || CCStr.equalsIgnoreCase("null"))
                            CCStr = "0";
                    }
                    if (jarray.getJSONObject(i).has("Murrel")) {
                        MurrelStr = jarray.getJSONObject(i).getString("Murrel");
                        if (MurrelStr.equalsIgnoreCase("") || MurrelStr.equalsIgnoreCase("anyType{}") || MurrelStr.equalsIgnoreCase("null"))
                            MurrelStr = "0";
                    }
                    if (jarray.getJSONObject(i).has("Prawn")) {
                        PrawnStr = jarray.getJSONObject(i).getString("Prawn");
                        if (PrawnStr.equalsIgnoreCase("") || PrawnStr.equalsIgnoreCase("anyType{}") || PrawnStr.equalsIgnoreCase("null"))
                            PrawnStr = "0";
                    }
                    if (jarray.getJSONObject(i).has("Catfish_jelly_wallage")) {
                        Catfish_jelly_wallageStr = jarray.getJSONObject(i).getString("Catfish_jelly_wallage");
                        if (Catfish_jelly_wallageStr.equalsIgnoreCase("") || Catfish_jelly_wallageStr.equalsIgnoreCase("anyType{}") || Catfish_jelly_wallageStr.equalsIgnoreCase("null"))
                            Catfish_jelly_wallageStr = "";
                    }
                    if (jarray.getJSONObject(i).has("Others")) {
                        OthersStr = jarray.getJSONObject(i).getString("Others");
                        if (OthersStr.equalsIgnoreCase("") || OthersStr.equalsIgnoreCase("anyType{}") || OthersStr.equalsIgnoreCase("null"))
                            OthersStr = "0";
                    }
                    if (jarray.getJSONObject(i).has("Catla_PerKg")) {
                        Catla_PerKgStr = jarray.getJSONObject(i).getString("Catla_PerKg");
                        if (Catla_PerKgStr.equalsIgnoreCase("") || Catla_PerKgStr.equalsIgnoreCase("anyType{}") || Catla_PerKgStr.equalsIgnoreCase("null"))
                            Catla_PerKgStr = "0";
                    }
                    if (jarray.getJSONObject(i).has("Rohu_PerKg")) {
                        Rohu_PerKgStr = jarray.getJSONObject(i).getString("Rohu_PerKg");
                        if (Rohu_PerKgStr.equalsIgnoreCase("") || Rohu_PerKgStr.equalsIgnoreCase("anyType{}") || Rohu_PerKgStr.equalsIgnoreCase("null"))
                            Rohu_PerKgStr = "0";
                    }
                    if (jarray.getJSONObject(i).has("Mrigala_PerKg")) {
                        Mrigala_PerKgStr = jarray.getJSONObject(i).getString("Mrigala_PerKg");
                        if (Mrigala_PerKgStr.equalsIgnoreCase("") || Mrigala_PerKgStr.equalsIgnoreCase("anyType{}") || Mrigala_PerKgStr.equalsIgnoreCase("null"))
                            Mrigala_PerKgStr = "0";
                    }
                    if (jarray.getJSONObject(i).has("CC_PerKg")) {
                        CC_PerKgStr = jarray.getJSONObject(i).getString("CC_PerKg");
                        if (CC_PerKgStr.equalsIgnoreCase("") || CC_PerKgStr.equalsIgnoreCase("anyType{}") || CC_PerKgStr.equalsIgnoreCase("null"))
                            CC_PerKgStr = "0";
                    }
                    if (jarray.getJSONObject(i).has("Murrel_PerKg")) {
                        Murrel_PerKgStr = jarray.getJSONObject(i).getString("Murrel_PerKg");
                        if (Murrel_PerKgStr.equalsIgnoreCase("") || Murrel_PerKgStr.equalsIgnoreCase("anyType{}") || Murrel_PerKgStr.equalsIgnoreCase("null"))
                            Murrel_PerKgStr = "0";
                    }
                    if (jarray.getJSONObject(i).has("Prawn_PerKg")) {
                        Prawn_PerKgStr = jarray.getJSONObject(i).getString("Prawn_PerKg");
                        if (Prawn_PerKgStr.equalsIgnoreCase("") || Prawn_PerKgStr.equalsIgnoreCase("anyType{}") || Prawn_PerKgStr.equalsIgnoreCase("null"))
                            Prawn_PerKgStr = "0";
                    }
                    if (jarray.getJSONObject(i).has("Catfish_PerKg")) {
                        Catfish_PerKgStr = jarray.getJSONObject(i).getString("Catfish_PerKg");
                        if (Catfish_PerKgStr.equalsIgnoreCase("") || Catfish_PerKgStr.equalsIgnoreCase("anyType{}") || Catfish_PerKgStr.equalsIgnoreCase("null"))
                            Catfish_PerKgStr = "0";
                    }
                    if (jarray.getJSONObject(i).has("Others_PerKg")) {
                        Others_PerKgStr = jarray.getJSONObject(i).getString("Others_PerKg");
                        if (Others_PerKgStr.equalsIgnoreCase("") || Others_PerKgStr.equalsIgnoreCase("anyType{}") || Others_PerKgStr.equalsIgnoreCase("null"))
                            Others_PerKgStr = "0";
                    }
                    if (jarray.getJSONObject(i).has("wb_name")) {
                        wb_nameStr = jarray.getJSONObject(i).getString("wb_name");
                        if (wb_nameStr.equalsIgnoreCase("") || wb_nameStr.equalsIgnoreCase("anyType{}") || wb_nameStr.equalsIgnoreCase("null"))
                            wb_nameStr = "";
                    }
                    if (jarray.getJSONObject(i).has("Seasionality")) {
                        SeasionalityStr = jarray.getJSONObject(i).getString("Seasionality");
                        if (SeasionalityStr.equalsIgnoreCase("") || SeasionalityStr.equalsIgnoreCase("anyType{}") || SeasionalityStr.equalsIgnoreCase("null"))
                            SeasionalityStr = "";
                    }

                    if (jarray.getJSONObject(i).has("tilapiaFish")) {
                        tilapiaFishStr1 = jarray.getJSONObject(i).getString("tilapiaFish");
                        if (tilapiaFishStr1.equalsIgnoreCase("") ||
                                tilapiaFishStr1.equalsIgnoreCase("anyType{}") ||
                                tilapiaFishStr1.equalsIgnoreCase("null"))
                            tilapiaFishStr1 = "0";
                    }

                    if (jarray.getJSONObject(i).has("tilapiaFish_kg")) {
                        tilapiaFish_kgStr = jarray.getJSONObject(i).getString("tilapiaFish_kg");
                        if (tilapiaFish_kgStr.equalsIgnoreCase("") ||
                                tilapiaFish_kgStr.equalsIgnoreCase("anyType{}") ||
                                tilapiaFish_kgStr.equalsIgnoreCase("null"))
                            tilapiaFish_kgStr = "0";
                    }

                    if (jarray.getJSONObject(i).has("pangasiusFish")) {
                        pangasiusFishStr1 = jarray.getJSONObject(i).getString("pangasiusFish");
                        if (pangasiusFishStr1.equalsIgnoreCase("") ||
                                pangasiusFishStr1.equalsIgnoreCase("anyType{}") ||
                                pangasiusFishStr1.equalsIgnoreCase("null"))
                            pangasiusFishStr1 = "0";
                    }

                    if (jarray.getJSONObject(i).has("pangasiusFish_kg")) {
                        pangasiusFish_kgStr = jarray.getJSONObject(i).getString("pangasiusFish_kg");
                        if (pangasiusFish_kgStr.equalsIgnoreCase("") ||
                                pangasiusFish_kgStr.equalsIgnoreCase("anyType{}") ||
                                pangasiusFish_kgStr.equalsIgnoreCase("null"))
                            pangasiusFish_kgStr = "0";
                    }

                    UpdateSpeciesBean updateSpeciesBean = new UpdateSpeciesBean(
                            "" + HuidStr,
                            "" + DistCodeStr,
                            "" + MandCodeStr,
                            "" + VillCodeStr,
                            "" + DistNameStr,
                            "" + MandNameStr,
                            "" + VillNameStr,
                            "" + Seasonality_codeStr,
                            "" + Culture_typeStr,
                            "" + Wb_CodeStr,
                            "" + Harvesting_dtStr,
                            "" + Tot_HarvestedStr,
                            "" + CatlaStr,
                            "" + RohuStr,
                            "" + MrigalaStr,
                            "" + CCStr,
                            "" + MurrelStr,
                            "" + PrawnStr,
                            "" + Catfish_jelly_wallageStr,
                            "" + OthersStr,
                            "" + pangasiusFishStr1,
                            "" + tilapiaFishStr1,
                            "" + Catla_PerKgStr,
                            "" + Rohu_PerKgStr,
                            "" + Mrigala_PerKgStr,
                            "" + CC_PerKgStr,
                            "" + Murrel_PerKgStr,
                            "" + Prawn_PerKgStr,
                            "" + Catfish_PerKgStr,
                            "" + Others_PerKgStr,
                            "" + pangasiusFish_kgStr,
                            "" + tilapiaFish_kgStr,
                            "" + wb_nameStr,
                            "" + SeasionalityStr

                    );

                    updateSpeciesListBeans.add(updateSpeciesBean);

                }


                if (updateSpeciesListBeans.size() > 0) {
                    updateDataFoundVal = 1;
                    prepareSpeciesDetails(speciesSortingVal);
                } else
                    prepareSpeciesDetails(speciesSortingVal);


                progressDialog.dismiss();


            } else {

                huidValStr = "0";
                updateDataFoundVal = 0;

                prepareSpeciesDetails(speciesSortingVal);

                progressDialog.dismiss();
            }
        } catch (Exception e) {
            progressDialog.dismiss();
            hide_linear_layout_leftover_stocking_qty.setVisibility(View.GONE);
            leftover_stocking_qty_txt.setText("");
            if (Utility.showLogs == 0)
                Log.d(TAG, "parsingGetSpeciesDataInsertFishHarvestingResp Exception: " + e.toString());
        }

    }

   // private List vehicleNoList = new ArrayList();



    private void isLocationServicesThere() {

        locationEnbled = false;
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(FishSeedStockingNavigation.this);
            dialog.setMessage("Enable GPS Settings").setCancelable(false).
                    setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub
                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(myIntent);
                            //get gps
                        }
                    });
            /*.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            })*/
            dialog.show();
        } else {


            locationEnbled = true;
            getLocationFusedClient();
        }
    }

    private boolean locationEnbled = false;
    /**
     * FusedLocationProviderApi Save request parameters
     */
    private LocationRequest mLocationRequest;
    /**
     * Provide callbacks for location events.
     */
    private LocationCallback mLocationCallback;
    /**
     * An object representing the current location
     */
    private Location mCurrentLocation;
    //A client that handles connection / connection failures for Google locations
    // (changed from play-services 11.0.0)
    private FusedLocationProviderClient mFusedLocationClient;
    private String provider, latitudeStr = "0.0", longitudeStr = "0.0";
    private boolean isNetworkLocation, isGPSLocation;

    private void getLocationFusedClient() {
        LocationManager mListener = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (mListener != null) {
            isGPSLocation = mListener.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkLocation = mListener.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (Utility.showLogs == 0)
                Log.e("gps, network", String.valueOf(isGPSLocation + "," + isNetworkLocation));
        }

        if (isGPSLocation) {

            provider = LocationManager.GPS_PROVIDER;
                   /* Intent intent = new Intent(SplashScreen.this, Login.class);
                    intent.putExtra("provider", LocationManager.GPS_PROVIDER);
                    startActivity(intent);
                    finish();*/
        } else if (isNetworkLocation) {
            provider = LocationManager.NETWORK_PROVIDER;
                  /*  Intent intent = new Intent(SplashScreen.this, Login.class);
                    intent.putExtra("provider", LocationManager.NETWORK_PROVIDER);
                    startActivity(intent);
                    finish();*/
        } else {
            //Device location is not set
            PermissionUtils.LocationSettingDialog.newInstance().show(getSupportFragmentManager(), "Setting");
        }
       /* handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isGPSLocation) {

                    provider=LocationManager.GPS_PROVIDER;
                   *//* Intent intent = new Intent(SplashScreen.this, Login.class);
                    intent.putExtra("provider", LocationManager.GPS_PROVIDER);
                    startActivity(intent);
                    finish();*//*
                } else if (isNetworkLocation) {
                    provider=LocationManager.NETWORK_PROVIDER;
                  *//*  Intent intent = new Intent(SplashScreen.this, Login.class);
                    intent.putExtra("provider", LocationManager.NETWORK_PROVIDER);
                    startActivity(intent);
                    finish();*//*
                } else {
                    //Device location is not set
                    PermissionUtils.LocationSettingDialog.newInstance().show(getSupportFragmentManager(), "Setting");
                }
            }
        }, 1500);*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            checkMyPermissionLocation();
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        /**
         * Location Setting API to
         */
        SettingsClient mSettingsClient = LocationServices.getSettingsClient(this);
        /*
         * Callback returning location result
         */
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult result) {
                super.onLocationResult(result);
                //mCurrentLocation = locationResult.getLastLocation();
                mCurrentLocation = result.getLocations().get(0);


                if (mCurrentLocation != null) {

                    if (Utility.showLogs == 0) {
                        Log.e("Location(Lat)==", "" + mCurrentLocation.getLatitude());
                        Log.e("Location(Long)==", "" + mCurrentLocation.getLongitude());
                    }

                    latitudeStr = "" + mCurrentLocation.getLatitude();
                    longitudeStr = "" + mCurrentLocation.getLongitude();
                }


                /*MarkerOptions options = new MarkerOptions();
                options.position(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()));
                BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
                options.icon(icon);
                Marker marker = mMap.addMarker(options);

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 17));*/
                /**
                 * To get location information consistently
                 * mLocationRequest.setNumUpdates(1) Commented out
                 * Uncomment the code below
                 */
                mFusedLocationClient.removeLocationUpdates(mLocationCallback);
            }

            //Locatio nMeaning that all relevant information is available
            @Override
            public void onLocationAvailability(LocationAvailability availability) {
                //boolean isLocation = availability.isLocationAvailable();
            }
        };
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(5000);
        //To get location information only once here
        mLocationRequest.setNumUpdates(3);
        if (provider.equalsIgnoreCase(LocationManager.GPS_PROVIDER)) {
            //Accuracy is a top priority regardless of battery consumption
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        } else {
            //Acquired location information based on balance of battery and accuracy (somewhat higher accuracy)
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        }

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        /**
         * Stores the type of location service the client wants to use. Also used for positioning.
         */
        LocationSettingsRequest mLocationSettingsRequest = builder.build();

        Task<LocationSettingsResponse> locationResponse = mSettingsClient.checkLocationSettings(mLocationSettingsRequest);
        locationResponse.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                if (Utility.showLogs == 0)
                    Log.e("Response", "Successful acquisition of location information!!");
                //
                if (ActivityCompat.checkSelfPermission(FishSeedStockingNavigation.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            }
        });
        //When the location information is not set and acquired, callback
        locationResponse.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        if (Utility.showLogs == 0)
                            Log.e("onFailure", "Location environment check");
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        String errorMessage = "Check location setting";
                        Log.e("onFailure", errorMessage);
                }
            }
        });


    }

    private void checkMyPermissionLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //Permission Check
            PermissionUtils.requestPermission(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //If the request code does not match
        if (requestCode != PermissionUtils.REQUEST_CODE) {
            return;
        }
        if (PermissionUtils.isPermissionGranted(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION}, grantResults)) {
            //If you have permission, go to the code to get the location value
            // initGoogleMapLocation();
        } else {
            Toast.makeText(this, "Stop apps without permission to use location information", Toast.LENGTH_SHORT).show();
            //finish();
        }
    }

    /**
     * Remove location information
     */
    @Override
    public void onStop() {
        super.onStop();
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    private List<FishSeedStockingEntryBean> fishSeedStockingEntryBeansList = new ArrayList<>();
    private List<FishSeedStockingEntryBean> fishSeedStockingEntrRecyclerList = new ArrayList<>();

    private List<FishSeedStockingEntryBean1> fishSeedStockingEntryBeansList1 = new ArrayList<>();
    private List<FishSeedStockingEntryBean1> fishSeedStockingEntrRecyclerList1 = new ArrayList<>();

    private List<FishSeedStockingEntryJSON> fishSeedStockingEntryJSONSList = new ArrayList<>();

    //    private FishSeedStockingDataRecyclerAdapter fishSeedStockingEntryDataAdapter;
    private FishSeedStockingDataRecyclerAdapter1 fishSeedStockingEntryDataAdapter1;

    private void prepareFishSeedStockingEntryData() {

        // speciesDataForRecyclerList = getModel(speciesSortingVal);
       /* fishSeedStockingEntryDataAdapter = new FishSeedStockingDataRecyclerAdapter(fishSeedStockingEntryBeansList);
        fish_seed_stocking_waterbody_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        fish_seed_stocking_waterbody_recyclerview.setItemAnimator(new DefaultItemAnimator());
        fish_seed_stocking_waterbody_recyclerview.setAdapter(fishSeedStockingEntryDataAdapter);*/

        fishSeedStockingEntryDataAdapter1 = new FishSeedStockingDataRecyclerAdapter1(fishSeedStockingEntryBeansList1);
        fish_seed_stocking_waterbody_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        fish_seed_stocking_waterbody_recyclerview.setItemAnimator(new DefaultItemAnimator());
        fish_seed_stocking_waterbody_recyclerview.setAdapter(fishSeedStockingEntryDataAdapter1);

        hide_linear_layout_fish_seed_stocking_waterbody_rv.setVisibility(View.VISIBLE);

    }

    private void prepareSpeciesDetails(int speciesSortingVal) {

        // speciesDataForRecyclerList = getModel(speciesSortingVal);
       /* fishSeedStockingEntryDataAdapter = new FishSeedStockingDataRecyclerAdapter(fishSeedStockingEntryBeansList);
        fish_seed_stocking_waterbody_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        fish_seed_stocking_waterbody_recyclerview.setItemAnimator(new DefaultItemAnimator());
        fish_seed_stocking_waterbody_recyclerview.setAdapter(fishSeedStockingEntryDataAdapter);*/

        fishSeedStockingEntryDataAdapter1 = new FishSeedStockingDataRecyclerAdapter1(fishSeedStockingEntryBeansList1);
        fish_seed_stocking_waterbody_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        fish_seed_stocking_waterbody_recyclerview.setItemAnimator(new DefaultItemAnimator());
        fish_seed_stocking_waterbody_recyclerview.setAdapter(fishSeedStockingEntryDataAdapter1);

        hide_linear_layout_fish_seed_stocking_waterbody_rv.setVisibility(View.VISIBLE);
    }

    public interface FishSeedStockingDataRecyclerAdapterListener {
        void onContactSelected(GetSubComponentMstBean contact);
    }

    public class FishSeedStockingDataRecyclerAdapter1 extends RecyclerView.Adapter<FishSeedStockingHolder1> {

        private FishSeedStockingDataRecyclerAdapterListener listener;

        public FishSeedStockingDataRecyclerAdapter1(List<FishSeedStockingEntryBean1> imageModelArrayList) {

            //  inflater = LayoutInflater.from(ctx);
            //   fishSeedStockingEntrRecyclerList = imageModelArrayList;
            // this.ctx = ctx;
        }

        @Override
        public FishSeedStockingHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fish_seed_stocking_waterbody_items1, parent, false);
//                    .inflate(R.layout.farmer_chq_dist_details_items, parent, false);


            return new FishSeedStockingHolder1(itemView);
        }

        @Override
        public void onBindViewHolder(final FishSeedStockingHolder1 holder, final int position) {

            FishSeedStockingEntryBean1 bankDetails = fishSeedStockingEntryBeansList1.get(position);
            holder.spices_name_txt.setText(bankDetails.getSpecies_name());
            holder.fingerling_per_kg_txt.setText(bankDetails.getNo_of_fingerlings_per_kg());
            holder.total_no_of_kgs_txt.setText(bankDetails.getTotal_no_of_kgs());
            holder.total_no_of_fingerlings_txt.setText(bankDetails.getTotal_no_of_fingerlings());
            holder.entered_spices_count_txt.setText(bankDetails.getEntered_species_count());
            holder.vehicle_txt.setText(bankDetails.getVehicle_number());

        }

        public void enableDisable(FishSeedStockingHolder1 holder, String qty) {
            if (Integer.valueOf(qty) > 0) {
               /* holder.qty_species_harvesting_edt.setEnabled(false);
                holder.price_per_kg_species_harvesting_edt.setEnabled(false);
                holder.entry_txt.setEnabled(false);
                holder.entry_txt.setClickable(false);
                holder.entry_txt.setTextColor(getResources().getColor(R.color.red_color_d));*/

            }
        }

        @Override
        public int getItemCount() {
            return fishSeedStockingEntrRecyclerList1.size();
        }

    }

    private List<String> speciesList = new ArrayList<>();

    private int alertEnteredSpiecesTotalFLG = 0;
    private Set<String> vehicleNoHashSet = new HashSet<String>();

    private void alertFishSeedStockingEntry() {
        final Dialog dialog = new Dialog(FishSeedStockingNavigation.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fish_seed_stocking_data_entry_alert1);
        dialog.setCancelable(false);
        Button btn_upload = dialog.findViewById(R.id.btn_update);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        final LinearLayout hide_linear_layout_species_data_entry_alert =
                dialog.findViewById(R.id.hide_linear_layout_species_data_entry_alert);

        final LinearLayout hide_linear_no_of_fingerlings_per_kg_alert_edt = dialog.findViewById(R.id.hide_linear_no_of_fingerlings_per_kg_alert_edt);
        final EditText no_of_fingerlings_per_kg_edt = dialog.findViewById(R.id.no_of_fingerlings_per_kg_alert_edt);

        final LinearLayout hide_linear_total_no_of_kgs_alert_edt = dialog.findViewById(R.id.hide_linear_total_no_of_kgs_alert_edt);
        final EditText total_no_of_kgs_edt = dialog.findViewById(R.id.total_no_of_kgs_alert_edt);

        final EditText total_no_of_fingerlings_alert_edt = dialog.findViewById(R.id.total_no_of_fingerlings_alert_edt);
        final TextView total_no_of_fingerlings_alert_txt = dialog.findViewById(R.id.total_no_of_fingerlings_alert_txt);

        final TextView spices_title_name_alert_txt = dialog.findViewById(R.id.spices_title_name_alert_txt);

        final EditText entered_spices_count_alert_edt = dialog.findViewById(R.id.entered_spices_count_alert_edt);
        /*final EditText rohu_count_alert_edt = dialog.findViewById(R.id.rohu_count_alert_edt);
        final TextView common_carp_alert_edt = dialog.findViewById(R.id.common_carp_alert_edt);
        final TextView mrigala_alert_edt = dialog.findViewById(R.id.mrigala_alert_edt);*/
        final TextView vehicle_number_alert_edt = dialog.findViewById(R.id.vehicle_number_alert_edt);
        final Spinner species_alert_spin = dialog.findViewById(R.id.species_alert_spin);

        total_no_of_kgs_edt.setFilters(new InputFilter[]{new DecimalDigitsInputFilterB4A4(3, 2)});

        if (!seasonalBol) {

            hide_linear_no_of_fingerlings_per_kg_alert_edt.setVisibility(View.VISIBLE);
            hide_linear_total_no_of_kgs_alert_edt.setVisibility(View.VISIBLE);
            total_no_of_fingerlings_alert_txt.setVisibility(View.VISIBLE);

            total_no_of_fingerlings_alert_edt.setVisibility(View.GONE);
        } else {
            total_no_of_fingerlings_alert_edt.setVisibility(View.VISIBLE);

            hide_linear_no_of_fingerlings_per_kg_alert_edt.setVisibility(View.GONE);
            hide_linear_total_no_of_kgs_alert_edt.setVisibility(View.GONE);
            total_no_of_fingerlings_alert_txt.setVisibility(View.GONE);
        }

        no_of_fingerlings_per_kg_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {

                    String total_no_of_fingerlingsStr = "";
                    if (s.toString().trim().length() > 0) {

                        if (total_no_of_kgs_edt.getText().toString().trim().length() > 0) {

                           /* total_no_of_fingerlingsStr = "" + (
                                    Integer.parseInt(s.toString().trim()) *
                                            Integer.parseInt(total_no_of_kgs_edt.getText().toString().trim())
                            );*/

                            //16Sept2021
                            total_no_of_fingerlingsStr = "" + Math.round(
                                    Integer.parseInt(s.toString().trim()) *
                                            Float.parseFloat(total_no_of_kgs_edt.getText().toString().trim())
                            );
                        }
                    }


                    total_no_of_fingerlings_alert_txt.setText(total_no_of_fingerlingsStr);

                    int enteredSpiecesTotalFLG = 0;

                    enteredSpiecesTotalFLG = alertEnteredSpiecesTotalFLG +
                            Integer.parseInt(total_no_of_fingerlingsStr);

                    if (Utility.showLogs == 0)
                        Log.d(TAG, "no_of_fingerlings_per_kg_edt valida: " + enteredSpiecesTotalFLG);

                    if (enteredSpiecesTotalFLG >
                            Integer.parseInt(Stocking_LeftSpinStr)) {
                        Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info",
                                "Entered Total No. of fingerlings cannot be greater than leftover stocking quantity (" + Stocking_LeftSpinStr + ")", true);

                        /*"Entered Total No. of fingerlings (" +enteredSpiecesTotalFLG+
                                        ") cannot be greater than leftover stocking quantity (" + Stocking_LeftStr + ")"*/

                        no_of_fingerlings_per_kg_edt.setText("");

                    }

                } catch (Exception e) {
                    if (Utility.showLogs == 0)
                        Log.d(TAG, "Alert Exception " + e.toString());
                }

            }
        });

        total_no_of_kgs_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    if (no_of_fingerlings_per_kg_edt.getText().toString().trim().length() > 0) {

                        String total_no_of_fingerlingsStr = "";
                        if (s.toString().trim().length() > 0) {
                           /* total_no_of_fingerlingsStr = "" + (
                                    Integer.parseInt(no_of_fingerlings_per_kg_edt.getText().toString().trim()) *
                                            Integer.parseInt(s.toString().trim())
                            );*/

                            //16Sept2021
                            if (!s.toString().trim().startsWith(".")) {
                                if (s.toString().trim().contains(".")) {
                                    if (!s.toString().trim().endsWith(".")) {
                                        if (Utility.showLogs == 0)
                                            Log.d(TAG, "entered str: " + s.toString().trim());

                                        total_no_of_fingerlingsStr = "" + Math.round(
                                                Integer.parseInt(no_of_fingerlings_per_kg_edt.getText().toString().trim()) *
                                                        Float.parseFloat(s.toString().trim())
                                        );


                                        if (Utility.showLogs == 0)
                                            Log.d(TAG, "total_no_of_fingerlingsStr: " +total_no_of_fingerlingsStr);
                                    }

                                } else {
                                    Log.d(TAG, "else entered str: " + s.toString().trim());

                                    total_no_of_fingerlingsStr = "" + Math.round(
                                            Integer.parseInt(no_of_fingerlings_per_kg_edt.getText().toString().trim()) *
                                                    Float.parseFloat(s.toString().trim())
                                    );
                                }

                            }

                        }


                        int enteredSpiecesTotalFLG = 0;

                        enteredSpiecesTotalFLG = alertEnteredSpiecesTotalFLG +
                                Integer.parseInt(total_no_of_fingerlingsStr);

                        if (Utility.showLogs == 0)
                            Log.d(TAG, "total_no_of_kgs_edt valida: " + enteredSpiecesTotalFLG);

                        if (enteredSpiecesTotalFLG >
                                Integer.parseInt(Stocking_LeftSpinStr)) {
                            Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info",
                                    "Entered Total No. of fingerlings cannot be greater than leftover stocking quantity (" + Stocking_LeftSpinStr + ")", true);

                            /*"Entered Total No. of fingerlings (" +enteredSpiecesTotalFLG+
                                            ") cannot be greater than leftover stocking quantity (" + Stocking_LeftStr + ")"*/

                            total_no_of_kgs_edt.setText("");

                        } else
                            total_no_of_fingerlings_alert_txt.setText(total_no_of_fingerlingsStr);
                    }
                } catch (Exception e) {
                    if (Utility.showLogs == 0)
                        Log.d(TAG, "Alert Exception " + e.toString());
                }

            }
        });

        hide_linear_layout_species_data_entry_alert.setVisibility(View.GONE);

        Utility.callHideKeyBoard(FishSeedStockingNavigation.this);
        speciesList = new ArrayList<>();
        speciesList.add("Select");
        speciesList.add("Catla");
        speciesList.add("Rohu");
        if (seasonalBol) {
            speciesList.add("Common Carp");
        } else {
            speciesList.add("Mrigala");
        }

        Utility.assignArrayAdpListToSpin(FishSeedStockingNavigation.this, speciesList, species_alert_spin);
        species_alert_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    spices_title_name_alert_txt.setText(species_alert_spin.getSelectedItem().toString().trim());
                    hide_linear_layout_species_data_entry_alert.setVisibility(View.VISIBLE);
                } else {
                    spices_title_name_alert_txt.setText("");
                    hide_linear_layout_species_data_entry_alert.setVisibility(View.GONE);
                }

                no_of_fingerlings_per_kg_edt.setText("");
                total_no_of_kgs_edt.setText("");
                total_no_of_fingerlings_alert_txt.setText("");
                entered_spices_count_alert_edt.setText("");
                vehicle_number_alert_edt.setText("");
                total_no_of_fingerlings_alert_edt.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Utility.callHideKeyBoard(FishSeedStockingNavigation.this);

                    if (!seasonalBol) {
                        if (no_of_fingerlings_per_kg_edt.getText().toString().trim().length() == 0) {

                            //issuing_quantity_edt.setError("Please enter Qty. in kgs.");
                            Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info",
                                    "Please Enter No. of fingerlings per Kg.", false);

                            return;

                        }

                        if (total_no_of_kgs_edt.getText().toString().trim().length() == 0) {

                            // price_per_kg_species_edt.setError("Please enter price per kg");

                            Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info",
                                    "Please Enter Total No. of Kgs", false);

                            return;

                        }
                    } else {

                        if (total_no_of_fingerlings_alert_edt.getText().toString().trim().length() == 0) {

                            // price_per_kg_species_edt.setError("Please enter price per kg");

                            Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info",
                                    "Please Enter Total No. of Fingerlings", false);

                            return;

                        }

                    }

                    int enteredSpiecesTotalFLG = 0;

                    if (!seasonalBol)
                        enteredSpiecesTotalFLG = alertEnteredSpiecesTotalFLG +
                                Integer.parseInt(total_no_of_fingerlings_alert_txt.getText().toString().trim());
                    else
                        enteredSpiecesTotalFLG = alertEnteredSpiecesTotalFLG +
                                Integer.parseInt(total_no_of_fingerlings_alert_edt.getText().toString().trim());


                    if (Utility.showLogs == 0)
                        Log.d(TAG, "enteredSpiecesTotalFLG valida: " + enteredSpiecesTotalFLG);

                    if (enteredSpiecesTotalFLG >
                            Integer.parseInt(Stocking_LeftSpinStr )) {
                        Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info",
                                "Entered Total No. of fingerlings cannot be greater than leftover stocking quantity (" + Stocking_LeftSpinStr  + ")", true);
                        return;
                    }

                    if (vehicle_number_alert_edt.getText().toString().trim().length() == 0) {

                        // price_per_kg_species_edt.setError("Please enter price per kg");

                        Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info",
                                "Please Enter Vehicle Number", false);

                        return;

                    }

                    if (vehicle_number_alert_edt.getText().toString().trim().length() <= 7) {

                        // price_per_kg_species_edt.setError("Please enter price per kg");

                        Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info",
                                "Please Enter valid Vehicle Number (Minimum 7-characters)", false);

                        return;

                    }

                    if (checkEnteredVehicleNumber(spices_title_name_alert_txt.getText().toString().trim(),
                            vehicle_number_alert_edt.getText().toString().trim())) {

                        // price_per_kg_species_edt.setError("Please enter price per kg");

                        Utility.showAlertDialog(FishSeedStockingNavigation.this, "Info",
                                "Already data entered for " + spices_title_name_alert_txt.getText().toString().trim()
                                        + " in this vehicle number " +
                                        vehicle_number_alert_edt.getText().toString().trim(), false);

                        return;

                    }

                  /*  FishSeedStockingEntryBean fishSeedStockingEntryBean =
                            new FishSeedStockingEntryBean(
                                    "" + no_of_fingerlings_per_kg_edt.getText().toString().trim(),
                                    "" + total_no_of_kgs_edt.getText().toString().trim(),
                                    "" + total_no_of_fingerlings_alert_txt.getText().toString().trim(),
                                    "" + catla_count_alert_edt.getText().toString().trim(),
                                    "" + rohu_count_alert_edt.getText().toString().trim(),
                                    "" + common_carp_alert_edt.getText().toString().trim(),
                                    "" + mrigala_alert_edt.getText().toString().trim(),
                                    "" + vehicle_number_alert_edt.getText().toString().trim()
                            );*/

                    String no_of_fingerlings_per_kg_edtStr = "0",
                            total_no_of_kgs_edtStr = "0",
                            total_no_of_fingerlings_alert_txtStr;

                    if (!seasonalBol) {
                        no_of_fingerlings_per_kg_edtStr = no_of_fingerlings_per_kg_edt.getText().toString().trim();
                        total_no_of_kgs_edtStr = total_no_of_kgs_edt.getText().toString().trim();
                        total_no_of_fingerlings_alert_txtStr = total_no_of_fingerlings_alert_txt.getText().toString().trim();
                    } else {
                        total_no_of_fingerlings_alert_txtStr = total_no_of_fingerlings_alert_edt.getText().toString().trim();
                    }

                    alertEnteredSpiecesTotalFLG = alertEnteredSpiecesTotalFLG + Integer.parseInt(total_no_of_fingerlings_alert_txtStr);
                    FishSeedStockingEntryBean1 fishSeedStockingEntryBean =
                            new FishSeedStockingEntryBean1(
                                    "" + no_of_fingerlings_per_kg_edtStr,
                                    "" + total_no_of_kgs_edtStr,
                                    "" + total_no_of_fingerlings_alert_txtStr,
                                    "" + entered_spices_count_alert_edt.getText().toString().trim(),
                                    "" + spices_title_name_alert_txt.getText().toString().trim(),
                                    "" + vehicle_number_alert_edt.getText().toString().trim()
                            );

                    fishSeedStockingEntryBeansList1.add(fishSeedStockingEntryBean);
                    fishSeedStockingEntrRecyclerList1.add(fishSeedStockingEntryBean);

                    if (fishSeedStockingEntrRecyclerList1.size() == 1) {
                        vehicleNoHashSet.add(vehicle_number_alert_edt.getText().toString().trim());

                        prepareFishSeedStockingEntryData();

                    } else {
                        vehicleNoHashSet.add(vehicle_number_alert_edt.getText().toString().trim());
                        fishSeedStockingEntryDataAdapter1.notifyItemInserted(fishSeedStockingEntrRecyclerList1.size() - 1);
                    }

                    hide_linear_layout_fish_seed_stocking_waterbody_rv.setVisibility(View.VISIBLE);

                    hide_linear_stocking_certificate_submit_btn.setVisibility(View.VISIBLE);

                    hide_linear_capture_stocking_certificate.setVisibility(View.VISIBLE);

                    if (photoBase64Str3.length() == 0)
                        submit_data_btn.setVisibility(View.GONE);

                    species_alert_spin.setSelection(0, true);

                    Toast.makeText(FishSeedStockingNavigation.this, "Added successfully.", Toast.LENGTH_SHORT).show();

                    // dialog.cancel();

                } catch (Exception e) {

                    if (Utility.showLogs == 0)
                        Log.d(TAG, "Exception-add spices alert: " + e.toString());


                    hide_linear_layout_fish_seed_stocking_waterbody_rv.setVisibility(View.GONE);
                    hide_linear_stocking_certificate_submit_btn.setVisibility(View.GONE);
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Utility.callHideKeyBoard(FishSeedStockingNavigation.this);
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private boolean checkEnteredVehicleNumber(String spicesName, String vehicleNo) {
        boolean vehicleNoFlag = false;

        for (int a = 0; a < fishSeedStockingEntrRecyclerList1.size(); a++) {

            if (spicesName.equalsIgnoreCase(
                    fishSeedStockingEntrRecyclerList1.get(a).getSpecies_name()) &&
                    vehicleNo.equalsIgnoreCase(
                            fishSeedStockingEntrRecyclerList1.get(a).getVehicle_number())
            )
                vehicleNoFlag = true;
        }

        return vehicleNoFlag;
    }

    public static String imageFilePath;//Made it static as need to override the original image with compressed image.

    private final int TAKE_PICTURE_ONE2 = 3;

    private String photoBase64Str3 = "";

    public static Bitmap directBitmap = null;
    public static String directCapturedPhotoPath = null;

    private String filename2;
    private Uri imageUri;

    private void callCameraNew(int takePictureOne, String fileName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "New Picture");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        //Camera intent

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, takePictureOne);
    }

    private int photoCalVal = 0;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE_ONE2 && resultCode == Activity.RESULT_OK) {

            try {
                photoCalVal = 2;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap picture = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));

                // Bitmap newPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

//            Bitmap resizedBitmap = resizeBitmap(bitmap);

                if (Utility.showLogs == 0) {
                    Log.e(TAG, "bitmap.getWidth(): " + picture.getWidth());
                    Log.e(TAG, "bitmap.getHeight(): " + picture.getHeight());
                }

                Bitmap resizedBitmap = resizeBitmap(picture, 720);

            /*Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap,
                    (int)(bitmap.getWidth()*0.9),
                    (int)(bitmap.getHeight()*0.9), true);*/
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                if (Utility.showLogs == 0) {
                    Log.e(TAG, "resizedBitmap.getWidth(): " + resizedBitmap.getWidth());
                    Log.e(TAG, "resizedBitmap.getHeight(): " + resizedBitmap.getHeight());
                }
               /* Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap resizedBitmap = resizeBitmap(bitmap);

                resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);*/
                byte[] byteArray = stream.toByteArray();

                photoBase64Str3 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                byteArray = Base64.decode(photoBase64Str3, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                //stocking_certificate_imageview1.setImageBitmap(decodedByte);
                stocking_certificate_imageview.setImageBitmap(decodedByte);

                submit_data_btn.setVisibility(View.VISIBLE);
                stocking_certificate_imageview.setVisibility(View.VISIBLE);

                if (Utility.showLogs == 0)
                    Log.e(TAG, "photoBase64Str3 Size: " + photoBase64Str3.length());

                ContentResolver contentResolver = this.getContentResolver();
                contentResolver.delete(imageUri, null, null);
                // new ImageCompression().execute(Utility.imageFilePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static Bitmap resizeBitmap(Bitmap source, int maxLength) {
        try {
            if (source.getHeight() >= source.getWidth()) {
                int targetHeight = maxLength;
                if (source.getHeight() <= targetHeight) { // if image already smaller than the required height
                    return source;
                }

                double aspectRatio = (double) source.getWidth() / (double) source.getHeight();
                int targetWidth = (int) (targetHeight * aspectRatio);

                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                }
                return result;
            } else {
                int targetWidth = maxLength;

                if (source.getWidth() <= targetWidth) { // if image already smaller than the required height
                    return source;
                }

                double aspectRatio = ((double) source.getHeight()) / ((double) source.getWidth());
                int targetHeight = (int) (targetWidth * aspectRatio);

                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                }
                return result;

            }
        } catch (Exception e) {
            return source;
        }
    }

    public void onError(String error) {
        progressDialog.dismiss();
        Toast.makeText(FishSeedStockingNavigation.this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        //  Utility.callSignOutAlert(FishSeedStockingNavigation.this);

        startActivity(new Intent(FishSeedStockingNavigation.this, SelectionPage.class));
        finish();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                || keyCode == KeyEvent.KEYCODE_HOME) {

//            Utility.callSignOutAlert(FishSeedStockingNavigation.this);

            startActivity(new Intent(FishSeedStockingNavigation.this, SelectionPage.class));
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }
}
