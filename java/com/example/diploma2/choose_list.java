package com.example.diploma2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class choose_list extends AppCompatActivity {
    private Button button;
    private Button buttonank;
    private Button bluebutton;
    private Button obr;
    private Button checkwifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_list);
        button= (Button) findViewById(R.id.goback);
        buttonank= (Button) findViewById(R.id.anketa) ;
        bluebutton= (Button) findViewById(R.id.module);
        obr= (Button) findViewById(R.id.obr);
        checkwifi= (Button) findViewById(R.id.checkwifi);
        buttonank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAnk();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openMainActivity(); }
        });
        bluebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Openbluetooth();
            }
        });
        obr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {openGraph();

            }
        });





    }
        public void openMainActivity(){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
    }
        public void openAnk(){
        Intent intent1 = new Intent(this, Anketa.class);
        startActivity(intent1);
        }
        public void Openbluetooth(){
            Intent intent2 = new Intent(this, bluetooth.class);
            startActivity(intent2);
        }
        public void openGraph(){
            Intent intent3 = new Intent(this, MotionGraph.class);
            startActivity(intent3);
        }


}