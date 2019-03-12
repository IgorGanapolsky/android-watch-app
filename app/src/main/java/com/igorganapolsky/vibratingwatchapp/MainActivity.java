package com.igorganapolsky.vibratingwatchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.llAddTimerButtonLayout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), SetTimerActivity.class));
    }
}
