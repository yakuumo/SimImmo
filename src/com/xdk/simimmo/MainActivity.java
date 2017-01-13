package com.xdk.simimmo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(
        		ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        final ImageView menspic = (ImageView) findViewById(R.id.menspic);
        Animation hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.slide_left_to_right);
        menspic.startAnimation(hyperspaceJump);
        menspic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent i = new Intent(getApplicationContext(),MensualitesByRange.class);
                startActivity(i);
            }
        });
        
        final ImageView cappic = (ImageView) findViewById(R.id.cappic);
        hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.slide_right_to_left);
        cappic.startAnimation(hyperspaceJump);
        cappic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent i = new Intent(getApplicationContext(),Capital.class);
                startActivity(i);
            }
        });
        
        final ImageView lignespic = (ImageView) findViewById(R.id.butt_lignes);
        hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.slide_left_to_right);
        lignespic.startAnimation(hyperspaceJump);
        lignespic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent i = new Intent(getApplicationContext(),Lignes_de_Pret.class);
                startActivity(i);
            }
        });
        
        /*final Button button = (Button) findViewById(R.id.okmens);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent i = new Intent(getApplicationContext(),Mensualites.class);
                startActivity(i);
            }
        });
        
        final Button button2 = (Button) findViewById(R.id.okmensbyr);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent i = new Intent(getApplicationContext(),MensualitesByRange.class);
                startActivity(i);
            }
        });
        
        final Button button3 = (Button) findViewById(R.id.capemp);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent i = new Intent(getApplicationContext(),Capital.class);
                startActivity(i);
            }
        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    
}
