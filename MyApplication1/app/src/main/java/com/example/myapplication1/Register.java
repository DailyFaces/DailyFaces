package com.example.myapplication1;

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
//        Dialognisya();
//        Dialognisya();
    }
    public void init(){
        username = (EditText) findViewById(R.id.Username);
        email = (EditText) findViewById(R.id.Email);
        password = (EditText) findViewById(R.id.Password);
        confirmPassword = (EditText) findViewById(R.id.confirmpassword);
        test = (TextView) findViewById(R.id.testingniBurce);
        btnregister = (Button) findViewById(R.id.registerpagebtn);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerVolley(username.getText().toString(),email.getText().toString(), password.getText().toString());
                System.out.println(username.getText().toString());
//                volley();
//                test();

            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().isEmpty()||password.getText().toString().isEmpty() || email.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please provide a username, email and password!", Toast.LENGTH_LONG).show();
//                    btnregister.setEnabled(false);
                }else{
                    if(password.getText().toString().equals(confirmPassword.getText().toString())){
                        registerVolley(username.getText().toString(),email.getText().toString(), password.getText().toString());
//                        btnregister.setEnabled(true);
                    }else{
                        Toast.makeText(getApplicationContext(), "Password did not match!", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }
    public void registerVolley(String username,String email, String password){

//        final TextView textView = (TextView) findViewById(R.id.resText2);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://172.16.11.44:3000/accounts/create/"+username+"/"+ email+ "/"+password;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
//                        textView.setText("Response is: "+ response);
                        Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error.toString(
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
//    public void test(){
//        final TextView textView = (TextView) findViewById(R.id.text);
//
//// Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url ="http://172.16.11.7:3000/insert/login";
//
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("username",username.getText().toString());
//        params.put("password", password.getText().toString());
//        Log.e("params","==> " + params);
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.POST,
//                url,
//                new JSONObject(params),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject  response) {
//                        if (response.length() == 0) {
//                            Toast.makeText(getApplicationContext(), "Doesn't exist or incorrect password!", LENGTH_LONG).show();
////                            pd.hide();
//                        } else{
//                            Toast.makeText(getApplicationContext(), username.getText() + " is logged in!", LENGTH_LONG).show();
//                            Intent intent1 = new Intent(getApplicationContext(), Register.class);
//                            startActivity(intent1);
////                            pd.hide();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                textView.setText(error.toString());
//            }
//        });
//
//// Add the request to the RequestQueue.
//        queue.add(jsonObjectRequest);
//    }
//    public void volley(){
//        final TextView textView = (TextView) findViewById(R.id.testingniBurce);
//        // Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url ="http://172.16.11.8:3000/insert-user/"+username.getText().toString()+"/"+email.getText().toString()+"/"+password.getText().toString();
//        Toast.makeText(getApplicationContext(), username.getText(), Toast.LENGTH_LONG).show();
//        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        System.out.println(response);
//                        // Display the first 500 characters of the response string.
//                        textView.setText("Response is: "+ response);
//
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
//            }
//        });
//
//        // Add the request to the RequestQueue.
//        queue.add(stringRequest);
//
//    }

//    public void Dialognisya(){
//    builder = new AlertDialog.Builder(this);
//        btnregister.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            //Uncomment the below code to Set the message and title from the strings.xml file
////            builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);
//
//            //Setting message manually and performing action on button click
//            builder.setMessage("Register successfully!")
//                    .setCancelable(false)
//                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            finish();
//                            Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    });
////                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
////                        public void onClick(DialogInterface dialog, int id) {
////                            //  Action for 'NO' Button
////                            dialog.cancel();
////                            Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
////                                    Toast.LENGTH_SHORT).show();
////                        }
////                    });
//            //Creating dialog box
//            AlertDialog alert = builder.create();
//            //Setting the title manually
////            alert.setTitle("AlertDialogExample");
//            alert.show();
//        }
//    });
//    }

}
