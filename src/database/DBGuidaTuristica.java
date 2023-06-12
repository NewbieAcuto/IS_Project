package database;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.EntityGuidaTuristica;

public class DBGuidaTuristica {

	private String cognome;
	private String nome;
	private int eta;
	private String sesso;
	private String lingue;
	private boolean disponibile;
	private int annoAbilitazione;
	
	private DBVisitaGuidata visita;
	
	public DBGuidaTuristica() {
		super();
		this.visita = new DBVisitaGuidata();
	}
	
	
	public DBGuidaTuristica(String cognome) {
		this.cognome = cognome;
		this.visita = new DBVisitaGuidata();
		
		caricaDaDB();
	}
	
	public DBGuidaTuristica(EntityGuidaTuristica guida) {
		
		this.cognome=guida.getCognome();
		this.nome=guida.getNome();
		this.eta=guida.getEta();
		this.sesso=guida.getSesso();
		this.lingue=guida.getLingue();
		this.annoAbilitazione=guida.getAnnoAbilitazione();
		this.disponibile = guida.getDisponibile();
		DBVisitaGuidata visita=new DBVisitaGuidata(guida.getVisita());
		this.visita=visita;
		
	}

	public void caricaDaDB() {
		
		String query = "SELECT * FROM GUIDETURISTICHE WHERE Cognome='"+this.cognome+"';";	
		try {
			
			java.sql.ResultSet rs = DBConnectionManager.selectQuery(query);
			
			if(rs.next()) { //se ho un risultato
				
				this.setNome(rs.getString("Nome"));
				this.setEta(rs.getInt("Eta"));
				this.setSesso(rs.getString("Sesso"));	
				this.setLingue(rs.getString("Lingue"));
				this.setAnnoAbilitazione(rs.getInt("AnnoAbilitazione"));
				this.setDisponibile(rs.getBoolean("Disponibile"));
				
				
			}
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void caricaVisitaGuidaDaDB() {
		
		
		String query = new String("SELECT * FROM VISITEGUIDATE WHERE GuideTuristiche_Cognome='"+this.cognome+"')" );
		//System.out.println(query); //stampo query per controllo in fase di DEBUG, poi posso commentare
		
		try {
			
			java.sql.ResultSet rs = DBConnectionManager.selectQuery(query);
			
			if(rs.next()) {	//while perch� mi aspetto pi� risultati			
							
				//NB: non dimenticare di istanziare l'oggetto Corso
				//altrimenti non potremmo salvare i suoi dati
				DBVisitaGuidata visita = new DBVisitaGuidata();
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
	

	public int SalvaInDB() {
		
		int ret = 0;
		
		String query = "INSERT INTO OPZIONI(Cognome, Nome, Eta, Sesso, Lingue, AnnoAbilitazione, Disponibile) VALUES ( \'"+this.cognome+"\',"+"\'"+this.nome+"\','"+this.eta+"\','"+this.lingue+"\','"+this.annoAbilitazione+"\','"+this.disponibile+"')";
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
        String query = new String("DELETE FROM GUIDETURISTICHE WHERE Cognome ='"+this.cognome+"'");
        
        try{
        	
			ret = DBConnectionManager.updateQuery(query);
			
        } catch (ClassNotFoundException | SQLException e) {
        	
            e.printStackTrace();
            ret = -1;
            
        }
        
        return ret;	// ret = 0, non ci sono stati errori
        
    }	
	
    public static ArrayList<DBGuidaTuristica> CercaGuideDisponibili() {
		
		
		ArrayList<DBGuidaTuristica> guideDisponibili = new ArrayList<DBGuidaTuristica>();
		String query = new String("SELECT * FROM GUIDETURISTICHE WHERE Disponibile='"+1+"';");
		
		try {
			ResultSet rs = DBConnectionManager.selectQuery(query);
		
			while(rs.next()) {
				
				DBGuidaTuristica guida = new DBGuidaTuristica();
				guida.setCognome(rs.getString("Cognome"));
				guida.setNome(rs.getString("Nome"));
				guida.setEta(rs.getInt("Eta"));
				guida.setSesso(rs.getString("Sesso"));
				guida.setLingue(rs.getString("Lingue"));
				guida.setAnnoAbilitazione(rs.getInt("AnnoAbilitazione"));
				guida.setDisponibile(rs.getBoolean("Disponibile"));
				
				guideDisponibili.add(guida);
				
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return guideDisponibili;
	}

	
	
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getEta() {
		return eta;
	}
	public void setEta(int eta) {
		this.eta = eta;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getLingue() {
		return lingue;
	}
	public void setLingue(String lingue) {
		this.lingue = lingue;
	}
	public int getAnnoAbilitazione() {
		return annoAbilitazione;
	}
	public void setAnnoAbilitazione(int annoAbilitazione) {
		this.annoAbilitazione = annoAbilitazione;
	}

	public boolean getDisponibile() {
		return disponibile;
	}

	public void setDisponibile(boolean disponibile) {
		this.disponibile = disponibile;
	}

	public DBVisitaGuidata getVisita() {
		return visita;
	}


	public void setVisita(DBVisitaGuidata visita) {
		this.visita = visita;
	}
	
	
}
