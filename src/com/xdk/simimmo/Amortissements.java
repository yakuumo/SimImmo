package com.xdk.simimmo;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Vector;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.xdk.simimmo.model.Calculs;
import com.xdk.simimmo.model.Pret;

public class Amortissements extends Activity {
	
	public static Pret pret = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(
        		ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_amortissements);
		// Show the Up button in the action bar.
		setupActionBar();
		
		if(pret != null){
			TableLayout table = (TableLayout) findViewById(R.id.tablayout);
			Vector<Vector<Double>> amort = Calculs.caclulAmortissement(pret.getCapital(), pret.getDuree(), pret.getMensualites(pret.getDuree()), pret.getTaux(), pret.getAssuranceTaux());
			TableRow row= new TableRow(this);
			DecimalFormat df = new DecimalFormat();
			df.setRoundingMode(RoundingMode.HALF_UP);
			df.setMaximumFractionDigits(2);
	        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
	        row.setLayoutParams(lp);
	        row.setDuplicateParentStateEnabled(true);
	        int index = 0;
	        
	        row.addView(Utils.createTableHeaderTextView(this, "Mois"));
	        row.addView(Utils.createTableHeaderTextView(this, "Mens."));
	        row.addView(Utils.createTableHeaderTextView(this, "Assu."));
	        row.addView(Utils.createTableHeaderTextView(this, "Inter."));
	        row.addView(Utils.createTableHeaderTextView(this, "Reste"));
	        table.addView(row,index);
	        index++;
	        
	        for(Vector<Double> currentrow : amort){
	        	row= new TableRow(this);
	        	row.setLayoutParams(lp);
		        row.setDuplicateParentStateEnabled(true);
		        
		        row.addView(Utils.createTableTextView(this, df.format(currentrow.get(0))));
		        row.addView(Utils.createTableTextView(this, df.format(currentrow.get(1))));
		        row.addView(Utils.createTableTextView(this, df.format(currentrow.get(2))));
		        row.addView(Utils.createTableTextView(this, df.format(currentrow.get(3))));
		        row.addView(Utils.createTableTextView(this, df.format(currentrow.get(4))));
		        table.addView(row,index);
		        index++;
	        	
	        }
	        pret = null;
		} else if(Lignes_de_Pret.ra != null){
			Vector<Vector<Vector<Double>>> tabs = Lignes_de_Pret.ra.getAmortTables();
			double mens_optim = Lignes_de_Pret.ra.getMensualiteOptimale();
			double cout_total = Lignes_de_Pret.ra.getCout();
			TableLayout table = (TableLayout) findViewById(R.id.tablayout);
			
			TableRow row= new TableRow(this);
			DecimalFormat df = new DecimalFormat();
			df.setRoundingMode(RoundingMode.HALF_UP);
			df.setMaximumFractionDigits(2);
	        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
	        row.setLayoutParams(lp);
	        row.setDuplicateParentStateEnabled(true);
	        int index = 0;
	        
	        row.addView(Utils.createTableHeaderTextView(this, "Mois"));
	        row.addView(Utils.createTableHeaderTextView(this, "Mens."));
	        row.addView(Utils.createTableHeaderTextView(this, "Assu."));
	        row.addView(Utils.createTableHeaderTextView(this, "Inter."));
	        row.addView(Utils.createTableHeaderTextView(this, "Reste"));
	        table.addView(row,index);
	        index++;
	        for(Vector<Vector<Double>> amort : tabs){
		        for(Vector<Double> currentrow : amort){
		        	row= new TableRow(this);
		        	row.setLayoutParams(lp);
			        row.setDuplicateParentStateEnabled(true);
			        
			        row.addView(Utils.createTableTextView(this, df.format(currentrow.get(0))));
			        row.addView(Utils.createTableTextView(this, df.format(currentrow.get(1))));
			        row.addView(Utils.createTableTextView(this, df.format(currentrow.get(2))));
			        row.addView(Utils.createTableTextView(this, df.format(currentrow.get(3))));
			        row.addView(Utils.createTableTextView(this, df.format(currentrow.get(4))));
			        table.addView(row,index);
			        index++;
		        }
	        }
	        
	        row= new TableRow(this);
        	row.setLayoutParams(lp);
	        row.setDuplicateParentStateEnabled(true);
	        
	        row.addView(Utils.createTableHeaderTextView(this, "Mens."));
	        row.addView(Utils.createTableHeaderTextView(this, "Optim."));
	        row.addView(Utils.createTableHeaderTextView(this, df.format(mens_optim)));
	        row.addView(Utils.createTableHeaderTextView(this, "Cout"));
	        row.addView(Utils.createTableHeaderTextView(this, df.format(cout_total)));
	        table.addView(row,index);
			
			Lignes_de_Pret.ra = null;
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.amortissements, menu);
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
