package com.example.android.eventatyapp;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ViewAddEventActivity extends AppCompatActivity {


    private static final String EXTRA_PAGE="com.example.android.eventatyapp.page";
    private static final String EXTRA_EMAIL="email";
    private static final String EXTRA_NAME = "name";
    private static final String EXTRA_DETAIL="detail";
    private static final String EXTRA_ID="id";

     private String mPage = "view";
    private String mEmail ;
    private String mDetail ;
    private String mName ;
    private int mId ;

    public static Intent newIntent(Context context,String page,String name,String detail,String email,int id){
        Intent i = new Intent(context,ViewAddEventActivity.class);
        i.putExtra(EXTRA_PAGE,page);
        i.putExtra(EXTRA_NAME,name);
        i.putExtra(EXTRA_DETAIL,detail);
        i.putExtra(EXTRA_EMAIL,email);
        i.putExtra(EXTRA_ID,id);


        return i;


    }
//    private FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_add);

        if(getIntent().getStringExtra(EXTRA_PAGE)!=null){
            mPage =getIntent().getStringExtra(EXTRA_PAGE);//.toString
            if(getIntent().getStringExtra(EXTRA_DETAIL)!=null) {
                mDetail = getIntent().getStringExtra(EXTRA_DETAIL);
                mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
                mName = getIntent().getStringExtra(EXTRA_NAME);
                mId = getIntent().getIntExtra(EXTRA_ID,0);

            }
        }
//FragmentManager
        FragmentManager  mFragmentManager = getSupportFragmentManager();
        Fragment fragment = mFragmentManager.findFragmentById(R.id.view_edit_fragment_container);

        if(mPage.equals("view")) {
            if (fragment == null) {
                fragment = ViewEventFragment.newInstance(mName,mDetail,mEmail);
                mFragmentManager.beginTransaction().add(R.id.view_edit_fragment_container, fragment).commit();

            }
        }else if(mPage.equals("add")){
            if (fragment == null) {
                fragment = new AddEditEventFragment();
                mFragmentManager.beginTransaction().add(R.id.view_edit_fragment_container, fragment).commit();

            }
        }
        else if(mPage.equals("edit")){
            if (fragment == null) {
               fragment =  AddEditEventFragment.newInstance(mName,mDetail,mPage,mId);
                mFragmentManager.beginTransaction().add(R.id.view_edit_fragment_container, fragment).commit();

            }
        }

    }
}
