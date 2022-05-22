package com.example.newsapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder> {
    private final ArrayList<com.example.newsapplication.CategoryRVModal> CategoryRVModal;
    private ArrayList<CategoryRVModal>CategoryRVModals;
    private Context context;
    private CategoryClickinterface categoryClickinterface;
    public CategoryRVAdapter(ArrayList<CategoryRVModal> categoryRVModals, Context context,CategoryClickinterface categoryClickinterface) {
        this.CategoryRVModal = categoryRVModals;
        this.context = context;
        this.categoryClickinterface = categoryClickinterface;

    }


    @NonNull
    @Override
    public CategoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        CategoryRVModal categoryRVMOdal = (com.example.newsapplication.CategoryRVModal) CategoryRVModals.get(position);
        CategoryRVModal categoryRVModal = null;
        holder.categoryTV.setText(categoryRVModal.getCategory());
        Picasso.get().load(categoryRVMOdal.getCategoryImageUrl()).into(holder.categoryIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickinterface.onCategoryClick(position);
            }
        });
        



    }

    @Override
    public int getItemCount() {
        return CategoryRVModals.size();
    }
    public interface CategoryClickinterface{
        void onCategoryClick(int position);


    }
    public class ViewHolder extends RecyclerView.ViewHolder{
       private TextView categoryTV;
       private ImageView categoryIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTV = itemView.findViewById(R.id.idTVCategory);
            categoryIV = itemView.findViewById(R.id.idIVCategory);
            }
    }
}
