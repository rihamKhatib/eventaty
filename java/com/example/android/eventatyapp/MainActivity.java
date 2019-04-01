package com.example.android.eventatyapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String EXTRA_ID = "id";
    private static final String EXTRA_PAGE = "page";
    private Lab mLab;
    private TextView mUserNameTextView,mUserEmailTextView;
    private FragmentManager fragmentManager;
    private FloatingActionButton floatingActionButton;
    private int userIdFromFragment ;

    public static Intent newIntent(Context context, String page, int id){
        Intent i = new Intent(context,MainActivity.class);
        i.putExtra(EXTRA_ID,id);
        i.putExtra(EXTRA_PAGE,page);

        return i;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mLab = Lab.get(this);
       fragmentManager = getSupportFragmentManager();
       Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);


       if(fragment==null){

           fragment = new EventFragment();
           fragmentManager.beginTransaction().add(R.id.fragment_container,fragment).commit();
       }

        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = ViewAddEventActivity.newIntent(MainActivity.this,"add",null,null,null,-1);

                startActivity(i);
            }
        });
        if(getIntent().getStringExtra(EXTRA_PAGE)!=null){
            floatingActionButton.setVisibility(View.GONE);
            fragment = EventFragment.newInstance(getIntent().getIntExtra(EXTRA_ID,-1));
            fragmentManager.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
        if(mLab.getUser().getRole().equals("supplier"))
            floatingActionButton.setVisibility(View.VISIBLE);
        else
            floatingActionButton.setVisibility(View.GONE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        mUserNameTextView = (TextView)hView.findViewById(R.id.user_name_nav);
        mUserNameTextView.setText(mLab.getUser().getName());

        mUserEmailTextView = (TextView)hView.findViewById(R.id.textView_user_email);
        mUserEmailTextView.setText(mLab.getUser().getEmail());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            mLab.setUser(null);
            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
//            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (id == R.id.nav_event) {
            if(mLab.getUser().getRole().equals("supplier"))
                floatingActionButton.setVisibility(View.VISIBLE);
            else
                floatingActionButton.setVisibility(View.GONE);
           fragment = new EventFragment();
           fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit();

        }
//        else if (id == R.id.nav_edit) {
//            fragment = new EditFragment();
//            fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit();
//        }
        else if (id == R.id.nav_home) {
            Intent i = new Intent(MainActivity.this,HomeActivity.class);
            floatingActionButton.setVisibility(View.GONE);
            startActivity(i);

        }
        else if (id == R.id.nav_fav) {
            floatingActionButton.setVisibility(View.GONE);
            fragment = new FavouriteFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit();
   } else if (id == R.id.nav_change_password) {
            floatingActionButton.setVisibility(View.GONE);
            fragment = new ChangePasswordFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit();
     }
//     else if (id == R.id.nav_share) {
//            fragment = new ShareFragment();
//            fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit();
//     }

     else if (id == R.id.nav_category) {
            floatingActionButton.setVisibility(View.GONE);
//            fragment = new ShareFragment();
//            fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit();
            Intent i = new Intent(MainActivity.this,CategoryActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
