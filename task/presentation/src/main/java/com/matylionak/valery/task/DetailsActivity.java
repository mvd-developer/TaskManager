package com.matylionak.valery.task;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.matylionak.valery.task.base.BaseActivity;
import com.matylionak.valery.task.databinding.ActivityDetailsBinding;


public class DetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        DetailsViewModel viewModel = new DetailsViewModel();
        this.viewModel = viewModel;

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(CONSTANTS.TASK_ID)){
               int id = intent.getIntExtra(CONSTANTS.TASK_ID, 0);
                Toast.makeText(this, "TASK DETAILS", Toast.LENGTH_SHORT).show();
                viewModel.setId(id);
                viewModel.setEnableEditAndDelet(true);
            }
            else if (intent.hasExtra(CONSTANTS.EMPTY)){
                Toast.makeText(this, "CREATE NEW TASK", Toast.LENGTH_SHORT).show();
                viewModel.setEnabled(true);
            }

        }
        ActivityDetailsBinding binding = DataBindingUtil.
                setContentView(this, R.layout.activity_details);
        binding.setViewModel(viewModel);
        viewModel.setActivity(this);
        viewModel.setBinding(binding);

        super.onCreate(savedInstanceState);
    }
}
