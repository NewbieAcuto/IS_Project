package database;

import java.sql.ResultSet;
import entity.EntityVisitaGuidata;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBVisitaGuidata {
	private int idVisita ;
	private String nome; 
	private String descrizione;
	private String citta;
	private int maxPartecipanti;
	private double prezzoBase;
	
	private DBOffertaSpeciale offerta;
	private ArrayList<DBPrenotazione> prenotazioni;
	private DBSocieta societa;
	private DBGuidaTuristica guida;
	private ArrayList<DBOpzione> opzioni;
	
	
	public DBVisitaGuidata() {
		super();
		this.offerta = new DBOffertaSpeciale();
		this.prenotazioni = new ArrayList<DBPrenotazione>();
		this.societa = new DBSocieta();
		this.guida = new DBGuidaTuristica();
		
	}
	
	
	public DBVisitaGuidata(int idVisita) {
		this.idVisita = idVisita;
		this.offerta = new DBOffertaSpeciale();
		this.prenotazioni = new ArrayList<DBPrenotazione>();
		this.societa = new DBSocieta();
		this.guida = new DBGuidaTuristica();
		
		caricaDaDB();
	}
	
	public DBVisitaGuidata(EntityVisitaGuidata visita) {
		
		this.idVisita=visita.getIdVisita();
		this.nome=visita.getNome();
		this.descrizione=visita.getDescrizione();
		this.citta=visita.getCitta();
		this.maxPartecipanti=visita.getMaxPartecipanti();
		this.prezzoBase=visita.getPrezzoBase();
		this.prenotazioni=new ArrayList<DBPrenotazione>();
		this.opzioni=new ArrayList<DBOpzione>();
		
		DBOffertaSpeciale offerta=new DBOffertaSpeciale(visita.getOfferta());
		this.offerta=offerta;
		
		DBSocieta societa=new DBSocieta(visita.getSocieta());
		this.societa=societa;
		
		DBGuidaTuristica guida=new DBGuidaTuristica(visita.getGuida());
		this.guida=guida;
		
		for(int i=0; i<visita.getPrenotazioni().size(); i++) {
			
			DBPrenotazione prenotazione=new DBPrenotazione(visita.getPrenotazioni().get(i));
			this.prenotazioni.add(prenotazione);
			
		}
		
		for(int i=0; i<visita.getOpzioni().size(); i++) {
			
			DBOpzione opzione=new DBOpzione(visita.getOpzioni().get(i));
			this.opzioni.add(opzione);
			
		}
		
	}

	public void caricaDaDB() {
		
		String query = "SELECT * FROM VISITEGUIDATA WHERE IdVisita='"+this.idVisita+"';";	
		try {
			
			java.sql.ResultSet rs = DBConnectionManager.selectQuery(query);
			
			if(rs.next()) { //se ho un risultato
				
				this.setNome(rs.getString("Nome"));
				this.setDescrizione(rs.getString("Descrizione"));
				this.setCitta(rs.getString("Citta"));	
				this.setMaxPartecipanti(rs.getInt("MaxPartecipanti"));
				this.setPrezzoBase(rs.getDouble("PrezzoBase"));
			}
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void caricaOffertaVisitaDaDB() {
		
		
		String query = new String("SELECT * FROM OFFERTE WHERE IdOfferta='"+this.offerta.getIdOfferta()+"')" );
		//System.out.println(query); //stampo query per controllo in fase di DEBUG, poi posso commentare
		
		try {
			
			ResultSet rs = DBConnectionManager.selectQuery(query);
			
			while(rs.next()) {	
				
				DBOffertaSpeciale offerta = new DBOffertaSpeciale();
				offerta.setPercentualeSconto(rs.getDouble("PercentualeSconto"));
				offerta.setInizio(rs.getDate("Inizio"));				
				offerta.setFine(rs.getDate("Fine"));
				
				
				offerta.caricaVisiteOffertaDaDB();
				this.offerta = offerta;
				
				
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void caricaGuidaVisitaDaDB() {
		
		
		String query = new String("SELECT * FROM GUIDETURISTICHE WHERE Cognome='"+this.guida.getCognome()+"')" );
		//System.out.println(query); //stampo query per controllo in fase di DEBUG, poi posso commentare
		
		try {
			
			ResultSet rs = DBConnectionManager.selectQuery(query);
			
			while(rs.next()) {	//while perch� mi aspetto pi� risultati			
							
				//NB: non dimenticare di istanziare l'oggetto Corso
				//altrimenti non potremmo salvare i suoi dati
				DBGuidaTuristica guida = new DBGuidaTuristica();
				guida.setCognome(rs.getString("Cognome"));
				guida.setNome(rs.getString("Nome"));				
				guida.setEta(rs.getInt("Eta"));
				guida.setSesso(rs.getString("Sesso"));
				guida.setLingue(rs.getString("Lingue"));				
				guida.setAnnoAbilitazione(rs.getInt("AnnoAbilitazione"));
				
				
				guida.caricaVisitaGuidaDaDB();
				this.guida = guida; 
				
				
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
public void caricaSocietaVisitaDaDB() {
		
		
		String query = new String("SELECT * FROM SOCIETA WHERE Nome='"+this.societa.getNome()+"')" );
		//System.out.println(query); //stampo query per controllo in fase di DEBUG, poi posso commentare
		
		try {
			
			ResultSet rs = DBConnectionManager.selectQuery(query);
			
			while(rs.next()) {	
				
				DBSocieta societa = new DBSocieta();
				societa.setNome(rs.getString("Nome"));
				societa.setIndirizzo(rs.getString("Indirizzo"));				
				societa.setTelefono(rs.getInt("Telefono"));
				societa.setEmail(rs.getString("Email"));
				
				
				societa.caricaVisiteSocietaDaDB();
				this.societa = societa;
				
				
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


public void caricaPrenotazioniVisitaDaDB() {
	
	
	String query = new String("SELECT * FROM PRENOTAZIONI WHERE Visita='"+this.idVisita+"')" );
	//System.out.println(query); //stampo query per controllo in fase di DEBUG, poi posso commentare
	
	try {
		
		ResultSet rs = DBConnectionManager.selectQuery(query);
		
		while(rs.next()) {	//while perch� mi aspetto pi� risultati			
						
			//NB: non dimenticare di istanziare l'oggetto Corso
			//altrimenti non potremmo salvare i suoi dati
			DBPrenotazione prenotazione = new DBPrenotazione();
			prenotazione.setData(rs.getDate("Data"));
			prenotazione.setOra(rs.getTime("Ora"));				
			prenotazione.setPrezzoTotale(rs.getDouble("PrezzoTotale"));
			
			
			prenotazione.caricaUtentePrenotazioneDaDB();
			prenotazione.caricaVisitaPrenotazioneDaDB();
			this.prenotazioni.add(prenotazione); //salvo l'oggetto corso appena caricato
			//come attributo dell'oggetto StudenteDB in questione
			
			
		}
		
	} catch (ClassNotFoundException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

		
		public void caricaOpzioniVisitaDaDB() {
			
			
			String query = new String("SELECT * FROM OPZIONI WHERE VisiteGuida_IdVisita='"+this.idVisita+"')" );
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
					this.opzioni.add(opzione); //salvo l'oggetto corso appena caricato
					//come attributo dell'oggetto StudenteDB in questione
					
					
				}
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		public int SalvaInDB() {
			
			int ret = 0;
			
			String query = new String("INSERT INTO VISITEGUIDATE(IdVisita, Nome, Descrizione, Citta, MaxPartecipanti, PrezzoBase, Societa_Nome, GuideTuristiche_Cognome, Offerta) VALUES ( \'"+this.idVisita+"\',"+"\'"+this.nome+"\','"+this.descrizione+"\','"+this.citta+"\','"+this.maxPartecipanti+"\','"+this.prezzoBase+"\','"+this.societa.getNome()+"\','"+this.guida.getCognome()+"\','"+this.offerta.getIdOfferta()+"')");  
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
	        String query = new String("DELETE FROM VISITEGUIDATE WHERE IdVisita ='"+this.idVisita+"'");
	        
	        try{
	        	
				ret = DBConnectionManager.updateQuery(query);
				
	        } catch (ClassNotFoundException | SQLException e) {
	        	
	            e.printStackTrace();
	            ret = -1;
	            
	        }
	        
	        return ret;	// ret = 0, non ci sono stati errori
	        
	    }
	    
// Funzione di modifica in DB che verrà richiamata dal modulo ModificaVisitaGuidata nella Entity  
	    public int ModificaInDB() {
			
			int ret = 0;
			
			String query = new String("UPDATE VISITEGUIDATE(IdVisita, Nome, Descrizione, Citta, MaxPartecipanti, PrezzoBase, Societa_Nome, GuideTuristiche_Cognome, Offerta) VALUES (\'"+this.idVisita+"\',"+"\'"+this.nome+"\','"+this.descrizione+"\','"+this.citta+"\','"+this.maxPartecipanti+"\','"+this.prezzoBase+"\','"+this.societa.getNome()+"\','"+this.guida.getCognome()+"\','"+this.offerta.getIdOfferta()+"')  WHERE IdVisita ='"+this.idVisita+"';");
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
	    
// funzione che cerca nel db una visita guidata tramite la chiave e restituisce l'oggetto DBVisitaGuidata
		public static DBVisitaGuidata TrovaVisita(int idVisita) {
			
			String query = new String("SELECT * FROM VISITEGUIDATE WHERE IdVisita="+idVisita+"';");
			
			DBVisitaGuidata visita = null;
			
			try {
				ResultSet rs = DBConnectionManager.selectQuery(query);
				
				if(rs.next()) {
					
					visita = new DBVisitaGuidata(idVisita);
					
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
			return visita;
		}
// Funzione che effettua una query su VISITEGUIDATE e ritorna un ArrayList di DBVisitaGuidata
	 public static ArrayList<DBVisitaGuidata> VisualizzaVisite() {
			
			
			ArrayList<DBVisitaGuidata> visite = new ArrayList<DBVisitaGuidata>();
			String query = new String("SELECT * FROM VISITEGUIDATE;");
			
			try {
				ResultSet rs = DBConnectionManager.selectQuery(query);
			
				while(rs.next()) {
					
					DBVisitaGuidata visita = new DBVisitaGuidata(rs.getInt("IdVisita"));
				
					visite.add(visita);
					
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return visite;
		}
	
	public int getIdVisita() {
		return idVisita;
	}
	public void setIdVisita(int idVisita) {
		this.idVisita = idVisita;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public int getMaxPartecipanti() {
		return maxPartecipanti;
	}
	public void setMaxPartecipanti(int maxPartecipanti) {
		this.maxPartecipanti = maxPartecipanti;
	}
	public double getPrezzoBase() {
		return prezzoBase;
	}
	public void setPrezzoBase(double prezzoBase) {
		this.prezzoBase = prezzoBase;
	}
	public DBOffertaSpeciale getOfferta() {
		return offerta;
	}
	public void setOfferta(DBOffertaSpeciale offerta) {
		this.offerta = offerta;
	}
	public ArrayList<DBPrenotazione> getPrenotazioni() {
		return prenotazioni;
	}
	public void setPrenotazioni(ArrayList<DBPrenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	public DBSocieta getSocieta() {
		return societa;
	}
	public void setSocieta(DBSocieta societa) {
		this.societa = societa;
	}
	public DBGuidaTuristica getGuida() {
		return guida;
	}
	public void setGuida(DBGuidaTuristica guida) {
		this.guida = guida;
	}


	public ArrayList<DBOpzione> getOpzioni() {
		return opzioni;
	}


	public void setOpzioni(ArrayList<DBOpzione> opzioni) {
		this.opzioni = opzioni;
	}
	
}
