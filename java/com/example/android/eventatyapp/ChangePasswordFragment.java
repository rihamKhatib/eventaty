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
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;


public class ChangePasswordFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CONF = "confirm";
    private static final String ARG_PASS = "password";
     EditText newPassword,confirmPassword;
     Button changePassword;
    private Lab mLab;
    private User mUser;


    // TODO: Rename and change types of parameters

    private String mConfirmPassEditText;
    private String mPassEditText;


    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ChangePasswordFragment newInstance(String confirm, String password) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONF, confirm);
        args.putString(ARG_PASS, password);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mConfirmPassEditText = getArguments().getString(ARG_CONF);
            mPassEditText = getArguments().getString(ARG_PASS);
        }
        mLab =Lab.get(getActivity());
        mUser = mLab.getUser();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Change Password");
        View v;
        v=inflater.inflate(R.layout.fragment_change_password, container, false);
     //   oldPassword = (EditText) v.findViewById(R.id.old_password);
        newPassword = (EditText) v.findViewById(R.id.new_password);
        confirmPassword = (EditText) v.findViewById(R.id.confirm_new_password);
        final int user_id= mUser.getId();

        changePassword = (Button) v.findViewById(R.id.change_password_button);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str =confirmPassword.getText().toString();
                if(confirmPassword.getText().toString().equals("")||
                        newPassword.getText().toString().equals("")){

                    Toast.makeText(getActivity(),"Please Enter Your Password",Toast.LENGTH_SHORT).show();
                }else if(!confirmPassword.getText().toString().equals(newPassword.getText().toString())){
                    Toast.makeText(getActivity(),"Your Password Doesn't Match",Toast.LENGTH_SHORT).show();
                }else{
                    String  url1 =mLab.routeurl+"mobile/changePassword/user_id/"+user_id+"/password/"+newPassword.getText();
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url1,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //   try {
                                    // ArrayList<Tasks>taskses = new ArrayList<>();

                                    //program??
//                                    ArrayList<Program> programs = new ArrayList<>();

                                    String s = response.toString();


                                    String str = response.toString();
                                    str.toString();
                                    Toast.makeText(getContext(),"Your Password Havw Been Changed",Toast.LENGTH_SHORT).show();
//                ??                    Intent i = MainActivity.newIntent(getActivity(),"other");
//     ??                               startActivity(i);
                                    Intent i = new Intent(getActivity(),MainActivity.class);
                                    startActivity(i);

//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                        Log.d("Erorr:",e.toString());
//                                        // Toast.makeText(getActivity(),"fail",Toast.LENGTH_SHORT).show();
//                                    }




                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            error.printStackTrace();

                        }
                    })
                    {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("type", String.valueOf(mSubItemsType.toString()));
//
//                return params;
//
//            }
                    } ;

                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            5000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    Mysingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);




                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();
                        }


                    };


                    Mysingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);


                }}
        });
        return v;
    }


}
