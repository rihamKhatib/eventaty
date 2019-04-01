package com.example.android.eventatyapp;

import android.app.Activity;
import android.content.Intent;
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


public class RegisterFragment extends Fragment {

    private EditText mPassword,mName,mEmail,mConfirmPssword,mAddressEditText;
    private Button mRegisterButton;
    private Spinner mRegisterType;
    private Lab mLab;
    public RegisterFragment() {
    }


    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLab=Lab.get(getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Register");

        View v = inflater.inflate(R.layout.fragment_register, container, false);
        mEmail = (EditText) v.findViewById(R.id.email_edit_text_login);
         mName = (EditText) v.findViewById(R.id.name_edit_text_login);
        mPassword = (EditText) v.findViewById(R.id.password_edit_text_login);
         mConfirmPssword = (EditText) v.findViewById(R.id.conPassword_edit_text_login);
         mAddressEditText = (EditText)v.findViewById(R.id.address_edit_text_login) ;
        mRegisterButton = (Button) v.findViewById(R.id.register_button);

        mRegisterType = (Spinner) v.findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.user_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRegisterType.setAdapter(adapter);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEmail.getText().toString().equals("")||mConfirmPssword.getText().toString().equals("")||mPassword.getText().toString().equals("")||mName.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Fill all inputs", Toast.LENGTH_SHORT).show();
                }else{
                    String text = mRegisterType.getSelectedItem().toString();
                    if(text.equals("Supplier")) {
                        Fragment fragment = ContinueRegistrationFragment.newInstance(mEmail.getText().toString(),mConfirmPssword.getText().toString()
                                ,mAddressEditText.getText().toString(),mName.getText().toString(),mPassword.getText().toString());
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.login_fragment_container, fragment).commit();
                    }else{
                        //TODO Register User on server
                        String name = mName.getText().toString();
                        String email = mEmail.getText().toString();
                        String password = mPassword.getText().toString();
                        String confirmPassword = mConfirmPssword.getText().toString();
                        String type = mRegisterType.getSelectedItem().toString();
                        String address = mAddressEditText.getText().toString();
                        String url = mLab.routeurl + "mobile/registeruser/"+name+"/"+password+"/"+email+"/"+type+"/"+address;

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
                                            int id = data.getInt("id");

                                            User user = new User(name, email,  role,  address, id);
                                            user.setRole(role);
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
//                        Intent i = new Intent(getActivity(),MainActivity.class);
//                        startActivity(i);
                    }
                }

                }
        });

        return v;
    }





}
