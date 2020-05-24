package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_COUNTS = "com.example.restaurantapp.example.counts";
    public static final String EXTRA_FOODNAMES = "com.example.restaurantapp.example.foodnames";
    public static final String EXTRA_PRICES = "com.example.restaurantapp.example.prices";
    public static final String EXTRA_IMAGEIDS = "com.example.restaurantapp.example.imageids";
    private static final String TAG = "main_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.list);

        TextView cartCountView = findViewById(R.id.textView);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        int cartCount = 0;
        cartCountView.setText("" + cartCount);
        CustomAdaper adapter = new CustomAdaper(this, cartCountView, cartCount);

        listView.setAdapter(adapter);

        final ArrayList<Integer> mCount = adapter.getMCount();
        final ArrayList<String> mFoodNames = adapter.getMFoodNames();
        final ArrayList mPrices = adapter.getMPrices();
        final ArrayList mImageIds = adapter.getMImageIds();
        final ArrayList<String> mStringPrices = new ArrayList();

        for(int i = 0; i < mPrices.size(); i++){
             mStringPrices.add(String.valueOf(mPrices.get(i)));
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "" + mFoodNames);
                Log.d(TAG, "" + mCount);
                Log.d(TAG, "" + mPrices);
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                intent.putExtra("value2", 200);
                intent.putStringArrayListExtra(EXTRA_FOODNAMES,  mFoodNames);
                intent.putStringArrayListExtra(EXTRA_PRICES, mStringPrices);
                intent.putIntegerArrayListExtra(EXTRA_COUNTS, mCount);
                intent.putIntegerArrayListExtra(EXTRA_IMAGEIDS, mImageIds);
                startActivity(intent);
            }
        });
    }
    
    private class CustomAdaper extends BaseAdapter {

        private final Context mContext;
        private TextView cartCountView;
        private int cartCount;
        private ArrayList<String> mFoodNames = new ArrayList();
        private ArrayList<Integer> mCount = new ArrayList();
        private ArrayList<Double> mPrices = new ArrayList();
        private ArrayList<Integer> mImageId  = new ArrayList();
        private static final String TAG = "MyActivity";

        CustomAdaper(Context mContext, TextView cartCountView, int cartCount) {
            this.cartCountView = cartCountView;
            this.cartCount = cartCount;
            this.mContext = mContext;
            createFoodItem("Cheese Burger", 8.25, R.drawable.cheeseburger);
            createFoodItem("Cheese Pizza", 17.99, R.drawable.cheesepizza);
            createFoodItem("Chicken Burrito", 5.25, R.drawable.chickenburrito);
            createFoodItem("Coffee", 1.75, R.drawable.coffee);
            createFoodItem("Fettuccine", 4.35, R.drawable.fettuccine);
            createFoodItem("Fries with sauce", 2.5, R.drawable.frenchfrieswithsauce);
            createFoodItem("Hamburger", 5,R.drawable.hamburger);
            createFoodItem("Stir Fry", 12.85, R.drawable.stirfry);
            createFoodItem("Macaroni", 8.85, R.drawable.macaroni);
            createFoodItem("Pancakes", 3.5, R.drawable.pancakes);
            createFoodItem("Plain Fries", 2, R.drawable.plainfries);
            createFoodItem("Salad", 4, R.drawable.salad);
        }

        @Override
        public int getCount() {
            return mFoodNames.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public ArrayList<String> getMFoodNames() {
            return mFoodNames;
        }

        public ArrayList<Integer> getMCount() {
            return mCount;
        }

        public ArrayList<Double> getMPrices() {
            return mPrices;
        }

        public ArrayList<Integer> getMImageIds() {
            return mImageId;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View rowMain = layoutInflater.inflate(R.layout.row_main, parent, false);
            TextView foodName =  rowMain.findViewById(R.id.foodName);
            TextView price =  rowMain.findViewById(R.id.price);
            CircleImageView image = rowMain.findViewById(R.id.circleImageView);
            final TextView count = rowMain.findViewById(R.id.count);
            Button addButton = rowMain.findViewById(R.id.add);
            Button removeButton = rowMain.findViewById(R.id.remove);

            addButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int num = mCount.get(position);
                    num++;
                    cartCount++;
                    mCount.set(position, num);
                    count.setText(mCount.get(position).toString());
                    cartCountView.setText("" + cartCount);
                }
            });

           removeButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int num = mCount.get(position);
                    if(num > 0) {
                        num--;
                        cartCount--;
                        mCount.set(position, num);
                        count.setText(mCount.get(position).toString());
                        cartCountView.setText("" + cartCount);
                    }
                }
            });

            image.setImageResource(mImageId.get(position));
            foodName.setText(mFoodNames.get(position));
            price.setText("$" + mPrices.get(position).toString());
            count.setText(mCount.get(position).toString());
            return rowMain;
        }

        private void createFoodItem(String name, double price,int imageResource) {
            mFoodNames.add(name);
            mCount.add(0);
            mPrices.add(price);
            mImageId.add(imageResource);
        }

    }
    





}
