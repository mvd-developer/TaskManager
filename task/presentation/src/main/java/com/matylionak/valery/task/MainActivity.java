package com.matylionak.valery.task;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.matylionak.valery.task.base.BaseActivity;
import com.matylionak.valery.task.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        MainActivityViewModel viewModel = new MainActivityViewModel();
        this.viewModel = viewModel;
        viewModel.setActivity(this);
        ActivityMainBinding binding = DataBindingUtil.
                setContentView(this, R.layout.activity_main);
        binding.setViewModel(viewModel);
        viewModel.setBinding(binding);


        super.onCreate(savedInstanceState);
    }
}
