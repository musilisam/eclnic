package com.example.sam.e_clinic;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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

import static com.example.sam.e_clinic.LoginActivity.user_id;
import static com.example.sam.e_clinic.PatientCaregiverList.number;
import static com.example.sam.e_clinic.PatientCaregiverList.payday;
import static com.example.sam.e_clinic.PatientCaregiverList.paynight;
import static com.example.sam.e_clinic.PatientCaregiverList.usernameusername_caregiver;
import static com.example.sam.e_clinic.book.care_giver_name;

public class Payment extends AppCompatActivity {

    ListView listView;

    EditText e21;
    EditText e20;
    TextView t16;
    TextView t20;
    TextView t18;
    TextView t22;
    TextView t17;
    TextView tvd;
    Button button9;
    Button button6;

    double editText21,textView16,textView18;

//    public  void saveThisItem(String edit,int day1){
//        int one=Integer.parseInt(edit);
//        int mult=day1*one;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        listView = (ListView) findViewById(R.id.listview);
        button9 = (Button) findViewById(R.id.button9);
        e21 = (EditText) findViewById(R.id.editText21);
        e20 = (EditText) findViewById(R.id.editText20);
        t16 = (TextView) findViewById(R.id.textView16);
        t17 = (TextView) findViewById(R.id.textView17);
        t18 = (TextView) findViewById(R.id.textView18);
        t20 = (TextView) findViewById(R.id.textView20);
        t22 = (TextView) findViewById(R.id.textView22);
        button6 = (Button) findViewById(R.id.button6);


        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String hrs1 = e20.getText().toString();
                String hrs2 = e21.getText().toString();

                String paynight = t17.getText().toString();
                String paydaay = t16.getText().toString();


                int hrsnight = Integer.parseInt(hrs1);
                int hrsday = Integer.parseInt(hrs2);

                int paynigh = Integer.parseInt(paynight);
                int paysn = Integer.parseInt(paydaay);


                int total1 = paysn * hrsnight;
                t18.setText(String.valueOf(total1));
                int total2 = paynigh * hrsday;
                t20.setText(String.valueOf(total2));
                int alltotaltt = total2 + total1;
                t22.setText(String.valueOf(alltotaltt));


                Toast.makeText(Payment.this, "hoursnight", Toast.LENGTH_SHORT).show();
                Toast.makeText(Payment.this, "hoursday", Toast.LENGTH_SHORT).show();


            }
        });


        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String total;
                total = t22.getText().toString().trim();
                //care_giver_name
               // int totall= Integer.parseInt(total);

               sendpaymentdetails(total, tvd.getText().toString(),
                       care_giver_name, user_id);

            }

            public void sendpaymentdetails(final String total, final String payer_number,
                                           final String caregiver, final String user) {
                class GetJSON extends AsyncTask<Void, Void, String> {

                    ProgressDialog pDialog;


                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();

                        pDialog = new ProgressDialog(Payment.this);
                        pDialog.setMessage("sending...");
                        pDialog.setCancelable(false);
                        pDialog.show();
                    }

                    @Override
                    protected String doInBackground(Void... voids)
                    {
                        RequestHandler rh = new RequestHandler();
                        HashMap<String, String> paramms = new HashMap<>();
                        paramms.put("total", total);
                        paramms.put("payer_number", payer_number);
                        paramms.put("caregiver", usernameusername_caregiver);
                        paramms.put("user", user);
                        String s = rh.sendPostRequest(URLs.main + "mpesa.php", paramms);
                        return s;

                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        pDialog.dismiss();

Log.d("result",s);
Log.d("result", s.substring(0,1));
//                        Toast.makeText(Payment.this, s, Toast.LENGTH_SHORT).show();

                        if (s.substring(0,1).equals("1"))
                        {
                            new AlertDialog.Builder(Payment.this).setMessage("payment success.").show();

                        } else
                        {
                            new AlertDialog.Builder(Payment.this).setTitle("Attention!").setMessage("service is currently down.").show();
                        }

                    }
                }
                GetJSON jj= new GetJSON();
                jj.execute();

            }


        });


        TextView tvp = (TextView) findViewById(R.id.textView16);
        tvp.setText(payday);

        TextView tve = (TextView) findViewById(R.id.textView17);
        tve.setText(paynight);

         tvd = (TextView) findViewById(R.id.textView21);
        tvd.setText(number);
    }



//        public void sum_and_product(View view) {
//            EditText editText21 = (EditText) findViewById(R.id.editText21);
//            EditText editText20 = (EditText) findViewById(R.id.editText20);
//            TextView textView18 = (TextView) findViewById(R.id.textView18);
//            TextView textView20 = (TextView) findViewById(R.id.textView20);
//            int e21 = Integer.valueOf(e21.getText().toString());
//            int textView16 = Integer.valueOf(textView16.getText().toString());
//            int add = textView18 + textView20;
//            int product = editText21 * textView16;
//            editText20.setText(Integer.toString(add));
//            textView20.setText(Integer.toString(product));
//        }
//
//        public void clear(View view) {
//            EditText editText21 = (EditText) findViewById(R.id.editText21);
//            EditText editText20 = (EditText) findViewById(R.id.editText20);
//            TextView textView18 = (TextView) findViewById(R.id.textView18);
//            TextView textView20 = (TextView) findViewById(R.id.textView20);
//            editText21.setText("");
//            editText20.requestFocus();
//            editText21.setText("");
//            editText20.setText("");
//            textView18.setText("");
//            textView20.setText("");
//        }







//        setOnFocusChangeListener( e21,21);
//        setOnFocusChangeListener(e20, 20);



//        private void setOnFocusChangeListener(EditText editText, final int day){
//
//            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if(!hasFocus) {
//                        saveThisItem(e21.getText(), day);
//                    }
//                }
//            });
//        }
//
//
//
//
//    private void setOnFocusChangeListener(EditText editText, final int night){
//
//        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(!hasFocus) {
//                    saveThisItem(e20.getText(), night);
//                }
//            }
//        });
//    }
//




//        TextWatcher textWatcher = new TextWatcher() {
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // TODO Auto-generated method stub
//                calculate();
//
//            }
//
//
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//                // TODO Auto-generated method stub
//
//            }
//
//        };
//
//       // e21.addTextChangedListener(textWatcher);
//      //  e20.addTextChangedListener(textWatcher);
//
//    }
//
//    private void initialize() {
//        // TODO Auto-generated method stub
//
//        e21 = (EditText) findViewById(R.id.editText21);
//        e20 = (EditText) findViewById(R.id.editText20);
//
//
//       // tvResult = (TextView) findViewById(R.id.tvResult);
//
//
//    }
//
//    private void calculate() {
//        // TODO Auto-generated method stub
//
//
//        double heightInt = 1;
//        double weightInt = 0;
//
//        if (e21 != null)
//            heightInt = Double.parseDouble(e21.getText().toString());
//
//        if (e20 != null)
//            weightInt = Double.parseDouble(e20.getText().toString());
//    }
//
//       // result = weightInt / (heightInt * heightInt);
//      //  String textResult = "Your Bill is " + result;
//       // tvResult.setText(textResult);
//


    private void sum_and_product() {
    }


    public void fetch_caregivers()
    {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(Payment.this);
                pDialog.setMessage("Fetching payments...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("name", "");
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
                    String payday, paynight,phone_number;


                    payday=jo.getString("payday");
                    paynight=jo.getString("paynight");
                    phone_number=jo.getString("phone_number");

/*

  payday= employees.get("payday");
                 paynight= employees.get("paynight");
                 number= employees.get("phone_number")
 */


//                                    R.id.textView16
//                                    R.id.textView17
//                                    R.id.textView21


                    HashMap<String, String> employees = new HashMap<>();

                    employees.put("payday", payday);
                    employees.put("paynight", paynight);
                    employees.put("phone_number", phone_number);

                    list.add(employees);
                }
                else
                {

                }





            }





        } catch (JSONException e) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Payment.this);
            alertDialogBuilder.setTitle("Attention!").setMessage(String.valueOf(e))
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setCancelable(true).show();
        }

        ListAdapter adapter = new SimpleAdapter(Payment.this, list, R.layout.activity_payment,
                new String[]{"payday", "paynight", "phone_number"}, new int[]{
                R.id.textView16, R.id.textView17,R.id.textView21});
       // listView.setAdapter(adapter);












    }}


