package com.example.denpotap.autotrade;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
    }

    public void dial(View view)throws SecurityException{
        if(Build.VERSION.SDK_INT >= 23){
            Call call = new Call(getApplicationContext());
            call.dial(view, ContactsActivity.this);
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
        call.onRequestPermissionsResult(requestCode, permissions, grantResults, ContactsActivity.this);
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
