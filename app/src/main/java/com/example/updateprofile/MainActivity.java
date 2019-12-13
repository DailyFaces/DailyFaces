package com.example.updateprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.services.ImageLoader.ImageInterface;
import com.example.services.ImageLoader.ImageInterface;
import com.example.services.ImageLoader.ImagesLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    Button changeBtn, updateBtn, saveBtn;
    EditText fname;
    EditText mname;
    EditText lname;
    EditText age;
    EditText bdate;
    EditText gender;
    EditText contact;
    ImageView profilePic;
    private static int RESULT_LOAD_IMG = 1;
    String path;
    String extension;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        changeBtn = (Button) findViewById(R.id.changePicBtn);
        updateBtn = (Button) findViewById(R.id.updatebtn);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        saveBtn.setVisibility(View.GONE);
//        updateBtn.setVisibility(View.VISIBLE);
        fname = (EditText) findViewById(R.id.fname);
        mname = (EditText) findViewById(R.id.mname);
        lname = (EditText) findViewById(R.id.lname);
        age = (EditText) findViewById(R.id.age);
        bdate = (EditText) findViewById(R.id.bdate);
        gender = (EditText) findViewById(R.id.gender);
        contact = (EditText) findViewById(R.id.contact);
        profilePic = (ImageView) findViewById(R.id.profilePic);

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSave();
                System.out.println(path + " test");

            }
        });
    }

    // Setting up the profile picture
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), selectedImage);
                path = encodeFromString(selectedImage); //BASE 64 FORMAT
                extension = convertMediaUriToPath(imageUri);
                circularBitmapDrawable.setCircular(true);
                profilePic.setImageDrawable(circularBitmapDrawable);        //set image on imageView
                saveBtn.setVisibility(View.VISIBLE);
                System.out.println(extension + " test");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(MainActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
    public static String encodeFromString(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    //converting URI to pathname
    public String convertMediaUriToPath(Uri uri) {
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, proj,  null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        System.out.println(path + " test");
        return path.substring(path.lastIndexOf("."));
    }

    //Volley for updating the profile ---- sending request to the backend
    public void update() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://172.16.11.32:3000/informations/update";
//        String url ="http://172.16.11.15:3000/informations/update";

        HashMap<String, String> params = new HashMap<>();
        params.put("account_id","9");
        params.put("first_name",fname.getText().toString());
        params.put("middle_name", mname.getText().toString());
        params.put("last_name", lname.getText().toString());
        params.put("age", age.getText().toString());
        params.put("birth_date", bdate.getText().toString());
        params.put("gender", gender.getText().toString());
        params.put("contact_number", contact.getText().toString());
        Log.e("params","==> " + params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject  response) {
                        System.out.println(response + " tibs");
                        Toast.makeText(MainActivity.this,"Your profile is updated",Toast.LENGTH_LONG).show();
                        try {
                            JSONArray data = response.getJSONArray("results");
                            JSONObject responseData = (JSONObject) data.get(0);
                            System.out.println( responseData + " test");
                            fname.setText(responseData.optString("first_name"));
                            fname.setText(responseData.optString("first_name"));
                            mname.setText(responseData.optString("middle_name"));
                            lname.setText(responseData.optString("last_name"));
                            age.setText(responseData.optString("age"));
                            bdate.setText(responseData.optString("birth_date"));
                            gender.setText(responseData.optString("gender"));
                            contact.setText(responseData.optString("contact_number"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        fname.setText(response.optString("first_name"));
//                        fname.setText(response.optString("first_name"));
//                        mname.setText(response.optString("middle_name"));
//                        lname.setText(response.optString("last_name"));
//                        age.setText(response.optString("age"));
//                        bdate.setText(response.optString("birth_date"));
//                        gender.setText(response.optString("gender"));
//                        contact.setText(response.optString("contact_number"));
//                        Log.d("response", response.getJSONArray("result"));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error "+ error );
                    Toast.makeText(MainActivity.this,"There's an error while updating.",Toast.LENGTH_LONG).show();
//                textView.setText("That didn't work!"+ error);
                    errorpen();
            }
        });
// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);


    }

    //Volley for updating the profile ---- sending request to the backend
    public void updatePic() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://172.16.11.32:3000/profiles/create";
//        String url ="http://172.16.11.15:3000/images/create";
//        String url ="http://172.16.11.15:3000/images/create";

        HashMap<String, String> params = new HashMap<>();
        params.put("account_id", "9");
        params.put("extension", extension);
        params.put("image", path);
        Log.e("params","==> " + params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject  response) {
                        System.out.println(response.toString());
                        try {
                            JSONArray data = response.getJSONArray("rows");
                            JSONObject responseData = (JSONObject) data.get(0);

//                            RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), decodeBase64(response.optString("image")));
                            ImagesLoader loadImage = new ImagesLoader(imageCallback(), getApplicationContext());
                            loadImage.loadImage(responseData.optString("url"));
//                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), response.optString("url"));
//                        circularBitmapDrawable.setCircular(true);
//                        profilePic.setImageDrawable(circularBitmapDrawable);
                            saveBtn.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this,"Your profile picture is updated",Toast.LENGTH_LONG).show();
                            System.out.println(response.optString("image"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(MainActivity.this,"There's an error while updating.",Toast.LENGTH_LONG).show();
                errorpenSave();
            }
        });
        queue.add(jsonObjectRequest);
    }

    public ImageInterface imageCallback(){
        ImageInterface callback = new ImageInterface() {
            @Override
            public void notifySuccess(Bitmap image) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), image);
                circularBitmapDrawable.setCircular(true);
                profilePic.setImageDrawable(circularBitmapDrawable);
            }

            @Override
            public void notifyError(FailReason error) {

            }
        };
        return callback;
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    // modal for error while updating
    public void errorpen(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("There's a problem in your network. Please try again.");
        alertDialogBuilder.setPositiveButton("Try Again",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        update();
                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    //modal for update confirmation
    public void open(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to update your profile?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                update();
                            }
                        });
        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void errorpenSave(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("There's a problem in your network. Please try again.");
        alertDialogBuilder.setPositiveButton("Try Again",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        updatePic();
                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveBtn.setVisibility(View.GONE);
//                profilePic.setImageResource(0);
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    //modal for update confirmation
    public void openSave(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to update your profile picture?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        updatePic();
                    }
                });
        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveBtn.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
