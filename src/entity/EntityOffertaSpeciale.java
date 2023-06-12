package entity;

import java.util.Date;
import database.DBVisitaGuidata;
import java.util.ArrayList;
import database.DBOffertaSpeciale;

public class EntityOffertaSpeciale {

	private int idOfferta;
	private double percentualeSconto;
	private Date inizio;
	private Date fine;
	private ArrayList<EntityVisitaGuidata> visite;
	
	public EntityOffertaSpeciale() {
		
		super();
		this.visite=new ArrayList<EntityVisitaGuidata>();
		
	}
	
	public EntityOffertaSpeciale(int idOfferta) {
		
		DBOffertaSpeciale offerta=new DBOffertaSpeciale(idOfferta);
		this.idOfferta=offerta.getIdOfferta();
		this.percentualeSconto=offerta.getPercentualeSconto();
		this.inizio=offerta.getInizio();
		this.fine=offerta.getFine();
		this.visite=new ArrayList<EntityVisitaGuidata>();
		
		offerta.caricaVisiteOffertaDaDB();
		caricaVisiteOfferta(offerta);
		
	}
	
	public EntityOffertaSpeciale(DBOffertaSpeciale offerta) {
		
		this.idOfferta=offerta.getIdOfferta();
		this.percentualeSconto=offerta.getPercentualeSconto();
		this.inizio=offerta.getInizio();
		this.fine=offerta.getFine();
		
		offerta.caricaVisiteOffertaDaDB();
		caricaVisiteOfferta(offerta);
		
	}
	
	public EntityOffertaSpeciale(int idOfferta, double percentualeSconto, Date inizio, Date fine,
			ArrayList<EntityVisitaGuidata> visite) {
		super();
		this.idOfferta = idOfferta;
		this.percentualeSconto = percentualeSconto;
		this.inizio = inizio;
		this.fine = fine;
		this.visite = visite;
		
	}
	
	public int ScriviSuDB() {
		
		DBOffertaSpeciale offerta=new DBOffertaSpeciale();
		offerta.setIdOfferta(this.idOfferta);
		offerta.setPercentualeSconto(this.percentualeSconto);
		offerta.setInizio(this.inizio);
		offerta.setFine(this.fine);
		
		ArrayList<DBVisitaGuidata> visite=new ArrayList<DBVisitaGuidata>();
		
		for(int k=0; k<this.getVisite().size(); k++) {
		
			DBVisitaGuidata visita=new DBVisitaGuidata(this.getVisite().get(k));
			visite.add(visita);
			
		}
		
		offerta.setVisite(visite);
		
		int i=offerta.SalvaInDB();
		return i;
		
	}

	public void caricaVisiteOfferta(DBOffertaSpeciale offerta) {
		
		for(int i=0; i<offerta.getVisite().size(); i++) {
			
			EntityVisitaGuidata visita=new EntityVisitaGuidata(offerta.getVisite().get(i));
			this.visite.add(visita);
			
		}
		
	}

	public int getIdOfferta() {
		return idOfferta;
	}

	public void setIdOfferta(int idOfferta) {
		this.idOfferta = idOfferta;
	}

	public double getPercentualeSconto() {
		return percentualeSconto;
	}

	public void setPercentualeSconto(double percentualeSconto) {
		this.percentualeSconto = percentualeSconto;
	}

	public Date getInizio() {
		return inizio;
	}

	public void setInizio(Date inizio) {
		this.inizio = inizio;
	}

	public Date getFine() {
		return fine;
	}

	public void setFine(Date fine) {
		this.fine = fine;
	}

	public ArrayList<EntityVisitaGuidata> getVisite() {
		return visite;
	}

	public void setVisite(ArrayList<EntityVisitaGuidata> visite) {
		this.visite = visite;
	}
	
	public void addVisita(EntityVisitaGuidata visita) {
		this.visite.add(visita);
	}
	
}
