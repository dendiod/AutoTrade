package com.example.denpotap.autotrade;

import android.arch.core.util.Function;

public class HeapSort {
    public static void sort(Function<Car, Integer>function, boolean up){
        int n = MainActivity.cars.size();
        for (int i = n / 2 - 1; i >= 0; i--){
            heapify(n, i, function, up);
        }

        for (int i=n-1; i>=0; i--){
            Car temp = MainActivity.cars.get(0);
            MainActivity.cars.set(0, MainActivity.cars.get(i));
            MainActivity.cars.set(i, temp);
            heapify(i, 0, function, up);
        }
    }

    private static  void heapify(int n, int i, Function<Car, Integer>function, boolean up){
        int largest = i;
        int l = 2*i + 1;
        int r = 2*i + 2;
        if(up == true) {
            if (l < n && function.apply(MainActivity.cars.get(l)) > function.apply(MainActivity.cars.get(largest)))
                largest = l;
            if (r < n && function.apply(MainActivity.cars.get(r)) > function.apply(MainActivity.cars.get(largest)))
                largest = r;
        }
        else{
            if (l < n && function.apply(MainActivity.cars.get(l)) < function.apply(MainActivity.cars.get(largest)))
                largest = l;
            if (r < n && function.apply(MainActivity.cars.get(r)) < function.apply(MainActivity.cars.get(largest)))
                largest = r;
        }

        if (largest != i){
            Car swap = MainActivity.cars.get(i);
            MainActivity.cars.set(i, MainActivity.cars.get(largest));
            MainActivity.cars.set(largest, swap);
            heapify(n, largest, function, up);
        }
    }

}
