package com.example.whatsappme;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class UserHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "WhatsappMe.db";
    public static final int VERSION = 1;
    Context context;

    public UserHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE WhatsappMe_table(Mobile_number INTEGER PRIMARY KEY, Time TEXT)");
        Toast.makeText(context, "SQL Started", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        if (i < i1) {
//            db.execSQL("ALTER TABLE WhatsappMe_table ADD TIME TEXT");
//        }

    }

    public long createMethod(SQLiteDatabase db, String Mobile_number, String Time) {
        ContentValues cv = new ContentValues();
        cv.put("Mobile_number", Mobile_number);
        cv.put("Time", Time);

        long set = db.insert("WhatsappMe_table", null, cv);
        return set;
    }

    public long deleteMethod(SQLiteDatabase db) {
        //db.execSQL("delete from WhatsappMe_table");
        long set = db.delete("WhatsappMe_table",null, null);
        return set;
    }
}
