package com.example.android.eventatyapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class AddEditEventFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_DETAIL = "detail";
    private static final String ARG_NAME = "name";
    private static final String ARG_PAGE = "page";
    private static final String ARG_ID = "id";

    // TODO: Rename and change types of parameters
    private String mName;
    private String mDetail;
    private int mId;

    private String mPage="add";


    EditText nameEditText,detailsEditText;
    Button addEventButton;
    private User mUser;
    ImageView eventImageView;
    private Lab mLab;


    public AddEditEventFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddEditEventFragment newInstance(String name, String detail,String page, int id) {
        AddEditEventFragment fragment = new AddEditEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DETAIL, detail);
        args.putString(ARG_NAME, name);
        args.putString(ARG_PAGE, page);
        args.putInt(ARG_ID,id);


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDetail = getArguments().getString(ARG_DETAIL);
            mName = getArguments().getString(ARG_NAME);
            mId = getArguments().getInt(ARG_ID);

            mPage = getArguments().getString(ARG_PAGE);

        }
        mLab =Lab.get(getActivity());
        mUser = mLab.getUser();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_add_edit_event, container, false);

        nameEditText = (EditText) v.findViewById(R.id.add_event_edit_Text_name);
        detailsEditText = (EditText) v.findViewById(R.id.add_event_edit_Text_details);
        addEventButton = (Button) v.findViewById(R.id.add_event_button);
        final int user_id= mUser.getId();

        if(mPage.equals("add")){
            addEventButton.setText("Add new Event");
        }else{
            addEventButton.setText("Edit Event");
            nameEditText.setText(mName);
            detailsEditText.setText(mDetail);

        }
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mPage.equals("add")) {
                    String url = mLab.routeurl + "mobile/insertEvent/" + nameEditText.getText().toString() + "/" + detailsEditText.getText().toString() + "/" + user_id;
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    response.toString();
                                    //JSONArray jsonArray = new JSONArray(response);
//                                    JSONObject jsonObejct = new JSONObject(response);
//
//                                    boolean status =  jsonObejct.getBoolean("STATUS");
//                                    if(status) {
//                                        Toast.makeText(getContext(), "your date is "+mDate, Toast.LENGTH_SHORT).show();
//                                    }else{
//                                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
//                                    }

                                    Intent i = new Intent(getActivity(), MainActivity.class);
                                    startActivity(i);


                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(getContext(), "Try Again,The data can't Entered or Changed", Toast.LENGTH_SHORT).show();


                        }
                    }) {


                    };

                    Mysingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
                }else if(mPage.equals("edit")){

                    String url = mLab.routeurl + "mobile/updateEvent/" +mId+"/"+ nameEditText.getText().toString()
                            + "/" + detailsEditText.getText().toString() + "/" + user_id;
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    response.toString();
                                    //JSONArray jsonArray = new JSONArray(response);
//                                    JSONObject jsonObejct = new JSONObject(response);
//
//                                    boolean status =  jsonObejct.getBoolean("STATUS");
//                                    if(status) {
//                                        Toast.makeText(getContext(), "your date is "+mDate, Toast.LENGTH_SHORT).show();
//                                    }else{
//                                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
//                                    }

                                    Intent i = new Intent(getActivity(), MainActivity.class);
                                    startActivity(i);


                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(getContext(), "Try Again,The data can't Entered or Changed", Toast.LENGTH_SHORT).show();


                        }
                    }) {


                    };

                    Mysingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
                }
            }
        });
        return v;

    }
}
