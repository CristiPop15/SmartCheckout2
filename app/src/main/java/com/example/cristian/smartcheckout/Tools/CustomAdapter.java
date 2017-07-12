package com.example.cristian.smartcheckout.Tools;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cristian.smartcheckout.R;

import java.util.List;

/**
 * Created by Cristian on 7/12/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    private Context mContext;
    private List<Row> product_list;
    private TextView total;
    private TextView total_nr_products;
    private Button add_button;
    private Button substract_button;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price, quantity;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            price = (TextView) view.findViewById(R.id.price);
            quantity = (TextView)view.findViewById(R.id.quantity);
            add_button = (Button)view.findViewById(R.id.add_button);
            substract_button = (Button)view.findViewById(R.id.substract_button);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(mContext, "Product deleted - "+ShoppingCart.getCart().getProduct(getAdapterPosition()),Toast.LENGTH_SHORT).show();
                    ShoppingCart.getCart().removeProduct(getAdapterPosition());
                    total.setText(String.valueOf(ShoppingCart.getCart().getTotal()));
                    total_nr_products.setText(String.valueOf(ShoppingCart.getCart().getTotalNumberOfProducts()));
                    notifyDataSetChanged();
                    return false;
                }
            });

            add_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShoppingCart.getCart().addProduct(product_list.get(getAdapterPosition()).product);
                    total.setText(String.valueOf(ShoppingCart.getCart().getTotal()));
                    total_nr_products.setText(String.valueOf(ShoppingCart.getCart().getTotalNumberOfProducts()));
                    notifyDataSetChanged();
                }
            });

            substract_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShoppingCart.getCart().removeOneProduct(product_list.get(getAdapterPosition()).product);
                    total.setText(String.valueOf(ShoppingCart.getCart().getTotal()));
                    total_nr_products.setText(String.valueOf(ShoppingCart.getCart().getTotalNumberOfProducts()));
                    notifyDataSetChanged();

                }
            });

        }
    }

    public CustomAdapter(Context mContext, List<Row> product_list, TextView total, TextView total_nr_products) {
        this.mContext = mContext;
        this.product_list = product_list;
        this.total = total;
        this.total_nr_products = total_nr_products;
    }

    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.MyViewHolder holder, int position) {
        Row p = product_list.get(position);
        holder.name.setText(p.product.productName);
        holder.price.setText(String.valueOf(p.product.price));
        holder.quantity.setText(String.valueOf(p.getNumOfItems()));
    }


    @Override
    public int getItemCount() {
        return product_list.size();
    }
}
