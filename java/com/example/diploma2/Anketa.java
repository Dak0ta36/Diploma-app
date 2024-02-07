package com.example.diploma2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Anketa extends AppCompatActivity {
    private Button buttonlist;
    EditText mDateFormat;
    private EditText fio, dateofbirth, gender,ves, rost, allergic, diagnosis;
    private String USER_KEY = "User";
    private String dateofbirthh;
    private Button savedata;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = db.getReference();
    DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anketa);
        savedata = (Button) findViewById(R.id.saveinfo);
        buttonlist= (Button) findViewById(R.id.tolist);
        savedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = mDatabase.getKey();
                String name = fio.getText().toString();
                String pol = gender.getText().toString();
                String ves1 = ves.getText().toString();
                String rost1 = rost.getText().toString();
                String allergic1 = allergic.getText().toString();
                String diagnosis1 = diagnosis.getText().toString();
                User newUser = new User(id,name, "01.01.2001", pol, ves1, rost1, allergic1, diagnosis1 );
                mDatabase.setValue(id);

            }
        });
        buttonlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {openlist();}
        });
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR); 
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDateFormat = findViewById(R.id.dateofbirth);

        mDateFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // выбор даты рождения
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Anketa.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth+"/"+ month+"/"+year;
                dateofbirthh = date;
                mDateFormat.setText(date);
            }

        };

        init();
            }
            private void init(){
                fio = findViewById(R.id.fio);
                gender=findViewById(R.id.gender);
                ves=findViewById(R.id.ves);
                rost=findViewById(R.id.rost);
                allergic=findViewById(R.id.allergic);
                diagnosis=findViewById(R.id.diagnosis);


            }


    public void openlist(){
        Intent intent = new Intent(this, choose_list.class);
        startActivity(intent);

    }

}