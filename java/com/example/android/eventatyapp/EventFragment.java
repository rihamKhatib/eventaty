package com.example.android.eventatyapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class EventFragment extends Fragment
         {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NAME = "name";
    private static final String ARG_EMAIL = "email";
    private static final String ARG_DETAIL = "detail";

             private static final String ARG_USER_ID = "id";
    // TODO: Rename and change types of parameters

    private Lab mLab;
    private String mDetail;
    private String mEmail;
    private String mName;
    private int mId;

    private EventAdapter mEventAdapter;


    private RecyclerView mRecyclerView;
    private EventAdapter eventAdapter;
    public EventFragment() {
        // Required empty public constructor
    }


    public static EventFragment newInstance(
          //  String email,String detail ,String name,
            int userId) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_EMAIL, email);
//        args.putString(ARG_NAME, name);
//        args.putString(ARG_DETAIL, detail);
        args.putInt(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEmail = getArguments().getString(ARG_EMAIL);
            mName = getArguments().getString(ARG_NAME);
            mDetail = getArguments().getString(ARG_DETAIL);
            if(getArguments().getInt(ARG_USER_ID)>0){
                mId = getArguments().getInt(ARG_USER_ID);
            }
        }
        mLab =Lab.get(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(getArguments()!=null){
            mId = getArguments().getInt(ARG_USER_ID);
        }else{
            mId = 0;
        }
       View v = inflater.inflate(R.layout.fragment_event, container, false);
       mRecyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_events);
       mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       updateUI();
        return v;
    }
    private class EventHolder extends RecyclerView.ViewHolder{

        private TextView nameTextView,priceTextView;
        private ImageButton mViewImageButton ;
        private ImageButton mEditImageButton ;
        public EventHolder(View itemView) {
            super(itemView);
           nameTextView =(TextView)itemView.findViewById(R.id.name_event_item);
            mViewImageButton = (ImageButton)itemView.findViewById(R.id.view_event_item_button);
            mEditImageButton = (ImageButton)itemView.findViewById(R.id.edit_event_item_button);

            if(!mLab.getUser().getRole().equals("supplier")){
                mEditImageButton.setVisibility(View.GONE);
            }
        }

        public void bind(final Event e){
            nameTextView.setText(e.getName());
            mViewImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = ViewAddEventActivity.newIntent(getActivity(),"view",e.getName(),e.getDetail(),e.getEmail(),e.getId());
                    startActivity(i);
                }
            });
            mEditImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 Intent i  = ViewAddEventActivity.newIntent(getActivity(),"edit",e.getName(),e.getDetail(),e.getEmail(),e.getId());
                 startActivity(i);
                                  }
            });
        }
    }

    private class EventAdapter extends RecyclerView.Adapter<EventHolder>{

        private ArrayList<Event>mEvents;
        public EventAdapter(ArrayList<Event> events){
            mEvents = events;
        }
        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewTypte) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.event_item,parent,false);
            return new EventHolder(view);
        }

        @Override
        public void onBindViewHolder(EventHolder holder, int position) {
            holder.bind(mEvents.get(position));
        }


        @Override
        public int getItemCount() {
            return mEvents.size();
        }
        public void setmEvents(ArrayList<Event>events){
            mEvents= events;
        }
    }

    public void updateUI(){
        //  ArrayList<User> users = Lab.getLab(getActivity()).getDoctors();
        String url1;

        if(getArguments()!=null){
            url1 = mLab.routeurl+"mobile/myEyeEvents/"+getArguments().getInt(ARG_USER_ID);
        }else{
            url1 = mLab.routeurl+"mobile/myEyeEvents/"+mLab.getUser().getId();//+mId;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // ArrayList<Tasks>taskses = new ArrayList<>();
                            ArrayList<User> users = new ArrayList<>();
                            ArrayList<Event> myEvent =new ArrayList<>();
                            String s = response.toString();
                            JSONArray jsonArray = new JSONArray(response);

                            String str = response.toString();
                            str.toString();
                            // Toast.makeText(getActivity(),"success",Toast.LENGTH_SHORT).show();

                            int count =0;
                            while(count< jsonArray.length()){
                                JSONObject jsonObject = jsonArray.getJSONObject(count);
                                String name = jsonObject.getString("name");
                                String detail = jsonObject.getString("detail");
                                String email = jsonObject.getString("email");
                                int id = jsonObject.getInt("id");



                                Event event = new Event();
                                event.setName(name);
                                event.setDetail(detail);
                                event.setEmail(email);
                                event.setId(id);

                                myEvent.add(event);
                                count++;
                            }


                            if (mEventAdapter == null) {
                                mEventAdapter = new EventAdapter(myEvent);

                            } else {
                                mEventAdapter.setmEvents(myEvent);
                                mEventAdapter.notifyDataSetChanged();
                            }
                            mRecyclerView.setAdapter(mEventAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Erorr:",e.toString());
                            // Toast.makeText(getActivity(),"fail",Toast.LENGTH_SHORT).show();
                        }





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


//        Lab lab = Lab.get(getActivity());
//        if(eventAdapter==null){
//            eventAdapter = new EventAdapter(lab.getEvents());
//        }else{
//            eventAdapter.setmEvents(lab.getEvents());
//            eventAdapter.notifyDataSetChanged();
//        }
//        mRecyclerView.setAdapter(eventAdapter);
    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        updateUI();
//    }
}
