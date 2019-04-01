package com.example.android.eventatyapp;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SupplierProfileActivity extends AppCompatActivity {


    private static final String EXTRA_ADDRESS ="address" ;
    private static final String EXTRA_NAME ="name" ;
    private static final String EXTRA_DETAIL ="detail" ;
    private static final String EXTRA_EMAIL ="email" ;
    private static final String EXTRA_ID ="id" ;
   // private FragmentManager mFragmentManager;

    private String mAddress,mName,mEmail,mDetail;
    private int mId ;
    public static Intent newIntent(Context context, String address, String name, String detail, String email, int id){
        Intent i = new Intent(context,SupplierProfileActivity.class);
        i.putExtra(EXTRA_ADDRESS,address);
        i.putExtra(EXTRA_NAME,name);
        i.putExtra(EXTRA_DETAIL,detail);
        i.putExtra(EXTRA_EMAIL,email);
        i.putExtra(EXTRA_ID,id);


        return i;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_profile);
        if(getIntent().getStringExtra(EXTRA_NAME)!=null){
            mAddress = getIntent().getStringExtra(EXTRA_ADDRESS);
            mDetail=getIntent().getStringExtra(EXTRA_DETAIL);
            mEmail=getIntent().getStringExtra(EXTRA_EMAIL);
            mName =getIntent().getStringExtra(EXTRA_NAME);
            mId =getIntent().getIntExtra(EXTRA_ID,0);

        }
        //FragmentManager
        FragmentManager  mFragmentManager = getSupportFragmentManager();

        Fragment fragment = mFragmentManager.findFragmentById(R.id.supplier_profile_fragment_container);

        if(fragment==null){
            fragment = ProfileSupplierFragment.newInstance(mName,mEmail,mAddress,mDetail,mId);

            mFragmentManager.beginTransaction().add(R.id.supplier_profile_fragment_container,fragment).commit();
        }

    }
}

