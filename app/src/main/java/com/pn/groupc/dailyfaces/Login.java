package com.pn.groupc.dailyfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pn.groupc.dailyfaces.MainActivity;
import com.pn.groupc.dailyfaces.Register;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;

public class Login extends AppCompatActivity {

    Button login = null, register= null;
    EditText username;
    EditText password;
    SharedPreferences prf;

    TextView text;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
        pd = new ProgressDialog(Login.this);
       ;
    }

    public void init() {
        register = (Button) findViewById(R.id.btnregister);
        login = (Button) findViewById(R.id.btnlogin);
        username = findViewById((R.id.Username));
        password = findViewById(R.id.password);
        text = findViewById(R.id.testing);

        prf = getSharedPreferences("user_details",MODE_PRIVATE);

        final Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        Map<String,?> keys = prf.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){
//            System.out.println(entry.getKey() + ": " + entry.getValue().toString());
        }
//
        SharedPreferences myPrefs = this.getSharedPreferences("user_details", MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putString("email", String.valueOf(username.getText()));
        editor.putString("password", String.valueOf(password.getText()));

        editor.commit();

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(username.getText().toString().isEmpty()&& password.getText().toString().isEmpty()) {
                    text.setText("Input Fields are empty Please Fill up the Fields");
                }
                else {
                    pd.setMessage("Loading..");
                    pd.show();
                    test();
//                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                    intent.putExtra("EMAIL", "Welcome : " + username.getText().toString());
                    startActivity(intent);
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

    }

    public void test() {

        RequestQueue queue = Volley.newRequestQueue(this);

        String url ="http://172.16.11.44:3000/auth/";

        HashMap<String, String> params = new HashMap<>();
        params.put("username",username.getText().toString());
        params.put("password", password.getText().toString());
        Log.e("params","==> " + params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject  response) {
                        if (response.length() == 0) {
                            Toast.makeText(getApplicationContext(), "Doesn't exist or incorrect password!", LENGTH_LONG).show();
                            pd.hide();
                        } else{
                            Toast.makeText(getApplicationContext(), username.getText() + " is logged in!", LENGTH_LONG).show();
                            setContentView(R.layout.dashboard);
                            Intent intent = new Intent(getApplicationContext(),Dashboard.class);
                            startActivity(intent);
                            pd.hide();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), LENGTH_LONG).show();
            }
        });

        queue.add(jsonObjectRequest);
    }

}


