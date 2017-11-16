package com.matylionak.valery.task;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.matylionak.valery.task.databinding.ItemBinding;


public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ItemBinding binding;
    private ItemClickListener itemClickListener;

    public MyHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.OnClick(view, getAdapterPosition());
    }

    public void setBinding(ItemBinding binding) {
        this.binding = binding;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
