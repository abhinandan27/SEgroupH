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

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(Color.GREEN);
        tabLayout.setSelectedTabIndicatorHeight(10);
        tabLayout.setupWithViewPager(mViewPager);

        //setting default tab with a list


       // Toast.makeText(getApplicationContext(),tabLayout.getSelectedTabPosition(),Toast.LENGTH_LONG).show();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override

            public void onTabSelected(TabLayout.Tab tab) {
                //Toast.makeText(getApplicationContext(),tab.getText(),Toast.LENGTH_LONG).show();
                if(tab.getText().equals("Smart List"))
                    setDefaultTabWithSmartList();
                Button upload=(Button)findViewById(R.id.uploadItems);
                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            uploadItems(v);
                    }
                });
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //Toast.makeText(getApplicationContext(),tab.getText(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //Toast.makeText(getApplicationContext(),tab.getText(),Toast.LENGTH_LONG).show();
            }
        });


}

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list_page, menu);
        return true;
    }

    public void uploadItems(View v)
    {
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

        //uploading data on the server

        //sending data to server
        //change url

        /*
        String url="http://192.168.0.15:3000/Users/addUser";


        StringRequest req=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
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

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(req);
        */
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
    public void setDefaultTabWithSmartList()
    {
        //get ietms from list
        //for now I am taking few items
        String []items={"milk","Biscuits","Meat","Chicken"};
        ListAdapter itemsAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        ListView listview=(ListView)findViewById(R.id.item_list_view);
        listview.setAdapter(itemsAdapter);
    }

    //responding to menu items

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
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
