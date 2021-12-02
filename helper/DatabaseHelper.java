package com.org.nic.ts.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.org.nic.ts.custom.Utility;
import com.org.nic.ts.model.harvesting.CultureTypeBean;
import com.org.nic.ts.model.harvesting.District;
import com.org.nic.ts.model.harvesting.FinYearBean;
import com.org.nic.ts.model.harvesting.Mandal;
import com.org.nic.ts.model.harvesting.SeasonalityBean;
import com.org.nic.ts.model.harvesting.SpeciesBean;
import com.org.nic.ts.model.harvesting.Village;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";
    private String TAG = DatabaseHelper.class.getSimpleName();

    // Database Version
    private static final int DATABASE_VERSION = 1;
//    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "EMATSYA_DB";

    // Table Names
    private static final String TABLE_ApplicantType_MST= "ApplicantType_MST";
    private static final String TABLE_Component_MST = "ComponentMST_MST";
    private static final String TABLE_SubComponent_MST = "SubComponent_MST";

    private static final String TABLE_CHECK_POINT_MST = "CHECK_POINT_MST";
    private static final String TABLE_DISTRICT_MST = "DISTRICT_MST";
    private static final String TABLE_MANDAL_MST = "MANDAL_MST";
    private static final String TABLE_VILLAGE_MST = "VILLAGE_MST";

    private static final String TABLE_SeasonalityMst= "SeasonalityMst";
    private static final String TABLE_CultureTypeMst = "CultureTypeMst";
    private static final String TABLE_SpeciesMst = "SpeciesMst";

    private static final String TABLE_FinYearMST = "FinYearMST";
    private static final String TABLE_LIC_SCHEME = "LIC_SCHEME";
    private static final String TABLE_GenderMst = "GenderMst";
    private static final String TABLE_LICIEReasonMst = "LICIEReasonMst";


    // TABLE_ANIMAL_MST - column names
    private static final String KEY_ApplicantTypeCode = "ApplicantTypeCode";
    private static final String KEY_ApplicantTypeName = "ApplicantTypeName";

    // TABLE_ANIMAL_TYPE_MST - column names
    private static final String KEY_ComponentCode = "ComponentCode";
    private static final String KEY_ComponentName = "ComponentName";

    // TABLE_BANK_MST - column names
    private static final String KEY_SubComponentName= "SubComponentName";
    private static final String KEY_SubComponentCode = "SubComponentCode";

    // CheckPoint Mst Table - column names
    private static final String KEY_CheckpointCode = "CheckpointCode";
    private static final String KEY_CheckpointName = "CheckpointName";
//    private static final String KEY_VillName_Tel = "VillName_Tel";

    // District Mst Table - column names
    private static final String KEY_StateCode = "StateCode";
    private static final String KEY_DistrictCode = "DistrictCode";
    private static final String KEY_DistrictName = "DistrictName";
   private static final String KEY_DistName_Tel = "DistName_Tel";
   private static final String KEY_DistCode_Lg = "DistCode_Lg";

    // Mandal Table - column names
    private static final String KEY_MandCode = "MandCode";
    private static final String KEY_MandName = "MandName";
    private static final String KEY_MandName_Tel = "MandName_Tel";
    private static final String KEY_MandCode_LG = "MandCode_LG";


    // Village Table - column names
    private static final String KEY_VillCode = "VillCode";
    private static final String KEY_VillName = "VillName";
    private static final String KEY_VillName_Tel = "VillName_Tel";
    private static final String KEY_VillCode_LG = "VillCode_LG";

    // SeasonalityBean - column names
    private static final String KEY_SNo = "SNo";
    private static final String KEY_Seasionality = "Seasionality";

    // CultureTypeMst - column names
    private static final String KEY_Fishculture_cd = "Fishculture_cd";
    private static final String KEY_Fishculture_Name = "Fishculture_Name";

    // SpeciesMst - column names
    private static final String KEY_LandTypecode = "LandTypecode";
    private static final String KEY_LandTypedesc = "LandTypedesc";
    private static final String KEY_Type = "Type";

    // FinYearMST - column names
    private static final String KEY_Year_Code = "Year_Code";
    private static final String KEY_Year_Desc = "Year_Desc";

    // Table Create Statements
    // TABLE_ANIMAL_MST table create statement
    private static final String CREATE_TABLE_ApplicantType_MST = "CREATE TABLE "
            + TABLE_ApplicantType_MST + "(" + KEY_ApplicantTypeCode + " TEXT," + KEY_ApplicantTypeName + " TEXT" + ")";

    // Table Create Statements
    // TABLE_ANIMAL_MST table create statement
    private static final String CREATE_TABLE_Component_MST = "CREATE TABLE "
            + TABLE_Component_MST + "(" + KEY_ComponentCode + " TEXT," + KEY_ComponentName
            + " TEXT" + ")";

    // TABLE_BANK_MST table create statement
    private static final String CREATE_TABLE_SubComponent_MST = "CREATE TABLE "
            + TABLE_SubComponent_MST + "(" + KEY_SubComponentName + " TEXT," + KEY_SubComponentCode
            + " TEXT" + ")";

    // TABLE_CHECKPOINT_MST  table create statement
    private static final String CREATE_TABLE_CHECKPOINT_MST = "CREATE TABLE "
            + TABLE_CHECK_POINT_MST + "(" + KEY_CheckpointCode + " TEXT," + KEY_CheckpointName
            + " TEXT" + ")";

    // TABLE_DISTRICT_MST table create statement
    private static final String CREATE_TABLE_DISTRICT_MST = "CREATE TABLE "
            + TABLE_DISTRICT_MST + "("
            + KEY_StateCode + " TEXT,"
            + KEY_DistrictCode + " TEXT,"
            + KEY_DistrictName + " TEXT,"
            + KEY_DistName_Tel + " TEXT,"
            + KEY_DistCode_Lg + " TEXT" + ")";

    // TABLE_MANDAL_MST table create statement
    private static final String CREATE_TABLE_MANDAL_MST = "CREATE TABLE "
            + TABLE_MANDAL_MST + "("
            + KEY_DistrictCode + " TEXT,"
            + KEY_MandCode + " TEXT,"
            + KEY_MandName + " TEXT,"
            + KEY_MandName_Tel + " TEXT,"
            + KEY_MandCode_LG + " TEXT" + ")";

    // TABLE_VILLAGE_MST table create statement
    private static final String CREATE_TABLE_VILLAGE_MST = "CREATE TABLE "
            + TABLE_VILLAGE_MST + "("
            + KEY_DistrictCode + " TEXT,"
            + KEY_MandCode + " TEXT,"
            + KEY_VillCode + " TEXT,"
            + KEY_VillName + " TEXT,"
            + KEY_VillName_Tel + " TEXT,"
            + KEY_VillCode_LG + " TEXT" + ")";

    // TABLE_SeasonalityMst_MST  table create statement
    private static final String CREATE_TABLE_SeasonalityMst = "CREATE TABLE "
            + TABLE_SeasonalityMst + "("
            + KEY_SNo + " TEXT,"
            + KEY_Seasionality + " TEXT" + ")";

    // TABLE_CultureTypeMst  table create statement
    private static final String CREATE_TABLE_CultureTypeMst = "CREATE TABLE "
            + TABLE_CultureTypeMst + "("
            + KEY_Fishculture_cd + " TEXT,"
            + KEY_Fishculture_Name + " TEXT" + ")";

    // SpeciesMst  table create statement
    private static final String CREATE_TABLE_SpeciesMst = "CREATE TABLE "
            + TABLE_SpeciesMst + "("
            + KEY_LandTypecode + " TEXT,"
            + KEY_LandTypedesc   + " TEXT,"
            + KEY_Type + " TEXT" + ")";

    // TABLE_FinYearMST  table create statement
    private static final String CREATE_TABLE_FinYearMST = "CREATE TABLE "
            + TABLE_FinYearMST + "("
            + KEY_Year_Code + " TEXT,"
            + KEY_Year_Desc + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate Tables");

// creating required tables
        db.execSQL(CREATE_TABLE_ApplicantType_MST);
        db.execSQL(CREATE_TABLE_CHECKPOINT_MST);
        db.execSQL(CREATE_TABLE_SubComponent_MST);

        db.execSQL(CREATE_TABLE_DISTRICT_MST);
        db.execSQL(CREATE_TABLE_MANDAL_MST);
        db.execSQL(CREATE_TABLE_VILLAGE_MST);

        db.execSQL(CREATE_TABLE_SeasonalityMst);
        db.execSQL(CREATE_TABLE_CultureTypeMst);
        db.execSQL(CREATE_TABLE_SpeciesMst);
        db.execSQL(CREATE_TABLE_FinYearMST);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Updating table from " + oldVersion + " to " + newVersion);

        switch (oldVersion) {
            case 1:

                if (Utility.showLogs == 0)
                    Log.d(TAG, "case 1: Updating table from ");

              /*  db.execSQL("DROP TABLE " + TABLE_ANIMAL_MST);
                db.execSQL("DROP TABLE " + TABLE_ANIMAL_TYPE_MST);
                db.execSQL("DROP TABLE " + TABLE_BANK_MST);

                onCreate(db);*/
                break;

           /* case 2:
                String sql = "SOME_QUERY";
                db.execSQL(sql);
                break;*/
        }
    }

    public int getTableCount(String tableName) {
        String selectQuery = "SELECT  * FROM " + tableName;

        if (Utility.showLogs == 0)
            Log.d(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        return c.getCount();

    }

    //Inserting District data
    public long createDistrictData(District dist) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DistrictCode, dist.getDistCode());
        values.put(KEY_DistrictName, dist.getDistName());
        values.put(KEY_DistName_Tel, dist.getDistName_Tel());
        values.put(KEY_DistCode_Lg, dist.getDistCode_Lg());
        // insert row
        long todo_id = db.insert(TABLE_DISTRICT_MST, null, values);

        return todo_id;
    }

    //Inserting Mandal data
    public long createMandalData(Mandal mandal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DistrictCode, mandal.getDistCode());
        values.put(KEY_MandCode, mandal.getMandCode());
        values.put(KEY_MandName, mandal.getMandName());
        values.put(KEY_MandName_Tel, mandal.getMandName_Tel());
        values.put(KEY_MandCode_LG, mandal.getMandCode_LG());
//        values.put(KEY_MandCode_L, mandal.getMandCode_L());

        // insert row
        long todo_id = db.insert(TABLE_MANDAL_MST, null, values);

        return todo_id;
    }

    //Inserting Village data
    public long createVillageData(Village village) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DistrictCode, village.getDistCode());
        values.put(KEY_MandCode, village.getMandCode());
        values.put(KEY_VillCode, village.getVillCode());
        values.put(KEY_VillName, village.getVillName());
        values.put(KEY_VillName_Tel, village.getVillName_Tel());
        values.put(KEY_VillCode_LG, village.getVillCode_LG());
//        values.put(KEY_ClusterName, village.getClusterName());
//        values.put(KEY_PPBNO_Prefix, village.getPPBNO_Prefix());

        // insert row
        long todo_id = db.insert(TABLE_VILLAGE_MST, null, values);

        return todo_id;
    }

    //gettting district data
    public List<District> getAllDistrictData() {
        List<District> districtData = new ArrayList<District>();
        String selectQuery = "SELECT  * FROM " + TABLE_DISTRICT_MST;

        if (Utility.showLogs == 0)
            Log.d(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (Utility.showLogs == 0)
            Log.d(LOG, "District count: " + c.getCount());

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                District td = new District();
                td.setDistCode(c.getString((c.getColumnIndex(KEY_DistrictCode))));
                td.setDistName((c.getString(c.getColumnIndex(KEY_DistrictName))));
                td.setDistName_Tel(c.getString(c.getColumnIndex(KEY_DistName_Tel)));
                td.setDistCode_Lg(c.getString(c.getColumnIndex(KEY_DistCode_Lg)));
                // adding to todo list
                districtData.add(td);
            } while (c.moveToNext());
        }

        return districtData;
    }

    //gettting Mandal data
    public List<Mandal> getAllMandalData(String distCode) {
        List<Mandal> mandalData = new ArrayList<Mandal>();
        String selectQuery = "SELECT  * FROM " + TABLE_MANDAL_MST
                + " WHERE DistrictCode ='" + distCode + "'" +" order by MandName";

        if (Utility.showLogs == 0)
            Log.d(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (Utility.showLogs == 0)
            Log.d(LOG, "Mandal count: " + c.getCount());

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Mandal td = new Mandal();
                td.setDistCode(c.getString((c.getColumnIndex(KEY_DistrictCode))));
                td.setMandCode((c.getString(c.getColumnIndex(KEY_MandCode))));
                td.setMandName(c.getString(c.getColumnIndex(KEY_MandName)));
                td.setMandName_Tel(c.getString(c.getColumnIndex(KEY_MandName_Tel)));
                td.setMandCode_LG(c.getString(c.getColumnIndex(KEY_MandCode_LG)));
//                td.setMandCode_L(c.getString(c.getColumnIndex(KEY_MandCode_L)));

                mandalData.add(td);
            } while (c.moveToNext());
        }

        return mandalData;
    }

    //gettting village data
    public List<Village> getAllVillageData(String distCode, String mandalCode) {
        List<Village> villageData = new ArrayList<Village>();
        String selectQuery = "SELECT  * FROM " + TABLE_VILLAGE_MST
                + " WHERE DistrictCode ='" + distCode + "'"
                + " AND MandCode='" + mandalCode + "'" +" order by VillName";

        if (Utility.showLogs == 0)
            Log.d(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (Utility.showLogs == 0)
            Log.d(LOG, "getAllVillageData count: " + c.getCount());

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Village td = new Village();
                td.setDistCode(c.getString((c.getColumnIndex(KEY_DistrictCode))));
                td.setMandCode((c.getString(c.getColumnIndex(KEY_MandCode))));
                td.setVillCode(c.getString((c.getColumnIndex(KEY_VillCode))));
                td.setVillName((c.getString(c.getColumnIndex(KEY_VillName))));
                td.setVillName_Tel(c.getString(c.getColumnIndex(KEY_VillName_Tel)));
                td.setVillCode_LG(c.getString(c.getColumnIndex(KEY_VillCode_LG)));

                // adding to todo list
                villageData.add(td);
            } while (c.moveToNext());
        }

        return villageData;
    }

    //Inserting Seasonality data
    public long createSeasonalityData(SeasonalityBean dist) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SNo, dist.getSNo());
        values.put(KEY_Seasionality, dist.getSeasionality());
        // insert row
        long todo_id = db.insert(TABLE_SeasonalityMst, null, values);

        return todo_id;
    }

    //gettting Seasonality data
    public List<SeasonalityBean> getAllSeasonalityData() {
        List<SeasonalityBean> beanData = new ArrayList<SeasonalityBean>();
        String selectQuery = "SELECT  * FROM " + TABLE_SeasonalityMst;

        if (Utility.showLogs == 0)
            Log.d(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (Utility.showLogs == 0)
            Log.d(LOG, "getAllSeasonalityData count: " + c.getCount());

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                SeasonalityBean td = new SeasonalityBean();
                td.setSNo(c.getString((c.getColumnIndex(KEY_SNo))));
                td.setSeasionality((c.getString(c.getColumnIndex(KEY_Seasionality))));

                // adding to todo list
                beanData.add(td);
            } while (c.moveToNext());
        }

        return beanData;
    }

    //Inserting CultureType data
    public long createCultureTypeData(CultureTypeBean dist) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_Fishculture_cd, dist.getFishculture_cd());
        values.put(KEY_Fishculture_Name, dist.getFishculture_Name());
        // insert row
        long todo_id = db.insert(TABLE_CultureTypeMst, null, values);

        return todo_id;
    }

    //gettting CultureType data
    public List<CultureTypeBean> getAllCultureTypeData() {
        List<CultureTypeBean> beanData = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CultureTypeMst;

        if (Utility.showLogs == 0)
            Log.d(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (Utility.showLogs == 0)
            Log.d(LOG, "getAllCultureTypeData count: " + c.getCount());

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                CultureTypeBean td = new CultureTypeBean();
                td.setFishculture_cd(c.getString((c.getColumnIndex(KEY_Fishculture_cd))));
                td.setFishculture_Name((c.getString(c.getColumnIndex(KEY_Fishculture_Name))));

                // adding to todo list
                beanData.add(td);
            } while (c.moveToNext());
        }

        return beanData;
    }

    public String getCultureTypeCode(String type){
        String cultureTypeCodeStr="";
        String selectQuery = "SELECT  "+KEY_Fishculture_cd+" FROM " + TABLE_CultureTypeMst+
                " WHERE "+KEY_Fishculture_Name+"='"+type+"'";

        if (Utility.showLogs == 0)
            Log.d(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (Utility.showLogs == 0)
            Log.d(LOG, "getCultureTypeCode count: " + c.getCount());

        if (c.moveToFirst())
            cultureTypeCodeStr= c.getString((c.getColumnIndex(KEY_Fishculture_cd)));

        return cultureTypeCodeStr;
    }

    //Inserting Species data
    public long createSpeciesData(SpeciesBean dist) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LandTypecode, dist.getLandTypecode());
        values.put(KEY_LandTypedesc, dist.getLandTypedesc());
        values.put(KEY_Type, dist.getType());
        // insert row
        long todo_id = db.insert(TABLE_SpeciesMst, null, values);

        return todo_id;
    }

    //gettting Species data
    public List<SpeciesBean> getAllSpeciesData() {
        List<SpeciesBean> beanData = new ArrayList<SpeciesBean>();
        String selectQuery = "SELECT  * FROM " + TABLE_SpeciesMst;

        if (Utility.showLogs == 0)
            Log.d(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (Utility.showLogs == 0)
            Log.d(LOG, "getAllSpeciesData count: " + c.getCount());

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                SpeciesBean td = new SpeciesBean();
                td.setLandTypecode(c.getString((c.getColumnIndex(KEY_LandTypecode))));
                /*if (c.getString(c.getColumnIndex(KEY_LandTypedesc)).equalsIgnoreCase(
                        "Jalli Fish or Cat Fish") )
                td.setLandTypedesc("Cat Fish (Wallago, Marpu(Indian) Jella etc)");
                else*/
                td.setLandTypedesc((c.getString(c.getColumnIndex(KEY_LandTypedesc))));
                td.setType((c.getString(c.getColumnIndex(KEY_Type))));

                // adding to todo list
                beanData.add(td);
            } while (c.moveToNext());
        }

        return beanData;
    }

    //Inserting FinYear data
    public long createFinYearData(FinYearBean dist) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_Year_Code, dist.getYear_Code());
        values.put(KEY_Year_Desc, dist.getYear_Desc());
        // insert row
        long todo_id = db.insert(TABLE_FinYearMST, null, values);

        return todo_id;
    }

    //gettting FinYear data
    public List<FinYearBean> getAllFinYearData() {
        List<FinYearBean> beanData = new ArrayList<FinYearBean>();
        String selectQuery = "SELECT  * FROM " + TABLE_FinYearMST;

        if (Utility.showLogs == 0)
            Log.d(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (Utility.showLogs == 0)
            Log.d(LOG, "getAllFinYearData count: " + c.getCount());

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                FinYearBean td = new FinYearBean();
                td.setYear_Code(c.getString((c.getColumnIndex(KEY_Year_Code))));
                td.setYear_Desc((c.getString(c.getColumnIndex(KEY_Year_Desc))));

                // adding to todo list
                beanData.add(td);
            } while (c.moveToNext());
        }

        return beanData;
    }

    public int deleteTableData(String tableName) {

        SQLiteDatabase db = this.getReadableDatabase();
        int c = db.delete(tableName, null, null);

        return c;

    }
}
