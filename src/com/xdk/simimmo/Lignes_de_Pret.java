package com.xdk.simimmo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.xdk.simimmo.model.Calculs;
import com.xdk.simimmo.model.Pret;
import com.xdk.simimmo.model.ResultAmortissement;

public class Lignes_de_Pret extends Activity {
	
	public static ResultAmortissement ra = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(
        		ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_lignes_de__pret);
		// Show the Up button in the action bar.
		setupActionBar();
		
		final ImageButton button = (ImageButton) findViewById(R.id.ok_lignes);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	List<Pret> prets = new ArrayList<Pret>();
            	
            	EditText ass = (EditText) findViewById(R.id.ass_globale);
        		double a = Calculs.parseDouble(ass.getText().toString().replace(",", "."))/100;
        		
        		EditText mens_max_txt = (EditText) findViewById(R.id.mens_max);
            	double mens_max = Calculs.parseDouble(mens_max_txt.getText().toString());
            	
            	EditText montant1 = (EditText) findViewById(R.id.p1_cap);
            	double capital1 = Calculs.parseDouble(montant1.getText().toString());
            	
            	if(capital1 > 0.0){
            		EditText duree = (EditText) findViewById(R.id.p1_duree);
            		long timestart = Calculs.parseLong(duree.getText().toString())*12;
            		EditText taux = (EditText) findViewById(R.id.p1_taux);
            		double t = Calculs.parseDouble(taux.getText().toString().replace(",", "."))/100;
            		Pret p = new Pret(capital1,t,timestart,a);
            		prets.add(p);
            	}
            	
            	EditText montant2 = (EditText) findViewById(R.id.p2_cap);
            	double capital2 = Calculs.parseDouble(montant2.getText().toString());
            	
            	if(capital1 > 0.0){
            		EditText duree = (EditText) findViewById(R.id.p2_duree);
            		long timestart = Calculs.parseLong(duree.getText().toString())*12;
            		EditText taux = (EditText) findViewById(R.id.p2_taux);
            		double t = Calculs.parseDouble(taux.getText().toString().replace(",", "."))/100;
            		Pret p = new Pret(capital2,t,timestart,a);
            		prets.add(p);
            	}
            	
            	EditText montant3 = (EditText) findViewById(R.id.p3_cap);
            	double capital3 = Calculs.parseDouble(montant3.getText().toString());
            	
            	if(capital1 > 0.0){
            		EditText duree = (EditText) findViewById(R.id.p3_duree);
            		long timestart = Calculs.parseLong(duree.getText().toString())*12;
            		EditText taux = (EditText) findViewById(R.id.p3_taux);
            		double t = Calculs.parseDouble(taux.getText().toString().replace(",", "."))/100;
            		Pret p = new Pret(capital3,t,timestart,a);
            		prets.add(p);
            	}
            	
            	Lignes_de_Pret.ra = Calculs.caclulAmortissement(prets, mens_max);
            	
            	Intent i = new Intent(getApplicationContext(),Amortissements.class);
                startActivity(i);
            }
        });
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lignes_de__pret, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
