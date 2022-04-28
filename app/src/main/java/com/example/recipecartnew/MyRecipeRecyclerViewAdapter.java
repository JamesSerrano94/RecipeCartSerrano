package com.example.recipecartnew;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recipecartnew.databinding.FragmentRecipeBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyRecipeRecyclerViewAdapter extends RecyclerView.Adapter<MyRecipeRecyclerViewAdapter.ViewHolder> {


    private final ArrayList<itemDescription> mValues;

    public MyRecipeRecyclerViewAdapter(ArrayList<itemDescription> items) {
        mValues = items;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentRecipeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
//        holder.mIngredient.setText(mValues.get(position).name);
//        holder.mAmount.setText(String.valueOf(mValues.get(position).amount));

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final TextView mIngredient;
//        public final TextView mAmount;
        public itemDescription mItem;

        public ViewHolder(FragmentRecipeBinding binding) {
            super(binding.getRoot());
//            mIngredient = binding.IngredientList;
//            mAmount = binding.AmountList;
        }


    }
}