package com.example.android.eventatyapp;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by rihamkhatib on 3/16/2018.
 */

public class Lab {

    private ArrayList<Category> mCategories;
    private ArrayList<Location> mLocations;
    private User mUser;
//public String routeurl="http://192.168.0.24/laravel/public/";

public String routeurl ="http://192.168.1.37:8000/";

    private ArrayList<User> mUsers;
    private int[] categoriesImage = {R.drawable.balloons,R.drawable.acc,R.drawable.photography,R.drawable.dj,R.drawable.florist,R.drawable.carrent};
    private String categoryNames[]={"Balloons","Accessories","photography","DJ and Music","Florist","Car Renting"};
    private int[] locationsImage = {R.drawable.bethlehem,R.drawable.hebron,R.drawable.nablus,R.drawable.jerusalem,R.drawable.ramallah};
    private String locationNames[]={"Bethlehem","Hebron","Nablus","Jerusalem","Ramallah"};

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    private static Lab sLab ;
    private ArrayList<Event> events ;
    private Lab(Context context){

        init();
        initLoc();
        initUser();
//        events = new ArrayList<Event>();
//        for (int i = 0; i <50; i++) {
//            Event event = new Event(i,"name","url");
//            events.add(event);
//        }
    }

    public static Lab get(Context context){

        if(sLab==null){
            sLab = new Lab(context);

        }
        return sLab;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
    private void init(){
        mCategories = new ArrayList<>();
        for (int i = 0; i < categoriesImage.length; i++) {
            Category c = new Category(categoryNames[i],i,categoriesImage[i]);
            mCategories.add(c);
        }

    }
    private void initLoc(){
        mLocations = new ArrayList<>();
        for (int i = 0; i < locationsImage.length; i++) {
            Location l = new Location(locationNames[i],i,locationsImage[i]);
            mLocations.add(l);
        }

    }


    public ArrayList<Category> getCategories() {
        return mCategories;
    }
    public ArrayList<Location> getmLocations() {
        return mLocations;
    }


    private void initUser(){
        mUsers = new ArrayList<>();
        String types[]={"customer","suppliers"};
        for (int i = 0; i < 100; i++) {
            String type="";
            if(1%2==0)
                type =types[0];
            else
                type =types[1];

            User u= new User("User Name "+i,"email@gmail.com",type,categoryNames[i%categoryNames.length],i);

            mUsers.add(u);
        }

    }

    public ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i <mUsers.size() ; i++) {
            if(mUsers.get(i).getRole().equals("suppliers"))
                users.add(mUsers.get(i));
        }
        return users;
    }
}
