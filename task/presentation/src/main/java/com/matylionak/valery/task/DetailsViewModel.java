package com.matylionak.valery.task;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.matylionak.valery.data.Entity.Task;
import com.matylionak.valery.domain.Package;
import com.matylionak.valery.domain.UseCaseDeleteAsync;
import com.matylionak.valery.domain.UseCaseGetByIdAsync;
import com.matylionak.valery.domain.UseCaseInsertTaskAsync;
import com.matylionak.valery.domain.UseCaseUpdateTaskAsync;
import com.matylionak.valery.task.base.BaseViewModel;
import com.matylionak.valery.task.databinding.ActivityDetailsBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DetailsViewModel implements BaseViewModel {
    private int id;
    private ObservableField<String> name = new ObservableField<>("Wash a cat");
    private ObservableField<String> description = new ObservableField<>("Here goes the description of your task");
    private ObservableField<Integer> progress = new ObservableField<>(0);

    private ObservableField<String> setStartTime = new ObservableField<>("SET TIME");
    private ObservableField<String> setStartDate = new ObservableField<>("SET DATE");

    private ObservableField<String> setEndTime = new ObservableField<>("SET TIME");
    private ObservableField<String> setEndDate = new ObservableField<>("SET DATE");
    private ObservableField<Integer> state = new ObservableField<>(0);
    private ObservableField<Integer> estimatedTime = new ObservableField<>(0);


    private ObservableField<String> seekBarValue = new ObservableField<>("0");
    private ObservableField<Boolean> enabled = new ObservableField<>(false);
    private ObservableField<Boolean> enableEditAndDelet = new ObservableField<>(false);
    private int count;
    private Activity activity;
    private ActivityDetailsBinding binding;

    @Override
    public void init() {
        setSpinner();

        if (id != 0) {
            Task task = new Task();
            UseCaseGetByIdAsync getByIdAsync = new UseCaseGetByIdAsync();
            try {
                task = getByIdAsync.execute(new Package(activity, id, null)).get();
            } catch (Exception w) {
                Log.e("TAG", "interapt.exeption" + w.getMessage());
            }

            name.set(task.getTitle());
            description.set(task.getDescription());
            progress.set(task.getProgress());
            seekBarValue.set(task.getProgress() + " %");
            setStartTime.set(task.getStartTime());
            setStartDate.set(task.getStartDate());
            setEndTime.set(task.getEndTime());
            setEndDate.set(task.getEndDate());
            binding.spinner.setSelection(task.getState());
            estimatedTime.set(task.getEstimated());
            count = task.getEstimated();
        }


    }

    @Override
    public void release() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }


    private void setSpinner() {
        String[] data = {STATUS.NEW_TASK, STATUS.IN_PROGRESS, STATUS.DONE};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, R.layout.support_simple_spinner_dropdown_item, data);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setSelection(0);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state.set(i);
                binding.spinner.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    //сохраняем при нажатии, проверяем правильное заполнение полей
    public void save() {
        if (checkFields()) {
            Task task = new Task();
            task.setTitle(name.get());
            task.setDescription(description.get());
            task.setProgress(progress.get());
            task.setStartTime(setStartTime.get());
            task.setStartDate(setStartDate.get());
            task.setEndTime(setEndTime.get());
            task.setEndDate(setEndDate.get());
            task.setState(state.get());
            task.setEstimated(estimatedTime.get());
            if (id == 0) {
                UseCaseInsertTaskAsync useCaseInsertTask = new UseCaseInsertTaskAsync();
                useCaseInsertTask.execute(new Package(activity, task));
            } else {
                task.setID(id);
                UseCaseUpdateTaskAsync useCaseUpdateTask = new UseCaseUpdateTaskAsync();
                useCaseUpdateTask.execute(new Package(activity, task));
            }
            Toast.makeText(activity, "SAVE TASK ", Toast.LENGTH_SHORT).show();
            activity.onBackPressed();
        } else {
            Toast.makeText(activity, "CHECK DATES AND ESTIMATED TIME", Toast.LENGTH_SHORT).show();
        }

    }


    public void edit() {
        enabled.set(true);
    }


    public void delete() {
        UseCaseDeleteAsync useCaseDeleteAsync = new UseCaseDeleteAsync();
        useCaseDeleteAsync.execute(new Package(activity, id));
        activity.onBackPressed();

    }

    public void onValueChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
        progress.set(progresValue);
        seekBarValue.set(progresValue + " %");
    }


    public void setStartTimeDialog() {
        int[] dates = getCurrentDate();
        TimePickerDialog dialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                if (i1 <= 9) {
                    setStartTime.set(i + ":0" + i1);
                } else {
                    setStartTime.set(i + ":" + i1);
                }

            }
        }, dates[3], dates[4], true);
        dialog.show();


    }

    public void setStartDateDialog() {
        int[] dates = getCurrentDate();

        DatePickerDialog dialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                setStartDate.set(i2 + "." + (i1 + 1) + "." + i);
            }
        }, dates[0], dates[1], dates[2]);
        dialog.show();
    }


    public void setEndTimeDialog() {
        int[] dates = getCurrentDate();
        TimePickerDialog dialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                if (i1 <= 9) {
                    setEndTime.set(i + ":0" + i1);
                } else {
                    setEndTime.set(i + ":" + i1);
                }
            }
        }, dates[3], dates[4], true);
        dialog.show();


    }

    public void setEndDateDialog() {
        int[] dates = getCurrentDate();
        DatePickerDialog dialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                setEndDate.set(i2 + "." + (i1 + 1) + "." + i);
            }
        }, dates[0], dates[1], dates[2]);
        dialog.show();
    }

    public void plus() {
        estimatedTime.set(++count);
    }

    public void minus() {
        if (count >= 1) {
            estimatedTime.set(--count);
        }
    }



    private int[] getCurrentDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        return new int[]{year, month, day, hour, min};
    }


    //check for right completed fields
    private boolean checkFields() {
        if (setStartTime.get().equals("SET DATE") || setStartDate.get().equals("SET DATE") || setEndTime.get().equals("SET DATE") || setEndDate.get().equals("SET DATE")) {
            return false;
        }
        boolean check = false;
        String result = setStartTime.get() + " " + setStartDate.get();
        String result2 = setEndTime.get() + " " + setEndDate.get();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        try {
            Date date = dateFormat.parse(result);
            Date date2 = dateFormat.parse(result2);
            check = (date2.getTime() - date.getTime() - estimatedTime.get() * 1000 * 60 * 60) >= 0;

        } catch (ParseException e) {
            Log.e("TAG", e.getMessage());
        }


        return check;
    }


//////////////////GETTERS & SETTERS///////////////////

    public ObservableField<String> getName() {
        return name;
    }

    public void setName(ObservableField<String> name) {
        this.name = name;
    }

    public ObservableField<String> getDescription() {
        return description;
    }

    public void setDescription(ObservableField<String> description) {
        this.description = description;
    }

    public ObservableField<Integer> getProgress() {
        return progress;
    }

    public void setProgress(ObservableField<Integer> progress) {
        this.progress = progress;
    }

    public ObservableField<String> getSetStartTime() {
        return setStartTime;
    }

    public void setSetStartTime(ObservableField<String> setStartTime) {
        this.setStartTime = setStartTime;
    }

    public ObservableField<String> getSetStartDate() {
        return setStartDate;
    }

    public void setSetStartDate(ObservableField<String> setStartDate) {
        this.setStartDate = setStartDate;
    }

    public ObservableField<String> getSetEndTime() {
        return setEndTime;
    }

    public void setSetEndTime(ObservableField<String> setEndTime) {
        this.setEndTime = setEndTime;
    }

    public ObservableField<String> getSetEndDate() {
        return setEndDate;
    }

    public void setSetEndDate(ObservableField<String> setEndDate) {
        this.setEndDate = setEndDate;
    }


    public void setId(int id) {
        this.id = id;
    }


    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setBinding(ActivityDetailsBinding binding) {
        this.binding = binding;
    }

    public ObservableField<Boolean> getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean flag) {
        enabled.set(flag);
    }

    public ObservableField<Integer> getState() {
        return state;
    }

    public void setState(ObservableField<Integer> state) {
        this.state = state;
    }

    public ObservableField<String> getSeekBarValue() {
        return seekBarValue;
    }

    public void setSeekBarValue(ObservableField<String> seekBarValue) {
        this.seekBarValue = seekBarValue;
    }

    public ObservableField<Boolean> getEnableEditAndDelet() {
        return enableEditAndDelet;
    }

    public void setEnableEditAndDelet(Boolean enableEditAndDelet) {
        this.enableEditAndDelet.set(enableEditAndDelet);
    }

    public ObservableField<Integer> getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime.set(estimatedTime);
    }
}
