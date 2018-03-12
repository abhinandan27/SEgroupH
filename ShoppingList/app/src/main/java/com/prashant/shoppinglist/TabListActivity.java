package com.prashant.shoppinglist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabListActivity extends AppCompatActivity {



    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static final int CAM_REQ = 1111;

    private ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_list);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //second tab will open
        mViewPager.setCurrentItem(1);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(Color.GREEN);
        tabLayout.setSelectedTabIndicatorHeight(10);
        tabLayout.setupWithViewPager(mViewPager);

        //setting default tab with a list


       // Toast.makeText(getApplicationContext(),tabLayout.getSelectedTabPosition(),Toast.LENGTH_LONG).show();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Toast.makeText(getApplicationContext(),tab.getText(),Toast.LENGTH_LONG).show();
                disableProgressBar();
                disableListProgressBar();
                if(tab.getText().equals("Smart List"))
                {
                    enableListProgressBar();
                    setDefaultTabWithSmartList();}
                else
                {
                    /*
                   //final ProgressBar uploadProgressBar=(ProgressBar) findViewById(R.id.upload_progressBar) ;
                    Button upload=(Button)findViewById(R.id.uploadItems);
                    upload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            enableProgressBar();
                            uploadItems();


                        }
                    });
                    */
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //Toast.makeText(getApplicationContext(),"Unselected",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //Toast.makeText(getApplicationContext(),"Resected",Toast.LENGTH_LONG).show();
            }


        });


}

    public void enableListProgressBar()
    {
        ProgressBar uploadProgressBar=(ProgressBar)findViewById(R.id.smartListProgress);
        uploadProgressBar.setVisibility(View.VISIBLE);
    }


    public void disableListProgressBar()
    {
        ProgressBar uploadProgressBar=(ProgressBar)findViewById(R.id.smartListProgress);
        uploadProgressBar.setVisibility(View.INVISIBLE);
    }

    //methods to enable and disable progress bar
    public void enableProgressBar()
    {
        ProgressBar uploadProgressBar=(ProgressBar)findViewById(R.id.upload_progressBar);
        uploadProgressBar.setVisibility(View.VISIBLE);
    }


    public void disableProgressBar()
    {
        ProgressBar uploadProgressBar=(ProgressBar)findViewById(R.id.upload_progressBar);
        uploadProgressBar.setVisibility(View.INVISIBLE);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list_page, menu);
        return true;
    }

    public void uploadItems()
    {
        enableProgressBar();
        EditText item1,quantity1,price1,date1;
        EditText item2,quantity2,price2,date2;
        EditText item3,quantity3,price3,date3;
        EditText item4,quantity4,price4,date4;
        EditText item5,quantity5,price5,date5;
        EditText item6,quantity6,price6,date6;

        //getting data for item 1
        item1=(EditText)findViewById(R.id.item1);
        quantity1=(EditText)findViewById(R.id.quantity1);
        price1=(EditText)findViewById(R.id.price1);
        date1=(EditText)findViewById(R.id.date1);

        //
        item2=(EditText)findViewById(R.id.item2);
        quantity2=(EditText)findViewById(R.id.quantity2);
        price2=(EditText)findViewById(R.id.price2);
        date2=(EditText)findViewById(R.id.date2);

        item3=(EditText)findViewById(R.id.item3);
        quantity3=(EditText)findViewById(R.id.quantity3);
        price3=(EditText)findViewById(R.id.price3);
        date3=(EditText)findViewById(R.id.date3);


        item4=(EditText)findViewById(R.id.item4);
        quantity4=(EditText)findViewById(R.id.quantity4);
        price4=(EditText)findViewById(R.id.price4);
        date4=(EditText)findViewById(R.id.date4);

        item5=(EditText)findViewById(R.id.item5);
        quantity5=(EditText)findViewById(R.id.quantity5);
        price5=(EditText)findViewById(R.id.price5);
        date5=(EditText)findViewById(R.id.date5);

        item6=(EditText)findViewById(R.id.item6);
        quantity6=(EditText)findViewById(R.id.quantity6);
        price6=(EditText)findViewById(R.id.price6);
        date6=(EditText)findViewById(R.id.date6);

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


        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        //json list
        final String json = new Gson().toJson(list);
        if(list.size()!=0)
        {;

            String url="http://"+Server.serverAddress+"/Shopping/addItem";


            StringRequest req=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                   // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                    disableProgressBar();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    disableProgressBar();
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<>();
                    Bundle b=getIntent().getExtras();
                    SimpleDateFormat sdf=new SimpleDateFormat("YYYYMMdd");
                    params.put("emailId",b.getString("emailId"));
                    params.put("date",sdf.format(new Date()));
                    params.put("workload",b.getString("workload"));
                    params.put("number_of_people",String.valueOf(b.getInt("number_of_people")));
                    params.put("season",b.getString("season"));
                    params.put("week_of_month",String.valueOf(b.getInt("week_of_month")));
                    params.put("holidays",b.getString("holidays"));
                    params.put("list",json);
                   // Toast.makeText(getApplicationContext(),params.get("list"),Toast.LENGTH_LONG).show();
                    return params;

                }
            };
            //check response

            RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
            queue.add(req);

        list.clear();}


        //once data is sent
        //we clear every field

        //clear when it successfully uploaded on server
        //modify this
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

        date1.getText().clear();
        date2.getText().clear();
        date3.getText().clear();
        date4.getText().clear();
        date5.getText().clear();
        date6.getText().clear();

        //cleared




    }

    //setting date
    private void processDate() {
        Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_LONG).show();
    }

    //list progress bar
    public void setDefaultTabWithSmartList()
    {   enableProgressBar();
        //get ietms from from server
        Bundle b=getIntent().getExtras();
        String user=b.getString("emailId");

        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,7);
        SimpleDateFormat sdf=new SimpleDateFormat("YYYYMMdd");
        String parsedDate=sdf.format(cal.getTime());

        final String url="http://"+Server.serverAddress+"/Shopping/getList?emailId="+user+"date="+parsedDate;
        Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();

        StringRequest req=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                setItems(response);
                disableListProgressBar();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                disableListProgressBar();
                Toast.makeText(getApplicationContext(),"Please try again",Toast.LENGTH_LONG).show();

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

        RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
        queue.add(req);









    }

    //setting items in a list
    public void setItems(String response)
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

        ListAdapter itemsAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        ListView listview=(ListView)findViewById(R.id.item_list_view);
        listview.setAdapter(itemsAdapter);

    }

    //responding to menu items

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.refresh:
                TabLayout tb=(TabLayout)findViewById(R.id.tabs);
                if(tb.getSelectedTabPosition()!=1)
                    setDefaultTabWithSmartList();
                return true;
            case R.id.clear :
                //request server to erase data
               return true;
            case R.id.exit:
                this.finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;


        }
        return true;
    }
}
