package com.xdk.simimmo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.xdk.simimmo.model.Calculs;
import com.xdk.simimmo.model.Pret;

public class MensualitesByRange extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(
        		ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_mensualites_by_range);
		
		final ImageButton button = (ImageButton) findViewById(R.id.okbyr);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	EditText montant = (EditText) findViewById(R.id.capitalbyr);
            	double capital = Calculs.parseDouble(montant.getText().toString());
            	EditText dureestart = (EditText) findViewById(R.id.dureefrom);
        		long timestart = Calculs.parseLong(dureestart.getText().toString());
        		EditText dureeend = (EditText) findViewById(R.id.dureeto);
        		long timeend = Calculs.parseLong(dureeend.getText().toString());
        		RadioButton annees = (RadioButton) findViewById(R.id.anneesbyr);
        		long time = timestart;
        		if(annees.isChecked()){
        			time *= 12;
        		} 
        		EditText taux = (EditText) findViewById(R.id.tauxbyr);
        		double t = Calculs.parseDouble(taux.getText().toString().replace(",", "."))/100;
        		EditText ass = (EditText) findViewById(R.id.assbyr);
        		double a = Calculs.parseDouble(ass.getText().toString().replace(",", "."))/100;
        		Mensualites.currentPret = new Pret(capital,t,time,a);
        		List<Long> list = new ArrayList<Long>();
        		list.add(time);
        		for(long i = timestart+1;i<=timeend;i++){
        			long newtime = i;
        			if(annees.isChecked()){
        				newtime *= 12;
        			}
        			list.add(newtime);
        		}
        		
        		Mensualites.currentPret.addAllCouts(list,Mensualites.currentPret.addAllMensualites(list));
            	Intent i = new Intent(getApplicationContext(),DisplayMensualites.class);
                startActivity(i);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mensualites_by_range, menu);
		return true;
	}

}
