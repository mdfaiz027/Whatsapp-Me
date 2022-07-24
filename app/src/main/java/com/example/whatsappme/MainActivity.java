package com.example.whatsappme;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextInputEditText phoneNumber, addMsg;
    Button openChatBtn;
    SwitchMaterial aSwitch;
    TextInputLayout addMsgTextField;
    FloatingActionButton historyBtn;

    SQLiteDatabase db;
    UserHelper userHelper;
    //String time = getTime();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNumber = findViewById(R.id.idPhoneNum);
        openChatBtn = findViewById(R.id.idOpenChatBtn);
        addMsg = findViewById(R.id.idAddMsg);
        aSwitch = findViewById(R.id.idSwitch);
        addMsgTextField = findViewById(R.id.outlinedTextField2);

        historyBtn = findViewById(R.id.history);

        userHelper=new UserHelper(this);
        db=userHelper.getWritableDatabase();

        //Adding the visibility method to the switch

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b) {
                        addMsgTextField.setVisibility(View.VISIBLE);
                    }else{
                        addMsgTextField.setVisibility(View.GONE);
                        //addMsg.setText(null);
                    }
                }
        });

        //On click listener method for the chat button

        openChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {

                String time = getTime();
                String inputPhoneNumber = phoneNumber.getText().toString();
                String inputAddMsg = addMsg.getText().toString();

                    if(inputPhoneNumber.trim().equals(""))
                    {
                        phoneNumber.setError("Enter the phone number!");
                    }
                    else if(aSwitch.isChecked()==true && inputAddMsg.trim().equals(""))
                    {
                        addMsg.setError("Enter the message!");
                    }
                    else if(aSwitch.isChecked()==true && !inputAddMsg.trim().equals(""))
                    {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+inputPhoneNumber+"&text="+inputAddMsg));
                            startActivity(intent);

                            //adding the number in the history
                            userHelper.createMethod(db,inputPhoneNumber,time);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+inputPhoneNumber));
                            startActivity(intent);

                            //adding the number in the history
                            userHelper.createMethod(db,inputPhoneNumber,time);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }
            });

        //On click listener for the history button
        
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
        }

        //Created the method to get the current time

        private String getTime() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
            Date date = new Date();
            return dateFormat.format(date);
        }

}