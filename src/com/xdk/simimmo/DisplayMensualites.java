package com.xdk.simimmo;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.xdk.simimmo.model.Pret;

public class DisplayMensualites extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(
        		ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_display_mensualites);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Pret p = Mensualites.currentPret;
		DecimalFormat df = new DecimalFormat();
		df.setRoundingMode(RoundingMode.HALF_UP);
		df.setMaximumFractionDigits(2);
		EditText capital = (EditText) findViewById(R.id.capital);
		capital.setText(df.format(p.getCapital()));
		EditText duree = (EditText) findViewById(R.id.resduree);
		duree.setText(""+p.getDuree());
		EditText taux = (EditText) findViewById(R.id.restaux);
		taux.setText(""+p.getTaux()*100);
		EditText ass = (EditText) findViewById(R.id.resass);
		ass.setText(df.format(p.getAssuranceValue()));
		TableLayout table = (TableLayout) findViewById(R.id.tablelayout);
		Map<Long,Double> map = p.getAllMensualites();
		Map<Long,Double> couts = p.getAllCouts();
		if(map == null || map.size() == 0){
			map = new HashMap<Long,Double>();
			double m = p.getMensualites(p.getDuree());
			map.put(p.getDuree(),m);
			couts = new HashMap<Long,Double>();
			couts.put(p.getDuree(),p.getCout(p.getDuree(),m));
		}
		
		int index = 0;

		TableRow row= new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        row.setDuplicateParentStateEnabled(true);
        
        row.addView(Utils.createTableTextView(this, "Durée\t"));
        row.addView(Utils.createTableTextView(this, "Mensualités\t"));
        row.addView(Utils.createTableTextView(this, "Cout"));
        row.addView(Utils.createTableTextView(this, "Amort."));
        table.addView(row,index);
        index++;
        
		for(Map.Entry<Long, Double> entry : map.entrySet()){
			row= new TableRow(this);
	        row.setLayoutParams(lp);
	        row.setDuplicateParentStateEnabled(true);
	        row.addView(Utils.createTableTextView(this, ""+entry.getKey()));
	        row.addView(Utils.createTableTextView(this, df.format(entry.getValue()+p.getAssuranceValue())));
	        row.addView(Utils.createTableTextView(this, df.format(couts.get(entry.getKey())+(p.getAssuranceValue()*entry.getKey()))));
	        DataButton b = new DataButton(this,new Pret(p.getCapital(),p.getTaux(),entry.getKey(),p.getAssuranceTaux()));
	        b.setText("A");
	        row.addView(b);
	        table.addView(row,index);
	        index++;
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
		getMenuInflater().inflate(R.menu.display_mensualites, menu);
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
