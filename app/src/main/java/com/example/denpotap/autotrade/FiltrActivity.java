package com.example.denpotap.autotrade;

import android.arch.core.util.Function;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;

public class FiltrActivity extends AppCompatActivity {

    public static int index;
    private  String[]sortbase = {"По рейтингу","От дешевых к дорогим", "От дорогих к дешевым"};
    private  String[]sort = new String[3];
    private  ArrayList<Car>cars = new ArrayList<>();
    public static String lowPrice, highPrice, lowCapacity, highCapacity, lowConsumption,
            highConsumption, lowPower, highPower;
    public static CheckBox[]allCheckboxes = new CheckBox[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtr);

        for (int i = 0; i < sort.length; i++) {
            sort[i] = sortbase[i];
        }

        FileWork fileWork = new FileWork(getApplicationContext());
        File file1 = new File(getFilesDir(), "sort.txt");
        File file2 = new File(getFilesDir(), "lowcapacity.txt");
        File file3 = new File(getFilesDir(), "highcapacity.txt");
        File file4 = new File(getFilesDir(), "lowprice.txt");
        File file5 = new File(getFilesDir(), "highprice.txt");
        File file6 = new File(getFilesDir(), "lowconsumption.txt");
        File file7 = new File(getFilesDir(), "highconsumption.txt");
        File file8 = new File(getFilesDir(), "checkboxes.txt");
        File file9 = new File(getFilesDir(), "lowpower.txt");
        File file10 = new File(getFilesDir(), "highpower.txt");
        if(!file1.exists())
            fileWork.fileWrite("sort.txt", "0");
        if(!file2.exists())
            fileWork.fileWrite("lowcapacity.txt", "");
        if(!file3.exists())
            fileWork.fileWrite("highcapacity.txt", "");
        if(!file4.exists())
            fileWork.fileWrite("lowprice.txt", "");
        if(!file5.exists())
            fileWork.fileWrite("highprice.txt", "");
        if(!file6.exists())
            fileWork.fileWrite("lowconsumption.txt", "");
        if(!file7.exists())
            fileWork.fileWrite("highconsumption.txt", "");
        if(!file8.exists())
            fileWork.fileWrite("checkboxes.txt", "");
        if(!file9.exists())
            fileWork.fileWrite("lowpower.txt", "");
        if(!file10.exists())
            fileWork.fileWrite("highpower.txt", "");

        CheckBox checkBox3 = findViewById(R.id.petrol); allCheckboxes[0] = checkBox3;
        CheckBox checkBox4 = findViewById(R.id.diz); allCheckboxes[1] = checkBox4;
        CheckBox checkBox5 = findViewById(R.id.autotrans); allCheckboxes[2] = checkBox5;
        CheckBox checkBox6 = findViewById(R.id.mechtrans);  allCheckboxes[3] = checkBox6;
        CheckBox checkBox7 = findViewById(R.id.wheelfour);  allCheckboxes[4] = checkBox7;
        CheckBox checkBox8 = findViewById(R.id.wheelfront);  allCheckboxes[5] = checkBox8;
        CheckBox checkBox9 = findViewById(R.id.wheelback);  allCheckboxes[6] = checkBox9;

        fileWork.fileRead("sort.txt");
        fileWork.fileRead("lowprice.txt");
        fileWork.fileRead("highprice.txt");
        fileWork.fileRead("lowcapacity.txt");
        fileWork.fileRead("highcapacity.txt");
        fileWork.fileRead("lowconsumption.txt");
        fileWork.fileRead("highconsumption.txt");
        fileWork.fileRead("lowpower.txt");
        fileWork.fileRead("highpower.txt");
        fileWork.fileRead("checkboxes.txt");

        String temp = sort[0];
        sort[0] = sort[index];
        sort[index] = temp;

        Spinner spinner = (Spinner) findViewById(R.id.sort);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sort);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        EditText editText1 = findViewById(R.id.lowprice);
        EditText editText2 = findViewById(R.id.highprice);
        EditText editText3 = findViewById(R.id.lowcapacity);
        EditText editText4 = findViewById(R.id.highcapacity);
        EditText editText5 = findViewById(R.id.lowconsumption);
        EditText editText6 = findViewById(R.id.highconsumption);
        EditText editText7 = findViewById(R.id.lowpower);
        EditText editText8 = findViewById(R.id.highpower);

        editText1.setText(lowPrice); editTextListener(editText1);
        editText2.setText(highPrice); editTextListener(editText2);
        editText3.setText(lowCapacity); editTextListener(editText3);
        editText4.setText(highCapacity); editTextListener(editText4);
        editText5.setText(lowConsumption); editTextListener(editText5);
        editText6.setText(highConsumption); editTextListener(editText6);
        editText7.setText(lowPower); editTextListener(editText7);
        editText8.setText(highPower); editTextListener(editText8);

        for(int i = 0; i < allCheckboxes.length; i++){
            editCheckBoxListener(allCheckboxes[i]);
        }
        filtration();
    }

    public  void apply(View view){
        Spinner spinner = (Spinner) findViewById(R.id.sort);
        String item = (String)spinner.getItemAtPosition(index);
        for(int i = 0; i < sortbase.length; i++){
            if(item == sortbase[i]) {
                index = i;
                break;
            }
        }

        FileWork fileWork = new FileWork(getApplicationContext());
        fileWork.fileWrite("sort.txt", Integer.toString(index));
        fileWork.fileRead("sort.txt");

        if (sortbase[index] == "По рейтингу")
            HeapSort.sort(new Function<Car, Integer>() {
                @Override
                public Integer apply(Car input) {
                    return input.getRate();
                }
            }, false);
        else if (sortbase[index] == "От дешевых к дорогим")
            HeapSort.sort(new Function<Car, Integer>() {
                @Override
                public Integer apply(Car input) {
                    return input.getPrice();
                }
            }, true);
        else
            HeapSort.sort(new Function<Car, Integer>() {
                @Override
                public Integer apply(Car input) {
                    return input.getPrice();
                }
            }, false);

        String string = "";
        for(int i = 0; i < MainActivity.cars.size(); i++){
            string += Integer.toString(MainActivity.cars.get(i).getIndex()) + " ";
        }
        fileWork.fileWrite("carsIndex.txt", string);
        string = "";
        for(int i = 0; i < allCheckboxes.length; i++){
            if(allCheckboxes[i].isChecked())
                string += Integer.toString(i) + " ";
        }
        fileWork.fileWrite("checkboxes.txt", string);

        MainActivity.cars.clear();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private  boolean checkCheckBoxes(CheckBox[]checkBoxes, Car car, Function<Car, String>function){
        boolean nothingIsChecked = true;
        for(int i = 0; i < checkBoxes.length; i++){
            if(checkBoxes[i].isChecked())
                nothingIsChecked = false;
        }
        if(nothingIsChecked)
            return  true;
        else{
            for(int i = 0; i < checkBoxes.length; i++){
                if(checkBoxes[i].isChecked() && function.apply(car).equals(checkBoxes[i].getText()))
                    nothingIsChecked = true;
            }
            if(nothingIsChecked)
                return true;
            else
                return false;
        }
    }

    private void editTextListener(final EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtration();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private  void editCheckBoxListener(CheckBox checkBox){
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filtration();
            }
        });
    }

    private void writeNumbersToFile(EditText editText, String filename){
        FileWork fileWork = new FileWork(getApplicationContext());
        if(editText.getText().toString().endsWith("."))
            fileWork.fileWrite(filename, editText.getText().toString() + "0");
        else
            fileWork.fileWrite(filename, editText.getText().toString());
    }

    private void filtration(){
        EditText editText1 = findViewById(R.id.lowprice); writeNumbersToFile(editText1, "lowprice.txt");
        EditText editText2 = findViewById(R.id.highprice); writeNumbersToFile(editText2, "highprice.txt");
        EditText editText3 = findViewById(R.id.lowcapacity); writeNumbersToFile(editText3, "lowcapacity.txt");
        EditText editText4 = findViewById(R.id.highcapacity); writeNumbersToFile(editText4, "highcapacity.txt");
        EditText editText5 = findViewById(R.id.lowconsumption); writeNumbersToFile(editText5, "lowconsumption.txt");
        EditText editText6 = findViewById(R.id.highconsumption); writeNumbersToFile(editText6, "highconsumption.txt");
        EditText editText7 = findViewById(R.id.lowpower); writeNumbersToFile(editText7, "lowpower.txt");
        EditText editText8 = findViewById(R.id.highpower); writeNumbersToFile(editText8, "highpower.txt");
        FileWork fileWork = new FileWork(getApplicationContext());
        fileWork.fileRead("lowprice.txt");
        fileWork.fileRead("highprice.txt");
        fileWork.fileRead("lowcapacity.txt");
        fileWork.fileRead("highcapacity.txt");
        fileWork.fileRead("lowconsumption.txt");
        fileWork.fileRead("highconsumption.txt");
        fileWork.fileRead("lowpower.txt");
        fileWork.fileRead("highpower.txt");

        CheckBox checkBox3 = findViewById(R.id.petrol);
        CheckBox checkBox4 = findViewById(R.id.diz);
        CheckBox checkBox5 = findViewById(R.id.autotrans);
        CheckBox checkBox6 = findViewById(R.id.mechtrans);
        CheckBox checkBox7 = findViewById(R.id.wheelfour);
        CheckBox checkBox8 = findViewById(R.id.wheelback);
        CheckBox checkBox9 = findViewById(R.id.wheelfront);

        CheckBox[]checkFuel = {checkBox3, checkBox4};
        CheckBox[]checkTrans = {checkBox5, checkBox6};
        CheckBox[]checkWheelDrive = {checkBox7, checkBox8, checkBox9};

        MainActivity.cars.clear();
        cars.clear();

        if(lowPrice.length() == 0)
            lowPrice = "0";
        if(highPrice.length() == 0)
            highPrice ="1000000";
        if(Integer.parseInt(lowPrice) > Integer.parseInt(highPrice)){
            String temp = lowPrice;
            lowPrice = highPrice;
            highPrice = temp;
        }

        if(lowCapacity.length() == 0)
            lowCapacity = "0";

        if(highCapacity.length() == 0)
            highCapacity ="10";

        if(Double.parseDouble(lowCapacity) >Double.parseDouble(highCapacity)){
            String temp = lowCapacity;
            lowCapacity = highCapacity;
            highCapacity = temp;
        }

        if(lowConsumption.length() == 0)
            lowConsumption = "0";

        if(highConsumption.length() == 0)
            highConsumption = "70";

        if(Double.parseDouble(lowConsumption) > Double.parseDouble( highConsumption)){
            String temp = lowConsumption;
            lowConsumption =  highConsumption;
            highConsumption = temp;
        }

        if(lowPower.length() == 0)
            lowPower = "0";
        if(highPower.length() == 0)
            highPower ="1000";
        if(Integer.parseInt(lowPower) > Integer.parseInt(highPower)){
            String temp = lowPower;
            lowPower = highPower;
            highPower = temp;
        }

        for(int i = 0; i  < MainActivity.carsArray.length; i++){
            if(MainActivity.carsArray[i].getPrice() >= Integer.parseInt(lowPrice) &&
                    MainActivity.carsArray[i].getPrice() <= Integer.parseInt(highPrice) &&
                    MainActivity.carsArray[i].getCapacity() >= Double.parseDouble(lowCapacity) &&
                    MainActivity.carsArray[i].getCapacity() <= Double.parseDouble(highCapacity) &&
                    MainActivity.carsArray[i].getConsumption() >= Double.parseDouble(lowConsumption) &&
                    MainActivity.carsArray[i].getConsumption() <= Double.parseDouble(highConsumption) &&
                    MainActivity.carsArray[i].getPower() >= Integer.parseInt(lowPower) &&
                    MainActivity.carsArray[i].getPower() <= Integer.parseInt(highPower))
                cars.add(MainActivity.carsArray[i]);
        }

        for(int i = 0; i < cars.size(); i++){
            if(checkCheckBoxes(checkFuel, cars.get(i), new Function<Car, String>() {
                        @Override
                        public String apply(Car input) {
                            return input.getFuel();
                        }
                    }) &&
                    checkCheckBoxes(checkTrans, cars.get(i), new Function<Car, String>() {
                        @Override
                        public String apply(Car input) {
                            return input.getTransmission();
                        }
                    }) &&
                    checkCheckBoxes(checkWheelDrive, cars.get(i), new Function<Car, String>() {
                        @Override
                        public String apply(Car input) {
                            return input.getWheelDrive();
                        }
                    }))
                MainActivity.cars.add(cars.get(i));
        }
        Button button = findViewById(R.id.showresults);
        button.setText("Показать результаты" + " (" + Integer.toString(MainActivity.cars.size()) + ")");
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

