package com.example.sam.e_clinic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static com.example.sam.e_clinic.PatientCaregiverList.usernameusername_caregiver;
import static com.example.sam.e_clinic.URLs.main;

public class Rate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        final RatingBar simpleRatingBar = (RatingBar) findViewById (R.id.ratingBar);

        Button submitButton = (Button) findViewById ( R.id.button13 );

        submitButton.setOnClickListener (new View. OnClickListener () {
            @Override
            public void onClick (View v) {

                String totalStars = "Total Stars:: " + simpleRatingBar . getNumStars ();
                Float rating =  simpleRatingBar . getRating ();
                Toast. makeText ( getApplicationContext (), totalStars + "\n" + rating ,
                        Toast . LENGTH_LONG ). show ();

                     rate(usernameusername_caregiver,String.valueOf(rating));
            }
        });
    }


    public void rate(final String user,final String pass){

        final ProgressDialog pdialog= new ProgressDialog(Rate.this);
        pdialog.setMessage("rating");
        pdialog.show();
        RequestQueue queue = Volley.newRequestQueue( Rate.this);
        String url = main+"ratings.php";
        StringRequest PostRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pdialog.dismiss();

                        if(response.trim().equals("1"))
                        {
                            Toast.makeText(Rate.this, "success!! ", Toast.LENGTH_SHORT).show();
//                            user_id=user;
//                            Intent homeIntent =new Intent(LoginActivity.this,PatientActivity.class);
//                            LoginActivity.this.startActivity(homeIntent);

                        }
                        else {
                            Toast.makeText(Rate.this, "error!! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pdialog.dismiss();
                        Toast.makeText(Rate.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams()


            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", user);
                params.put("ratings",pass);


                return params;
            }
        };
        queue.add(PostRequest);

    }



    }
