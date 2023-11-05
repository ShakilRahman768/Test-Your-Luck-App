package com.example.testyourluck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private Set<Integer> correctCardIndices = new HashSet<>();

    private DatabaseHelper databaseHelper;

    private ProgressBar progressBar;
    private int initialProgress = 0;
    TextView coinCountTextView,textViewNote;
    Button buttonNext,buttonStart,buttonAgain;
    ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonNext = findViewById(R.id.Next);
        buttonStart = findViewById(R.id.Start);
        buttonAgain = findViewById(R.id.Again);
        imageView1 = findViewById(R.id.card1);
        imageView2 = findViewById(R.id.card2);
        imageView3 = findViewById(R.id.card3);
        imageView4 = findViewById(R.id.card4);
        imageView5 = findViewById(R.id.card5);
        imageView6 = findViewById(R.id.card6);
        imageView7 = findViewById(R.id.card7);
        imageView8 = findViewById(R.id.card8);
        imageView9 = findViewById(R.id.card9);
        coinCountTextView = findViewById(R.id.coinCountTextView);
        textViewNote = findViewById(R.id.textViewNote);

        databaseHelper = new DatabaseHelper(this);


        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(initialProgress);


        Random random = new Random();
        while (correctCardIndices.size() < 8) {
            int randomIndex = random.nextInt(9);
            correctCardIndices.add(randomIndex);
        }

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        int currentCoinCount = databaseHelper.getCoinCount();
        coinCountTextView.setText(""+currentCoinCount);
        // Add click listeners to each ImageView
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView card = (ImageView) gridLayout.getChildAt(i);
            final int cardIndex = i;

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int j = 0; j < gridLayout.getChildCount(); j++) {
                        ImageView card = (ImageView) gridLayout.getChildAt(j);
                        card.setOnClickListener(null); // Remove the click listener
                    }

                    if (correctCardIndices.contains(cardIndex)) {
                        // Correct card
                        // Display a message or perform any other action
                        //Toast toast = Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT);
                        //toast.setGravity(Gravity.TOP, 0, 0);
                        //toast.show();
                        buttonNext.setVisibility(View.VISIBLE);
                        textViewNote.setVisibility(View.GONE);
                        show();


                        int currentCoinCount = databaseHelper.getCoinCount();
                        currentCoinCount += 10;
                        databaseHelper.updateCoins(currentCoinCount);

                        int updatedCoinCount = databaseHelper.getCoinCount();
                        coinCountTextView.setText(""+updatedCoinCount);

                        initialProgress = 13;
                        progressBar.setProgress(initialProgress);

                        RightActivity.r = 10;

                        Intent screen = new Intent(MainActivity.this, RightActivity.class);
                        startActivity(screen);



                    } else {
                        // Incorrect card
                        // Display a message or perform any other action
                        //showToast("Wrong card. Correct index: " + getCorrectCardIndexString());
                        show();
                        buttonStart.setVisibility(View.VISIBLE);
                        buttonAgain.setVisibility(View.VISIBLE);
                        textViewNote.setVisibility(View.GONE);
                        Intent screen = new Intent(MainActivity.this, WrongActivity.class);
                        startActivity(screen);
                    }
                }
            });
        }

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
            }
        });

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        buttonAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentCoinCount = databaseHelper.getCoinCount();

                if(currentCoinCount>=5000){
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent);

                    currentCoinCount -= 5000;
                    databaseHelper.updateCoins(currentCoinCount);

                    int updatedCoinCount = databaseHelper.getCoinCount();
                    coinCountTextView.setText(""+updatedCoinCount);
                }

                else{
                    Toast.makeText(MainActivity.this, "Not Enough Chips!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }




    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

    private String getCorrectCardIndexString() {
        StringBuilder indexString = new StringBuilder();
        for (Integer index : correctCardIndices) {
            indexString.append(index).append(", ");
        }
        // Remove the trailing comma and space
        return indexString.substring(0, indexString.length() - 2);
    }

    private void show() {
        if(correctCardIndices.contains(0)){
            imageView1.setImageResource(R.drawable.card_font);
        }
        else{
            imageView1.setImageResource(R.drawable.wrong_card);
        }

        if(correctCardIndices.contains(1)){
            imageView2.setImageResource(R.drawable.card_font);
        }
        else{
            imageView2.setImageResource(R.drawable.wrong_card);
        }

        if(correctCardIndices.contains(2)){
            imageView3.setImageResource(R.drawable.card_font);
        }
        else{
            imageView3.setImageResource(R.drawable.wrong_card);
        }

        if(correctCardIndices.contains(3)){
            imageView4.setImageResource(R.drawable.card_font);
        }
        else{
            imageView4.setImageResource(R.drawable.wrong_card);
        }

        if(correctCardIndices.contains(4)){
            imageView5.setImageResource(R.drawable.card_font);
        }
        else{
            imageView5.setImageResource(R.drawable.wrong_card);
        }

        if(correctCardIndices.contains(5)){
            imageView6.setImageResource(R.drawable.card_font);
        }
        else{
            imageView6.setImageResource(R.drawable.wrong_card);
        }

        if(correctCardIndices.contains(6)){
            imageView7.setImageResource(R.drawable.card_font);
        }
        else{
            imageView7.setImageResource(R.drawable.wrong_card);
        }

        if(correctCardIndices.contains(7)){
            imageView8.setImageResource(R.drawable.card_font);
        }
        else{
            imageView8.setImageResource(R.drawable.wrong_card);
        }

        if(correctCardIndices.contains(8)){
            imageView9.setImageResource(R.drawable.card_font);
        }
        else{
            imageView9.setImageResource(R.drawable.wrong_card);
        }

    }

    ///====================================================
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired
    private long mBackPressed;

    // When user click bakpress button this method is called
    @Override
    public void onBackPressed() {
        // When user press back button

        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else {

            Toast.makeText(getBaseContext(), "Press again to exit",
                    Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();



    }


}