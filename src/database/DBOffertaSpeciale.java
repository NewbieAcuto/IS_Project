package database;

import java.sql.ResultSet;
import entity.EntityOffertaSpeciale;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class DBOffertaSpeciale {
	private int idOfferta;
	private double percentualeSconto;
	private Date inizio;
	private Date fine;
	
	private ArrayList<DBVisitaGuidata> visite;
	
	
	
	public DBOffertaSpeciale() {
		super();
		this.visite = new ArrayList<DBVisitaGuidata>();
	}
	
	
	public DBOffertaSpeciale(int idOfferta) {
		this.idOfferta = idOfferta;
		this.visite = new ArrayList<DBVisitaGuidata>();
		
		caricaDaDB();
	}
	
	public DBOffertaSpeciale(EntityOffertaSpeciale offerta) {
		
		this.idOfferta=offerta.getIdOfferta();
		this.percentualeSconto=offerta.getPercentualeSconto();
		this.inizio=offerta.getInizio();
		this.fine=offerta.getFine();
		
		for(int i=0; i<offerta.getVisite().size(); i++) {
			
			DBVisitaGuidata visita=new DBVisitaGuidata(offerta.getVisite().get(i));
			this.visite.add(visita);
			
		}
		
	}

	public void caricaDaDB() {
		
		String query = "SELECT * FROM OFFERTESPECIALI WHERE IdOfferta='"+this.idOfferta+"';";	
		try {
			
			java.sql.ResultSet rs = DBConnectionManager.selectQuery(query);
			
			if(rs.next()) { //se ho un risultato
				
				this.setPercentualeSconto(rs.getDouble("PercentualeSconto"));
				this.setInizio(rs.getDate("Inizio"));
				this.setFine(rs.getDate("Fine"));
				
			}
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void caricaVisiteOffertaDaDB() {
		
		// Scrivo la query da inoltrare al DB
		String query = new String("SELECT * FROM VISITAGUIDATA WHERE Offerta='"+this.idOfferta+"'");
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
		
		String query = "INSERT INTO OFFERTESPECIALI(IdOfferta, PercentualeSconto, Inizio, Fine) VALUES ( \'"+this.idOfferta+"\',"+"\'"+this.percentualeSconto+"\','"+this.inizio+"\','"+this.fine+"')"; 
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
        String query = new String("DELETE FROM OFFERTESPECIALI WHERE IdOfferta ='"+this.idOfferta+"'");
        
        try{
        	
			ret = DBConnectionManager.updateQuery(query);
			
        } catch (ClassNotFoundException | SQLException e) {
        	
            e.printStackTrace();
            ret = -1;
            
        }
        
        return ret;	// ret = 0, non ci sono stati errori
        
    }
    
    
	public double getPercentualeSconto() {
		return percentualeSconto;
	}
	public void setPercentualeSconto(double percentualeSconto) {
		this.percentualeSconto = percentualeSconto;
	}
	public Date getInizio() {
		return inizio;
	}
	public void setInizio(Date inizio) {
		this.inizio = inizio;
	}
	public Date getFine() {
		return fine;
	}
	public void setFine(Date fine) {
		this.fine = fine;
	}
	public ArrayList<DBVisitaGuidata> getVisite() {
		return visite;
	}
	public void setVisite(ArrayList<DBVisitaGuidata> visite) {
		this.visite = visite;
	}
	public int getIdOfferta() {
		return idOfferta;
	}
	public void setIdOfferta(int idOfferta) {
		this.idOfferta = idOfferta;
	}
	
}
