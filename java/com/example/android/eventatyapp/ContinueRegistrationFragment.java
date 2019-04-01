package com.example.android.eventatyapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ContinueRegistrationFragment extends Fragment {


    private EditText mLocationEditText,mCompanyNameEditText,mWorkFieldEditText,mDetailEditText,mPhoneEditText;
    private Button mRegisterButton;
    private static final String ARG_EMAIL = "email";
    private static final String ARG_CONFIRMPASSWORD = "confitmPassword";
    private static final String ARG_PASSWORD = "password";
    private static final String ARG_NAME = "name";
    private static final String ARG_ADDRESS = "address";
    private Lab mLab;
    private Spinner mCategorySpinner;


    // TODO: Rename and change types of parameters
    private String mEmail;
    private String mConfirmPassword;
    private String mPassword;
    private String mName;
    private String mAddress;




    public ContinueRegistrationFragment() {
        // Required empty public constructor
    }

    public static ContinueRegistrationFragment newInstance(String email, String confitmPassword,String address,String name,String password) {
        ContinueRegistrationFragment fragment = new ContinueRegistrationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        args.putString(ARG_CONFIRMPASSWORD, confitmPassword);
        args.putString(ARG_PASSWORD, password);
        args.putString(ARG_NAME, name);
        args.putString(ARG_ADDRESS, address);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEmail = getArguments().getString(ARG_EMAIL);
            mConfirmPassword = getArguments().getString(ARG_CONFIRMPASSWORD);
            mPassword = getArguments().getString(ARG_PASSWORD);
            mName = getArguments().getString(ARG_NAME);
            mAddress = getArguments().getString(ARG_ADDRESS);


        }
        mLab =Lab.get(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Continue Registration");
        View v = inflater.inflate(R.layout.fragment_continue_registration, container, false);

        mCompanyNameEditText = (EditText)v.findViewById(R.id.company_name_registration);
        mDetailEditText = (EditText)v.findViewById(R.id.work_detail_registration);
        mPhoneEditText = (EditText)v.findViewById(R.id.phone_registration);
        mWorkFieldEditText = (EditText)v.findViewById(R.id.work_field_registration);
      //  mLocationEditText= (EditText)v.findViewById(R.id.service_location_registration);
        final Spinner spinner = (Spinner) v.findViewById(R.id.service_location_registration);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.service_location, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        mCategorySpinner = (Spinner) v.findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategorySpinner.setAdapter(categoryAdapter);



        mRegisterButton = (Button) v.findViewById(R.id.registration_button_continue);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCompanyNameEditText.getText().toString().equals("")||mDetailEditText.getText().toString().equals("")||mPhoneEditText.getText().toString().equals("")
                        ||mWorkFieldEditText.getText().toString().equals(""))
                    Toast.makeText(getActivity(), "Fill all inputs", Toast.LENGTH_SHORT).show();
                else{
                    //TODO Register User on server
               //     {
                            String name = mName;
                            String confirmPassword= mConfirmPassword;
                            String address=mAddress;
                            String email = mEmail;
                            String password = mPassword;
                            String work_detail = mDetailEditText.getText().toString();
                            String company_name = mCompanyNameEditText.getText().toString();
                        String work_field = mWorkFieldEditText.getText().toString();
                        String location = spinner.getSelectedItem().toString();
                        String phone = mPhoneEditText.getText().toString();
                    String category = mCategorySpinner.getSelectedItem().toString();





                        String url = mLab.routeurl + "mobile/registerSupplier/"+name+"/"+password+"/"+email+"/"+address+"/"
                                +work_field+"/"+company_name+"/"+phone+"/"+work_detail+"/"+location+"/"+category;

                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                response.toString();
                                                //JSONArray jsonArray = new JSONArray(response);
                                                JSONObject jsonObejct = new JSONObject(response);
                                                JSONObject data = jsonObejct.getJSONObject("DATA");
                                                //     boolean flag = jsonObject.getBoolean("STATUS");
                                                //       if(flag){
                                                //JSONObject json2 = jsonObject.getJSONObject(response);
                                                //   Toast.makeText(getContext(),jsonObject.getString("MESSAGE"),Toast.LENGTH_SHORT).show();
                                                String name = data.getString("name");
                                                String email = data.getString("email");
                                                String address = data.getString("address");
                                                String role = data.getString("role");
                                         //       String category = data.getString("category");
                                                int id = data.getInt("id");

                                                User user = new User(name, email,  role,  address, id);
                                                user.setRole(role);
                                             //   user.setCategory(category);
                                                mLab.setUser(user);
                                                Toast.makeText(getContext(), "Signing In", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(getActivity(),MainActivity.class);
                                                startActivity(i);
                                                //    }else {

                                                //      }
                                                // {"STATUS":false,"MESSAGE":"Username or Password Unauthenticate","DATA":null}

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Toast.makeText(getContext(), "Try Again,You Entered The Information Wrong", Toast.LENGTH_SHORT).show();
                                            }


                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    error.printStackTrace();
                                    Toast.makeText(getContext(), "Try Again,You Entered The Information Wrong", Toast.LENGTH_SHORT).show();

                                }
                            }) {


                            };

                            Mysingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

//
//                    Intent i = new Intent(getActivity(),MainActivity.class);
//                startActivity(i);
            }}
        });
        return v;
    }



}
