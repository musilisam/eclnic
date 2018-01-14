package com.example.sam.e_clinic;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
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

import static com.example.sam.e_clinic.LoginActivity.user_id;

public class patientapplications extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientapplications);
        listView=(ListView)findViewById(R.id.listview3);
        fetch_patientapplications(user_id);
       // Toast.makeText(this, user_id, Toast.LENGTH_SHORT).show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, String> employees = new HashMap<>(i);
                String username= employees.get("username");
    }
        });
}
    public void fetch_patientapplications(final String user_id)
    {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(patientapplications.this);
                pDialog.setMessage("Fetching patient applications...");
                pDialog.setCancelable(false);
                pDialog.show();

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("user_id", user_id);
                String s = rh.sendPostRequest(URLs.main + "fetch_patientapplications.php", paramms);
                return s;

            }


            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                pDialog.dismiss();
                showthem(s);
                //new AlertDialog.Builder(patientapplications.this).setMessage(s).show();
               //Toast.makeText(patientapplications.this, s, Toast.LENGTH_SHORT).show();
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
                    String username, patient,date_of_request, location, description;
                    String phone = jo.getString("phone");
                    username=jo.getString("username");
                    patient=jo.getString("patient");
                    date_of_request=jo.getString("date_of_request");
                    location=jo.getString("location");
                    description=jo.getString("description");

                    HashMap<String, String> employees = new HashMap<>();
                    employees.put("username", username);
                    employees.put("patient", patient);
                    employees.put("phone", phone);
                    employees.put("date_of_request", date_of_request);
                    employees.put("location", location);
                    employees.put("description", description);
                    list.add(employees);
                }
                else
                {
                    Toast.makeText(this, "no data available", Toast.LENGTH_SHORT).show();
                }


            }

        } catch (JSONException e) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(patientapplications.this);
            alertDialogBuilder.setTitle("Attention!").setMessage(String.valueOf(e))
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setCancelable(true).show();
        }

        ListAdapter adapter = new SimpleAdapter(patientapplications.this, list, R.layout.layout2,
                new String[]{"username", "patient","date_of_request","location","description", "phone"}, new int[]{R.id.textView10,
                R.id.textView11,R.id.textView12,R.id.textView13,R.id.textView58,R.id.textView56});
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                TextView tt = (TextView)view.findViewById(R.id.textView5);
//                final String user= tt.getText().toString();

            TextView number = (TextView) view.findViewById(R.id.textView56);
            final String num = number.getText().toString();



                AlertDialog.Builder builder = new AlertDialog.Builder(patientapplications.this);
                builder.setMessage("Do you want to approve or cancel request? ");
                builder.setTitle("Please confirm");
                builder.setPositiveButton("Approve via sms", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        verify_caregiver(user);

                        sendSMS("+254"+num, "Your request for caregiver services has been approved by "+user_id);
                    }
                })
                        .setNegativeButton("Cancel", null)
                        .show();

            }
        });

    }



    public void sendSMS(String phoneNo, String msg)
    {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}