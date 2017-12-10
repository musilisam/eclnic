package com.example.sam.e_clinic;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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

public class PatientCaregiverList extends AppCompatActivity {
ListView listView;

public static  String usernameusername_caregiver;
public  static  String payday, paynight, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_caregiver_list);
        listView=(ListView)findViewById(R.id.listview);
        fetch_caregivers("");


    }



    public void fetch_caregivers(final String name)
    {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(PatientCaregiverList.this);
                pDialog.setMessage("Fetching caregivers...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("type", "true");
                String s = rh.sendPostRequest(URLs.main + "fetch_caregiver.php", paramms);
                return s;

            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();

                Log.d("patientcaregiver", s);
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

            String succes = "0";
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);


                succes = jo.getString("success");
                if (succes.equals("1")) {
                    String username, county, certid, phone_number, payday, paynight, ratings;

                    username = jo.getString("username");
                    county = jo.getString("county");
                    certid = jo.getString("certid");
                    ratings=jo.getString("rating");
                    payday = jo.getString("payday");
                    paynight = jo.getString("paynight");
                    phone_number = jo.getString("phone_number");


                    HashMap<String, String> employees = new HashMap<>();
                    employees.put("username", username);
                    employees.put("county", county);
                    employees.put("certid", certid);
                    employees.put("payday", payday);
                    employees.put("rating", ratings);
                    employees.put("paynight", paynight);
                    employees.put("phone_number", phone_number);
                    list.add(employees);
                } else {

                }


            }


        } catch (JSONException e) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PatientCaregiverList.this);
            alertDialogBuilder.setTitle("Attention!").setMessage(String.valueOf(e))
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setCancelable(true).show();
        }

        ListAdapter adapter = new SimpleAdapter(PatientCaregiverList.this, list, R.layout.caregiver_list,
                new String[]{"username", "county", "payday", "paynight", "phone_number", "rating"}, new int[]{R.id.textView5,
                R.id.textView7, R.id.textView19, R.id.textView9, R.id.textView8, R.id.textView44});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {

                TextView tusername = (TextView) view.findViewById(R.id.textView5);
                String username = tusername.getText().toString();
                usernameusername_caregiver=username;
                TextView tcounty = (TextView) view.findViewById(R.id.textView7);
                String county = tcounty.getText().toString();
                TextView tpayday = (TextView) view.findViewById(R.id.textView19);
                payday = tpayday.getText().toString();
                TextView tpaynight = (TextView) view.findViewById(R.id.textView9);
                paynight = tpaynight.getText().toString();
                TextView tnumber = (TextView) view.findViewById(R.id.textView8);
                number = tnumber.getText().toString();
                startActivity(new Intent(PatientCaregiverList.this, book.class)
                .putExtra("care_giver_name",  username));

            }




        });
    }
}
