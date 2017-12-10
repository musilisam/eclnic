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
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.sam.e_clinic.LoginActivity.user_id;

public class Editprofile extends AppCompatActivity {
EditText e24,e25,e26,e28;
Button button15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        e24=(EditText)findViewById(R.id.editText24);

        e25=(EditText)findViewById(R.id.editText25);
        e26=(EditText)findViewById(R.id.editText26);
        e28=(EditText)findViewById(R.id.editText28);
        button15=(Button)findViewById(R.id.button15);

        patientedit(user_id);
        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String patient, username,county,phonenumber,pass;

                patient=e24.getText().toString();
                username=e25.getText().toString();
                county=e26.getText().toString();
                pass=e28.getText().toString();


                if(patient.equals("")||username.equals("")||county.equals("")||pass.equals("")){

                    Toast passs = Toast.makeText(Editprofile.this, "Fill in all the fields", Toast.LENGTH_LONG);
                    passs.show();

                }

                else

                {
                    editprofile( patient, username,county,pass);
                }


                Log.d("user_id", user_id);




            }
        });
    }

    public void patientedit(final String user_id)
    {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(Editprofile.this);
                pDialog.setMessage("Fetching patient profile...");
                pDialog.setCancelable(false);
                pDialog.show();

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("user_id", user_id);
                String s = rh.sendPostRequest(URLs.main + "patientedit.php", paramms);
                return s;

            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
                showthem(s);
                Toast.makeText(Editprofile.this, s, Toast.LENGTH_SHORT).show();
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
                    String username, patient, county, password;

                    username = jo.getString("username");
                    EditText e25;
                    e25=(EditText)findViewById(R.id.editText25);
                    e25.setText(username);
                    patient = jo.getString("patient");
                    EditText e24;
                    e24=(EditText)findViewById(R.id.editText24);
                    e24.setText(patient);
                    county = jo.getString("county");
                    EditText e26;
                    e26=(EditText)findViewById(R.id.editText26);
                    e26.setText(county);
                    password = jo.getString("password");
                    EditText e28;
                    e28=(EditText)findViewById(R.id.editText28);
                    e28.setText(password);

                    HashMap<String, String> employees = new HashMap<>();
                    employees.put("username", username);
                    employees.put("patient", patient);
                    employees.put("county", county);
                    employees.put("password", password);
                    list.add(employees);
                } else {
//                    Toast.makeText(this, "no data available", Toast.LENGTH_SHORT).show();
                }


            }


        } catch (JSONException e) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Editprofile.this);
            alertDialogBuilder.setTitle("Attention!").setMessage(String.valueOf(e))
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setCancelable(true).show();
        }

    }



    public void editprofile( final String patient, final String username, final String county, final String pass)
    {

        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog= new ProgressDialog(Editprofile.this);
                pDialog.setMessage("Updating changes...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params)
            {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("patient", patient);
                paramms.put("username", username);
                paramms.put("county", county);
                paramms.put("pass", pass);

                paramms.put("username", user_id);
                Log.d("params", String.valueOf(paramms));
                String s = rh.sendPostRequest(URLs.main+"editprofile.php", paramms);
                return s;

            }



            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                pDialog.dismiss();
                Toast.makeText(Editprofile.this, s, Toast.LENGTH_SHORT).show();

                if(s.equals("1"))
                {
//                   new SweetAlertDialog(SignUp.this, SweetAlertDialog.SUCCESS_TYPE).setContentText("Registration success. Please sign in to continue.")
//                           .setTitleText("Attention.").show();

                    new AlertDialog.Builder(Editprofile.this).setMessage("Successfully updated.").show();

                }
                else
                {
//                        new SweetAlertDialog(SignUp.this, SweetAlertDialog.ERROR_TYPE).setContentText("Registration Failed.")
//                                .setTitleText("Attention.").show();

                    new AlertDialog.Builder(Editprofile.this).setTitle("Attention!").setMessage("Update Failed.").show();
                }

            }


        }
        GetJSON jj = new GetJSON();
        jj.execute();
    }
}
