package com.example.android.eventatyapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {


    private static final String EXTRA_Location = "location" ;
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter mCategoryAdapter;
    LinearLayout linearLayout,linearLayout2;
    private  String mLocation = null;
    public static Intent newIntent(Context context, String location){
        Intent i = new Intent(context,CategoryActivity.class);
        i.putExtra(EXTRA_Location,location);
        return i;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        if(getIntent().getStringExtra(EXTRA_Location)!=null){
            mLocation = getIntent().getStringExtra(EXTRA_Location);
        }
        categoryRecyclerView = (RecyclerView)findViewById(R.id.category_recycler_view);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

         linearLayout = (LinearLayout)findViewById(R.id.category_fragment_container);
        linearLayout.setVisibility(View.INVISIBLE);

        linearLayout2= (LinearLayout)findViewById(R.id.for_recycler);

        updateUI();
    }

    private class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mNameTextView;
        private ImageView mImageView;
        private String categoryName ;
        public CategoryHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView)itemView.findViewById(R.id.name_category_item);
            mImageView = (ImageView)itemView.findViewById(R.id.image_category_iem);
            itemView.setOnClickListener(this);
        }

        public void bind(Category category){
            mNameTextView.setText(category.getName());
            mImageView.setImageResource(category.getImage());
            categoryName = category.getName();
        }

        @Override
        public void onClick(View view) {
            FragmentManager fragmentManager= getSupportFragmentManager();
            linearLayout.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.GONE);
            Fragment fragment = SupplierFragment.newInstance(categoryName,mLocation);
            fragmentManager.beginTransaction().add(R.id.category_fragment_container,fragment).commit();
        }
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder>{

        private ArrayList<Category> mCategories;
        public CategoryAdapter(ArrayList<Category> categories) {
            mCategories = categories;
        }

        @Override
        public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(CategoryActivity.this);
            View view = inflater.inflate(R.layout.category_item,parent,false);
            return new CategoryHolder(view);
        }

        @Override
        public void onBindViewHolder(CategoryHolder holder, int position) {
            holder.bind(mCategories.get(position));
        }

        @Override
        public int getItemCount() {
            return mCategories.size();
        }
        public void setmCategories(ArrayList<Category> categories){
            mCategories = categories;
        }
    }

    private void updateUI(){
        if(mCategoryAdapter==null){
            mCategoryAdapter = new CategoryAdapter(Lab.get(CategoryActivity.this).getCategories());

        }else {
            mCategoryAdapter.setmCategories(Lab.get(CategoryActivity.this).getCategories());
            mCategoryAdapter.notifyDataSetChanged();
        }
        categoryRecyclerView.setAdapter(mCategoryAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }
}
