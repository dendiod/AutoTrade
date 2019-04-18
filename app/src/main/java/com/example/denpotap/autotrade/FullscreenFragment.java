package com.example.denpotap.autotrade;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.*;

public class FullscreenFragment extends android.support.v4.app.Fragment {
    private int pageNumber;
    public static FullscreenFragment newInstance(int page) {
        FullscreenFragment fragment = new FullscreenFragment();
        Bundle args=new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_blank_fullscreen, container, false);
        TouchImageView touchImageView = result.findViewById(R.id.touchimage);
        TextView textView = result.findViewById(R.id.numberphoto);
        if(DetailsActivity.activity.equals("main")) {
            Picasso.get().load(MyArrays.arrays[MainActivity.cars.get(MainActivity.index).getIndex()][pageNumber]).into(touchImageView);
            textView.setText("Фото " + Integer.toString(pageNumber + 1) + " из " + Integer.toString(MyArrays.arrays[MainActivity.cars.get(MainActivity.index).getIndex()].length));
        }
        else{
            Picasso.get().load(MyArrays.arrays[FavouritesActivity.cars.get(MainActivity.index).getIndex()][pageNumber]).into(touchImageView);
            textView.setText("Фото " + Integer.toString(pageNumber + 1) + " из " + Integer.toString(MyArrays.arrays[FavouritesActivity.cars.get(MainActivity.index).getIndex()].length));
        }
        touchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Фото " + Integer.toString(pageNumber + 1) + " из " + Integer.toString(MyArrays.arrays[MainActivity.cars.get(MainActivity.index).getIndex()].length), Toast.LENGTH_SHORT).show();
            }
        });
        return result;
    }
}
