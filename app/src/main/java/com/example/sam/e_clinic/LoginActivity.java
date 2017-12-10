package com.example.sam.e_clinic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.sam.e_clinic.URLs.main;


public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    Button login;
    Spinner spinner;
    public  static  String user_id;
    TextView link_signup,signinlink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=(Button) findViewById(R.id.button);

        username=(EditText) findViewById(R.id.editText);
        password=(EditText) findViewById(R.id.editText2);
        link_signup=(TextView) findViewById(R.id.textView3);
        signinlink=(TextView) findViewById(R.id.zz);
        spinner=(Spinner)findViewById(R.id.spinner);
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Login as..");
        spinnerArray.add("Patient");
        spinnerArray.add("Care Giver");
        spinnerArray.add("Administrator");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        signinlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int req=spinner.getSelectedItemPosition();
                String index=String.valueOf(req);

                if(index.equals("0"))
                {
                  //
                }
                else if(index.equals("1"))
                {

                    Intent intent = new Intent(LoginActivity.this, SignUp.class);
                    startActivity(intent);
                }
                else if(index.equals("2"))
                {

                    Intent intent = new Intent(LoginActivity.this, AdminAddCaregiver.class);
                    startActivity(intent);
                }
                else if(index.equals("3"))
                {
//                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
//                    startActivity(intent);
                }




            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user,pass;
                user= username.getText().toString().trim();
                pass=password.getText().toString().trim();

                int position = spinner.getSelectedItemPosition();
                switch (position){

                    case 0:
                        Toast.makeText(LoginActivity.this, "Please select the desired login designator", Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        patientlogin(user,pass);
                        break;
                    case 2:
                        caregiverlogin(user,pass);
                        break;
                    case 3:

                        adminlogin(user,pass);
                        break;

                }

                //Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();


            }
            public void patientlogin(final String user,final String pass){

                final ProgressDialog pdialog= new ProgressDialog(LoginActivity.this);
                pdialog.setMessage("Logging you in");
                pdialog.show();
                RequestQueue queue = Volley.newRequestQueue( LoginActivity.this);
                String url = main+"patientlogin.php";
                StringRequest PostRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pdialog.dismiss();

                                if(response.trim().equals("1"))
                                {
                                    user_id=user;
                                    Intent homeIntent =new Intent(LoginActivity.this,PatientActivity.class);
                                    LoginActivity.this.startActivity(homeIntent);

                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Login failed!! ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pdialog.dismiss();
                                Toast.makeText(LoginActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                            }
                        }

                ) {
                    @Override
                    protected Map<String, String> getParams()


                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", user);
                        params.put("password",pass);


                        return params;
                    }
                };
                queue.add(PostRequest);

            }
            public void  caregiverlogin (final String user,final String pass){

                final ProgressDialog pdialog= new ProgressDialog(LoginActivity.this);
                pdialog.setMessage("Logging you in");
                pdialog.show();

                RequestQueue queue = Volley.newRequestQueue( LoginActivity.this);
                String url = main+"caregiverlogin.php";
                StringRequest PostRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pdialog.dismiss();

                                if(response.trim().equals("1"))
                                {
                                    user_id=user;
                                    Intent homeIntent =new Intent(LoginActivity.this,caregivers.class);
                                    LoginActivity.this.startActivity(homeIntent);

                                }
                                else
                                    {
                                    Toast.makeText(LoginActivity.this, "Login failed!! ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pdialog.dismiss();
                                Toast.makeText(LoginActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                            }
                        }

                ) {
                    @Override
                    protected Map<String, String> getParams()


                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", user);
                        params.put("password",pass);


                        return params;
                    }
                };
                queue.add(PostRequest);

            }

            public void  adminlogin (final String user,final String pass){

                final ProgressDialog pdialog= new ProgressDialog(LoginActivity.this);
                pdialog.setMessage("Logging you in");
                pdialog.show();

                RequestQueue queue = Volley.newRequestQueue( LoginActivity.this);
                String url = main+"adminlogin.php";
                StringRequest PostRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pdialog.dismiss();

                                if(response.trim().equals("1"))
                                {
                                    Intent homeIntent =new Intent(LoginActivity.this,AdminMenu.class);
                                    LoginActivity.this.startActivity(homeIntent);

                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this, "Login failed!! ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pdialog.dismiss();
                                Toast.makeText(LoginActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                            }
                        }

                ) {
                    @Override
                    protected Map<String, String> getParams()


                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", user);
                        params.put("password",pass);


                        return params;
                    }
                };
                queue.add(PostRequest);

            }

        });








    }
}
