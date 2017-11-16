package com.matylionak.valery.task;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matylionak.valery.data.Entity.Task;
import com.matylionak.valery.task.databinding.ItemBinding;

import java.util.ArrayList;
import java.util.List;


public class MyAdaptor extends RecyclerView.Adapter<MyHolder> {
    private List<Task> list = new ArrayList<>();
    private Activity activity;

    public void setList(List<Task> arrayList) {
        list.clear();
        list.addAll(arrayList);
        notifyDataSetChanged();
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemBinding binding = ItemBinding.inflate(inflater, parent, false);
        return new MyHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        ItemViewModel viewModel = new ItemViewModel();
        viewModel.setTime(list.get(position).getEndTime());
        viewModel.setDate(list.get(position).getEndDate());
        viewModel.setProgress(list.get(position).getProgress());
        viewModel.setTitle(list.get(position).getTitle());
        viewModel.setStatus(list.get(position).getState());
        holder.binding.setViewModel(viewModel);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position) {
                Intent intent = new Intent(activity, DetailsActivity.class);
              intent.putExtra(CONSTANTS.TASK_ID, list.get(position).getID());
                activity.startActivity(intent);//описать код

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setActivity(Activity activity) {
        this.activity = activity;
    }


}
