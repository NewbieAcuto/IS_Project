package database;

import java.sql.ResultSet;
import entity.EntityOpzione;
import java.sql.SQLException;
import java.util.ArrayList;


public class DBOpzione {
	private int idOpzione;
	private String descrizione;
	private int durata;
	private String mezzo;
	private double maggiorazionePrezzo;
	
	private DBVisitaGuidata visita;
	private ArrayList<DBPrenotazione> prenotazioni;
		
		public DBOpzione() {
			super();
			this.visita = new DBVisitaGuidata();
		}
		
		
		public DBOpzione(int idOpzione) {
			this.idOpzione = idOpzione;
			this.visita = new DBVisitaGuidata();
			
			caricaDaDB();
		}
		
		public DBOpzione(EntityOpzione opzione) {
			
			this.idOpzione=opzione.getIdOpzione();
			this.descrizione=opzione.getDescrizione();
			this.durata=opzione.getDurata();
			this.mezzo=opzione.getMezzo();
			this.maggiorazionePrezzo=opzione.getMaggiorazionePrezzo();
			
			DBVisitaGuidata visita=new DBVisitaGuidata(opzione.getVisita());
			this.visita=visita;
			
			for(int i=0; i<opzione.getPrenotazioni().size(); i++) {
				
				DBPrenotazione prenotazione=new DBPrenotazione(opzione.getPrenotazioni().get(i));
				this.prenotazioni.add(prenotazione);
				
			}
			
		}
		
		public void caricaDaDB() {
			
			String query = "SELECT * FROM OPZIONI WHERE IdOpzione='"+this.idOpzione+"';";	
			try {
				
				ResultSet rs = DBConnectionManager.selectQuery(query);
				
				if(rs.next()) { //se ho un risultato
					
					//mi vado a prendere i dati, accedendo tramite il nome dell'attributo-colonna
					this.setDescrizione(rs.getString("Descrizione"));
					this.setDurata(rs.getInt("Durata"));
					this.setMezzo(rs.getString("Mezzo"));	
					this.setMaggiorazionePrezzo(rs.getDouble("MaggiorazionePrezzo"));
				}
			
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void caricaVisitaOpzioneDaDB() {
			
			
			String query = new String("SELECT * FROM VISITEGUIDATE WHERE IdVisita='"+this.visita.getIdVisita()+"')" );
			//System.out.println(query); //stampo query per controllo in fase di DEBUG, poi posso commentare
			
			try {
				
				java.sql.ResultSet rs = DBConnectionManager.selectQuery(query);
				
				if(rs.next()) {			
								
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
					
					this.visita = visita;
					
				}
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		public void caricaPrenotazioniOpzioneDaDB() {
			
			
			String query = new String("SELECT * FROM PRENOTAZIONI WHERE Opzione_IdOpzione='"+this.idOpzione+"')" );
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

			
			
			public int SalvaInDB() {
				
				int ret = 0;
				
				String query = "INSERT INTO OPZIONI(IdOpzione, Descrizione, Durata, Mezzo, MaggiorazionePrezzo, VisitaGuidata_IdVisita) VALUES ( \'"+this.idOpzione+"\',"+"\'"+this.descrizione+"\','"+this.durata+"\','"+this.mezzo+"\','"+this.maggiorazionePrezzo+"\','"+this.visita.getIdVisita()+"')";
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
		        String query = new String("DELETE FROM OPZIONI WHERE IdOpzione ='"+this.idOpzione+"'");
		        
		        try{
		        	
					ret = DBConnectionManager.updateQuery(query);
					
		        } catch (ClassNotFoundException | SQLException e) {
		        	
		            e.printStackTrace();
		            ret = -1;
		            
		        }
		        
		        return ret;	// ret = 0, non ci sono stati errori
		        
		    }	
	
	
	
	public ArrayList<DBPrenotazione> getPrenotazioni() {
		return prenotazioni;
	}


	public void setPrenotazioni(ArrayList<DBPrenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
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

	public DBVisitaGuidata getVisita() {
		return visita;
	}


	public void setVisita(DBVisitaGuidata visita) {
		this.visita = visita;
	}

	
	
}
