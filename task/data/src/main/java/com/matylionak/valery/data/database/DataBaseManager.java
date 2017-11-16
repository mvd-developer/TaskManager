package com.matylionak.valery.data.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.matylionak.valery.data.Entity.Task;

import java.util.ArrayList;
import java.util.List;

public class DataBaseManager {
    private DBHelper dbHelper;
    private SQLiteDatabase database;


    //database columns
    private static int idColIndex;
    private static int titleColIndex;
    private static int descriptionColIndex;
    private static int progressColIndex;
    private static int startTimeColIndex;
    private static int startDateColIndex;
    private static int endTimeColIndex;
    private static int endDateColIndex;
    private static int stateColIndex;
    private static int estimatedColIndex;

    public DataBaseManager(Context context) {
        dbHelper = new DBHelper(context);
    }


    public void open(boolean isWritable) {
        if (isWritable) {
            database = dbHelper.getWritableDatabase();

        } else {
            database = dbHelper.getReadableDatabase();
        }


    }

    public void close() {
        if (database != null) {
            database.close();
        }
    }

    public void insertTask(Task task) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO task ('title', 'description', 'progress', 'startTime', 'startDate', 'endTime','endDate', 'state', 'estimated') ");
        sql.append("VALUES ('");
        sql.append(task.getTitle());
        sql.append("', '");
        sql.append(task.getDescription());
        sql.append("', ");
        sql.append(task.getProgress());
        sql.append(", '");
        sql.append(task.getStartTime());
        sql.append("', '");
        sql.append(task.getStartDate());
        sql.append("', '");
        sql.append(task.getEndTime());
        sql.append("', '");
        sql.append(task.getEndDate());
        sql.append("', ");
        sql.append(task.getState());
        sql.append(", ");
        sql.append(task.getEstimated());
        sql.append(")");
        Log.e("TAG-insertTask", sql.toString());
        database.execSQL(sql.toString());
    }


    public List<Task> getTasks() {
        List<Task> list = new ArrayList<>();
        Cursor cursor = database.query("task", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {

            idColIndex = cursor.getColumnIndex("ID");
            titleColIndex = cursor.getColumnIndex("title");
            descriptionColIndex = cursor.getColumnIndex("description");
            progressColIndex = cursor.getColumnIndex("progress");
            startTimeColIndex = cursor.getColumnIndex("startTime");
            startDateColIndex = cursor.getColumnIndex("startDate");
            endTimeColIndex = cursor.getColumnIndex("endTime");
            endDateColIndex = cursor.getColumnIndex("endDate");
            stateColIndex = cursor.getColumnIndex("state");
            estimatedColIndex = cursor.getColumnIndex("estimated");
            do {
                list.add(factory(cursor));
            }
            while (cursor.moveToNext());
        } else
            Log.d("LOG_TAG", "0 rows");

        cursor.close();
        Log.e("TAG-return from SQL", list.toString());
        return list;
    }

    public Task getTaskById(int id) {
        Cursor cursor = database.rawQuery("SELECT * FROM task WHERE ID = " + id, null);

        if (cursor != null) {
            cursor.moveToFirst();
            Task task = factory(cursor);
            cursor.close();
            return task;
        }
        return null;
    }


    public void updateTask(Task task) {
        ContentValues values = new ContentValues();
        values.put("title", task.getTitle() );
        values.put("description", task.getDescription());
        values.put("progress", task.getProgress());
        values.put("startTime", task.getStartTime());
        values.put("startDate", task.getStartDate());
        values.put("endTime", task.getEndTime() );
        values.put("endDate", task.getEndDate() );
        values.put("state", task.getState());
        values.put("estimated", task.getEstimated());
        String s= String.valueOf(task.getID());
        database.update("task", values, "ID =?", new String[]{s});
    }

    public void deleteTask(int id) {
        database.delete("task", "ID = " + id, null);

    }

    public void deliteTable() {
        database.execSQL("DROP TABLE IF EXISTS task");
    }


    private Task factory(Cursor cursor) {
        Task task = new Task();
        task.setID(cursor.getInt(idColIndex));
        task.setTitle(cursor.getString(titleColIndex));
        task.setDescription(cursor.getString(descriptionColIndex));
        task.setProgress(cursor.getInt(progressColIndex));
        task.setStartTime(cursor.getString(startTimeColIndex));
        task.setStartDate(cursor.getString(startDateColIndex));
        task.setEndTime(cursor.getString(endTimeColIndex));
        task.setEndDate(cursor.getString(endDateColIndex));
        task.setState(cursor.getInt(stateColIndex));
        task.setEstimated(cursor.getInt(estimatedColIndex));
        return task;

    }

}
