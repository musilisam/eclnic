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
import android.widget.Button;
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

public class Patienthistory2 extends AppCompatActivity {
    ListView listView;

    public static  String usernameusername_username;
    public  static  String patient, county, phonenumber;
EditText editText23; Button button11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patienthistory2);
        listView=(ListView)findViewById(R.id.listview5);
        button11 = (Button)findViewById(R.id.button11);
        editText23 =(EditText)findViewById(R.id.editText23);
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String username= editText23.getText().toString();
              fetch_patients(username);

            }
        });



        fetch_patients("");
    }
    String url;

    public void fetch_patients(final String name)
    {

        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                if(name.length()>0)
                {
                    url =URLs.main + "search.php";
                }
                else
                {
                    url =URLs.main + "fetch_patients.php";

                }
                pDialog = new ProgressDialog(Patienthistory2.this);
                pDialog.setMessage("Fetching patients...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("search_string", name);
                String s = rh.sendPostRequest(url, paramms);
                return s;

            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
                showthem(s);
                Log.d("result",s);
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
                    String username, patient, county, phonenumber;

                    username = jo.getString("username");
                    patient = jo.getString("patient");
                    county = jo.getString("county");
                    phonenumber = jo.getString("phonenumber");


                    HashMap<String, String> employees = new HashMap<>();
                    employees.put("username", username);
                    employees.put("patient", patient);
                    employees.put("county", county);
                    employees.put("phonenumber", phonenumber);
                    list.add(employees);
                } else {

                }


            }


        } catch (JSONException e) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Patienthistory2.this);
            alertDialogBuilder.setTitle("Attention!").setMessage(String.valueOf(e))
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setCancelable(true).show();
        }

        ListAdapter adapter = new SimpleAdapter(Patienthistory2.this, list, R.layout.patient,
                new String[]{"username", "patient", "county", "phonenumber"}, new int[]{R.id.textView32,R.id.textView33,
                R.id.textView34, R.id.textView35});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                HashMap<String, String> employees = new HashMap<>(i);
                //String username= employees.get("username");

                TextView tusername = (TextView) view.findViewById(R.id.textView32);
                String username = tusername.getText().toString();


                Toast.makeText(Patienthistory2.this, username, Toast.LENGTH_SHORT).show();
                Log.d("username_____", username);
                startActivity(new Intent(Patienthistory2.this, Patienthistory.class)
                        .putExtra("username",  username));

            }




        });
    }
}
