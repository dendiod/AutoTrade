package com.example.denpotap.autotrade;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.*;

public class PhotoFragment extends android.support.v4.app.Fragment {
    private int pageNumber;

    public static PhotoFragment newInstance(int page) {
        PhotoFragment fragment = new PhotoFragment();
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
        View result = inflater.inflate(R.layout.fragment_blank_photo, container, false);
        ImageButton imageButton = result.findViewById(R.id.hello);
        TextView textView = result.findViewById(R.id.photonumber);

        if(DetailsActivity.activity.equals("main")) {
            textView.setText(Integer.toString(pageNumber + 1) + "/" + Integer.toString(MyArrays.arrays[MainActivity.cars.get(MainActivity.index).getIndex()].length));
            Picasso.get().load(MyArrays.arrays[MainActivity.cars.get(MainActivity.index).getIndex()][pageNumber]).into(imageButton);
        }
        else{
            textView.setText(Integer.toString(pageNumber + 1) + "/" + Integer.toString(MyArrays.arrays[FavouritesActivity.cars.get(MainActivity.index).getIndex()].length));
            Picasso.get().load(MyArrays.arrays[FavouritesActivity.cars.get(MainActivity.index).getIndex()][pageNumber]).into(imageButton);
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullscreenActivity.pageNumber = pageNumber;
                Intent intent = new Intent(getActivity(), FullscreenActivity.class);
                startActivity(intent);
            }
        });
        return result;
    }
}
