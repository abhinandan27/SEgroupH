package com.prashant.shoppinglist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class signupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button register=(Button) findViewById(R.id.registerButton);
        final EditText name=(EditText) findViewById(R.id.name);
        final EditText email=(EditText) findViewById(R.id.email);
        final EditText firstPass=(EditText) findViewById(R.id.password);
        final EditText confirmPassword=(EditText) findViewById(R.id.confirmPassword);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processRegisterRequest(name.getText().toString(),email.getText().toString(),firstPass.getText().toString(),confirmPassword.getText().toString());
            }
        });
    }

    public void processRegisterRequest(final String name,final String email,final String firstPassword,String secondPassword)
    {
        if(!firstPassword.equals(secondPassword))
        {
            Toast.makeText(getApplicationContext(),"Password does not match",Toast.LENGTH_LONG).show();
            Intent i=new Intent(this,signupActivity.class);
            startActivity(i);
        }
        else
        {

            //sending data to server

            String url="http://192.168.0.15:3000/Users/addUser";

            //json object is not needed
            JSONObject obj=new JSONObject();
            try {
                obj.put("name",name);
                obj.put("emailId",email);
                obj.put("password",firstPassword);

            } catch (JSONException e) {
                e.printStackTrace();
            }

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
                    params.put("name",name);
                    params.put("emailId",email);
                    params.put("password",firstPassword);
                    return params;

                }
            };
            //check response

            RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
            queue.add(req);
          //finished sending data

            //if response is true send to main activity page
            Intent i=new Intent(this,TabListActivity.class);
            startActivity(i);

        }
    }
}
