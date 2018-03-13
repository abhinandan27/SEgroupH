package com.prashant.shoppinglist;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Prashant on 2/12/2018.
 */

public class SmartListTab extends Fragment {
    EditText shopping_date;
    Button btn_get_list;
    private DatePickerDialog.OnDateSetListener mDateListener;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            //getting the first part
            final View rootView = inflater.inflate(R.layout.smart_list_fragment, container, false);

        shopping_date=(EditText)rootView.findViewById(R.id.shopping_date);

        //setting a default date
        Calendar cal=Calendar.getInstance();
        int year_c=cal.get(Calendar.YEAR);
        int month_c=cal.get(Calendar.MONTH)+1;
        int day_c=cal.get(Calendar.DATE);

        String date_c=String.valueOf(month_c)+"/"+String.valueOf(day_c)+"/"+String.valueOf(year_c);
        final String default_date=String.valueOf(year_c)+String.valueOf(month_c)+String.valueOf(day_c);
        shopping_date.setText(date_c);

        shopping_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int date=cal.get(Calendar.DATE);
                DatePickerDialog dialog=new DatePickerDialog(getContext(),android.R.style.Theme_DeviceDefault_Light,mDateListener,year,month,date);
                dialog.show();
            }
        });

        mDateListener=new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date=(month+1)+"/"+dayOfMonth+"/"+year;
                shopping_date.setText(date);

                //setting date of all other items to same


            }
        };


        btn_get_list=(Button)rootView.findViewById(R.id.get_list_button);

        btn_get_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enableListProgressBar(rootView);
                /*
                String arr[]=new StringBuilder(shopping_date.getText()).toString().split("/");
                SimpleDateFormat sdf=new SimpleDateFormat("YYYYMMdd");
                Calendar cal=Calendar.getInstance();

                cal.set(Integer.parseInt(arr[2]),Integer.parseInt(arr[0])-1,Integer.parseInt(arr[1]));
                // Toast.makeText(getContext(),sdf.format(cal.getTime()),Toast.LENGTH_LONG).show();
                String parsedDate=sdf.format(cal.getTime());


                */

                setDefaultTabWithSmartList(rootView);
            }
        });

            return  rootView;
    }



    public void setDefaultTabWithSmartList(final View v)
    {  // enableListProgressBar(v);
        //get ietms from from server
        Bundle b=getActivity().getIntent().getExtras();
        String user=b.getString("emailId");
        //String user="a";
        String arr[]=new StringBuilder(shopping_date.getText()).toString().split("/");
        SimpleDateFormat sdf=new SimpleDateFormat("YYYYMMdd");
        Calendar cal=Calendar.getInstance();

        cal.set(Integer.parseInt(arr[2]),Integer.parseInt(arr[0])-1,Integer.parseInt(arr[1]));
         //Toast.makeText(getContext(),arr[2],Toast.LENGTH_LONG).show();
        String parsedDate=sdf.format(cal.getTime());
        //String parsedDate="20180312";

        final String url="http://"+Server.serverAddress+"/Shopping/getList?emailId="+user+"date="+parsedDate;
        //Toast.makeText(getContext(),parsedDate,Toast.LENGTH_LONG).show();

        StringRequest req=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                setItems(response,v);
                disableListProgressBar(v);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                disableListProgressBar(v);
                Toast.makeText(getContext(),"Please try again",Toast.LENGTH_LONG).show();

                error.printStackTrace();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();


                return params;

            }
        };
        //check response

        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(req);









    }

    //setting items in a list
    public void setItems(String response,View v)
    {
        //parse response and get items
        //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

        //ArrayList<String> temp=new Gson().fromJson(response,ArrayList.class);
        Gson json=new Gson();

        Type type= new TypeToken<List<String>>(){}.getType();
        ArrayList<String> data=new ArrayList<String>();
        data=json.fromJson(response,type);
        //Toast.makeText(getApplicationContext(),data.toString(),Toast.LENGTH_LONG).show();

        String items[]=new String[data.size()];
        int index=0;
        for(String s:data)
            items[index++]=s;

        ListAdapter itemsAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,items);
        ListView listview=(ListView)v.findViewById(R.id.item_list_view);
        listview.setAdapter(itemsAdapter);

    }




    public void enableListProgressBar(View v)
    {
        ProgressBar smartList=(ProgressBar)v.findViewById(R.id.smartListProgress);
        smartList.setVisibility(View.VISIBLE);
    }


    public void disableListProgressBar(View v)
    {
        ProgressBar smartList=(ProgressBar)v.findViewById(R.id.smartListProgress);
        smartList.setVisibility(View.INVISIBLE);
    }
}
