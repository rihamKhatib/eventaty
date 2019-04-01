package com.example.android.eventatyapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class ProfileSupplierFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NAME = "name";
    private static final String ARG_EMAIL = "email";
    private static final String ARG_ADDRESS = "address";
    private static final String ARG_DETAIL = "detail";
    private static final String ARG_ID = "id";

    // TODO: Rename and change types of parameters
    private String mName;
    private String mEmail;
    private String mAddress;
    private String mDetail;
    private int mId;


    private TextView mNameTextView,mAddressTextView,mEmailTextView,mWorkFieldTextView;
    private ImageView mImageView;
    private Button mViewEventButton;
    public ProfileSupplierFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProfileSupplierFragment newInstance(String name, String email,String address,String detail,int id) {
        ProfileSupplierFragment fragment = new ProfileSupplierFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_EMAIL, email);
        args.putString(ARG_ADDRESS, address);
        args.putString(ARG_DETAIL, detail);
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            if(getArguments()!=null){
                mName = getArguments().getString(ARG_NAME);
                mAddress = getArguments().getString(ARG_ADDRESS);
                mEmail = getArguments().getString(ARG_EMAIL);
                mDetail = getArguments().getString(ARG_DETAIL);
                mId = getArguments().getInt(ARG_ID);
            }

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


       View v  = inflater.inflate(R.layout.fragment_supplier_profile, container, false);
        mAddressTextView = (TextView) v.findViewById(R.id.supplier_address_text_view);
        mEmailTextView = (TextView) v.findViewById(R.id.supplier_email_text_view);
        mNameTextView = (TextView) v.findViewById(R.id.supplier_name_text_view);
        mImageView = (ImageView) v.findViewById(R.id.supplier_image_view);
        mViewEventButton = (Button) v.findViewById(R.id.view_event_profile);
        mWorkFieldTextView = (TextView)v.findViewById(R.id.supplier_work_field_text_view) ;

        mAddressTextView.setText(mAddress);
        mEmailTextView.setText(mEmail);
        mAddressTextView.setText(mAddress);
        mNameTextView.setText(mName);
        mWorkFieldTextView.setText(mDetail);

        mViewEventButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = MainActivity.newIntent(getActivity(),"event",mId);

                startActivity(i);


            }
        });

 return v;

}}
