package com.example.android.eventatyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginFragment extends Fragment {

    private EditText mEmailEditText,mPasswordEditText;
    private Button mLoginButton;
    private Button mRegisterButton ;
    private Lab mLab;

    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        mLab  = Lab.get(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Login");

        View v = inflater.inflate(R.layout.fragment_login, container, false);
        mEmailEditText = (EditText)v.findViewById(R.id.email_edit_Text_login);
        mPasswordEditText = (EditText)v.findViewById(R.id.password_edit_text_login);
        mLoginButton = (Button)v.findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(mEmailEditText.getText().toString().equals("")||mPasswordEditText.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Fill Your Email And Password", Toast.LENGTH_SHORT).show();
                }else{
                    String email = mEmailEditText.getText().toString();

                    String password = mPasswordEditText.getText().toString();


                    String url = mLab.routeurl + "mobile/login/"+email+"/"+password;
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
                                        int id = data.getInt("id");
                                        String password = data.getString("password");
                                        String role = data.getString("role");

                                        User user = new User(name,  email,  id, password);
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

                }


            }
        });
                mRegisterButton = (Button)v.findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new RegisterFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.login_fragment_container,fragment).commit();
            }
        });
        return v;
    }





}
