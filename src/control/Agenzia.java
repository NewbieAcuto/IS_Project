package control;

import entity.*;

import java.sql.Time;

import java.util.Date;

public class Agenzia {

	public Agenzia() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static int AggiungiVisitaGuidata(int idVisita, String nome, String descrizione, String citta, int maxPartecipanti, double prezzoBase, String societa_Nome, int idOfferta, String guidaTuristica_Cognome) {
		EntityVisitaGuidata visita = new EntityVisitaGuidata();
		visita.setIdVisita(idVisita);
		visita.setNome(nome);
		visita.setDescrizione(descrizione);
		visita.setCitta(citta);
		visita.setMaxPartecipanti(maxPartecipanti);
		visita.setPrezzoBase(prezzoBase);
		
		EntityGuidaTuristica guida = new EntityGuidaTuristica(guidaTuristica_Cognome);
		
		if(!guida.getDisponibile()){
			return 0;
		}
		
		else{
			visita.setGuida(guida);
		
			guida.ModificaNelDB();
	
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
			return ret;
		}
	}

	public static int aggiungiOpzione(int id, String desc, int dur, String mez, double magp, int idVisita) {
		
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

	
	public static int aggiungiPrenotazione(String eml, int idv, Date dat, Time or, double pt, int ido) {
		
		EntityPrenotazione prenotazione=new EntityPrenotazione();
		
		prenotazione.setData(dat);
		prenotazione.setOra(or);
		
		EntityUtenteRegistrato utente=new EntityUtenteRegistrato(eml);
		utente.addPrenotazione(prenotazione);
		prenotazione.setUtente(utente);
		
		EntityVisitaGuidata visita=new EntityVisitaGuidata(idv);
		visita.addPrenotazione(prenotazione);
		prenotazione.setVisita(visita);
		
		double prezzoBase=visita.getPrezzoBase();
		double sconto=controllaSconto(idv);
		
		EntityOpzione opzione=new EntityOpzione(ido);
		prenotazione.setOpzione(opzione);
		
		double maggiorazione=opzione.getMaggiorazionePrezzo();
		double prezzoTotale=prezzoBase-(prezzoBase*sconto)/100+maggiorazione;
		prenotazione.setPrezzoTotale(prezzoTotale);
		
		int ret=prenotazione.ScriviSuDB();
		return ret;
	}
	
	public static int aggiungiUtenteRegistrato(String eml, String usern, String n, String c, String pass) {
		
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
	public static int ModificaVisitaGuidata(int idVisita, String nome, String descrizione, String citta, int maxPartecipanti, double prezzoBase, String societa_Nome, int idOfferta, String guidaTuristica_Cognome) {
		EntityVisitaGuidata visita = new EntityVisitaGuidata();
		visita.setIdVisita(idVisita);
		visita.setNome(nome);
		visita.setDescrizione(descrizione);
		visita.setCitta(citta);
		visita.setMaxPartecipanti(maxPartecipanti);
		visita.setPrezzoBase(prezzoBase);
		
		
		EntityGuidaTuristica guida = new EntityGuidaTuristica(guidaTuristica_Cognome);
		
		if(!guida.getDisponibile()){
			return 0;
		}
		
		else{
			visita.setGuida(guida);
		
			guida.ModificaNelDB();
	
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
			return ret;
		}
	}
	
	public int aggiungiGuidaTuristica(String c, String n, int et, String ses, String lin, int annoa){

		EntityGuidaTuristica guida=new EntityGuidaTuristica();

		guida.setCognome(c);
		guida.setNome(n);
		guida.setEta(et);
		guida.setSesso(ses);
		guida.setLingue(lin);
		guida.setAnnoAbilitazione(annoa);
		guida.setDisponibile(true);

		int ret=guida.scriviSuDB();
		return ret;

	}

	public static int Login(String eml, String pass){

		EntityUtenteRegistrato utente=new EntityUtenteRegistrato(eml);

		if(pass==utente.getPassword())
			return 1;

		else
			return 0;

	}

	public int controllaOpzione(int idv, int ido){

		EntityOpzione opzione=new EntityOpzione(ido);

		if(idv==opzione.getVisita().getIdVisita())
			return 1;

		else
			return 0;
	}

	public static double controllaSconto(int idv){

		EntityVisitaGuidata visita=new EntityVisitaGuidata(idv);

		if(visita.getOfferta().getInizio().before(null) && visita.getOfferta().getFine().after(null)){
			double sconto=visita.getOfferta().getPercentualeSconto();
			return sconto;
		}

		return 0;

	}
// Funzione per permettere la comunicazione con l'amministratore tramite il servizio mail
	public static void InviaEmail( String oggetto, String testo) {
        // Configurazione delle proprietà per la sessione di posta
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.office365.com"); 
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); 

        // Credenziali di accesso all'account Gmail usato per comunicare con l'amministratore
        final String username = "dinofedericoii@outlook.com";
        final String password = "unodue3quattro";

        // Creazione dell'oggetto Session con autenticazione
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Creazione del messaggio email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Indirizzo email del mittente
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("luiigcaretti@gmail.com")); // Indirizzo email del destinatario
            message.setSubject(oggetto);
            message.setText(testo);

            // Invio dell'email
            Transport.send(message);

            System.out.println("Email inviata con successo!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
	
}
