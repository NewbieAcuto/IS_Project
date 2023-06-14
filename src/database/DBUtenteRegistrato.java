package database;

import java.sql.ResultSet;
import entity.EntityUtenteRegistrato;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBUtenteRegistrato {
	private String email;
	private String username;
	private String nome;
	private String cognome;
	private String password;
	
	private ArrayList<DBPrenotazione> prenotazioni;	// Ogni utente conterrà una lista di prenotazioni che ha effettuato
	
	// Costruttore di base
	public DBUtenteRegistrato(){
		super();
		this.prenotazioni = new ArrayList<DBPrenotazione>();
	}
	
	// Costruttore che riceve in ingresso il valore della PK che verrà poi usato da caricaDaDB per recuperare i dati di un utente
	public DBUtenteRegistrato(String email){
		this.email = email;
		this.prenotazioni = new ArrayList<DBPrenotazione>();
		
		caricaDaDB();
	}
	
	public DBUtenteRegistrato(EntityUtenteRegistrato utente) {
		
		this.email=utente.getEmail();
		this.username=utente.getUsername();
		this.nome=utente.getNome();
		this.cognome=utente.getCognome();
		this.password=utente.getPassword();
		this.prenotazioni=new ArrayList<DBPrenotazione>();
		
		for(int i=0; i<utente.getPrenotazioni().size(); i++) {
			
			DBPrenotazione prenotazione=new DBPrenotazione(utente.getPrenotazioni().get(i));
			this.prenotazioni.add(prenotazione);
			
		}
		
	}
	
	public void caricaDaDB() {
		
		String query = "SELECT * FROM UTENTIREGISTRATI WHERE Email='"+this.email+"';";	
		try {
			
			java.sql.ResultSet rs = DBConnectionManager.selectQuery(query);
			
			if(rs.next()) { //se ho un risultato
				
				this.setUsername(rs.getString("Username"));
				this.setNome(rs.getString("Nome"));
				this.setCognome(rs.getString("Cognome"));	
				this.setPassword(rs.getString("Password"));
			}
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void caricaPrenotazioniUtenteDaDB() {
		
		
		String query = new String("SELECT * FROM PRENOTAZIONI WHERE EmailUtente='"+this.email+"';" );
		//System.out.println(query); //stampo query per controllo in fase di DEBUG, poi posso commentare
		
		try {
			
			ResultSet rs = DBConnectionManager.selectQuery(query);
			
			while(rs.next()) {	//while perch� mi aspetto pi� risultati			
						
				DBPrenotazione prenotazione = new DBPrenotazione();
				prenotazione.setData(rs.getDate("Data"));
				prenotazione.setOra(rs.getTime("Ora"));				
				prenotazione.setPrezzoTotale(rs.getDouble("PrezzoTotale"));
				
				prenotazione.caricaVisitaPrenotazioneDaDB();
				prenotazione.caricaUtentePrenotazioneDaDB();
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
			
			String query = "INSERT INTO UTENTIREGISTRATI(Email, Username, Nome, Cognome, Password) VALUES ( \'"+this.email+"\',"+"\'"+this.username+"\','"+this.nome+"\','"+this.cognome+"\','"+this.password+"');"; 
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
	        String query = new String("DELETE FROM UTENTIREGISTRATI WHERE Email ='"+this.email+"';");
	        
	        try{
	        	
				ret = DBConnectionManager.updateQuery(query);
				
	        } catch (ClassNotFoundException | SQLException e) {
	        	
	            e.printStackTrace();
	            ret = -1;
	            
	        }
	        
	        return ret;	// ret = 0, non ci sono stati errori
	        
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
	public ArrayList<DBPrenotazione> getPrenotazioni() {
		return prenotazioni;
	}
	public void setPrenotazioni(ArrayList<DBPrenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	
}
