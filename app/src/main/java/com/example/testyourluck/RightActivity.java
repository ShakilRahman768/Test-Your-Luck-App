package com.example.testyourluck;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

public class RightActivity extends AppCompatActivity {

    TextView textViewreward;

    public static int r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right);

        textViewreward = findViewById(R.id.textViewreward);

        textViewreward.setText("Congratulation!! You Got "+ r + " Chips");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
        MediaPlayer audio = MediaPlayer.create(this, R.raw.right_sound);
        audio.start();
        thread.start();

    }
}