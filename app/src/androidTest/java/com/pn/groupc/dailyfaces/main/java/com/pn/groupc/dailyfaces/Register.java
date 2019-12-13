package com.pn.groupc.dailyfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;




import org.json.JSONObject;

import java.util.HashMap;

import static android.widget.Toast.LENGTH_LONG;

public class Register extends AppCompatActivity {
    Button btnregister;
    EditText username;
    EditText confirmPassword;
    EditText email;
    EditText password;
    TextView test;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        btnregister = (Button) findViewById(R.id.registerpagebtn);
//        btnregister.setEnabled(false);
        init();
        Dialognisya();


    }

    public void init() {
        username = (EditText) findViewById(R.id.Username);
        email = (EditText) findViewById(R.id.Email);
        password = (EditText) findViewById(R.id.Password);
        confirmPassword = (EditText) findViewById(R.id.confirmpassword);
        test = (TextView) findViewById(R.id.testingniBurce);
        btnregister = (Button) findViewById(R.id.registerpagebtn);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                test();
                System.out.println(username.getText().toString());

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
//                volley();
//                test();

            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty() || email.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please provide a username, email and password!", Toast.LENGTH_LONG).show();
//                    btnregister.setEnabled(false);
                } else {
                    if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                        test();
//                        btnregister.setEnabled(true);
                    } else {
                        Toast.makeText(getApplicationContext(), "Password did not match!", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }

    public void test() {

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://172.16.11.44:3000/accounts/create";

        HashMap<String, String> params = new HashMap<>();
        params.put("username", username.getText().toString());
        params.put("email", email.getText().toString());
        params.put("password", password.getText().toString());
        Log.e("params", "==> " + params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.length() == 0) {
                            Toast.makeText(getApplicationContext(), "Registration unsuccessful.", LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Registered successfully.", LENGTH_LONG).show();
                            Dialognisya();
                            Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent1);
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


    public void Dialognisya() {
        builder = new AlertDialog.Builder(this);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("Register successfully!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}