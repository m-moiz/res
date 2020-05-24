package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartActivity extends AppCompatActivity {
    private final String TAG = "cart_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent intent = getIntent();
        ArrayList<String> foodNames = intent.getStringArrayListExtra(MainActivity.EXTRA_FOODNAMES);
        ArrayList<Integer> counts = intent.getIntegerArrayListExtra(MainActivity.EXTRA_COUNTS);
        ArrayList<Integer> imageIds =  intent.getIntegerArrayListExtra(MainActivity.EXTRA_IMAGEIDS);
        ArrayList<String> prices = intent.getStringArrayListExtra(MainActivity.EXTRA_PRICES);

        Log.d(TAG, "" + foodNames);
        Log.d(TAG, "" + counts);
        Log.d(TAG, "" + prices);

        ArrayList accumulatedPrices = calcAccumulatedPrices(counts, prices);

        ArrayList cartFoodNames = new ArrayList();
        ArrayList cartFoodCounts = new ArrayList();
        ArrayList cartFoodPrices = new ArrayList();
        ArrayList cartImageIds = new ArrayList();

        for(int i = 0; i < counts.size(); i++) {
            if(counts.get(i) > 0) {
                cartFoodNames.add(foodNames.get(i));
                cartFoodPrices.add(prices.get(i));
                cartFoodCounts.add(counts.get(i));
                cartImageIds.add(imageIds.get(i));
            }
        }

        ListView cartList = findViewById(R.id.total_list);
        double totalPrice = calcTotal(accumulatedPrices);
        TextView total = findViewById(R.id.textView3);
        total.setText("Total: $" + totalPrice);

        CustomAdapter adapter = new CustomAdapter(this,cartFoodNames, cartFoodCounts, cartFoodPrices, cartImageIds, accumulatedPrices);
        cartList.setAdapter(adapter);

    }


    private ArrayList calcAccumulatedPrices(ArrayList<Integer> counts, ArrayList<String> prices) {
        ArrayList accumulatedPrices = new ArrayList();
        for(int i = 0; i < counts.size(); i++) {
            if(counts.get(i) > 0) {
                Integer count = counts.get(i);
                Double price = Double.parseDouble(prices.get(i));
                accumulatedPrices.add(String.format("%.2f", count * price));
            }
        }

        return accumulatedPrices;
    }

    private double calcTotal(ArrayList<String> accumulatedPrices) {
        int total = 0;
        for (int i = 0; i < accumulatedPrices.size(); i++){
            total += Double.parseDouble(accumulatedPrices.get(i));
        }

        return total;
    }


    private class CustomAdapter extends BaseAdapter {

        private final Context mContext;
        private ArrayList foodNames;
        private ArrayList foodCounts;
        private ArrayList accumulatedPrices;
        private ArrayList foodPrices;
        private ArrayList imageIds;

        CustomAdapter(Context mContext, ArrayList foodNames, ArrayList foodCounts, ArrayList foodPrices,ArrayList imageIds,  ArrayList accumulatedPrices) {
            this.mContext = mContext;
            this.foodCounts = foodCounts;
            this.foodNames = foodNames;
            this.foodPrices = foodPrices;
            this.imageIds = imageIds;
            this.accumulatedPrices = accumulatedPrices;
        }

        @Override
        public int getCount() {
            return foodNames.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View rowCart = layoutInflater.inflate(R.layout.activity_row_cart, parent, false);
            TextView cartFoodName = rowCart.findViewById(R.id.cartFoodName);
            TextView cartPrice = rowCart.findViewById(R.id.cartPrice);
            TextView cartCount = rowCart.findViewById(R.id.cartCount);
            TextView accPrice = rowCart.findViewById(R.id.accPrice);
            CircleImageView circleImageView = rowCart.findViewById(R.id.circleImageView2);

            cartFoodName.setText(foodNames.get(position).toString());
            cartPrice.setText("$" + foodPrices.get(position).toString());
            cartCount.setText("x" + foodCounts.get(position).toString());
            accPrice.setText("$" + accumulatedPrices.get(position).toString());
            circleImageView.setImageResource((int) imageIds.get(position));

            return rowCart;
        }
    }

}
