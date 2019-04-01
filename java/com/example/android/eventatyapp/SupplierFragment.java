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
import java.util.function.Supplier;


public class SupplierFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CATEGORY = "category";
    private static final String ARG_LOCATION = "location";
    private SupplierAdapter mSupplierAdaptr;
    private RecyclerView mRecyclerView;
    // TODO: Rename and change types of parameters
    private String mCategory;
    private String mLocation;
    private Lab mLab;
//    User user_id;



    public SupplierFragment() {
        // Required empty public constructor
    }


    public static SupplierFragment newInstance(String category, String location) {
        SupplierFragment fragment = new SupplierFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        args.putString(ARG_LOCATION, location);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategory = getArguments().getString(ARG_CATEGORY);
            if(getArguments().getString(ARG_LOCATION)!=null)
            mLocation = getArguments().getString(ARG_LOCATION);
        }
        mLab = Lab.get(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v=inflater.inflate(R.layout.fragment_supplier, container, false);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_supplier);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateUI();

        return v;
    }

    private class SupplierHolder extends RecyclerView.ViewHolder{

        private TextView nameTextView,emailTextView;
        private ImageButton mViewImageButton,mViewStarButton;
        public SupplierHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView)itemView.findViewById(R.id.supplier_name_text_view);
            emailTextView = (TextView)itemView.findViewById(R.id.email_supplier_text_view);
            mViewImageButton = (ImageButton) itemView.findViewById(R.id.view_supplier_item_button);
            mViewStarButton= (ImageButton) itemView.findViewById(R.id.star_supplier_item_button);
        }

        public void bind(final User u){
            nameTextView.setText(u.getName());
            emailTextView.setText(String.valueOf(u.getEmail()));

            mViewImageButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent i = SupplierProfileActivity.newIntent(getActivity(),u.getAddress(),u.getName(),u.getWorkDetail(),u.getEmail(),u.getId());
                    startActivity(i);
                }
            });
            mViewStarButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
//String urlStar;
                    mViewStarButton.setImageResource(R.drawable.ic_star_yellow);
//                    urlStar = mLab.routeurl + "mobile/favoriteSet/" + user_id +"/"+
//                    mobile/favoriteSet/{user_id}/{supplier_id}

                }
            });
        }
    }
    private class SupplierAdapter extends RecyclerView.Adapter<SupplierHolder>{

        private ArrayList<User> mUsers;

        public SupplierAdapter(ArrayList<User> mUsers) {
            this.mUsers = mUsers;
        }

        @Override
        public SupplierHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.supplier_item,parent,false);
            return new SupplierHolder(view);
        }


        @Override
        public void onBindViewHolder(SupplierHolder holder, int position) {
            holder.bind(mUsers.get(position));
        }


        @Override
        public int getItemCount() {
            return mUsers.size();
        }
        public void setmEvents(ArrayList<User>users){
            mUsers= users;
        }
    }

    public void updateUI(){

        String url ;
        if(mLocation==null) {
            url = mLab.routeurl + "mobile/supplierCategory/" + mCategory;
        }else{
            url = mLab.routeurl + "mobile/suppliercategorylocation/" + mCategory+"/"+mLocation;
        }


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // ArrayList<Tasks>taskses = new ArrayList<>();
                            ArrayList<User> users = new ArrayList<>();
                         //   ArrayList<Event> myEvent =new ArrayList<>();
                            String s = response.toString();
                            JSONArray jsonArray = new JSONArray(response);

                            String str = response.toString();
                            str.toString();
                            // Toast.makeText(getActivity(),"success",Toast.LENGTH_SHORT).show();

                            int count =0;
                            while(count< jsonArray.length()){

                                //{"name":"d","email":"d","id":6,"work_field":"d","company_name":"d",
                                // "phone":"3","work_detail":"d","location":"Bethlehem",
                                // "user_id":25,"category":"photography"},
                                JSONObject jsonObject = jsonArray.getJSONObject(count);
                                String name = jsonObject.getString("name");
                                String detail = jsonObject.getString("work_detail");
                                String email = jsonObject.getString("email");

                                String phone = jsonObject.getString("phone");
                                String category = jsonObject.getString("category");
                                String address = jsonObject.getString("address");
                                String companyName = jsonObject.getString("company_name");
                                int id = jsonObject.getInt("user_id");



                                User user = new User();
                                user.setName(name);
                                user.setWorkDetail(detail);
                                user.setEmail(email);

                                user.setPhone(phone);
                                user.setCategory(category);
                                user.setCompanyName(companyName);
                                user.setId(id);
                                user.setAddress(address);
                                users.add(user);
                                count++;
                            }

                            Lab lab = Lab.get(getActivity());
                        if(mSupplierAdaptr==null){
                            mSupplierAdaptr = new SupplierAdapter(users);
                        }else{
                            mSupplierAdaptr.setmEvents(users);
                            mSupplierAdaptr.notifyDataSetChanged();
                        }
                        mRecyclerView.setAdapter(mSupplierAdaptr);


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



    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }



}
