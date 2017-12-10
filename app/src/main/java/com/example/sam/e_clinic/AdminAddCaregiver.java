package com.example.sam.e_clinic;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class AdminAddCaregiver extends AppCompatActivity
{
EditText e10, e11, e12, e13, e8, e9,e18;
Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_caregiver);

        e10=(EditText)findViewById(R.id.editText10);
        e11=(EditText)findViewById(R.id.editText11);
        e12=(EditText)findViewById(R.id.editText12);
        e13=(EditText)findViewById(R.id.editText3);
        e8=(EditText)findViewById(R.id.editText8);
        e18=(EditText)findViewById(R.id.editText18);
        e9=(EditText)findViewById(R.id.editText9);
        button5=(Button)findViewById(R.id.button5);

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, county, certificationid, phonenumber, payday, paynight,password;

                name=e10.getText().toString();
                county=e11.getText().toString();
                certificationid=e12.getText().toString();
                phonenumber=e13.getText().toString();
                payday=e8.getText().toString();
                paynight=e9.getText().toString();
                password=e18.getText().toString();


                if(name.equals("")||county.equals("")||certificationid.equals("")||phonenumber.equals("")||payday.equals("")||paynight.equals("")||password.equals("")){

                    Toast passs = Toast.makeText(AdminAddCaregiver.this, "Fill in all the fields", Toast.LENGTH_LONG);
                    passs.show();

                }
                else

                register_caregiver( name,  county,  certificationid, phonenumber, payday, paynight,password);


            }
        });



    }
    public void register_caregiver(final String name, final String county,final String certificationid, final String phonenumber, final String payday, final String paynight, final String password)
    {

        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog= new ProgressDialog(AdminAddCaregiver.this);
                pDialog.setMessage("Signing you in...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params)
            {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("name", name);
                paramms.put("county", county);
                paramms.put("certificationid", certificationid);
                paramms.put("phonenumber", phonenumber);
                paramms.put("payday", payday);
                paramms.put("paynight", paynight);
                paramms.put("password", password);
                String s = rh.sendPostRequest(URLs.main+"regcaregiver.php", paramms);
                return s;

            }



            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                pDialog.dismiss();

                if(s.equals("1"))
                {
//                   new SweetAlertDialog(SignUp.this, SweetAlertDialog.SUCCESS_TYPE).setContentText("Registration success. Please sign in to continue.")
//                           .setTitleText("Attention.").show();

                    new AlertDialog.Builder(AdminAddCaregiver.this).setMessage("Registration success.").show();





                }
                else
                {
//                        new SweetAlertDialog(SignUp.this, SweetAlertDialog.ERROR_TYPE).setContentText("Registration Failed.")
//                                .setTitleText("Attention.").show();
                    new AlertDialog.Builder(AdminAddCaregiver.this).setTitle("Attention!").setMessage("Registration failed.").show();
                }

            }


        }
        GetJSON jj = new GetJSON();
        jj.execute();
    }
}

