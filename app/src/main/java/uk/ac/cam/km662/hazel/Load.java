package uk.ac.cam.km662.hazel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Load extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               /* Create an Intent that will start the Login Activity. */
                Intent intent = new Intent(Load.this, Login.class);
                Load.this.startActivity(intent);
                Load.this.finish();
            }
        }, 2000);
    }








}
