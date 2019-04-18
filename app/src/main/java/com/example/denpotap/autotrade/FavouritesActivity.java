package com.example.denpotap.autotrade;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {
    public static ArrayList<Car>cars = new ArrayList<>();
    private ArrayList<TableRow>tableRows = new ArrayList<>();
    private ArrayList<ImageButton>buttons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        updateClick(findViewById(R.id.update3));
    }

    public void updateClick(View view){
        CheckNetwork checkNetwork = new CheckNetwork(getApplicationContext());
        checkNetwork.deleteButton((Button)view, (ImageView)findViewById(R.id.noinet3));
        if(checkNetwork.isOnline()){
            DetailsActivity.numbers.clear();
            FileWork fileWork = new FileWork(getApplicationContext());
            fileWork.fileRead("favourites.txt");

            for (int i = 0; i < DetailsActivity.numbers.size(); i++) {
                Car car = new Car(this);
                car.setScaleType(Car.ScaleType.FIT_XY);
                cars.add(car);
                car.setIndex(DetailsActivity.numbers.get(i));
            }
            nothingToShow();
            for(int i = 0; i < cars.size(); i++){
                Picasso.get().load(MyArrays.arrays[DetailsActivity.numbers.get(i)][0]).into(cars.get(i));
            }
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                orientation(1);
            else
                orientation(2);

        }
        else
            checkNetwork.updateButtonLocation((Button)findViewById(R.id.update3), (ImageView)findViewById(R.id.noinet3));
    }

    private void orientation(int num){
        ConstraintLayout constraintLayout = findViewById(R.id.constr);
        TableLayout tableLayout = findViewById(R.id.table1);
        for(int i = 0; i < tableRows.size(); i++){
            tableRows.get(i).removeAllViews();
        }
        tableLayout.removeAllViews();

        int screenWidth = (int)(getResources().getDisplayMetrics().density * getResources().getConfiguration().screenWidthDp);
        int xbut = 0, ybut = 0;
        for(int i = 0; i < buttons.size(); i++){
            ViewGroup viewGroup = (ViewGroup) buttons.get(i).getParent();
            viewGroup.removeView(buttons.get(i));
        }
        buttons.clear();

        for(int i = 0; i < cars.size(); i++){
            ImageButton button = new ImageButton(this);
            button.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_delete));
            buttons.add(button);
            constraintLayout.addView(button, new ConstraintLayout.LayoutParams(screenWidth/5/num, screenWidth/5/num));
            button.setX(xbut);
            button.setY(ybut);
            if((i+1) % num == 0){
                xbut = 0;
                ybut += screenWidth/num/3*2;
            }
            else
                xbut += screenWidth/num;
        }
        for(int i = 0; i < cars.size(); i++){
            if(i % num == 0){
                TableRow tableRow = new TableRow(this);
                for(int j = 0; j < num; j++){
                    if(i + j < cars.size())
                            tableRow.addView(cars.get(i + j), new TableRow.LayoutParams(screenWidth/num, screenWidth / num / 3 * 2));
                }
                tableLayout.addView(tableRow);
                tableRows.add(tableRow);
            }
        }
        for(int i = 0; i < cars.size(); i++){
            buttons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i = 0; i < buttons.size(); i++){
                        if(v == buttons.get(i)){
                            cars.remove(i);
                            DetailsActivity.numbers.remove(i);
                            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                                orientation(1);
                            else
                                orientation(2);
                            break;
                        }
                    }
                    changeInFile();
                    nothingToShow();
                }
            });
            cars.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i = 0; i < cars.size(); i++){
                        if(v == cars.get(i)) {
                            MainActivity.index = i;
                            break;
                        }
                    }
                    Intent intent = new Intent(FavouritesActivity.this, DetailsActivity.class);
                    intent.putExtra("index", "favourites");
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        CheckNetwork checkNetwork = new CheckNetwork(getApplicationContext());
        super.onConfigurationChanged(newConfig);
        if(findViewById(R.id.update3) != null)
            checkNetwork.updateButtonLocation((Button)findViewById(R.id.update3), (ImageView)findViewById(R.id.noinet3));

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            orientation(1);
        else
            orientation(2);
    }

    @Override
    public void onBackPressed() {
        DetailsActivity.numbers.clear();
        cars.clear();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    private void changeInFile(){
        FileWork fileWork = new FileWork(getApplicationContext());
        String string = "";
        for(int i = 0; i < DetailsActivity.numbers.size(); i++){
            string += Integer.toString(DetailsActivity.numbers.get(i)) + " ";
        }
        fileWork.fileWrite("favourites.txt", string);
    }

    private void nothingToShow(){
        if(cars.size() == 0){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load("https://i.ibb.co/kBvCQyq/nothing.png").into(imageView);
            ConstraintLayout constraintLayout = findViewById(R.id.favorconstr);
            constraintLayout.addView(imageView, new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
        }
    }
}
