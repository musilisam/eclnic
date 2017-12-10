package com.example.sam.e_clinic;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.sam.e_clinic.LoginActivity.user_id;

public class PatientActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    GridView grid;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    RelativeLayout mRelativeLayout;
    private Context mContext;
    Dialog login223 ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient2);

        try {
            mContext = getApplicationContext();
            mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);
            //mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            layoutManager = new GridLayoutManager(mContext, 2);
            recyclerView.setLayoutManager(layoutManager);
            // grid.setLayoutManager(layoutManager);
            adapter = new RecyclerAdapter();

            recyclerView.setAdapter(adapter);
            //grid.setAdapter((ListAdapter) adapter);
        } catch (Exception ex) {
            Toast.makeText(mContext, String.valueOf(ex), Toast.LENGTH_SHORT).show();
        }



    }



    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
    {
        public static final String MyPREFERENCES = "MyPrefs";
        //       SharedPreferences sharedpreferences;
        private String[] titles =
                {


                        "View CareGiver List",
                        "View history",
                        "Search Caregiver",
                        "Edit profile",


                };

        private String[] details = {
                "Item one details",
                "Item two details", "Item three details",
                "Item four details", "Item file details",
                "Item six details", "Item seven details",
                "Item eight details"};

        private int[] images = {


                R.drawable.list,
                R.drawable.update,
                R.drawable.search,
                R.drawable.edit,

        };


        class ViewHolder extends RecyclerView.ViewHolder {

            public int currentItem;
            public ImageView itemImage;
            public TextView itemTitle;
            public TextView itemDetail;

            public ViewHolder(View itemView) {
                super(itemView);

                itemImage = (ImageView) itemView.findViewById(R.id.imageView);
                itemTitle = (TextView) itemView.findViewById(R.id.textView6);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        switch (position){
                            case 0:
                                startActivity(new Intent(PatientActivity.this, PatientCaregiverList.class));
                                break;
                            case 1:
                                startActivity(new Intent(PatientActivity.this, Patienthistory.class)
                                .putExtra("username",user_id)
                                );
                                //Toast.makeText(PatientActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                startActivity(new Intent(PatientActivity.this, SearchCaregiver.class));
                                break;
                            case 3:
                                startActivity(new Intent(PatientActivity.this, Editprofile.class));
                                break;
//                            Toast.makeText(PatientActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                        }



                    }
                });
            }
        }



        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.main_activity_action_layout, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.itemTitle.setText(titles[i]);
            //  viewHolder.itemDetail.setText(details[i]);
            viewHolder.itemImage.setImageResource(images[i]);
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }
}
