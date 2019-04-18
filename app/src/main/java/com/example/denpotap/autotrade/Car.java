package com.example.denpotap.autotrade;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageHelper;
import android.support.v7.widget.TintContextWrapper;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class Car extends AppCompatImageButton {
    public Car(Context context) {
        this(context, null);
    }

    public Car(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.imageButtonStyle);
    }

    public Car(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int price;
    private int rate;
    private String brand;
    private String transmission;
    private String wheelDrive;
    private String fuel;
    private double capacity;
    private double consumption;
    private int power;
    private int index;
    public  void setPrice(int price)
    {
        this.price = price;
    }
    public  int getPrice()
    {
        return price;
    }
    public  void setRate(int rate)
    {
        this.rate = rate;
    }
    public  int getRate()
    {
        return rate;
    }
    public void setBrand(String brand){this.brand = brand;};
    public String getBrand(){return brand;}
    public void setTransmission(String transmission){this.transmission = transmission;}
    public String getTransmission(){return transmission;}
    public void setFuel(String fuel){this.fuel = fuel;}
    public String getFuel(){return fuel;}
    public void setCapacity(double capacity){this.capacity = capacity;}
    public double getCapacity(){return capacity;}
    public void setConsumption(double consumption){this.consumption = consumption;}
    public double getConsumption(){return consumption;}
    public void setPower(int power){this.power = power;}
    public int getPower(){return power;}
    public  void setWheelDrive(String wheelDrive){this.wheelDrive = wheelDrive;}
    public  String getWheelDrive(){return  wheelDrive;}
    public  void setIndex(int index){this.index = index;}
    public  int getIndex() {return index;}
}
