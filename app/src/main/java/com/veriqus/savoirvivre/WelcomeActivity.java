package com.veriqus.savoirvivre;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    public static final String NO_MORE_INTRO = "noMoreIntro";
    private boolean noMoreIntro = false;
    SharedPreferences.Editor editorIntro;

    private static int SPLASH_DISPLAY_LENGTH = 550;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences settingsIntro = getSharedPreferences(NO_MORE_INTRO, 0);
        noMoreIntro = settingsIntro.getBoolean("noMoreIntro", false);

        Log.i("Intro: ", noMoreIntro+"");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        TextView buttonToMain = (TextView) findViewById(R.id.okButton);
        TextView okButton = (TextView) findViewById(R.id.okButton);
        final Intent toTheMain = new Intent(WelcomeActivity.this, MainActivity.class);

        if (noMoreIntro) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(toTheMain);
                    finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        } else {
            okButton.setVisibility(View.VISIBLE);
            noMoreIntro = true;
            editorIntro = settingsIntro.edit();
            editorIntro.putBoolean(NO_MORE_INTRO, noMoreIntro);
            editorIntro.apply();
            editorIntro.commit();

            buttonToMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(toTheMain);
                    finish();
                }
            });
        }

    }
}
