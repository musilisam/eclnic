package com.example.sam.e_clinic;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminVerify extends AppCompatActivity {
ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_verify);
        listView=(ListView)findViewById(R.id.listview);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tt = (TextView)view.findViewById(R.id.textView5);
                final String user= tt.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminVerify.this);
                builder.setMessage("Are you sure you want to verify " +user);
                builder.setTitle("Please confirm");
                builder.setPositiveButton("Verify", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        verify_caregiver(user);
                    }
                })
                        .setNegativeButton("Cancel", null)
                        .show();

            }
        });

        fetch_caregivers();
    }


    public void verify_caregiver(final String caregiverusername)
    {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Toast.makeText(AdminVerify.this, caregiverusername, Toast.LENGTH_SHORT).show();
                pDialog = new ProgressDialog(AdminVerify.this);
                pDialog.setMessage("verifying caregiver...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("type", "verify");
                paramms.put("caregiverusername", caregiverusername);
                String s = rh.sendPostRequest(URLs.main + "fetch_caregiver.php", paramms);
                return s;

            }


            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                pDialog.dismiss();
              if(s.equals("1"))
              {
                  new AlertDialog.Builder(AdminVerify.this)
                          .setMessage("Caregiver verified")
                          .setPositiveButton("Okay", null)
                          .show();
                  fetch_caregivers();
              }
              else Toast.makeText(AdminVerify.this, "Operation failed!", Toast.LENGTH_SHORT).show();

            }


        }
        GetJSON jj = new GetJSON();
        jj.execute();
    }


    public void fetch_caregivers()
    {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(AdminVerify.this);
                pDialog.setMessage("Fetching caregivers...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("type", "false");
                String s = rh.sendPostRequest(URLs.main + "fetch_caregiver.php", paramms);
                return s;

            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
                showthem(s);
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

            String succes="0";
            for (int i = 0; i < result.length(); i++)
            {  JSONObject jo = result.getJSONObject(i);


                succes=jo.getString("success");
                if (succes.equals("1"))
                {
                    String username, county,certid, phone_number, payday, paynight;

                    username=jo.getString("username");
                    county=jo.getString("county");
                    certid=jo.getString("certid");
                    phone_number=jo.getString("phone_number");
                    payday=jo.getString("payday");
                    paynight=jo.getString("paynight");

                    HashMap<String, String> employees = new HashMap<>();
                    employees.put("username", username);
                    employees.put("county", county);
                    employees.put("certid", certid);
                    employees.put("phone_number", phone_number);
                    employees.put("payday", payday);
                    employees.put("paynight", paynight);
                    list.add(employees);
                }
                else
                {

                }





            }





        } catch (JSONException e) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminVerify.this);
            alertDialogBuilder.setTitle("Attention!").setMessage(String.valueOf(e))
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setCancelable(true).show();
        }

        ListAdapter adapter = new SimpleAdapter(AdminVerify.this, list, R.layout.caregiver_list,
                new String[]{"username", "county","payday","paynight","phone_number"}, new int[]{R.id.textView5,
                R.id.textView7,R.id.textView19,R.id.textView9,R.id.textView8});
        listView.setAdapter(adapter);
    }
}
