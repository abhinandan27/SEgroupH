package com.prashant.shoppinglist;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button signin,signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signin=(Button) findViewById(R.id.signin);
        signin.setOnClickListener(this);
        signup=(Button) findViewById(R.id.signup);
        signup.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.signin:
                // do your code
                EditText username=(EditText) findViewById(R.id.username);
                EditText password=(EditText) findViewById(R.id.password);
                requestLogin(username,password);
                break;

            case R.id.signup:
                // do your code
                Intent signupIntent=new Intent(this,signupActivity.class);
                startActivity(signupIntent);
                break;
             default:
                break;
        }

    }
    public void requestLogin(EditText username,EditText password)
    {

        //manage this stuff
        String user=username.getText().toString();
        String psw=password.getText().toString();

        //connecting in backgroud thread

        Intent survey=new Intent(this,SurveyActivity.class);
        startActivity(survey);

    }



}
