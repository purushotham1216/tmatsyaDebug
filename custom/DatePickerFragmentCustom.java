package com.org.nic.ts.custom;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.org.nic.ts.FishSeedStockingNavigation;
import com.org.nic.ts.R;
//import com.org.nic.ts.tmatsya.HarvestingNavigation;
/*import com.org.nic.ts.tmatsya.PrawnSeedStockingNavigation;
import com.org.nic.ts.tmatsya.R;*/

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerFragmentCustom extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use the current date as the default date in the date picker

        String startDateStr = FishSeedStockingNavigation.currentDate4mServer,
                endDateStr = FishSeedStockingNavigation.previousDate4mServer;

        long maxDate = 0, minDate = 0;
        int year = 0, month= 0 , day= 0;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateStart = null, dateEnd = null;
        try {

            if (Utility.showLogs == 0) {
                Log.d("DatePickerFragCustom", "startDateStr " + startDateStr);
                Log.d("DatePickerFragCustom", "endDateStr " + endDateStr);
            }

            if (startDateStr.length() != 0 && endDateStr.length() != 0) {
                dateStart = dateFormat.parse(startDateStr);
                dateEnd = dateFormat.parse(endDateStr);

                if (Utility.showLogs == 0) {
                    Log.d("DatePickerFragCustom if", "dateStart " + dateStart);
                    Log.d("DatePickerFragCustom if", "dateEnd " + dateEnd);
                }

                final Calendar c = Calendar.getInstance();
                c.setTime(dateStart);
                c.getActualMaximum(Calendar.YEAR);
                maxDate = c.getTime().getTime();
                 year = c.get(Calendar.YEAR);
                 month = c.get(Calendar.MONTH);
                 day = c.get(Calendar.DAY_OF_MONTH);

                c.add(Calendar.DATE, -1);
                minDate = c.getTime().getTime();

                if (Utility.showLogs == 0) {
                    Log.d("DatePickerFragCustom if", "maxDate " + maxDate);
                    Log.d("DatePickerFragCustom if", "minDate " + minDate);
                }
            } else {

                Date date = new Date();
                final Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.getActualMaximum(Calendar.YEAR);
                maxDate = c.getTime().getTime();
                 year = c.get(Calendar.YEAR);
                 month = c.get(Calendar.MONTH);
                 day = c.get(Calendar.DAY_OF_MONTH);


                c.add(Calendar.DATE, -1);
                minDate = c.getTime().getTime();

                if (Utility.showLogs == 0) {
                    Log.d("DatePickerFragCustom el", "maxDate " + maxDate);
                    Log.d("DatePickerFragCustom el", "minDate " + minDate);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
/*
        // Date date = new Date();
        final Calendar c = Calendar.getInstance();
        c.setTime(dateStart);
        c.getActualMaximum(Calendar.YEAR);
        long minDate = c.getTime().getTime();*/

        /*int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);*/

        /*final Calendar c1 = Calendar.getInstance();
        c1.setTime(dateEnd);
        c1.getActualMaximum(Calendar.YEAR);
        long maxDate = c1.getTime().getTime();

        int year = c1.get(Calendar.YEAR);
        int month = c1.get(Calendar.MONTH);
        int day = c1.get(Calendar.DAY_OF_MONTH);*/

       /* Date date = new Date();
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.getActualMaximum(Calendar.YEAR);
        long maxDate = c.getTime().getTime();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

         c.add(Calendar.DATE, -1);
        long minDate = c.getTime().getTime();*/

        /*final Calendar c2 = Calendar.getInstance();
        c2.setTime(date);
        c2.add(Calendar.DATE, -1);
        c2.getActualMaximum(Calendar.YEAR);
        long minDate = c2.getTime().getTime();*/




        /*Date date = new Date();
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.getActualMaximum(Calendar.YEAR);
        long maxDate = c.getTime().getTime();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);*/

        //Create a new DatePickerDialog instance and return it
        /*
            DatePickerDialog Public Constructors - Here we uses first one
            public DatePickerDialog (Context context, DatePickerDialog.OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth)
            public DatePickerDialog (Context context, int theme, DatePickerDialog.OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth)
         */
//        return new DatePickerDialog(getActivity(), this, year, month, day);
       /* DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(minDate);
//        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.getDatePicker().setMaxDate(maxDate);*/
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(minDate);
//        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.getDatePicker().setMaxDate(maxDate);
        return datePickerDialog;
    }


    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Do something with the date chosen by the user
            /*TextView tv = (TextView) getActivity().findViewById(R.id.tv);
            tv.setText("Date changed...");
            tv.setText(tv.getText() + "\nYear: " + year);
            tv.setText(tv.getText() + "\nMonth: " + month);
            tv.setText(tv.getText() + "\nDay of Month: " + day);*/

        String stringOfDate = day + "/" + (month + 1) + "/" + year;

        String activity = getActivity().getClass().getSimpleName();

        if (Utility.showLogs == 0)
            Log.d("activity", "activity: " + activity);

        if (activity.equals("BeneficiaryData")) {

            String monthStr = String.format("%02d", (month + 1));

          /*  BeneficiaryData.ddMMyyyyStr = day + "/" +monthStr + "/" + year;
            BeneficiaryData.MMddyyyyStr =monthStr+ "/" + day + "/" + year;
            BeneficiaryData.yyyyMMddStr = year + "/" +monthStr + "/" + day;*/

//            Button tv = (Button) getActivity().findViewById(R.id.date_of_purchase_btn);

//            tv.setText( day + "/" + monthStr + "/" + year);
        } else  if (activity.equals("PrawnSeedStockingNavigation")){
           /* PrawnSeedStockingNavigation.ddMMyyyyStr = day + "/" + (month + 1) + "/" + year;
            PrawnSeedStockingNavigation.MMddyyyyStr = (month + 1) + "/" + day + "/" + year;
            PrawnSeedStockingNavigation.yyyyMMddStr = year + "/" + (month + 1) + "/" + day;*/

          /*  Button tv = getActivity().findViewById(R.id.date_fish_seed_stocking_btn);
            tv.setText(stringOfDate);

            LinearLayout linearLayout = getActivity().findViewById(R.id.hide_linear_enter_seed_stocking_dtls_btn);
            linearLayout.setVisibility(View.VISIBLE);*/
            //  String monthStr=String.format("%02d", (month+1));

          /* if (month<10){
               monthStr=String.format("%02d", (month+1));
           }else{
               monthStr=String.format("%02d", (month+1));
           }*/

         /*   MainActivity.ddMMyyyyStr = day + "/" + monthStr + "/" + year;
            MainActivity.MMddyyyyStr = monthStr  + "/" + day + "/" + year;
            MainActivity.yyyyMMddStr = year + "/" + monthStr  + "/" + day;

            Button tv = (Button) getActivity().findViewById(R.id.date_of_purchase_btn);
            tv.setText( day + "/" + monthStr + "/" + year);*/
        }else {
            FishSeedStockingNavigation.ddMMyyyyStr = day + "/" + (month + 1) + "/" + year;
            FishSeedStockingNavigation.MMddyyyyStr = (month + 1) + "/" + day + "/" + year;
            FishSeedStockingNavigation.yyyyMMddStr = year + "/" + (month + 1) + "/" + day;

            Button tv = getActivity().findViewById(R.id.date_fish_seed_stocking_btn);
            tv.setText(stringOfDate);

            LinearLayout linearLayout = getActivity().findViewById(R.id.hide_linear_enter_seed_stocking_dtls_btn);
            linearLayout.setVisibility(View.VISIBLE);
            //  String monthStr=String.format("%02d", (month+1));

          /* if (month<10){
               monthStr=String.format("%02d", (month+1));
           }else{
               monthStr=String.format("%02d", (month+1));
           }*/

         /*   MainActivity.ddMMyyyyStr = day + "/" + monthStr + "/" + year;
            MainActivity.MMddyyyyStr = monthStr  + "/" + day + "/" + year;
            MainActivity.yyyyMMddStr = year + "/" + monthStr  + "/" + day;

            Button tv = (Button) getActivity().findViewById(R.id.date_of_purchase_btn);
            tv.setText( day + "/" + monthStr + "/" + year);*/
        }

        //  tv.setText(stringOfDate);
        //  selectDateBtn.setText(tv.getText() + "\n\nFormatted date: " + stringOfDate);
    }
}
