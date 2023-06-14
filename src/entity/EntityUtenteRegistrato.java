package entity;

import java.util.ArrayList;
import database.DBPrenotazione;
import database.DBUtenteRegistrato;

public class EntityUtenteRegistrato {

	private String email;
	private String username;
	private String nome;
	private String cognome;
	private String password;
	private ArrayList<EntityPrenotazione> prenotazioni;
	
	public EntityUtenteRegistrato() {
		
		super();
		this.prenotazioni=new ArrayList<EntityPrenotazione>();
		
	}

	public EntityUtenteRegistrato(String email) {
		
		DBUtenteRegistrato utente=new DBUtenteRegistrato(email);
		this.email=email;
		this.username=utente.getUsername();
		this.nome=utente.getNome();
		this.cognome=utente.getCognome();
		this.password=utente.getPassword();
		this.prenotazioni=new ArrayList<EntityPrenotazione>();
		
		utente.caricaPrenotazioniUtenteDaDB();
		caricaPrenotazioniUtente(utente);
		
	}
	
	public EntityUtenteRegistrato(DBUtenteRegistrato utente) {
		
		this.email=utente.getEmail();
		this.username=utente.getUsername();
		this.nome=utente.getNome();
		this.cognome=utente.getCognome();
		this.password=utente.getPassword();
		this.prenotazioni=new ArrayList<EntityPrenotazione>();
		
		utente.caricaPrenotazioniUtenteDaDB();
		caricaPrenotazioniUtente(utente);
	}
	
	public int ScriviSuDB() {
		
		DBUtenteRegistrato utente=new DBUtenteRegistrato();
		utente.setEmail(this.email);
		utente.setCognome(this.cognome);
		utente.setNome(this.nome);
		utente.setPassword(this.password);
		utente.setUsername(this.username);
		
		ArrayList<DBPrenotazione> prenotazioni=new ArrayList<DBPrenotazione>(); 
		
		for(int k=0; k<this.getPrenotazioni().size(); k++) {
			
			DBPrenotazione prenotazione=new DBPrenotazione(this.getPrenotazioni().get(k)); 
			prenotazioni.add(prenotazione);
			
		}
		
		utente.setPrenotazioni(prenotazioni);
		
		int i=utente.SalvaInDB();
		return i;
		
	}

	public void caricaPrenotazioniUtente(DBUtenteRegistrato utente) {
		
		for(int i=0; i<utente.getPrenotazioni().size(); i++) {
			
			EntityPrenotazione prenotazione=new EntityPrenotazione(utente.getPrenotazioni().get(i));
			this.prenotazioni.add(prenotazione);
			
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<EntityPrenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	public void setPrenotazioni(ArrayList<EntityPrenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	
	public void addPrenotazione(EntityPrenotazione prenotazione) {
		this.prenotazioni.add(prenotazione);
	}
	
}
