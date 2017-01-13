package com.xdk.simimmo;

import com.xdk.simimmo.model.Pret;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class DataButton extends Button {
	
	final Pret pret;

	public DataButton(Context context) {
		super(context);
		pret = null;
		// TODO Auto-generated constructor stub
	}
	
	public DataButton(Context context,Pret pret) {
		super(context);
		this.pret=pret;
		setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	Amortissements.pret = DataButton.this.pret;
            	Intent i = new Intent(DataButton.this.getContext(),Amortissements.class);
            	DataButton.this.getContext().startActivity(i);
            }
        });
	}

	public DataButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		pret = null;
	}

	public DataButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		pret = null;
	}

	public Pret getPret() {
		return pret;
	}
}
