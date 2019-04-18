package com.example.denpotap.autotrade;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    public static int pageNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        updateClick(findViewById(R.id.update2));
    }

    public void updateClick(View view){
        CheckNetwork checkNetwork = new CheckNetwork(getApplicationContext());
        checkNetwork.deleteButton((Button)view, (ImageView)findViewById(R.id.noinet2));
        if(checkNetwork.isOnline()){
           ViewPager viewPager = findViewById(R.id.pager1);
            viewPager.setAdapter(new FullscreenAdapter(getSupportFragmentManager()));
            viewPager.setCurrentItem(pageNumber);
        }
        else
            checkNetwork.updateButtonLocation((Button)findViewById(R.id.update2), (ImageView)findViewById(R.id.noinet2));
    }
}


