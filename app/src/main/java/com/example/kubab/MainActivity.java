package com.example.kubab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButton1Clicked(View v){
        Intent intent = new Intent(getApplicationContext(), LibraryActivity.class);
        startActivity(intent);
    }

    public void onButton2Clicked(View v){
        Intent intent = new Intent(getApplicationContext(), MillenniumhallActivity.class);
        startActivity(intent);
    }

    public void onButton3Clicked(View v){
        Intent intent = new Intent(getApplicationContext(), StudenthallB1Activity.class);
        startActivity(intent);
    }

    public void onButton4Clicked(View v){
        Intent intent = new Intent(getApplicationContext(), Studenthall1FActivity.class);
        startActivity(intent);
    }

    public void onButton5Clicked(View v){
        Intent intent = new Intent(getApplicationContext(), DormitoryActivity.class);
        startActivity(intent);
    }

    public void onButton6Clicked(View v){
        Intent intent = new Intent(getApplicationContext(), InformationActivity.class);
        startActivity(intent);
    }
}

