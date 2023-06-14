package entity;

import java.util.Date;
import java.sql.Time;
import database.DBOpzione;
import database.DBUtenteRegistrato;
import database.DBVisitaGuidata;
import database.DBPrenotazione;

public class EntityPrenotazione {

	private EntityVisitaGuidata visita;
	private EntityUtenteRegistrato utente;
	private Date data;
	private Time ora;
	private double PrezzoTotale;
	private EntityOpzione opzione;
	
	public EntityPrenotazione() {
		
		super();
	}
	
	public EntityPrenotazione(DBVisitaGuidata visita, DBUtenteRegistrato utente) {
		
		DBPrenotazione prenotazione=new DBPrenotazione(visita,utente);
		this.data=prenotazione.getData();
		this.ora=prenotazione.getOra();
		this.PrezzoTotale=prenotazione.getPrezzoTotale();
		
		prenotazione.caricaVisitaPrenotazioneDaDB();
		caricaVisitaPrenotazione(prenotazione);
		
		prenotazione.caricaUtentePrenotazioneDaDB();
		caricaUtentePrenotazione(prenotazione);
		
		prenotazione.caricaOpzionePrenotazioneDaDB();
		caricaOpzionePrenotazione(prenotazione);
		
	}
	
	public EntityPrenotazione(DBPrenotazione prenotazione) {
		
		this.data=prenotazione.getData();
		this.ora=prenotazione.getOra();
		this.PrezzoTotale=prenotazione.getPrezzoTotale();
		
		prenotazione.caricaVisitaPrenotazioneDaDB();
		caricaVisitaPrenotazione(prenotazione);
		
		prenotazione.caricaUtentePrenotazioneDaDB();
		caricaUtentePrenotazione(prenotazione);
		
		prenotazione.caricaOpzionePrenotazioneDaDB();
		caricaOpzionePrenotazione(prenotazione);
	}
	
	public int ScriviSuDB() {
		
		DBPrenotazione prenotazione=new DBPrenotazione();
		prenotazione.setData(this.data);
		prenotazione.setOra(this.ora);
		prenotazione.setPrezzoTotale(this.PrezzoTotale);
		
		DBVisitaGuidata visita1=new DBVisitaGuidata(this.visita);
		prenotazione.setVisita(visita1);

		DBUtenteRegistrato utente1=new DBUtenteRegistrato(this.utente);
		prenotazione.setUtente(utente1);
		
		DBOpzione opzione=new DBOpzione(this.opzione);
		prenotazione.setOpzione(opzione);
		
		int i=prenotazione.SalvaInDB();
		return i;
		
	}

	public void caricaOpzionePrenotazione(DBPrenotazione prenotazione) {
		
		EntityOpzione opzione=new EntityOpzione(prenotazione.getOpzione());
		this.opzione=opzione;
		
	}
	
	public void caricaVisitaPrenotazione(DBPrenotazione prenotazione) {
		
		EntityVisitaGuidata visita=new EntityVisitaGuidata(prenotazione.getVisita());
		this.visita=visita;
		
	}

	public void caricaUtentePrenotazione(DBPrenotazione prenotazione) {
		
		EntityUtenteRegistrato utente=new EntityUtenteRegistrato(prenotazione.getUtente());
		this.utente=utente;
		
	}
	
	public double calcolaPrezzoTotale(){
		
		double prezzoBase=this.visita.getPrezzoBase();
		double maggiorazione=this.opzione.getMaggiorazionePrezzo();
		double sconto=controllaSconto();
		
		double prezzoTotale=prezzoBase-(prezzoBase*sconto)/100+maggiorazione;
		setPrezzoTotale(prezzoTotale);
		return prezzoTotale;
	}
	
	public double controllaSconto(){
	
		if(this.offerta!=null)
			return this.offerta.getPercentualeSconto();
		
		else
			return 0;
	}

	public EntityVisitaGuidata getVisita() {
		return visita;
	}

	public void setVisita(EntityVisitaGuidata visita) {
		this.visita = visita;
	}

	public EntityUtenteRegistrato getUtente() {
		return utente;
	}

	public void setUtente(EntityUtenteRegistrato utente) {
		this.utente = utente;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Time getOra() {
		return ora;
	}

	public void setOra(Time ora) {
		this.ora = ora;
	}

	public double getPrezzoTotale() {
		return PrezzoTotale;
	}

	public void setPrezzoTotale(double prezzoTotale) {
		PrezzoTotale = prezzoTotale;
	}

	public EntityOpzione getOpzione() {
		return opzione;
	}

	public void setOpzione(EntityOpzione opzione) {
		this.opzione = opzione;
	}
	
}
