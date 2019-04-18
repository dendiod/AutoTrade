package com.example.denpotap.autotrade;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWork {
   private Context context;
    public FileWork(Context context) {
        this.context = context;
    }
    public  void fileWrite(String filename, String writetext){
            FileOutputStream fos = null;
            try {
                fos = context.openFileOutput(filename, context.MODE_PRIVATE);
                fos.write(writetext.getBytes());
            }
            catch(IOException ex) {
            }
            finally{
                try{
                    if(fos!=null)
                        fos.close();
                }
                catch(IOException ex){
                }
            }
    }

    public  void fileRead(String filename){
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(filename);
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            String str = new String(bytes);
            if(filename == "sort.txt")
                FiltrActivity.index = Integer.parseInt(str);
            else if(filename == "lowprice.txt")
                FiltrActivity.lowPrice = str;
            else if(filename == "highprice.txt")
                FiltrActivity.highPrice = str;
            else if(filename == "lowcapacity.txt")
                FiltrActivity.lowCapacity = str;
            else if(filename == "highcapacity.txt")
                FiltrActivity.highCapacity = str;
            else if(filename == "lowconsumption.txt")
                FiltrActivity.lowConsumption = str;
            else if(filename == "highconsumption.txt")
                FiltrActivity.highConsumption = str;
            else if(filename == "lowpower.txt")
                FiltrActivity.lowPower = str;
            else if(filename == "highpower.txt")
                FiltrActivity.highPower = str;

            else {
                char[] txt = str.toCharArray();
                str = "";
                int k = 0;
                for (int i = 0; i < txt.length; i++) {
                    if (txt[i] != ' ')
                        str += Character.toString(txt[i]);
                    if (txt[i] == ' ' || i == txt.length - 1) {
                        if(filename == "checkboxes.txt")
                            FiltrActivity.allCheckboxes[Integer.parseInt(str)].setChecked(true);
                        else if(filename == "carsIndex.txt")
                            MainActivity.cars.add(MainActivity.carsArray[Integer.parseInt(str)]);
                        else if(filename == "favourites.txt")
                            DetailsActivity.numbers.add(Integer.parseInt(str));
                        str = "";
                    }
                }
            }
        }
        catch(IOException ex) {
        }
        finally{
            try{
                if(fis!=null)
                    fis.close();
            }
            catch(IOException ex){
            }
        }
    }
}
