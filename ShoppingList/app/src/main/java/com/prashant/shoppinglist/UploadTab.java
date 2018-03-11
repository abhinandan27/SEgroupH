package com.prashant.shoppinglist;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.DatePicker;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Prashant on 2/12/2018.
 */

public class UploadTab extends Fragment {
    EditText item1,quantity1,price1,date1;
    EditText item2,quantity2,price2,date2;
    EditText item3,quantity3,price3,date3;
    EditText item4,quantity4,price4,date4;
    EditText item5,quantity5,price5,date5;
    EditText item6,quantity6,price6,date6;

    private DatePickerDialog.OnDateSetListener mDateListener1,mDateListener2,mDateListener3,mDateListener4,mDateListener5,mDateListener6;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //getting the first part
        final View rootView = inflater.inflate(R.layout.upload_list_fragment, container, false);
        Button b=(Button) rootView.findViewById(R.id.uploadItems);


        //getting data for item 1
        item1=(EditText)rootView.findViewById(R.id.item1);
        quantity1=(EditText)rootView.findViewById(R.id.quantity1);
        price1=(EditText)rootView.findViewById(R.id.price1);
        date1=(EditText)rootView.findViewById(R.id.date1);


        //
        item2=(EditText)rootView.findViewById(R.id.item2);
        quantity2=(EditText)rootView.findViewById(R.id.quantity2);
        price2=(EditText)rootView.findViewById(R.id.price2);
        date2=(EditText)rootView.findViewById(R.id.date2);

        item3=(EditText)rootView.findViewById(R.id.item3);
        quantity3=(EditText)rootView.findViewById(R.id.quantity3);
        price3=(EditText)rootView.findViewById(R.id.price3);
        date3=(EditText)rootView.findViewById(R.id.date3);


        item4=(EditText)rootView.findViewById(R.id.item4);
        quantity4=(EditText)rootView.findViewById(R.id.quantity4);
        price4=(EditText)rootView.findViewById(R.id.price4);
        date4=(EditText)rootView.findViewById(R.id.date4);

        item5=(EditText)rootView.findViewById(R.id.item5);
        quantity5=(EditText)rootView.findViewById(R.id.quantity5);
        price5=(EditText)rootView.findViewById(R.id.price5);
        date5=(EditText)rootView.findViewById(R.id.date5);

        item6=(EditText)rootView.findViewById(R.id.item6);
        quantity6=(EditText)rootView.findViewById(R.id.quantity6);
        price6=(EditText)rootView.findViewById(R.id.price6);
        date6=(EditText)rootView.findViewById(R.id.date6);

        //setting a default date
        Calendar cal=Calendar.getInstance();
        int year_c=cal.get(Calendar.YEAR);
        int month_c=cal.get(Calendar.MONTH)+1;
        int day_c=cal.get(Calendar.DATE);

        String date_c=String.valueOf(month_c)+"/"+String.valueOf(day_c)+"/"+String.valueOf(year_c);
        final String default_date=String.valueOf(year_c)+String.valueOf(month_c)+String.valueOf(day_c);
        date1.setText(date_c);


        //first date set listener
        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int date=cal.get(Calendar.DATE);
                DatePickerDialog dialog=new DatePickerDialog(getContext(),android.R.style.Theme_DeviceDefault_Light,mDateListener1,year,month,date);
                dialog.show();
            }
        });

        mDateListener1=new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date=(month+1)+"/"+dayOfMonth+"/"+year;
                date1.setText(date);

                //setting date of all other items to same


            }
        };

        //second date set listener

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int date=cal.get(Calendar.DATE);
                DatePickerDialog dialog=new DatePickerDialog(getContext(),android.R.style.Theme_DeviceDefault_Light,mDateListener2,year,month,date);
                dialog.show();
            }
        });

        mDateListener2=new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date=(month+1)+"/"+dayOfMonth+"/"+year;
                date2.setText(date);
            }
        };
        //3rd date set listener

        date3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int date=cal.get(Calendar.DATE);
                DatePickerDialog dialog=new DatePickerDialog(getContext(),android.R.style.Theme_DeviceDefault_Light,mDateListener3,year,month,date);
                dialog.show();
            }
        });

        mDateListener3=new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date=(month+1)+"/"+dayOfMonth+"/"+year;
                date3.setText(date);
            }
        };

        //$th listener

        date4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int date=cal.get(Calendar.DATE);
                DatePickerDialog dialog=new DatePickerDialog(getContext(),
                        android.R.style.Theme_DeviceDefault_Light,mDateListener4,year,month,date);
                dialog.show();
            }
        });

        mDateListener4=new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date=(month+1)+"/"+dayOfMonth+"/"+year;
                date4.setText(date);
            }
        };

        //5th listener

        date5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int date=cal.get(Calendar.DATE);
                DatePickerDialog dialog=new DatePickerDialog(getContext(),
                        android.R.style.Theme_DeviceDefault_Light,mDateListener5,year,month,date);
                dialog.show();
            }
        });

        mDateListener5=new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date=(month+1)+"/"+dayOfMonth+"/"+year;
                date5.setText(date);
            }
        };

        //6th listener

        date6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int date=cal.get(Calendar.DATE);
                DatePickerDialog dialog=new DatePickerDialog(getContext(),
                        android.R.style.Theme_DeviceDefault_Light,mDateListener6,year,month,date);
                dialog.show();
            }
        });

        mDateListener6=new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date=(month+1)+"/"+dayOfMonth+"/"+year;
                date6.setText(date);
            }
        };

        //listner ends
        //button listener

        b.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                EditText date=(EditText)rootView.findViewById(R.id.date1);
                String arr[]=new StringBuilder(date1.getText()).toString().split("/");
                SimpleDateFormat sdf=new SimpleDateFormat("YYYYMMdd");
                Calendar cal=Calendar.getInstance();

                cal.set(Integer.parseInt(arr[2]),Integer.parseInt(arr[0])-1,Integer.parseInt(arr[1]));
               // Toast.makeText(getContext(),sdf.format(cal.getTime()),Toast.LENGTH_LONG).show();
                String parsedDate=sdf.format(cal.getTime());
                enableProgressBar(rootView);
                uploadItems(rootView,parsedDate);
               // disableProgressBar(rootView);

            }
        });
        return  rootView;
    }


    public void enableProgressBar(View v)
    {
        ProgressBar uploadProgressBar=(ProgressBar)v.findViewById(R.id.upload_progressBar);
        uploadProgressBar.setVisibility(View.VISIBLE);
    }

    public void disableProgressBar(View v)
    {
        ProgressBar uploadProgressBar=(ProgressBar)v.findViewById(R.id.upload_progressBar);
        uploadProgressBar.setVisibility(View.INVISIBLE);
    }
    public void uploadItems(final View v, final String parsedDate)
    {





        Bundle b=getActivity().getIntent().getExtras();
        final ArrayList<String> list=new ArrayList<String>();


        if(item1.getText()!=null && item1.getText().length()>0)
            list.add(item1.getText().toString());
        if(item2.getText()!=null && item2.getText().length()>0)
            list.add(item2.getText().toString());
        if(item3.getText()!=null  && item3.getText().length()>0)
            list.add(item3.getText().toString());
        if(item4.getText()!=null  && item4.getText().length()>0)
            list.add(item4.getText().toString());
        if(item5.getText()!=null && item5.getText().length()>0)
            list.add(item5.getText().toString());
        if(item6.getText()!= null && item6.getText().length()>0)
            list.add(item6.getText().toString());
       // Toast.makeText(getContext(),list.toString(),Toast.LENGTH_LONG).show();

        //setting on click listener for date



        //Toast.makeText(getContext(),tempDate,Toast.LENGTH_LONG).show();
        if(list.size()!=0)
        {

            final String json = new Gson().toJson(list);
            String url="http://"+Server.serverAddress+"/Shopping/addItem";



            StringRequest req=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    item1.getText().clear();
                    item2.getText().clear();
                    item3.getText().clear();
                    item4.getText().clear();
                    item5.getText().clear();
                    item6.getText().clear();

                    price1.getText().clear();
                    price2.getText().clear();
                    price3.getText().clear();
                    price4.getText().clear();
                    price5.getText().clear();
                    price6.getText().clear();

                    quantity1.getText().clear();
                    quantity2.getText().clear();
                    quantity3.getText().clear();
                    quantity4.getText().clear();
                    quantity5.getText().clear();
                    quantity6.getText().clear();

                    //date1.getText().clear();
                    date2.getText().clear();
                    date3.getText().clear();
                    date4.getText().clear();
                    date5.getText().clear();
                    date6.getText().clear();

                    disableProgressBar(v);
                    Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    disableProgressBar(v);
                    Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                    // uploadProgressBar.setVisibility(View.INVISIBLE);
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<>();
                    Bundle b=getActivity().getIntent().getExtras();





                    //getting a date


                    //Calendar cal=Calendar.getInstance();

                   // cal.set(Integer.parseInt(arr[2]),Integer.parseInt(arr[0]),Integer.parseInt(arr[1]));



                    //Toast.makeText(getContext(),tempDate,Toast.LENGTH_LONG).show();


                    params.put("emailId",b.getString("emailId"));
                    params.put("date",parsedDate);
                    params.put("workload",String.valueOf(b.getInt("workload")));
                    params.put("number_of_people",String.valueOf(b.getInt("number_of_people")));
                    params.put("season",String.valueOf(b.getInt("season")));
                    params.put("week_of_month",String.valueOf(b.getInt("week_of_month")));
                    params.put("holidays",String.valueOf(b.getInt("holidays")));
                    params.put("list",json);

                    return params;

                }
            };
            //check response

            RequestQueue queue= Volley.newRequestQueue(getContext());
            queue.add(req);

            list.clear();}


        //json list

    }



    private void processDate() {
        Toast.makeText(getContext(),"Hello",Toast.LENGTH_LONG).show();
    }



}
