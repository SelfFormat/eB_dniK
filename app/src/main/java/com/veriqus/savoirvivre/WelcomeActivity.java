package com.veriqus.savoirvivre;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    public static final String NO_MORE_INTRO = "noMoreIntro";
    private boolean noMoreIntro = true;
    SharedPreferences.Editor editorIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences settingsIntro = getSharedPreferences(NO_MORE_INTRO, 0);
        editorIntro = settingsIntro.edit();
        editorIntro.putBoolean("noMoreIntro", noMoreIntro);
        editorIntro.apply();
        editorIntro.commit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        TextView buttonToMain = (TextView) findViewById(R.id.okButton);
//        final Animation in = new AlphaAnimation(0.0f, 1.0f);
//        in.setDuration(3000);
//        buttonToMain.startAnimation(in);


        buttonToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toTheMain = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(toTheMain);
                finish();
            }
        });

    }
}
