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

    private final List<recipeDescription> mValues;
    private OnNoteListener monNoteListener;

    //Accepts Interface
    public void acceptsInterfaceObject(OnNoteListener obj){
        monNoteListener = obj;
    }

    //OnClick
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            System.out.println(5);
        }
    };

    //Constructor
    public MyRecipeRecyclerViewAdapter(List<recipeDescription> items) {
        mValues = items;
        this.acceptsInterfaceObject(new OnNoteListener() {
            @Override
            public void onNoteClick(int position) {
                //System.out.println("J");
                //need to figure this out
            }
        });

    }
    //Constructor
    public MyRecipeRecyclerViewAdapter(List<recipeDescription> items, OnNoteListener onNoteListener) {
        mValues = items;
        this.monNoteListener = onNoteListener;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentRecipeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), monNoteListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTitle.setText(mValues.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView mTitle;

        public ViewHolder(FragmentRecipeBinding binding, OnNoteListener onNoteListener) {
            super(binding.getRoot());
            mTitle = binding.RecipeList;
            monNoteListener = onNoteListener;
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {
            monNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}