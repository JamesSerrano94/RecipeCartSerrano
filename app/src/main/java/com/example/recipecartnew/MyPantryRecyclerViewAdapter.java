package com.example.recipecartnew;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recipecartnew.databinding.FragmentPantryBinding;

import java.util.ArrayList;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyPantryRecyclerViewAdapter extends RecyclerView.Adapter<MyPantryRecyclerViewAdapter.ViewHolder> {



    private final ArrayList<itemDescription> mValues;

    public MyPantryRecyclerViewAdapter(ArrayList<itemDescription> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentPantryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIngredient.setText(mValues.get(position).name);
        holder.mUnit.setText(String.valueOf(mValues.get(position).unit));
        holder.mAmount.setText(String.format("%.2f", mValues.get(position).amount));

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIngredient;
        public final TextView mAmount;
        public final TextView mUnit;
        public itemDescription mItem;

        public ViewHolder(FragmentPantryBinding binding) {
            super(binding.getRoot());
            mIngredient = binding.IngredientList;
            mAmount = binding.AmountList;
            mUnit = binding.unitList;

        }


    }
}