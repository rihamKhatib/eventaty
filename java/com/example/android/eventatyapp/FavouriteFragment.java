package com.example.android.eventatyapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;


public class FavouriteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView mRecyclerView;
    private SupplierAdapter mSupplierAdapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FavouriteFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static FavouriteFragment newInstance(String param1, String param2) {
        FavouriteFragment fragment = new FavouriteFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

        @Override

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("My Favourite");
            View v;
            v=inflater.inflate(R.layout.fragment_favourite, container, false);
            mRecyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_favourite);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            updateUI();
return v;
    }
    private class SupplierHolder extends RecyclerView.ViewHolder{

        private TextView nameTextView,emailTextView;
        private ImageButton mViewImageButton,mViewStarButton;
        public SupplierHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView)itemView.findViewById(R.id.supplier_name_text_view);
            emailTextView = (TextView)itemView.findViewById(R.id.email_supplier_text_view);
            mViewImageButton = (ImageButton) itemView.findViewById(R.id.view_supplier_item_button);
            mViewStarButton = (ImageButton) itemView.findViewById(R.id.star_supplier_item_button);


        }

        public void bind(User u){
            nameTextView.setText(u.getName());
            emailTextView.setText(String.valueOf(u.getEmail()));

            mViewImageButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(),SupplierProfileActivity.class);
                    startActivity(i);
                }
            });
            mViewStarButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    mViewStarButton.setImageResource(R.drawable.ic_star_black_24dp);


                }
            });
        }
    }
    private class SupplierAdapter extends RecyclerView.Adapter<SupplierHolder>{

        private ArrayList<User> mUsers;

        public SupplierAdapter(ArrayList<User> mUsers) {
            this.mUsers = mUsers;
        }

        @Override
        public SupplierHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.supplier_item,parent,false);
            return new SupplierHolder(view);
        }


        @Override
        public void onBindViewHolder(SupplierHolder holder, int position) {
            holder.bind(mUsers.get(position));
        }


        @Override
        public int getItemCount() {
            return mUsers.size();
        }
        public void setmEvents(ArrayList<User>users){
            mUsers= users;
        }
    }
    public void updateUI(){
        Lab lab = Lab.get(getActivity());
        if(mSupplierAdapter==null){
            mSupplierAdapter = new SupplierAdapter(lab.getUsers());
        }else{
            mSupplierAdapter.setmEvents(lab.getUsers());
            mSupplierAdapter.notifyDataSetChanged();
        }
        mRecyclerView.setAdapter(mSupplierAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


}
