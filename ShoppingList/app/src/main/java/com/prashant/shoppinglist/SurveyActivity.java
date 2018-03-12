package com.prashant.shoppinglist;

import android.content.Intent;
import android.content.SyncStatusObserver;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);



        //setRadioButtons();
        final Spinner dropdown=(Spinner)findViewById(R.id.spinnerpeople);
        String options[]={"1 to 3","4 to 6","7 to 10","More than 10"};
        ArrayAdapter optionsAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,options);
        dropdown.setAdapter(optionsAdapter);

        String seasons[]={"Spring","Summer","Fall","Winter"};
        ArrayAdapter seassonAdapater=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,seasons);
        final Spinner seasonDropdown=(Spinner)findViewById(R.id.seasonsSpinner);
        seasonDropdown.setAdapter(seassonAdapater);

        //setting onclick listener
        Button sendData=(Button)findViewById(R.id.senddata);
        sendData.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //values to pass to next page
                Bundle b=getIntent().getExtras();
                String email=b.getString("emailId");
                String temp_workload;
                int people;
                String month;
                int week,season,workload=1;


                Calendar cal=Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
                month = sdf.format(cal.MONTH);
                week=cal.WEEK_OF_MONTH;

                //getting value of workload
                RadioGroup rg=(RadioGroup)findViewById(R.id.workloadRadiogroup);
                int radioButtonId=rg.getCheckedRadioButtonId();
                View radioButton = rg.findViewById(radioButtonId);
                int idx = rg.indexOfChild(radioButton);
                RadioButton r = (RadioButton)  rg.getChildAt(idx);
                temp_workload = r.getText().toString();

                if(temp_workload.equalsIgnoreCase("Low"))
                    workload=1;
                else if(temp_workload.equalsIgnoreCase("Medium"))
                    workload=2;
                else if(temp_workload.equalsIgnoreCase("High"))
                    workload=3;
                else if(temp_workload.equalsIgnoreCase("Very High"))
                    workload=4;
                //getting value of Holiday

                EditText hdays=(EditText) findViewById(R.id.holidays_number);
                int holidays=Integer.parseInt(hdays.getText().toString());

                String numberOfPeople=dropdown.getItemAtPosition(dropdown.getSelectedItemPosition()).toString();
                people=0;
                if(numberOfPeople.equals("1 to 3"))
                    people=3;
                else if(numberOfPeople.equals("4 to 6"))
                    people=6;
                else if(numberOfPeople.equals("7 to 10"))
                    people=10;
                else people=15;


                String temp=seasonDropdown.getItemAtPosition(seasonDropdown.getSelectedItemPosition()).toString();
                //"Spring","Summer","Fall","Winter"
                if(temp.equals("Spring"))
                    season=1;
                else if(temp.equals("Summer"))
                    season=2;
                else if(temp.equals("Fall"))
                    season=3;
                else season=4;


                Intent tabIntent=new Intent(getApplicationContext(),TabListActivity.class);
                tabIntent.putExtra("emailId",email);
                tabIntent.putExtra("workload",workload);
                tabIntent.putExtra("number_of_people",people);
                tabIntent.putExtra("season",season);
                tabIntent.putExtra("week_of_month",week);
                tabIntent.putExtra("month",month);
                tabIntent.putExtra("holidays",holidays);

                startActivity(tabIntent);


            }
        });


}




}
