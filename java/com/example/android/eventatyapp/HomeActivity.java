package com.example.android.eventatyapp;

import android.app.Activity;
import android.content.Intent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
private RecyclerView homeRecyclerView;
    private HomeActivity.HomeAdapter mHomeAdapter;
    LinearLayout linearLayout,linearLayout2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeRecyclerView = (RecyclerView)findViewById(R.id.home_recycler_view);
        homeRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        linearLayout = (LinearLayout)findViewById(R.id.home_fragment_container);
        linearLayout.setVisibility(View.INVISIBLE);

        linearLayout2= (LinearLayout)findViewById(R.id.for_recycler);

        updateUI();
    }



    private class HomeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mNameTextView;
        private ImageView mImageView;
        private String mLocation;
        public HomeHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView)itemView.findViewById(R.id.name_home_item);
            mImageView = (ImageView)itemView.findViewById(R.id.image_home_item);
            itemView.setOnClickListener(this);
        }

        public void bind(Location location) {
            mNameTextView.setText(location.getName());
            mImageView.setImageResource(location.getImage());
            mLocation = location.getName();
        }


        @Override
        public void onClick(View view) {
            FragmentManager fragmentManager= getSupportFragmentManager();
            linearLayout.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.GONE);
            Intent intent= CategoryActivity.newIntent(getApplicationContext(),mLocation);
            startActivity(intent);
        }
    }

    private class HomeAdapter extends RecyclerView.Adapter<HomeActivity.HomeHolder>{

        private ArrayList<com.example.android.eventatyapp.Location>mLocations ;
        public HomeAdapter(ArrayList<com.example.android.eventatyapp.Location> locations) {
       mLocations = locations;
        }

        @Override
        public HomeActivity.HomeHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);
            View view = inflater.inflate(R.layout.home_item,parent,false);
            return new HomeActivity.HomeHolder(view);
        }

        @Override
        public void onBindViewHolder(HomeActivity.HomeHolder holder, int position) {
            holder.bind(mLocations.get(position));
        }

        @Override
        public int getItemCount() {
            return mLocations.size();
        }
        public void setmLocations(ArrayList<com.example.android.eventatyapp.Location> locations){
            mLocations = locations;
        }
    }

    private void updateUI(){
        if(mHomeAdapter==null){
            mHomeAdapter = new HomeActivity.HomeAdapter(Lab.get(HomeActivity.this).getmLocations());

        }else {
            mHomeAdapter.setmLocations(Lab.get(HomeActivity.this).getmLocations());
            mHomeAdapter.notifyDataSetChanged();
        }
        homeRecyclerView.setAdapter(mHomeAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }
}
