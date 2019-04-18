package com.example.denpotap.autotrade;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    public static ArrayList<Integer>numbers = new ArrayList<>();
    public static String activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Bundle arguments = getIntent().getExtras();
        activity = arguments.get("index").toString();
        updateClick(findViewById(R.id.update1));
    }

    public void updateClick(View view){
        CheckNetwork checkNetwork = new CheckNetwork(getApplicationContext());
        checkNetwork.deleteButton((Button)view, (ImageView)findViewById(R.id.noinet1));
        TextView textView1 = findViewById(R.id.bodytext);
        TextView textView2 = findViewById(R.id.fueltext);
        TextView textView3 = findViewById(R.id.transmissiontext);
        TextView textView4 = findViewById(R.id.wheeldrivetext);
        TextView textView5 = findViewById(R.id.capacitytext);
        TextView textView6 = findViewById(R.id.consumptiontext);
        TextView textView7 = findViewById(R.id.powertext);
        TextView textView8 = findViewById(R.id.fulltext);
        if(checkNetwork.isOnline()){
            textView1.setText("Основные характеристики");
            textView2.setText("Топливо");
            textView3.setText("КПП");
            textView4.setText("Привод");
            textView5.setText("Объём двигателя, л");
            textView6.setText("Расход топлива (смешанный цикл), л");
            textView7.setText("Мощность, л.с");
            textView8.setText("Полные характеристики");
            ImageButton button = findViewById(R.id.call);
            ImageButton button1 = findViewById(R.id.favour);
            button.setVisibility(View.VISIBLE);
            button1.setVisibility(View.VISIBLE);
            Picasso.get().load("https://i.ibb.co/MCNKD8c/call.png").into(button);
            Picasso.get().load("https://i.ibb.co/yWM5sWT/star.png").into(button1);
            ViewPager viewPager = findViewById(R.id.pager);
            viewPager.setAdapter(new PhotoAdapter(getSupportFragmentManager()));

            TextView inFavourites = findViewById(R.id.infavor);
            if(activity.equals("main")) {
                numbers.clear();
                FileWork fileWork = new FileWork(getApplicationContext());
                fileWork.fileRead("favourites.txt");
                for(int i = 0; i < numbers.size(); i++){
                    if(numbers.get(i) == MainActivity.cars.get(MainActivity.index).getIndex()) {
                        inFavourites.setVisibility(View.VISIBLE);
                        break;
                    }
                }
                fillWithContent(MainActivity.cars);
            }
            else {
                inFavourites.setVisibility(View.VISIBLE);
                fillWithContent(FavouritesActivity.cars);
            }
        }
        else
            checkNetwork.updateButtonLocation((Button)findViewById(R.id.update1), (ImageView)findViewById(R.id.noinet1));
    }

    private void fillWithContent(ArrayList<Car>arrayList){
        TextView textBrand = findViewById(R.id.brand);
        TextView textPrice = findViewById(R.id.price);
        TextView textBody = findViewById(R.id.body);
        TextView textFuel = findViewById(R.id.fuel);
        TextView textTransmission = findViewById(R.id.transmission);
        TextView textWheelDrive = findViewById(R.id.wheeldrive);
        TextView textCapacity = findViewById(R.id.capacity);
        TextView textConsumption = findViewById(R.id.consumption);
        TextView textPower = findViewById(R.id.power);
        TextView textFullInfo = findViewById(R.id.full);

        textBrand.setText(MyArrays.detailsBrands[arrayList.get(MainActivity.index).getIndex()]);
        textPrice.setText(MyArrays.prices[arrayList.get(MainActivity.index).getIndex()] + " $");
        textBody.setText(MyArrays.bodies[arrayList.get(MainActivity.index).getIndex()]);
        textFuel.setText(MyArrays.fuel[arrayList.get(MainActivity.index).getIndex()]);
        textTransmission.setText(MyArrays.transmissions[arrayList.get(MainActivity.index).getIndex()]);
        textWheelDrive.setText(MyArrays.wheelDrive[arrayList.get(MainActivity.index).getIndex()]);
        textCapacity.setText(MyArrays.capacity[arrayList.get(MainActivity.index).getIndex()]);
        textConsumption.setText(MyArrays.consumption[arrayList.get(MainActivity.index).getIndex()]);
        textPower.setText(MyArrays.power[arrayList.get(MainActivity.index).getIndex()]);
        textFullInfo.setText(MyArrays.full[arrayList.get(MainActivity.index).getIndex()]);
    }

    public void toFavourites(View view){
        numbers.clear();
        FileWork fileWork = new FileWork(getApplicationContext());
        fileWork.fileRead("favourites.txt");
        boolean norepeat = true;
        for(int i = 0; i < numbers.size(); i++){
            if (numbers.get(i) == MainActivity.cars.get(MainActivity.index).getIndex())
                norepeat = false;
        }
        if(norepeat) {
            String string = "";
            for(int i = 0; i < numbers.size(); i++){
                string += Integer.toString(numbers.get(i)) + " ";
            }
            string += Integer.toString(MainActivity.cars.get(MainActivity.index).getIndex());
                fileWork.fileWrite("favourites.txt", string);

        }
        TextView textView = findViewById(R.id.infavor);
        textView.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Добавлено в избранное", Toast.LENGTH_SHORT).show();
    }

    public void dial(View view)throws SecurityException{
        if(Build.VERSION.SDK_INT >= 23){
            Call call = new Call(getApplicationContext());
            call.dial(view, DetailsActivity.this);
        }
        else{
            String toDial = "tel:" + "0974804887";
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(toDial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) throws SecurityException {
        Call call = new Call(getApplicationContext());
        call.onRequestPermissionsResult(requestCode, permissions, grantResults, DetailsActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }
}
