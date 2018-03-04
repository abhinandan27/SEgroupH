package com.prashant.shoppinglist;

import android.content.SyncStatusObserver;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);



        //setRadioButtons();
        final Spinner dropdown=(Spinner)findViewById(R.id.spinnerpeople);
        String options[]={"1 to 3","4 to 6","7 to 10","More than 10"};
        ArrayAdapter optionsAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,options);
        dropdown.setAdapter(optionsAdapter);


        Button send=(Button) findViewById(R.id.senddata);
       String load;
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup rg=(RadioGroup)findViewById(R.id.workloadRadiogroup);
                RadioButton rb=(RadioButton)findViewById(rg.getCheckedRadioButtonId());

                CharSequence load=rb.getText();


                TextView textView = (TextView)dropdown.getSelectedView();
                String result = textView.getText().toString();
                int people=0;
                if(result.contains("3"))
                    people=3;
                else if(result.contains("6"))
                    people=6;
                else if(result.contains("10"))
                    people=10;
                else people=15;

                sendData(load,people);

            }
        });

}

    private void sendData(CharSequence load, int people) {

        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
        String month = sdf.format(cal.getTime());
        int week=cal.WEEK_OF_MONTH;


    }


}
