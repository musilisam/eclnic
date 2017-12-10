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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.sam.e_clinic.LoginActivity.user_id;


public class Patienthistory extends AppCompatActivity {
ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patienthistory);

        listView=(ListView)findViewById(R.id.listview4);
       // Log.d("username",getIntent().getStringExtra("username"));

        String username = getIntent().getStringExtra("username");
        if(username.equals("all"))
        {
            String url=URLs.main + "Patienthistory.php";
            fetch_Patienthistory(getIntent().getStringExtra("username")
            ,url);

        }
        else {


            fetch_Patienthistory(getIntent().getStringExtra("username"), URLs.main + "Patienthistory.php");

        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, String> employees = new HashMap<>(i);
                String username= employees.get("username");
            }
        });





    }

    private void fetch_Patienthistory(final String user_id, final String url) {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(Patienthistory.this);
                pDialog.setMessage("Fetching patient history...");
                pDialog.setCancelable(false);
                pDialog.show();
                Log.d("user_id", user_id);

            }

            @Override
            protected String doInBackground(Void... params)
            {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("user_id", user_id);
                String s = rh.sendPostRequest(url, paramms);
                return s;

            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
                showthem(s);
                Toast.makeText(Patienthistory.this, s, Toast.LENGTH_SHORT).show();
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
                    String username, patient,date_of_request, location, caregiver;

                    username=jo.getString("username");
                    patient=jo.getString("patient");
                    date_of_request=jo.getString("date_of_request");
                    location=jo.getString("location");
                    caregiver=jo.getString("caregiver");

                    HashMap<String, String> employees = new HashMap<>();
                    employees.put("username", username);
                    employees.put("patient", patient);
                    employees.put("date_of_request", date_of_request);
                    employees.put("location", location);
                    employees.put("caregiver", caregiver);
                    list.add(employees);
                }
                else
                {
                    Toast.makeText(this, "no data available", Toast.LENGTH_SHORT).show();
                }





            }





        } catch (JSONException e) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Patienthistory.this);
            alertDialogBuilder.setTitle("Attention!").setMessage(String.valueOf(e))
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setCancelable(true).show();
        }

        ListAdapter adapter = new SimpleAdapter(Patienthistory.this, list, R.layout.layout,
                new String[]{"username", "patient","date_of_request","location","caregiver"}, new int[]{R.id.textView10,
                R.id.textView11,R.id.textView12,R.id.textView13,R.id.textView36});
        listView.setAdapter(adapter);



    }
}
