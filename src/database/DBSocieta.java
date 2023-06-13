package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.EntitySocieta;

public class DBSocieta{
	private String nome;
	private String indirizzo;
	private int telefono;
	private String email;
	
	private ArrayList<DBVisitaGuidata> visite;
	
	// Costruttore di base
	public DBSocieta(){
		super();
		this.visite = new ArrayList<DBVisitaGuidata>();
	}
	
	// Costruttore che prende in ingresso la PK, carica dal DB l'oggetto con quella PK
	public DBSocieta(String nome) {
		this.nome = nome;
		this.visite = new ArrayList<DBVisitaGuidata>();
		
		caricaDaDB();
	}
	
	public DBSocieta(EntitySocieta societa) {
		
		this.nome=societa.getNome();
		this.indirizzo=societa.getIndirizzo();
		this.telefono=societa.getTelefono();
		this.email=societa.getEmail();
		this.visite=new ArrayList<DBVisitaGuidata>();
		
		for(int i=0; i<societa.getVisite().size(); i++) {
			
			DBVisitaGuidata visita=new DBVisitaGuidata(societa.getVisite().get(i));
			this.visite.add(visita);
			
		}
		
	}
	
	// effettua il caricamento nell'oggetto DBSocieta dei dati contenuti nel DB associati a quella PK
	public void caricaDaDB() {
		
		String query = "SELECT * FROM SOCIETA WHERE Nome='"+this.nome+"';";	
		try {
			
			ResultSet rs = DBConnectionManager.selectQuery(query);
			
			if(rs.next()) { //se ho un risultato
				
				//mi vado a prendere i dati, accedendo tramite il nome dell'attributo-colonna
				this.setIndirizzo(rs.getString("Indirizzo"));
				this.setTelefono(rs.getInt("Telefono"));
				this.setEmail(rs.getString("Email"));				
			}
		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
		
	// Carico dal DB anche l'oggetto di cui è composto DBSocietà, cioè DBVisitaGuidata, accedendo ad esso tramite la FK	
	public void caricaVisiteSocietaDaDB() {
			
		// Scrivo la query da inoltrare al DB
		String query = new String("SELECT * FROM VISITAGUIDATA WHERE Societa_Nome='"+this.nome+"'");
		//System.out.println(query); //stampo query per controllo in fase di DEBUG
			
		try {
				
			ResultSet rs = DBConnectionManager.selectQuery(query);
			
			while(rs.next()) {	//while perchè mi aspetto più risultati, avendo un ArrayList di oggetti
												
				DBVisitaGuidata visita = new DBVisitaGuidata();
				visita.setIdVisita(rs.getInt("IdVisita"));
				visita.setNome(rs.getString("Nome"));
				visita.setDescrizione(rs.getString("Descrizione"));
				visita.setCitta(rs.getString("Citta"));	
				visita.setMaxPartecipanti(rs.getInt("MaxPartecipanti"));
				visita.setPrezzoBase(rs.getDouble("PrezzoBase"));				
				
				this.visite.add(visita);
				}
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
		}
	

	public int SalvaInDB() {
		
		int ret = 0;
		
		String query = "INSERT INTO SOCIETA(Nome, Indirizzo, Telefono, Email) VALUES ( \'"+this.nome+"\',"+"\'"+this.indirizzo+"\','"+this.telefono+"\','"+this.email+"')"; 
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
        String query = new String("DELETE FROM SOCIETA WHERE Nome ='"+this.nome+"'");
        
        try{
        	
			ret = DBConnectionManager.updateQuery(query);
			
        } catch (ClassNotFoundException | SQLException e) {
        	
            e.printStackTrace();
            ret = -1;
            
        }
        
        return ret;	// ret = 0, non ci sono stati errori
        
    }
    
    // Funzione per visualizzare le società presenti nel DB, ritorna un ArrayList di DBSocieta 
    public static ArrayList<DBSocieta> VisualizzaSocieta() {
		
		
		ArrayList<DBSocieta> societa = new ArrayList<DBSocieta>();
		String query = new String("SELECT * FROM SOCIETA;");
		
		try {
			ResultSet rs = DBConnectionManager.selectQuery(query);
		
			while(rs.next()) {
				
				DBSocieta s = new DBSocieta(rs.getString("Nome"));
			
				societa.add(s);
				
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return societa;
	}
    
    
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<DBVisitaGuidata> getVisite() {
		return visite;
	}

	public void setVisite(ArrayList<DBVisitaGuidata> visite) {
		this.visite = visite;
	}
}
