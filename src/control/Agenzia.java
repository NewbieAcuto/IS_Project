package control;

import entity.*;

import java.sql.Time;
import java.util.Date;

public class Agenzia {

	public Agenzia() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static void AggiungiVisitaGuidata(int idVisita, String nome, String descrizione, String citta, int maxPartecipanti, double prezzoBase, String societa_Nome, int idOfferta, String guidaTuristica_Cognome) {
		EntityVisitaGuidata visita = new EntityVisitaGuidata();
		visita.setIdVisita(idVisita);
		visita.setNome(nome);
		visita.setDescrizione(descrizione);
		visita.setCitta(citta);
		visita.setMaxPartecipanti(maxPartecipanti);
		visita.setPrezzoBase(prezzoBase);
		
		EntityGuidaTuristica guida = new EntityGuidaTuristica(guidaTuristica_Cognome);
		visita.setGuida(guida);
		
		EntitySocieta societa = new EntitySocieta(societa_Nome);
		visita.setSocieta(societa);
		
		EntityOffertaSpeciale offerta =  new EntityOffertaSpeciale(idOfferta);
		visita.setOfferta(offerta);
		
		int ret = visita.ScriviSuDB();
		
		if (ret != -1) {
	        System.out.println("Visita guidata inserita con successo. ID: " + idVisita);
	    } else {
	        System.out.println("Si è verificato un errore durante l'inserimento della visita guidata.");
	    }
		
	}

	public int aggiungiOpzione(int id, String desc, int dur, String mez, double magp, int idVisita) {
		
		EntityOpzione opzione=new EntityOpzione();
		
		opzione.setIdOpzione(id);
		opzione.setDescrizione(desc);
		opzione.setDurata(dur);
		opzione.setMezzo(mez);
		opzione.setMaggiorazionePrezzo(magp);
		
		EntityVisitaGuidata visita=new EntityVisitaGuidata(idVisita);
		opzione.setVisita(visita);
		visita.addOpzione(opzione);
		
		int ret=opzione.scriviSuDB();
		
		return ret;
		
	}

	public int aggiungiOffertaSpeciale(int id, double percs, Date in, Date fin) {
	
	EntityOffertaSpeciale offerta=new EntityOffertaSpeciale();

	offerta.setIdOfferta(id);
	offerta.setPercentualeSconto(percs);
	offerta.setInizio(in);
	offerta.setFine(fin);
	
	
	int ret=offerta.ScriviSuDB();
	return ret;
	
}

	public static int AggiungiSocieta(String nome, String indirizzo, int telefono, String email) {
		EntitySocieta societa = new EntitySocieta();
		societa.setNome(nome);
		societa.setIndirizzo(indirizzo);
		societa.setTelefono(telefono);
		societa.setEmail(email);
		
		int ret = societa.ScriviSuDB();
		
		return ret;
		
	}
	
	public static int AggiungiVisitaGuidata(int idVisita, String nome, String descrizione, String citta, int maxPartecipanti, double prezzoBase, String societa_Nome, int offerta, String guidaTuristica_Cognome) {
		EntityVisitaGuidata visita = new EntityVisitaGuidata();
		visita.setIdVisita(idVisita);
		visita.setNome(nome);
		visita.setDescrizione(descrizione);
		visita.setCitta(citta);
		visita.setMaxPartecipanti(maxPartecipanti);
		visita.setPrezzoBase(prezzoBase);
		
		
		if(EntityGuidaTuristica.TrovaGuideDisponibili() == 0) {
			System.out.println("Non ci sono guide disponibili da assegnare");
		};
		return 1;
		
	}
	
	public int aggiungiPrenotazione(String eml, int idv, Date dat, Time or, double pt, int ido) {
		
		EntityPrenotazione prenotazione=new EntityPrenotazione();
		
		prenotazione.setData(dat);
		prenotazione.setOra(or);
		prenotazione.setPrezzoTotale(pt);
		
		EntityUtenteRegistrato utente=new EntityUtenteRegistrato(eml);
		utente.addPrenotazione(prenotazione);
		prenotazione.setUtente(utente);
		
		EntityVisitaGuidata visita=new EntityVisitaGuidata(idv);
		visita.addPrenotazione(prenotazione);
		prenotazione.setVisita(visita);
		
		EntityOpzione opzione=new EntityOpzione(ido);
		prenotazione.setOpzione(opzione);
		
		int ret=prenotazione.ScriviSuDB();
		return ret;
		
	}
	
	public int aggiungiUtenteRegistrato(String eml, String usern, String n, String c, String pass) {
		
		EntityUtenteRegistrato utente=new EntityUtenteRegistrato();
		
		utente.setEmail(eml);
		utente.setNome(n);
		utente.setCognome(c);
		utente.setUsername(usern);
		utente.setPassword(pass);
		
		int ret=utente.ScriviSuDB();
		return ret;
		
	}

	// Funione di MOdifica di una visita, l'utente prima dell'inserimento dei dati deve ricevere una stampa delle visite, società, guide, 
	public static void ModificaVisitaGuidata(int idVisita, String nome, String descrizione, String citta, int maxPartecipanti, double prezzoBase, String societa_Nome, int idOfferta, String guidaTuristica_Cognome) {
		EntityVisitaGuidata visita = new EntityVisitaGuidata();
		visita.setIdVisita(idVisita);
		visita.setNome(nome);
		visita.setDescrizione(descrizione);
		visita.setCitta(citta);
		visita.setMaxPartecipanti(maxPartecipanti);
		visita.setPrezzoBase(prezzoBase);
		
		EntityGuidaTuristica guida = new EntityGuidaTuristica(guidaTuristica_Cognome);
		visita.setGuida(guida);
		
		EntitySocieta societa = new EntitySocieta(societa_Nome);
		visita.setSocieta(societa);
		
		EntityOffertaSpeciale offerta =  new EntityOffertaSpeciale(idOfferta);
		visita.setOfferta(offerta);
		
		int ret = visita.ModificaSuDB();
		
		if (ret != -1) {
	        System.out.println("Visita guidata modificata con successo. ID: " + idVisita);
	    } else {
	        System.out.println("Si è verificato un errore durante la modifica della visita guidata.");
	    }
		
	}
}
