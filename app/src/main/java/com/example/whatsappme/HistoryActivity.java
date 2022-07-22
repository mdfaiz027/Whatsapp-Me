package com.example.whatsappme;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
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

    String mobile;

    @SuppressLint("Range")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = findViewById(R.id.mobileList);
        historyModelList.clear();

        userHelper = new UserHelper(this);
        db = userHelper.getWritableDatabase();

        //Adding the mobile number using the time stamp from the Main Activity (Edit text) and adding it to the History Activity

        String[] col = {"Mobile_number", "Time"};
        String sortOrder = "Time" + " DESC";
        cursor = db.query("WhatsappMe_table", col, null, null, null, null, sortOrder);

        //Getting the mobile number from the Main Activity (Edit text) and adding it to the History Activity

        if (cursor.getCount() > 0 && cursor != null) {
            while (cursor.moveToNext()) {

                mobile = cursor.getString(cursor.getColumnIndex("Mobile_number"));
                //@SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("Time"));

                historyModel = new HistoryModel("" + mobile);
                historyModelList.add(historyModel);
                historyAdapter = new HistoryAdapter(getApplicationContext(), historyModelList);
                listView.setAdapter(historyAdapter);
            }
        } else {
            Toast.makeText(HistoryActivity.this, "No History found", Toast.LENGTH_SHORT).show();
        }

        //On item click listener for the history phone number in the list

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(HistoryActivity.this, "" + historyModelList.get(i).getMobileNumber(), Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+historyModelList.get(i).getMobileNumber()));
                    startActivity(intent);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    //On click method when clicked on popup and inflating clear history menu item

    public void showPopup(View view) {

        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_items, popup.getMenu());
        popup.show();

        //On menu item click method for the clear history menu item

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                long set = userHelper.deleteMethod(db);

                if(set == 0 ){
                    Toast.makeText(HistoryActivity.this, "History not found", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(HistoryActivity.this, ""+set, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(HistoryActivity.this, "History deleted successfully", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(HistoryActivity.this, ""+set, Toast.LENGTH_SHORT).show();
                }

                historyModelList.clear();
                historyAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

}
