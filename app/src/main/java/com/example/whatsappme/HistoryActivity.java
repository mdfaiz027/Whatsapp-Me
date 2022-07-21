package com.example.whatsappme;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    SQLiteDatabase db;

    Cursor cursor;
    ListView listView;
    UserHelper userHelper;

    HistoryModel historyModel;
    HistoryAdapter historyAdapter;
    List<HistoryModel> historyModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = findViewById(R.id.mobileList);

        userHelper=new UserHelper(this);
        db=userHelper.getWritableDatabase();

        historyModelList.clear();

        String[] col = {"Mobile_number", "Time"};
        String sortOrder = "Time" + " DESC";
        cursor = db.query("WhatsappMe_table",col, null,null,null,null, sortOrder);

        if(cursor.getCount()>0 && cursor!=null)
        {
            while (cursor.moveToNext()){

                @SuppressLint("Range") String mobile = cursor.getString(cursor.getColumnIndex("Mobile_number"));
                //@SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("Time"));

                historyModel = new HistoryModel(""+mobile);
                historyModelList.add(historyModel);
                historyAdapter = new HistoryAdapter(getApplicationContext(), historyModelList);
                listView.setAdapter(historyAdapter);
            }
        }
        else{
            Toast.makeText(HistoryActivity.this, "No data found", Toast.LENGTH_SHORT).show();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(HistoryActivity.this, ""+historyModelList.get(i).getMobileNumber(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}