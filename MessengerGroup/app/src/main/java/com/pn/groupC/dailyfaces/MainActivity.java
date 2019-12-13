package com.pn.groupC.dailyfaces;

import android.app.ListActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.pn.groupC.dailyfaces.Network.Network;
import com.pn.groupC.dailyfaces.Network.NetworkCallback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends ListActivity {
    String[] username = {
            "Princess",
            "Ivy",
            "Jake",
            "Joy",
            "Lalaine",
            "Hannah",
            "Lovely",
            "Kenn",
            "Tibs",
            "Jess",
            "Phine",
            "Renan"
    };
    Integer[] images = {
            R.drawable.pj,
            R.drawable.kai,
            R.drawable.jeff,
            R.drawable.cora,
            R.drawable.donn,
            R.drawable.lve,
            R.drawable.love,
            R.drawable.download,
            R.drawable.download,
            R.drawable.download,
            R.drawable.download,
            R.drawable.download
    };

    List<MessageGroup> messageGroupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setListAdapter(new CustomListAdapter(
                this, messageGroupList));
        System.out.println("fghfdsjhfdskahgfdhfgsdfhsdfh");

        ListView lv = (ListView) findViewById(android.R.id.list);
//        loadMessageGroup();
        loadMessageGroup2();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, username[position], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Message.class);
                intent.putExtra("messengerGroupId", getSelectedItemId());
                System.out.println(getSelectedItemId());
                String selectedItem = (String) parent.getItemAtPosition(position);
                startActivity(intent);
            }
        });

    }

    private void loadMessageGroup() {
        //getting the progressbar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        String url = "http://172.16.11.34:3000/ms-groups/retrieve-all";

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);
        System.out.println("fghfdsjhfdskahgfdhfgsdfhsdfh" + url);

        HashMap<String, Integer> postparams= new HashMap();
        postparams.put("account_id", 1);

        //creating a string request to send request to the url
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                new JSONObject(postparams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        progressBar.setVisibility(View.INVISIBLE);
                        ListView lv = (ListView) findViewById(android.R.id.list);
                        try {
                            //getting the whole json object from the response

                            //we have the array named messageGroup inside the object
                            //so here we are getting that json array
                            JSONObject messageGroupArray = response.getJSONObject("ms_groups");
                            //now looping through all the elements of the json array
                            for (int i = 0; i < messageGroupArray.length(); i++) {
                                Log.d("Response", messageGroupArray.getString("title"));
                                //getting the json object of the particular index inside the array JSONObject messageGroupObject = messageGroupArray.getJSONObject(i);


                                //creating a messageGroup object and giving them the values from json object
                                MessageGroup messageGroup = new MessageGroup(messageGroupArray.getString("title"), messageGroupArray.getString("created_at"), messageGroupArray.getInt("account_id"));

                                //adding the messageGroup to messageGroup List
                                messageGroupList.add(messageGroup);
                            }

                            //creating custom adapter object
                            CustomListAdapter adapter = new CustomListAdapter(getParent(), messageGroupList);

                            //adding the adapter to listview
                            lv.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }

        );
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(jsonObjectRequest);
//        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(JsonObject response) {
//                        System.out.println("fghfdsjhfdskahgfdhfgsdfhsdfh");
//                        //hiding the progressbar after completion
//                        progressBar.setVisibility(View.INVISIBLE);
//                        ListView lv = (ListView) findViewById(android.R.id.list);
//                        try {
//                            //getting the whole json object from the response
//                            JSONObject obj = new JSONObject(response);
//
//                            //we have the array named messageGroup inside the object
//                            //so here we are getting that json array
//                            JSONArray messageGroupArray = obj.getJSONArray("messageGroups");
//
//                            //now looping through all the elements of the json array
//                            for (int i = 0; i < messageGroupArray.length(); i++) {
//                                System.out.println("dhghdsfgfdsgh"+messageGroupArray.get(i));
//                                //getting the json object of the particular index inside the array
//                                JSONObject messageGroupObject = messageGroupArray.getJSONObject(i);
//
//                                //creating a messageGroup object and giving them the values from json object
//                                MessageGroup messageGroup = new MessageGroup(messageGroupObject.getString("username"), messageGroupObject.getString("lastMessage"), messageGroupObject.getInt("imageurl"));
//
//                                //adding the messageGroup to messageGroup List
//                                messageGroupList.add(messageGroup);
//                            }
//
//                            //creating custom adapter object
//                            CustomListAdapter adapter = new CustomListAdapter(getParent(), messageGroupList);
//
//                            //adding the adapter to listview
//                            lv.setAdapter(adapter);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //displaying the error in toast if occurs
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        //creating a request queue
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        //adding the string request to request queue
//        requestQueue.add(stringRequest);
    }

    public void loadMessageGroup2(){
        Network loadMessage = new Network(callback(),getApplicationContext());
        Map<String, String> body = new HashMap<String, String>();
        body.put("account_id","1");
        loadMessage.request("/ms-groups/retrieve-all",body);
    }

    public NetworkCallback callback(){
        NetworkCallback chats = new NetworkCallback() {
            @Override
            public void notifySuccess(JSONObject response) {
                System.out.println(response);
            }

            @Override
            public void notifyError(VolleyError error) {

            }
        };
        return chats;
    }

}
