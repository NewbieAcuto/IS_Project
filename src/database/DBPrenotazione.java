package database;

import java.util.Date;
import entity.EntityPrenotazione;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class DBPrenotazione {

	private DBVisitaGuidata visita;
	private DBUtenteRegistrato utente;
	private Date data;
	private Time ora;
	private double prezzoTotale;
	
	private DBOpzione opzione;
	
	public DBPrenotazione() {
		super();
		this.visita = new DBVisitaGuidata();
		this.utente = new DBUtenteRegistrato();
		this.opzione = new DBOpzione();
	}
	
	public DBPrenotazione(DBVisitaGuidata visita, DBUtenteRegistrato emailUtente) {
		this.visita = new DBVisitaGuidata();
		this.utente = new DBUtenteRegistrato();
		this.opzione = new DBOpzione();
		
		caricaDaDB();
	}
	
	public DBPrenotazione(EntityPrenotazione prenotazione) {
		
		this.data=prenotazione.getData();
		this.ora=prenotazione.getOra();
		this.prezzoTotale=prenotazione.getPrezzoTotale();
		
		DBOpzione opzione=new DBOpzione(prenotazione.getOpzione());
		this.opzione=opzione;
		
		DBVisitaGuidata visita=new DBVisitaGuidata(prenotazione.getVisita());
		this.visita=visita;
		
		DBUtenteRegistrato utente=new DBUtenteRegistrato(prenotazione.getUtente());
		this.utente=utente;
		
	}
	
	public void caricaDaDB() {
		
		String query = "SELECT * FROM PRENOTAZIONI WHERE Visita='"+this.visita.getIdVisita()+"' AND EmailUtente='"+this.utente.getEmail()+"';";	
		try {
			
			ResultSet rs = DBConnectionManager.selectQuery(query);
			
			if(rs.next()) { //se ho un risultato
				
				//mi vado a prendere i dati, accedendo tramite il nome dell'attributo-colonna
				this.setData(rs.getDate("Data"));
				this.setOra(rs.getTime("Ora"));
				this.setPrezzoTotale(rs.getDouble("PrezzoTotale"));	
			}
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
		
	public void caricaVisitaPrenotazioneDaDB() {
			
			
		String query = new String("SELECT * FROM VISITEGUIDATE WHERE IdVisita='"+this.visita.getIdVisita()+"';" );
		//System.out.println(query); //stampo query per controllo in fase di DEBUG, poi posso commentare
			
		try {
				
			java.sql.ResultSet rs = DBConnectionManager.selectQuery(query);
				
			if(rs.next()) {	//while perch� mi aspetto pi� risultati			
								
				//NB: non dimenticare di istanziare l'oggetto Corso
				//altrimenti non potremmo salvare i suoi dati
				DBVisitaGuidata visita = new DBVisitaGuidata();
				visita.setIdVisita(rs.getInt("IdVisita"));
				visita.setNome(rs.getString("Nome"));
				visita.setDescrizione(rs.getString("Descrizione"));
				visita.setCitta(rs.getString("Citta"));	
				visita.setMaxPartecipanti(rs.getInt("MaxPartecipanti"));
				visita.setPrezzoBase(rs.getDouble("PrezzoBase"));
			
				visita.caricaPrenotazioniVisitaDaDB();
				visita.caricaOffertaVisitaDaDB();
				visita.caricaSocietaVisitaDaDB();
				visita.caricaGuidaVisitaDaDB();
				visita.caricaOpzioniVisitaDaDB();
				
				this.visita = visita; //salvo l'oggetto corso appena caricato
				//come attributo dell'oggetto StudenteDB in questione
					
					
			}
				
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			
	}
		
	public void caricaUtentePrenotazioneDaDB() {
				
				
		String query = new String("SELECT * FROM UTENTIREGISTRATI WHERE Email='"+this.utente.getEmail()+"';" );
		//System.out.println(query); //stampo query per controllo in fase di DEBUG, poi posso commentare
				
		try {
					
			java.sql.ResultSet rs = DBConnectionManager.selectQuery(query);
					
			if(rs.next()) {			
									
				// istanzio un nuovo oggetto DBUtenteRegistrato tramite il costruttore che da solo 
				DBUtenteRegistrato utente = new DBUtenteRegistrato(rs.getString("Email"));
						
				utente.caricaPrenotazioniUtenteDaDB();
				this.utente = utente; //salvo l'oggetto corso appena caricato
				//come attributo dell'oggetto StudenteDB in questione
						
						
			}
					
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void caricaOpzionePrenotazioneDaDB() {
		
		
		String query = new String("SELECT * FROM OPZIONI WHERE IdOpzione='"+this.opzione.getIdOpzione()+"';" );
		//System.out.println(query); //stampo query per controllo in fase di DEBUG, poi posso commentare
		
		try {
			
			java.sql.ResultSet rs = DBConnectionManager.selectQuery(query);
			
			while(rs.next()) {	
				
				DBOpzione opzione = new DBOpzione();
				opzione.setIdOpzione(rs.getInt("IdOpzione"));
				opzione.setDescrizione(rs.getString("Descrizione"));
				opzione.setDurata(rs.getInt("Durata"));	
				opzione.setMaggiorazionePrezzo(rs.getDouble("MaggiorazionePrezzo"));
				
				opzione.caricaVisitaOpzioneDaDB();
				this.opzione = opzione;
				
				
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public int SalvaInDB() {
		
		int ret = 0;
		
		String query = "INSERT INTO PRENOTAZIONI(Data, Ora, PrezzoTotale, Visita, EmailUtente, Opzione_IdOpzione) VALUES ( \'"+this.data+"\',"+"\'"+this.ora+"\','"+this.prezzoTotale+"\','"+this.visita.getIdVisita()+"\','"+this.utente.getEmail()+"\','"+this.opzione.getIdOpzione()+"');";
		// System.out.println(query);
		try {
			ret = DBConnectionManager.updateQuery(query);
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = -1; //per segnalare l'errore di scrittura
		}
		
		return ret;	// ret = 0, non ci sono stati errori
	}

	
	// Funzione per eliminare un'istanza dal database
    public int EliminaDalDB() {
    	int ret = 0;
        String query = new String("DELETE FROM PRENOTAZIONI WHERE Visita ='"+this.visita.getIdVisita()+"' AND EmailUtente='"+this.utente.getEmail()+"';");
        
        try{
        	
			ret = DBConnectionManager.updateQuery(query);
			
        } catch (ClassNotFoundException | SQLException e) {
        	
            e.printStackTrace();
            ret = -1;
            
        }
        
        return ret;	// ret = 0, non ci sono stati errori
        
    }
    
    

	public DBUtenteRegistrato getUtente() {
		return utente;
	}

	public void setUtente(DBUtenteRegistrato utente) {
		this.utente = utente;
	}

	public double getPrezzoTotale() {
		return prezzoTotale;
	}

	public void setPrezzoTotale(double prezzoTotale) {
		this.prezzoTotale = prezzoTotale;
	}

	public DBVisitaGuidata getVisita() {
		return visita;
	}

	public void setVisita(DBVisitaGuidata visita) {
		this.visita = visita;
	}


	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public 	Time getOra() {
		return ora;
	}

	public void setOra(Time ora) {
		this.ora = ora;
	}

	public DBOpzione getOpzione() {
		return opzione;
	}

	public void setOpzione(DBOpzione opzione) {
		this.opzione = opzione;
	}

}
