package entity;

import database.DBPrenotazione;
import database.DBVisitaGuidata;
import java.util.ArrayList;
import database.DBOpzione;

public class EntityOpzione {

	private int idOpzione;
	private String descrizione;
	private int durata;
	private String mezzo;
	private double maggiorazionePrezzo;
	private EntityVisitaGuidata visita;
	private ArrayList<EntityPrenotazione> prenotazioni;
	
	public EntityOpzione() {
		
		super();
		this.prenotazioni=new ArrayList<EntityPrenotazione>();
	}
	
	public EntityOpzione(int idOpzione) {
		
		DBOpzione opzione=new DBOpzione(idOpzione);
		this.idOpzione=opzione.getIdOpzione();
		this.descrizione=opzione.getDescrizione();
		this.durata=opzione.getDurata();
		this.mezzo=opzione.getMezzo();
		this.maggiorazionePrezzo=opzione.getMaggiorazionePrezzo();
		this.prenotazioni=new ArrayList<EntityPrenotazione>();
		
		opzione.caricaPrenotazioniOpzioneDaDB();
		caricaPrenotazioniOpzione(opzione);
		
		opzione.caricaVisitaOpzioneDaDB();
		caricaVisitaOpzione(opzione);
		
	}
	
	public EntityOpzione(DBOpzione opzione) {
		
		this.idOpzione=opzione.getIdOpzione();
		this.descrizione=opzione.getDescrizione();
		this.durata=opzione.getDurata();
		this.mezzo=opzione.getMezzo();
		this.maggiorazionePrezzo=opzione.getMaggiorazionePrezzo();
		this.prenotazioni=new ArrayList<EntityPrenotazione>();
		
		opzione.caricaPrenotazioniOpzioneDaDB();
		caricaPrenotazioniOpzione(opzione);
		
	}
	
	public int scriviSuDB() {
		
		DBOpzione opzione=new DBOpzione();
		
		opzione.setIdOpzione(this.idOpzione);
		opzione.setDescrizione(this.descrizione);
		opzione.setDurata(this.durata);
		opzione.setMezzo(this.mezzo);
		opzione.setMaggiorazionePrezzo(this.maggiorazionePrezzo);
		
		DBVisitaGuidata visita=new DBVisitaGuidata(this.visita.getIdVisita());
		opzione.setVisita(visita);
		
		ArrayList<DBPrenotazione> prenotazioni=new ArrayList<DBPrenotazione>();
		
		for(int k=0; k<this.getPrenotazioni().size(); k++) {
			
			DBPrenotazione prenotazione=new DBPrenotazione(this.getPrenotazioni().get(k));
			prenotazioni.add(prenotazione);
			
		}
		
		opzione.setPrenotazioni(prenotazioni);
		
		int i=opzione.SalvaInDB();
		return i;
		
	}

	public void caricaPrenotazioniOpzione(DBOpzione opzione) {
		
		for(int i=0; i<opzione.getPrenotazioni().size(); i++) {
			
			EntityPrenotazione prenotazione=new EntityPrenotazione(opzione.getPrenotazioni().get(i));
			this.prenotazioni.add(prenotazione);
			
		}
		
	}
	
	public void caricaVisitaOpzione(DBOpzione opzione) {
		
		EntityVisitaGuidata visita=new EntityVisitaGuidata(opzione.getVisita());
		this.visita=visita;
		
	}

	public int getIdOpzione() {
		return idOpzione;
	}

	public void setIdOpzione(int idOpzione) {
		this.idOpzione = idOpzione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getDurata() {
		return durata;
	}

	public void setDurata(int durata) {
		this.durata = durata;
	}

	public String getMezzo() {
		return mezzo;
	}

	public void setMezzo(String mezzo) {
		this.mezzo = mezzo;
	}

	public double getMaggiorazionePrezzo() {
		return maggiorazionePrezzo;
	}

	public void setMaggiorazionePrezzo(double maggiorazionePrezzo) {
		this.maggiorazionePrezzo = maggiorazionePrezzo;
	}

	public ArrayList<EntityPrenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	public void setPrenotazioni(ArrayList<EntityPrenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}

	public EntityVisitaGuidata getVisita() {
		return visita;
	}

	public void setVisita(EntityVisitaGuidata visita) {
		this.visita = visita;
	}
	
	public void addPrenotazione(EntityPrenotazione prenotazione) {
		this.prenotazioni.add(prenotazione);
	}
	
}
