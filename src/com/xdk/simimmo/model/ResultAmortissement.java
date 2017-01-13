package com.xdk.simimmo.model;

import java.util.List;
import java.util.Vector;

public class ResultAmortissement {

	private Vector<Vector<Vector<Double>>> amortTables = null;
	private List<Pret> prets = null;
	private double mensualiteOptimale = 0.0;
	private double cout = 0.0;
	
	public ResultAmortissement() {
	}
	
	
	public ResultAmortissement(Vector<Vector<Vector<Double>>> amortTables,
			List<Pret> prets, double mensualiteOptimale,double cout) {
		super();
		this.amortTables = amortTables;
		this.prets = prets;
		this.mensualiteOptimale = mensualiteOptimale;
		this.cout = cout;
	}

	public double getCout() {
		return cout;
	}
	
	public void setCout(double cout) {
		this.cout = cout;
	}
	public Vector<Vector<Vector<Double>>> getAmortTables() {
		return amortTables;
	}

	public void setAmortTables(Vector<Vector<Vector<Double>>> amortTables) {
		this.amortTables = amortTables;
	}

	public List<Pret> getPrets() {
		return prets;
	}

	public void setPrets(List<Pret> prets) {
		this.prets = prets;
	}

	public double getMensualiteOptimale() {
		return mensualiteOptimale;
	}

	public void setMensualiteOptimale(double mensualiteOptimale) {
		this.mensualiteOptimale = mensualiteOptimale;
	}
	
	
	
}
