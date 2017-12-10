package com.example.sam.e_clinic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    EditText patient_id,patient,county,password,phonenumber,confirmpassword,username;
    Button SIGN_UP;
    TextView login_now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        patient_id = (EditText) findViewById(R.id.patient_id);
        patient = (EditText) findViewById(R.id.patient);
        county = (EditText) findViewById(R.id.county);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        phonenumber = (EditText) findViewById(R.id.phonenumber);
        confirmpassword = (EditText) findViewById(R.id.confirmpassword);
        login_now = (TextView) findViewById(R.id.textView2);
        SIGN_UP = (Button) findViewById(R.id.button7);

        login_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        SIGN_UP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
//                Toast.makeText(SignUp.this, "Sign up", Toast.LENGTH_SHORT).show();

                String p_id = patient_id.getText().toString().trim();
                String pattient = patient.getText().toString().trim();
                String countyy = county.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String user = username.getText().toString().trim();
                String phoneno = phonenumber.getText().toString().trim();
                String cpass = confirmpassword.getText().toString().trim();





                if(p_id.equals("")||pattient.equals("")||countyy.equals("")||pass.equals("")||user.equals("")||phoneno.equals("")||cpass.equals("")){

                    Toast passs = Toast.makeText(SignUp.this, "Fill in all the fields", Toast.LENGTH_LONG);
                    passs.show();

                }

                else if(!pass.equals(cpass)){
                    Toast passs = Toast.makeText(SignUp.this, "Passwords don't match", Toast.LENGTH_LONG);
                    passs.show();


                }
                else  signup(p_id, pattient, countyy, pass,phoneno,user);


            }
        });

    }

    public void signup(final String p_id, final String pattient, final String countyy, final String pass, final String phoneno,final String user) {

        // Toast.makeText(this, password, Toast.LENGTH_SHORT).show();

        final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setMessage("Loading");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.43.188/eclinic/reg.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(SignUp.this, String.valueOf(response), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(SignUp.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("p_id", p_id);
                params.put("pattient", pattient);
                params.put("countyy", countyy);
                params.put("pass", pass);
                params.put("phoneno", phoneno);
                params.put("user", user);

                return params;
            }
        };
        queue.add(postRequest);
    }


}
