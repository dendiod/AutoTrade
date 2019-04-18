package com.example.denpotap.autotrade;

import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.content.Intent;

import java.io.*;
import java.lang.annotation.IncompleteAnnotationException;
import java.util.ArrayList;

import com.squareup.picasso.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Car[]carsArray = new Car[MyArrays.arrays.length];
    public static ArrayList<Car>cars = new ArrayList<>();
     private ArrayList<TableRow>tableRows = new ArrayList<>();
    public static int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarmain);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        updateClick(findViewById(R.id.update));
    }

    public void filtrClick(View view){
        Intent intent = new Intent(this, FiltrActivity.class);
        startActivity(intent);
    }

    private void orientation(int num){
        TableLayout tableLayout = findViewById(R.id.table);
        for(int i = 0; i < tableRows.size(); i++){
            tableRows.get(i).removeAllViews();
        }
        tableLayout.removeAllViews();
        int screenWidth = (int)(getResources().getDisplayMetrics().density * getResources().getConfiguration().screenWidthDp);
        for(int i = 0; i < cars.size(); i++){
            if(i % num == 0){
                TableRow tableRow = new TableRow(this);
                for(int j = 0; j < num; j++){
                    if(i + j < cars.size())
                        tableRow.addView(cars.get(i+j), new TableRow.LayoutParams(screenWidth/num, screenWidth/num));
                }
                tableLayout.addView(tableRow);
                tableRows.add(tableRow);
            }
        }
    }

    public void updateClick(View view){
        CheckNetwork checkNetwork = new CheckNetwork(getApplicationContext());
        Button button1 = findViewById(R.id.buttonFiltr);
        checkNetwork.deleteButton((Button)view, (ImageView)findViewById(R.id.noinet));

        if(checkNetwork.isOnline()){
            button1.setClickable(true);
            for (int i = 0; i < carsArray.length; i++) {
                Car car = new Car(this);
                car.setScaleType(Car.ScaleType.FIT_XY);
                carsArray[i] = car;
                carsArray[i].setIndex(i);
                carsArray[i].setRate(Integer.parseInt(MyArrays.rate[i]));
                carsArray[i].setPrice(Integer.parseInt(MyArrays.prices[i]));
                carsArray[i].setWheelDrive(MyArrays.wheelDrive[i]);
                carsArray[i].setCapacity(Double.parseDouble(MyArrays.capacity[i]));
                carsArray[i].setConsumption(Double.parseDouble(MyArrays.consumption[i]));
                carsArray[i].setFuel(MyArrays.fuel[i]);
                carsArray[i].setPower(Integer.parseInt(MyArrays.power[i]));
                carsArray[i].setTransmission(MyArrays.transmissions[i]);
            }
            File file = new File(getFilesDir(), "carsIndex.txt");
            File file1 = new File(getFilesDir(), "favourites.txt");

            FileWork fileWork = new FileWork(getApplicationContext());
            if(!file.exists())
                fileWork.fileWrite("carsIndex.txt", "0 1 2 3 4 5 6 7 8 9 10");
            fileWork.fileRead("carsIndex.txt");

            for(int i = 0; i < cars.size(); i++){
                Picasso.get().load(MyArrays.arrays[cars.get(i).getIndex()][0]).into(cars.get(i));
            }
            if(!file1.exists())
                fileWork.fileWrite("favourites.txt", "");

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                orientation(1);
            else
                orientation(2);

            for(int i = 0; i < cars.size(); i++){
                cars.get(i).setOnClickListener(new View.OnClickListener(){
                    public  void onClick(View v){
                        for(int i = 0; i < cars.size(); i++){
                            if(v == cars.get(i)) {
                                index = i;
                                break;
                            }
                        }
                        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                        intent.putExtra("index", "main");
                        startActivity(intent);
                    }
                });
            }
            if(cars.size() == 0){
                ImageView imageView = new ImageView(this);
                Picasso.get().load("https://i.ibb.co/kBvCQyq/nothing.png").into(imageView);
                ConstraintLayout constraintLayout = findViewById(R.id.mainconstr);
                constraintLayout.addView(imageView, new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
            }
        }
        else {
            checkNetwork.updateButtonLocation((Button) findViewById(R.id.update), (ImageView)findViewById(R.id.noinet));
            button1.setClickable(false);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        CheckNetwork checkNetwork = new CheckNetwork(getApplicationContext());
        super.onConfigurationChanged(newConfig);
           if(findViewById(R.id.update) != null)
            checkNetwork.updateButtonLocation((Button)findViewById(R.id.update), (ImageView)findViewById(R.id.noinet));

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            orientation(1);
        else
            orientation(2);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            cars.clear();
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_favourites) {
            Intent intent = new Intent(this, FavouritesActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_contacts) {
            Intent intent = new Intent(this, ContactsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
