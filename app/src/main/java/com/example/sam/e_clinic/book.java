package com.example.sam.e_clinic;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import static com.example.sam.e_clinic.LoginActivity.user_id;
import static com.example.sam.e_clinic.PatientCaregiverList.usernameusername_caregiver;

public class book extends AppCompatActivity
{
    EditText e4,e5,e6,e7;
    Button button3;
    Button button12;
    TextView rate;
    public static String care_giver_name;
    public static String cordinates;
    Button button4;
private void mydate(){
    Calendar calendar = new GregorianCalendar();
    e6=(EditText)findViewById(R.id.editText6);
    int year       = calendar.get(Calendar.YEAR);
    int month      = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH); // Jan = 0, not 1
    e6.setText(year+"-"+(month+1)+"-"+dayOfMonth);

}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        mydate();


        Intent intent = getIntent();
        care_giver_name = intent.getStringExtra("care_giver_name");
        e4 = (EditText) findViewById(R.id.editText4);
e4.setVisibility(View.GONE);
        e5 = (EditText) findViewById(R.id.editText5);
        e5.setVisibility(View.GONE);
        e6 = (EditText) findViewById(R.id.editText6);
        e7 = (EditText) findViewById(R.id.editText7);
        e7.setText(cordinates);
        e7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                startActivity(new Intent(book.this, PlaceViewer.class));

            }
        });
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button8);
        button12 = (Button) findViewById(R.id.button12);
        rate = (TextView) findViewById(R.id.textView42);


        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String username, patient, date_of_request, location;
                username = user_id;//e4.getText().toString();
                patient = user_id;//e5.getText().toString();
                date_of_request = e6.getText().toString();
                location = e7.getText().toString();

                if (username.equals("") || patient.equals("") || date_of_request.equals("") || location.equals("")) {

                    Toast pass = Toast.makeText(book.this, "Fill in all the fields", Toast.LENGTH_LONG);
                    pass.show();


                } else {
                    book(username, patient, date_of_request, location, user_id);
                }
            }
        });


        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(book.this, Payment.class));

                Toast pass = Toast.makeText(book.this, "pay", Toast.LENGTH_LONG);
                pass.show();

            }

        });


        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(book.this, Rate.class));

                Toast pass = Toast.makeText(book.this, "rating", Toast.LENGTH_LONG);
                pass.show();

            }

        });


    }


    public void book(final String username, final String patient,
                     final String date_of_request, final String location, final String caregiver) {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(book.this);
                pDialog.setMessage("Booking...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("username", username);
                paramms.put("patient", patient);
                paramms.put("date_of_request", date_of_request);
                paramms.put("location", location);
                paramms.put("caregiver",usernameusername_caregiver);
                String s = rh.sendPostRequest(URLs.main + "book.php", paramms);
                return s;

            }
            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                pDialog.dismiss();

                Toast.makeText(book.this, s, Toast.LENGTH_SHORT).show();

                if(s.equals("1"))
                {
//                   new SweetAlertDialog(SignUp.this, SweetAlertDialog.SUCCESS_TYPE).setContentText("Registration success. Please sign in to continue.")
//                           .setTitleText("Attention.").show();

                    new AlertDialog.Builder(book.this).setMessage("booking success.").show();

                }
                else
                {
//                        new SweetAlertDialog(SignUp.this, SweetAlertDialog.ERROR_TYPE).setContentText("Registration Failed.")
//                                .setTitleText("Attention.").show();
                    new AlertDialog.Builder(book.this).setTitle("Attention!").setMessage("booking Failed.").show();
                }

            }
        }
        GetJSON jj = new GetJSON();
        jj.execute();
    }
}

