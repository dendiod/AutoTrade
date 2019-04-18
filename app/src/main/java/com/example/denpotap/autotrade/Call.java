package com.example.denpotap.autotrade;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

public class Call {
    private Context context;
    public Call(Context context){
        this.context = context;
    }
    public void dial(View view, Activity activity)throws SecurityException{
        try {
            String toDial = "tel:" + "0974804887";
            context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(toDial)));
        }
        catch (SecurityException ex){
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CALL_PHONE},
                    1);
        }
    }


    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults, Activity activity) throws SecurityException {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    String toDial = "tel:" + "0974804887";
                    context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(toDial)));
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(activity, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
