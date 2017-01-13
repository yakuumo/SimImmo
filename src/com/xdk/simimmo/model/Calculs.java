package com.xdk.simimmo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;



public class Calculs {
	
	

	public static double calculMensualites(double capital,long duree,double taux ){
		double resultat = 0.0;
		double montant = capital;
		double time = duree*1.0;
		double i = taux/12.0;
		
		if(taux == 0.0){
			resultat = capital/time;
		} else {		
			resultat = (montant*i)/(1-(1/Math.pow(1+i,time)));
		}
		
		return resultat;
	}
	
	public static double calculCout(double capital,long duree,double mensualites ){
		double montant = capital;
		double time = duree*1.0;
		
		return (mensualites*time)-montant;
	}
	
	public static Vector<Vector<Double>> caclulAmortissement(double capital,long duree,double mensualites,double taux,double assurance){
		Vector<Vector<Double>> results = new Vector<Vector<Double>>();
		double reste = capital;
		double t = taux/12.0;
		double ir = capital * t;
		double a = capital*assurance/12;
		for(long i = 1;i<=duree;i++){
			Vector<Double> row = new Vector<Double>();
			row.add(i*1.0);			
			if(reste >= mensualites){
				reste = reste - (mensualites - ir);
				row.add(mensualites+a);
				row.add(a);
				row.add(ir);
				row.add(reste);
			} else {
				row.add(reste+a);
				row.add(a);
				row.add(ir);
				row.add(0.0);
			}
			
			results.add(row);
			ir = reste * t;
		}
		
		return results;
	}
	
	public static double calculCapital(double mensualite,double taux,long duree,double assurance){
		double time = duree*1.0;
		double t = taux/12.0;
		if(t == 0.0){
			return (mensualite*duree)/(1+(assurance*duree/12));
		} else {
			double common = (1-(1/Math.pow(1+t,time)));
			double assurancepart = 1+((assurance/taux)*common);
			return (mensualite/t)*common/assurancepart;
		}
	}
	
	public static double parseDouble(String text){
		try{
			return Double.parseDouble(text);
		}catch(Exception e){
			return 0.0;
		}
	}
	
	public static long parseLong(String text){
		try{
			return Long.parseLong(text);
		}catch(Exception e){
			return 0;
		}
	}
	
	public static ResultAmortissement caclulAmortissement(List<Pret> prets,
			double mensmax) {
		ResultAmortissement objToReturn = new ResultAmortissement();
		Vector<Vector<Vector<Double>>> results = new Vector<Vector<Vector<Double>>>(prets.size());
		
		double mens_max_optim = mensmax;

		double last_mens = 0.0;
		double cout = 0.0;
		boolean stop = false;
		while (last_mens < mens_max_optim) {
			long mois = 0;
			Pret newp = null;
			Pret pret_encours = prets.get(0);
			double capital_encours = pret_encours.getCapital();
			double mens_encours = pret_encours.getMensualites(pret_encours
					.getDuree());
			List<Pret> prets_restant = new ArrayList<Pret>();
			prets_restant.addAll(prets);
			double cout_current = 0.0;
			Vector<Vector<Vector<Double>>> currentresults = new Vector<Vector<Vector<Double>>>(prets.size());
			int indexpretencours = 0;
			for(int i = 0;i<prets.size();i++){
				currentresults.add(new Vector<Vector<Double>>());
			}
			while (prets_restant.size() > 0) {
				prets_restant.remove(0);
				double pretsuivant = 0.0;
				boolean hasnextpret = prets_restant.size() > 0;
				Vector<Vector<Double>> res_pret_encours = currentresults.get(indexpretencours);
				Vector<Vector<Double>> res_pret_next = null;
				if(hasnextpret){
					res_pret_next = currentresults.get(indexpretencours+1);
				}
				while (capital_encours > 0.0) {
					Vector<Double> row_pret_encours = new Vector<Double>();
					Vector<Double> row_pret_next = null;
					mois++;
					double mensualite = mens_encours
							+ pret_encours.getAssuranceValue();
					
					
					
					cout_current += pret_encours.getAssuranceValue();
					cout_current += capital_encours * pret_encours.getTaux() / 12;

					
					if(hasnextpret){
						row_pret_next = new Vector<Double>();
					}
					double assurance_next = 0.0;
					double interet_next = 0.0;
					double capital_next = 0.0;
					for (int i = 0; i < prets_restant.size(); i++) {
						Pret pret = prets_restant.get(i);
						double capitalpourinterets = pret.getCapital();
						if (i == 0) {
							capitalpourinterets -= pretsuivant;
							assurance_next = pret.getAssuranceValue();
							interet_next = capitalpourinterets * pret.getTaux() / 12;
							capital_next = pret.getCapital();
						}
						cout_current += (capitalpourinterets * pret.getTaux() / 12)
								+ pret.getAssuranceValue();
						mensualite += (capitalpourinterets * pret.getTaux() / 12)
								+ pret.getAssuranceValue();
						
						if (i > 0) {
							Vector<Vector<Double>> res_pret_justforir = currentresults.get(indexpretencours+i+1);
							Vector<Double> row = new Vector<Double>();
							row.add(mois*1.0);
							row.add(pret.getAssuranceValue()+capitalpourinterets * pret.getTaux() / 12);
							row.add(pret.getAssuranceValue());
							row.add(capitalpourinterets * pret.getTaux() / 12);
							row.add(capitalpourinterets);
							res_pret_justforir.add(row);
						}
						
					}
					
					if(mensualite > mens_max_optim){
						System.out.println("Impossible mensualite Intermédiaire : "+mensualite +" > max : "+mens_max_optim);
						stop = true;
						break;
					}

					double utilisable = mens_max_optim - mensualite;
					pretsuivant += utilisable;
					if(hasnextpret){
						row_pret_next.add(mois*1.0);
						row_pret_next.add(utilisable+assurance_next+interet_next);
						row_pret_next.add(assurance_next);
						row_pret_next.add(interet_next);
						row_pret_next.add(capital_next-pretsuivant);
						res_pret_next.add(row_pret_next);
					}
					
					
					capital_encours -= mens_encours
							- (capital_encours * pret_encours.getTaux() / 12);
					if (capital_encours < 1e-5) {
						capital_encours = 0.0;
					} else if (mens_encours > capital_encours) {
						last_mens = capital_encours;
					}
					
					row_pret_encours.add(mois*1.0);
					row_pret_encours.add(mens_encours + pret_encours.getAssuranceValue());
					row_pret_encours.add(pret_encours.getAssuranceValue());
					row_pret_encours.add(capital_encours * pret_encours.getTaux() / 12);
					row_pret_encours.add(capital_encours);
					res_pret_encours.add(row_pret_encours);
					
					//System.out.println(mois + "\t, " + mens_max_optim + "\t, "
						//	+ mensualite + "\t, " + capital_encours + "\t, "
							//+ pretsuivant);
				}
				if(stop){
					mens_max_optim += 10;
					break;
				}
				indexpretencours++;
				if (prets_restant.size() > 0) {
					pret_encours = prets_restant.get(0);
					capital_encours = pret_encours.getCapital() - pretsuivant;
					newp = new Pret(capital_encours, pret_encours.getTaux(),
							pret_encours.getDuree() - mois,
							pret_encours.getAssuranceTaux());
					mens_encours = newp.getMensualites(pret_encours.getDuree()
							- mois);
					
					if(Double.isInfinite(mens_encours)){
						System.out.println("Montage impossible, dernier pret : "+newp.getCapital());
						stop = true;
						break;
					}
					
					if(prets_restant.size() == 1){
						if(Math.abs(mens_encours + pret_encours.getAssuranceValue()- mens_max_optim) > 30.0){
							System.out.println("Montage pas optimisé : dernière mensualité : "+(mens_encours + pret_encours.getAssuranceValue())+" < "+mens_max_optim);
							mens_max_optim -= 10;
							if((mens_encours + pret_encours.getAssuranceValue() > mens_max_optim)){
								stop = true;
							}
							break;
						}
					}
				} else {
					//System.out.println("Dernière mensualité = " + last_mens + " - Cout = "+cout);
					if(last_mens < mens_max_optim){
						mens_max_optim -= 10;
						results = currentresults;
						cout = cout_current;
					} else if(Math.abs(last_mens-mens_max_optim)>30){
						mens_max_optim += 10;
						break;
					} else {
						results = currentresults;
						cout = cout_current;
					}
				}
			}
			if(stop) break;
		}
		objToReturn.setAmortTables(results);
		objToReturn.setPrets(prets);
		objToReturn.setMensualiteOptimale(mens_max_optim);
		objToReturn.setCout(cout);
		//System.out.println("Mensualité optimale = " + mens_max_optim);
		return objToReturn;
	}
	
}
