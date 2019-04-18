package com.example.denpotap.autotrade;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.constraint.ConstraintLayout;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class CheckNetwork {
    private  Context context;
    public CheckNetwork(Context context){
        this.context = context;
    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public  void updateButtonLocation(Button button, ImageView imageView){
        int x = (int)(context.getResources().getDisplayMetrics().density * context.getResources().getConfiguration().screenWidthDp);
        int y = (int)(context.getResources().getDisplayMetrics().density * context.getResources().getConfiguration().screenHeightDp);
        button.setX(x/2 - 49 * context.getResources().getDisplayMetrics().density);
        button.setY(y/3*2);
        imageView.setLayoutParams(new ConstraintLayout.LayoutParams(x, y/3*2));
    }

    public  void deleteButton(Button button, ImageView imageView){
        if (isOnline()) {
            ViewGroup viewGroup = (ViewGroup) button.getParent();
            viewGroup.removeView(button);
            viewGroup.removeView(imageView);
        }
    }
}
