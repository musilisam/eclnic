package com.example.sam.e_clinic;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.sam.e_clinic.LoginActivity.user_id;

public class Caregiverprofile extends AppCompatActivity {
    EditText e27,e29,e30,e31;
    Button button14;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiverprofile);


        e27=(EditText)findViewById(R.id.editText27);
        e29=(EditText)findViewById(R.id.editText29);
        e30=(EditText)findViewById(R.id.editText30);
        e31=(EditText)findViewById(R.id.editText31);
        button14=(Button)findViewById(R.id.button14);
        caregiveredit(user_id);
        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username, county,phone_number,password;

                username=e27.getText().toString();
                county=e29.getText().toString();
                phone_number=e30.getText().toString();
                password=e31.getText().toString();

                if(phone_number.equals("")||username.equals("")||county.equals("")||password.equals("")){

                    Toast passs = Toast.makeText(Caregiverprofile.this, "Fill in all the fields", Toast.LENGTH_LONG);
                    passs.show();

                }

                else

                {
                    editprofile( phone_number,username,county,password);
                }


                Log.d("user_id", user_id);

//                editprofile( username, county, phone_number, password);


            }
        });
    }

    public void caregiveredit(final String user_id)
    {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(Caregiverprofile.this);
                pDialog.setMessage("Fetching caregiver profile...");
                pDialog.setCancelable(false);
                pDialog.show();

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("user_id", user_id);
                String s = rh.sendPostRequest(URLs.main + "caregiveredit.php", paramms);
                return s;

            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
                showthem(s);
//                Toast.makeText(Caregiverprofile.this, s, Toast.LENGTH_SHORT).show();
            }


        }
        GetJSON jj = new GetJSON();
        jj.execute();
    }

    private void showthem(String s) {

        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(s);
            JSONArray result = jsonObject.getJSONArray("result");

            String succes = "0";
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);


                succes = jo.getString("success");
                if (succes.equals("1")) {
                    String username, phone_number, county, password;

                    username = jo.getString("username");
                    EditText e27;
                    e27=(EditText)findViewById(R.id.editText27);
                    e27.setText(username);

                    phone_number = jo.getString("phone_number");
                    EditText e30;
                    e30=(EditText)findViewById(R.id.editText30);
                    e30.setText(phone_number);

                    county = jo.getString("county");
                    EditText e29;
                    e29=(EditText)findViewById(R.id.editText29);
                    e29.setText(county);

                    password = jo.getString("password");
                    EditText e31;
                    e31=(EditText)findViewById(R.id.editText31);
                    e31.setText(password);

                    HashMap<String, String> employees = new HashMap<>();
                    employees.put("username", username);
                    employees.put("phone_number", phone_number);
                    employees.put("county", county);
                    employees.put("password", password);
                    list.add(employees);
                } else {
//                    Toast.makeText(this, "no data available", Toast.LENGTH_SHORT).show();
                }


            }


        } catch (JSONException e) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Caregiverprofile.this);
            alertDialogBuilder.setTitle("Attention!").setMessage(String.valueOf(e))
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setCancelable(true).show();
        }

    }



    public void editprofile( final String username, final String county, final String phone_number, final String password)
    {

        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog= new ProgressDialog(Caregiverprofile.this);
                pDialog.setMessage("Updating changes...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params)
            {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("username", username);
                paramms.put("county", county);
                paramms.put("phone_number", phone_number);
                paramms.put("password", password);

                paramms.put("username", user_id);
                Log.d("params", String.valueOf(paramms));
                String s = rh.sendPostRequest(URLs.main+"caregiverprofile.php", paramms);
                return s;

            }



            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                pDialog.dismiss();
//                Toast.makeText(Caregiverprofile.this, s, Toast.LENGTH_SHORT).show();

                if(s.equals("1"))
                {
//                   new SweetAlertDialog(SignUp.this, SweetAlertDialog.SUCCESS_TYPE).setContentText("Registration success. Please sign in to continue.")
//                           .setTitleText("Attention.").show();

                    new AlertDialog.Builder(Caregiverprofile.this).setMessage("Successfully updated.").show();

                }
                else
                {
//                        new SweetAlertDialog(SignUp.this, SweetAlertDialog.ERROR_TYPE).setContentText("Registration Failed.")
//                                .setTitleText("Attention.").show();

                    new AlertDialog.Builder(Caregiverprofile.this).setTitle("Attention!").setMessage("Update Failed.").show();
                }

            }


        }
        GetJSON jj = new GetJSON();
        jj.execute();
    }


    }

