package com.xdk.simimmo.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;


public class Pret {
	public enum TIME_UNITS{
		MOIS,
		ANS
	}
	
	private double capital;
	private double taux;
	private long duree;
	private Map<Long,Double> mensualites = null;
	private Map<Long,Double> couts = null;
	private double assurance = 0.0;
	
	public Pret() {
		super();
	}
	
	public Pret(double capital, double taux, long duree){
		this(capital,taux,duree,0.0);
	}
	
	public Pret(double capital, double taux, long duree,double assurance) {
		super();
		this.capital = capital;
		this.taux = taux;
		this.duree = duree;
		this.assurance = assurance;
		double m = addMensualites(duree);
		addCouts(duree,m);
		
	}
	
	public double getAssuranceTaux(){
		return assurance;
	}
	
	public double getAssuranceValue() {
		return capital*assurance/12;
	}
	
	public double getCapital() {
		return capital;
	}
	public void setCapital(double capital) {
		this.capital = capital;
	}
	public double getTaux() {
		return taux;
	}
	public void setTaux(double taux) {
		this.taux = taux;
	}
	public long getDuree() {
		return duree;
	}
	public void setDuree(long duree) {
		this.duree = duree;
	}
	
	public Map<Long,Double> getAllMensualites(){
		if(mensualites == null){
			mensualites = new ConcurrentSkipListMap<Long, Double>();
		}
		return mensualites;
	}
	
	public Map<Long,Double> getAllCouts(){
		if(couts == null){
			couts = new ConcurrentSkipListMap<Long, Double>();
		}
		return couts;
	}
	
	public double getMensualites(long duree){		
		return addMensualites(duree);
	}
	
	public double getCout(long duree,double mensualites){		
		return addCouts(duree,mensualites);
	}
	
	
	public double addMensualites(long duree){
		getAllMensualites();
		Double m = mensualites.get(duree);
		if(m == null){
			m = Calculs.calculMensualites(this.capital,duree,this.taux);
			mensualites.put(duree,m);
		}
		return m;
	}
	
	public double addCouts(long duree,double mensualite){
		getAllCouts();
		Double c = couts.get(duree);
		if(c == null){
			c = Calculs.calculCout(this.capital,duree,mensualite);
			couts.put(duree,c);
		}
		return c;
	}
	
	public Map<Long,Double> addAllMensualites(List<Long> duree){
		Map<Long,Double> results = new HashMap<Long,Double>();
		for (long l : duree) {
			results.put(l,addMensualites(l));
		}
		return results;
	}
	
	public Map<Long,Double> addAllCouts(List<Long> duree,Map<Long,Double> mensualites){
		Map<Long,Double> results = new HashMap<Long,Double>();
		for (long l : duree) {
			double m = mensualites.get(l);
			results.put(l,addCouts(l,m));
		}
		return results;
	}

}
