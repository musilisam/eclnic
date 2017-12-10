package com.example.sam.e_clinic;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import static com.example.sam.e_clinic.LoginActivity.user_id;

public class Updatepayments extends AppCompatActivity {
    EditText e19, e22;
    Button button10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepayments);

        e19=(EditText)findViewById(R.id.editText19);
        e22=(EditText)findViewById(R.id.editText22);
        button10=(Button)findViewById(R.id.button10);

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String payday, paynight;

                payday=e19.getText().toString();
                paynight=e22.getText().toString();

                Log.d("user_id", user_id);

                updatepayments( payday, paynight);


            }
        });
            }

    public void updatepayments( final String payday, final String paynight)
    {

        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog= new ProgressDialog(Updatepayments.this);
                pDialog.setMessage("Updating payments...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params)
            {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("payday", payday);
                paramms.put("paynight", paynight);
                paramms.put("username", user_id);
                Log.d("params", String.valueOf(paramms));
                String s = rh.sendPostRequest(URLs.main+"updatepayments.php", paramms);
                return s;

            }



            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                pDialog.dismiss();
                Toast.makeText(Updatepayments.this, s, Toast.LENGTH_SHORT).show();

                if(s.equals("1"))
                {
//                   new SweetAlertDialog(SignUp.this, SweetAlertDialog.SUCCESS_TYPE).setContentText("Registration success. Please sign in to continue.")
//                           .setTitleText("Attention.").show();

                    new AlertDialog.Builder(Updatepayments.this).setMessage("Successfully updated.").show();

                }
                else
                {
//                        new SweetAlertDialog(SignUp.this, SweetAlertDialog.ERROR_TYPE).setContentText("Registration Failed.")
//                                .setTitleText("Attention.").show();

                    new AlertDialog.Builder(Updatepayments.this).setTitle("Attention!").setMessage("Update Failed.").show();
                }

            }


        }
        GetJSON jj = new GetJSON();
        jj.execute();
    }
}
