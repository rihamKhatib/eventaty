package com.example.android.eventatyapp;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private Button mRegisterButton ;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fragmentManager = getSupportFragmentManager();


        Fragment fragment = fragmentManager.findFragmentById(R.id.login_fragment_container);
        if(fragment==null){
            fragment = new LoginFragment();
            fragmentManager.beginTransaction().add(R.id.login_fragment_container,fragment).commit();
        }else{
            fragment = new LoginFragment();
            fragmentManager.beginTransaction().replace(R.id.login_fragment_container,fragment).commit();
        }

    }
}
