package entity;

import database.*;
import java.util.ArrayList;


public class EntitySocieta {

	private String nome;
	private String indirizzo;
	private int telefono;
	private String email;
	private ArrayList<EntityVisitaGuidata> visite;
	
	public EntitySocieta() {
		
		super();
		this.visite=new ArrayList<EntityVisitaGuidata>();
		
	}
	
	public EntitySocieta(String nome, String indirizzo, int telefono, String email, ArrayList<EntityVisitaGuidata> visite) {
		
		super();
		this.nome= nome;
		this.indirizzo= indirizzo;
		this.telefono= telefono;
		this.email= email;
		this.visite= visite;
		
	}
	
	public EntitySocieta(String nome){
		
		DBSocieta societa=new DBSocieta(nome);
		
		this.nome=societa.getNome();
		this.indirizzo=societa.getIndirizzo();
		this.telefono=societa.getTelefono();
		this.email=societa.getEmail();
		this.visite=new ArrayList<EntityVisitaGuidata>();
		
		societa.caricaVisiteSocietaDaDB();
		caricaVisiteSocieta(societa);
	}
	
	public EntitySocieta(DBSocieta societa) {
		
		this.nome=societa.getNome();
		this.indirizzo=societa.getIndirizzo();
		this.telefono=societa.getTelefono();
		this.email=societa.getEmail();
		this.visite=new ArrayList<EntityVisitaGuidata>();
		
		societa.caricaVisiteSocietaDaDB();
		caricaVisiteSocieta(societa);
		
	}
	
	public int ScriviSuDB() {
		
		DBSocieta societa=new DBSocieta();
		societa.setNome(this.nome);
		societa.setEmail(this.email);
		societa.setIndirizzo(this.indirizzo);
		societa.setTelefono(this.telefono);
		
		ArrayList<DBVisitaGuidata> visite=new ArrayList<DBVisitaGuidata>();
		
		for(int k=0; k<this.getVisite().size(); k++) {
			
			DBVisitaGuidata visita=new DBVisitaGuidata(this.getVisite().get(k));
			visite.add(visita);
			
		}
		
		societa.setVisite(visite);
		
		int i=societa.SalvaInDB();
		return i;
		
	}
	
	public void caricaVisiteSocieta(DBSocieta società) {
		
		for(int i=0; i<società.getVisite().size(); i++) {
			
			EntityVisitaGuidata visita=new EntityVisitaGuidata(società.getVisite().get(i));
			this.visite.add(visita);
			
		}
		
	}

	//Funzione di stampa delle societa presenti nel sistema
	public static ArrayList<EntitySocieta> VisualizzaSocieta() {		
		ArrayList<DBSocieta> societa = new ArrayList<DBSocieta>();
		ArrayList<EntitySocieta> EntitySocieta = new ArrayList<EntitySocieta>();
		societa = DBSocieta.VisualizzaSocieta();
			
		for(int i = 0; i < societa.size(); i++) {
			EntitySocieta societaEntity = new EntitySocieta(societa.get(i));
			
			EntitySocieta.add(societaEntity);
		}
		return EntitySocieta;
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

	public ArrayList<EntityVisitaGuidata> getVisite() {
		return visite;
	}

	public void setVisite(ArrayList<EntityVisitaGuidata> visite) {
		this.visite = visite;
	}
	
	public void addVisita(EntityVisitaGuidata visita) {
		this.visite.add(visita);
	}
	
}
