package com.example.android.eventatyapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


public class ViewEventFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NAME = "name";
    private static final String ARG_EMAIL = "email";
    private static final String ARG_DETAIL = "detail";

    TextView nameTextView,detailsTextView,emailTextView;


    // TODO: Rename and change types of parameters
    private String mName ="";
    private String mEmail="";
    private String mDetail="";




    public ViewEventFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ViewEventFragment newInstance(String detail, String name,String email) {
        ViewEventFragment fragment = new ViewEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DETAIL, detail);
        args.putString(ARG_EMAIL, email);
        args.putString(ARG_NAME, name);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDetail = getArguments().getString(ARG_DETAIL);
            mEmail = getArguments().getString(ARG_EMAIL);
            mName = getArguments().getString(ARG_NAME);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View v = inflater.inflate(R.layout.fragment_view_event, container, false);
        nameTextView = (TextView) v.findViewById(R.id.name_event_view);
        emailTextView = (TextView) v.findViewById(R.id.email_event_view);
        detailsTextView = (TextView) v.findViewById(R.id.details_event_view);
        nameTextView.setText(mName);
        emailTextView.setText(mEmail);
        detailsTextView.setText(mDetail);


        return v;
    }




}
