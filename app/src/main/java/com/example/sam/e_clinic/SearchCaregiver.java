package com.example.sam.e_clinic;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchCaregiver extends AppCompatActivity {
    ListView listView;
    Button button4;
    EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_caregiver);

        button4= (Button) findViewById(R.id.button4);
        search=(EditText)findViewById(R.id.searchView);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch_caregivers(search.getText().toString());





                }
          //  }
        });



        listView=(ListView)findViewById(R.id.listview2);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, String> employees = new HashMap<>(i);
                String username= employees.get("username");
                startActivity(new Intent(SearchCaregiver.this, book.class));


            }
        });
    }



    public void fetch_caregivers(final String search_string)
    {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(SearchCaregiver.this);
                pDialog.setMessage("searching caregivers...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("search_string", search_string);
                String s = rh.sendPostRequest(URLs.main + "fetch_caregiver2.php", paramms);
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
                    String username, county,certid, phone_number, payday, paynight, rating;

                    username=jo.getString("username");
                    county=jo.getString("county");
                    certid=jo.getString("certid");
                    payday=jo.getString("payday");
                    paynight=jo.getString("paynight");
                    phone_number=jo.getString("phone_number");
                    rating=jo.getString("rating");


                    HashMap<String, String> employees = new HashMap<>();
                    employees.put("username", username);
                    employees.put("county", county);
                    employees.put("certid", certid);
                    employees.put("payday", payday);
                    employees.put("paynight", paynight);
                    employees.put("phone_number", phone_number);
                    employees.put("rating", rating);

                    list.add(employees);
                }
                else
                {

                }





            }





        } catch (JSONException e) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SearchCaregiver.this);
            alertDialogBuilder.setTitle("Attention!").setMessage(String.valueOf(e))
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setCancelable(true).show();
        }

        ListAdapter adapter = new SimpleAdapter(SearchCaregiver.this, list, R.layout.caregiver_list,
                new String[]{"username", "county", "payday", "paynight", "phone_number","rating"}, new int[]{R.id.textView5,
                R.id.textView7,R.id.textView19,R.id.textView9,R.id.textView8,R.id.textView44});
        listView.setAdapter(adapter);
    }
}


