package com.example.recipecartnew;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recipecartnew.databinding.FragmentSearchBinding;

import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> {

    private final List<recipeDescription> mValues;
    private OnNoteListener monNoteListener;

    public void acceptsInterfaceObject(OnNoteListener obj){
        monNoteListener = obj;
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            System.out.println(5);
        }
    };

    public SearchRecyclerViewAdapter(List<recipeDescription> items) {
        mValues = items;
        this.acceptsInterfaceObject(new OnNoteListener() {
            @Override
            public void onNoteClick(int position) {
                //System.out.println("J");
                //need to figure this out
            }
        });



    }

    public SearchRecyclerViewAdapter(List<recipeDescription> items, OnNoteListener onNoteListener) {
        mValues = items;
        this.monNoteListener = onNoteListener;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), monNoteListener);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitle.setText(mValues.get(position).getTitle());
        holder.mImage.setImageResource(mValues.get(position).getImageName());
       // holder.mImage.setImageDrawable(draw);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public final TextView mTitle;
        public final ImageView mImage;
        //private final OnNoteListener onNoteListener;
        public recipeDescription mItem;
        //OnNoteListener onNoteListener;

        public ViewHolder(FragmentSearchBinding binding, OnNoteListener onNoteListener) {
            super(binding.getRoot());
            mTitle = binding.RecipeList2;
            mImage = binding.imageView4;

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