package entity;

import database.*;
import java.util.ArrayList;


public class EntityVisitaGuidata {

	private int idVisita ;
	private String nome; 
	private String descrizione;
	private String citta;
	private int maxPartecipanti;
	private double prezzoBase;
	private EntityOffertaSpeciale offerta;
	private ArrayList<EntityPrenotazione> prenotazioni;
	private ArrayList<EntityOpzione> opzioni;
	private EntitySocieta societa;
	private EntityGuidaTuristica guida;
	
	public EntityVisitaGuidata() {
		
		super();
		this.prenotazioni=new ArrayList<EntityPrenotazione>();
		this.opzioni=new ArrayList<EntityOpzione>();
		
	}
	
	/* Costruttore di copia da db a entity */
	public EntityVisitaGuidata(int idVisita) {
		
		DBVisitaGuidata visita=new DBVisitaGuidata(idVisita);
			
		this.idVisita=visita.getIdVisita();
		this.nome=visita.getNome();
		this.descrizione=visita.getDescrizione();
		this.citta=visita.getCitta();
		this.maxPartecipanti=visita.getMaxPartecipanti();
		this.prezzoBase=visita.getPrezzoBase();
		this.prenotazioni=new ArrayList<EntityPrenotazione>();
		this.opzioni=new ArrayList<EntityOpzione>();
			
		visita.caricaOffertaVisitaDaDB();
		caricaOffertaVisita(visita);
			
		visita.caricaPrenotazioniVisitaDaDB();
		caricaPrenotazioniVisita(visita);
			
		visita.caricaOpzioniVisitaDaDB();
		caricaOpzioniVisita(visita);
			
		visita.caricaSocietaVisitaDaDB();
		caricaSocietaVisita(visita);
			
		visita.caricaGuidaVisitaDaDB();
		caricaGuidaVisita(visita);
			
		}
		/* Costruttore di copia da db a entity */
	public EntityVisitaGuidata(DBVisitaGuidata visita) {
		
		this.idVisita=visita.getIdVisita();
		this.nome=visita.getNome();
		this.descrizione=visita.getDescrizione();
		this.citta=visita.getCitta();
		this.maxPartecipanti=visita.getMaxPartecipanti();
		this.prezzoBase=visita.getPrezzoBase();
		this.prenotazioni=new ArrayList<EntityPrenotazione>();
		this.opzioni=new ArrayList<EntityOpzione>();
			
		visita.caricaOffertaVisitaDaDB();
		caricaOffertaVisita(visita);
			
		visita.caricaPrenotazioniVisitaDaDB();
		caricaPrenotazioniVisita(visita);
			
		visita.caricaOpzioniVisitaDaDB();
		caricaOpzioniVisita(visita);
			
		visita.caricaSocietaVisitaDaDB();
		caricaSocietaVisita(visita);
			
		visita.caricaGuidaVisitaDaDB();
		caricaGuidaVisita(visita);
			
	}
	
	
		
	public EntityVisitaGuidata(int idVisita, String nome, String descrizione, String citta, int maxPartecipanti,
				double prezzoBase, EntityOffertaSpeciale offerta, ArrayList<EntityPrenotazione> prenotazioni,
				ArrayList<EntityOpzione> opzioni, EntitySocieta societa, EntityGuidaTuristica guida) {
			super();
			this.idVisita = idVisita;
			this.nome = nome;
			this.descrizione = descrizione;
			this.citta = citta;
			this.maxPartecipanti = maxPartecipanti;
			this.prezzoBase = prezzoBase;
			this.offerta = offerta;
			this.prenotazioni = prenotazioni;
			this.opzioni = opzioni;
			this.societa = societa;
			this.guida = guida;
			
		}
	
	public int ScriviSuDB() {
		
		DBVisitaGuidata visita=new DBVisitaGuidata();
		
		visita.setIdVisita(this.idVisita);
		visita.setDescrizione(this.descrizione);
		visita.setCitta(this.citta);
		visita.setMaxPartecipanti(this.maxPartecipanti);
		visita.setPrezzoBase(this.prezzoBase);
		
		DBOffertaSpeciale offerta=new DBOffertaSpeciale(this.offerta);
		visita.setOfferta(offerta);
		
		DBSocieta societa=new DBSocieta(this.societa);
		visita.setSocieta(societa);
		
		DBGuidaTuristica guida=new DBGuidaTuristica(this.guida);
		visita.setGuida(guida);
		
		ArrayList<DBPrenotazione> prenotazioni=new ArrayList<DBPrenotazione>();
		
		for(int k=0; k<this.getPrenotazioni().size(); k++) {
			
			DBPrenotazione prenotazione=new DBPrenotazione(this.getPrenotazioni().get(k));
			prenotazioni.add(prenotazione);
			
		}
		
		visita.setPrenotazioni(prenotazioni);
		
		int i=visita.SalvaInDB();
		return i;
		
	}
	
	public void caricaOffertaVisita(DBVisitaGuidata visita){
		
		EntityOffertaSpeciale offerta=new EntityOffertaSpeciale(visita.getOfferta());
		this.offerta=offerta;
		
	}
	
	public void caricaPrenotazioniVisita(DBVisitaGuidata visita) {
		
		for(int i=0; i<visita.getPrenotazioni().size(); i++) {
			
			EntityPrenotazione prenotazione=new EntityPrenotazione(visita.getPrenotazioni().get(i));
			this.prenotazioni.add(prenotazione);
			
		}
		
	}
	
	public void caricaOpzioniVisita(DBVisitaGuidata visita) {
		
		for(int i=0; i<visita.getOpzioni().size(); i++) {
			
			EntityOpzione opzione=new EntityOpzione(visita.getOpzioni().get(i));
			this.opzioni.add(opzione);
			
		}
	}
	
	public void caricaSocietaVisita(DBVisitaGuidata visita) {
		
		EntitySocieta societa=new EntitySocieta(visita.getSocieta());
		this.societa=societa;
		
	}
	
	public void caricaGuidaVisita(DBVisitaGuidata visita) {
		
		EntityGuidaTuristica guida=new EntityGuidaTuristica(visita.getGuida());
		this.guida=guida;
		
	}
// funzione per la ricerca di una visita guidata e return dell'oggetto EntityVisitaGuidata
	 public static EntityVisitaGuidata TrovaVisitaGuidata(int idVisita) {
		DBVisitaGuidata dbVisita = DBVisitaGuidata.TrovaVisita(idVisita);
		
		EntityVisitaGuidata visita = new EntityVisitaGuidata(dbVisita);
		
		return visita;
	}

//Funzione che restituisce una stampa delle visite guidate presenti nel sistema
	public static ArrayList<EntityVisitaGuidata> VisualizzaVisiteGuidate() {
		
		ArrayList<DBVisitaGuidata> visite = new ArrayList<DBVisitaGuidata>();
		ArrayList<EntityVisitaGuidata> EntityVisite = new ArrayList <EntityVisitaGuidata>();
		visite = DBVisitaGuidata.VisualizzaVisite();
		
		for(int i = 0; i < visite.size(); i++) {
			EntityVisitaGuidata EntityVisita = new EntityVisitaGuidata(visite.get(i));
			
			EntityVisite.add(EntityVisita);
		}
		return EntityVisite;
	}

//Funzione che restituisce una stampa delle visite guidate presenti nel sistema per citta inserita
	public static ArrayList<EntityVisitaGuidata> VisualizzaVisiteGuidate(String citta) {
	
		ArrayList<DBVisitaGuidata> visite = new ArrayList<DBVisitaGuidata>();
		ArrayList<EntityVisitaGuidata> EntityVisite = new ArrayList <EntityVisitaGuidata>();
		visite = DBVisitaGuidata.VisualizzaVisite(citta);
		
		for(int i = 0; i < visite.size(); i++) {
			EntityVisitaGuidata EntityVisita = new EntityVisitaGuidata(visite.get(i));
			
			EntityVisite.add(EntityVisita);
		}
		return EntityVisite;
	}
	
	//Funzione che restituisce una stampa delle visite guidate presenti nel sistema per citta e durata inserita
		public static ArrayList<EntityVisitaGuidata> VisualizzaVisiteGuidate(String citta, int durata) {
			
			ArrayList<DBVisitaGuidata> visite = new ArrayList<DBVisitaGuidata>();
			ArrayList<EntityVisitaGuidata> EntityVisite = new ArrayList <EntityVisitaGuidata>();
			visite = DBVisitaGuidata.VisualizzaVisite(citta, durata);
			
			for(int i = 0; i < visite.size(); i++) {
				EntityVisitaGuidata EntityVisita = new EntityVisitaGuidata(visite.get(i));
				
				EntityVisite.add(EntityVisita);
			}
			return EntityVisite;
		}
// Funzione per effettuare la modifica di una visita guidata
	public int ModificaSuDB() {
		
		DBVisitaGuidata visita=new DBVisitaGuidata();
		
		visita.setIdVisita(this.idVisita);
		visita.setDescrizione(this.descrizione);
		visita.setCitta(this.citta);
		visita.setMaxPartecipanti(this.maxPartecipanti);
		visita.setPrezzoBase(this.prezzoBase);
		
		DBOffertaSpeciale offerta=new DBOffertaSpeciale(this.offerta);
		visita.setOfferta(offerta);
		
		DBSocieta societa=new DBSocieta(this.societa);
		visita.setSocieta(societa);
		
		DBGuidaTuristica guida=new DBGuidaTuristica(this.guida);
		visita.setGuida(guida);
		
		ArrayList<DBPrenotazione> prenotazioni=new ArrayList<DBPrenotazione>();
		
		for(int k=0; k<this.getPrenotazioni().size(); k++) {
			
			DBPrenotazione prenotazione=new DBPrenotazione(this.getPrenotazioni().get(k));
			prenotazioni.add(prenotazione);
			
		}
		
		visita.setPrenotazioni(prenotazioni);
		
		int i=visita.ModificaInDB();
		return i;
		
	}

	
	// Funzione per effettuare l'eliminazione di una visita guidata
		public int EliminaSuDB() {
			
			DBVisitaGuidata visita=new DBVisitaGuidata();
			
			visita.setIdVisita(this.idVisita);
		
			int i=visita.EliminaDalDB();
			return i;
			
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

	public EntityOffertaSpeciale getOfferta() {
		return offerta;
	}

	public void setOfferta(EntityOffertaSpeciale offerta) {
		this.offerta = offerta;
	}

	public ArrayList<EntityPrenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	public void setPrenotazioni(ArrayList<EntityPrenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}

	public ArrayList<EntityOpzione> getOpzioni() {
		return opzioni;
	}

	public void setOpzioni(ArrayList<EntityOpzione> opzioni) {
		this.opzioni = opzioni;
	}

	public EntitySocieta getSocieta() {
		return societa;
	}
	
	public void addOpzione(EntityOpzione opzione) {
		this.opzioni.add(opzione);
	}
	
	public void addPrenotazione(EntityPrenotazione prenotazione) {
		this.prenotazioni.add(prenotazione);
	}

	public void setSocieta(EntitySocieta societa) {
		this.societa = societa;
	}

	public EntityGuidaTuristica getGuida() {
		return guida;
	}

	public void setGuida(EntityGuidaTuristica guida) {
		this.guida = guida;
	}
	
	@Override
	public String toString() {
		return "EntityVisitaGuidata [idVisita=" + this.idVisita + ", nome=" + this.nome + ", descrizione=" + this.descrizione
				+ ", citta=" + this.citta + ", maxPartecipanti=" + this.maxPartecipanti + ", prezzoBase=" + this.prezzoBase
				+ ", offerta=" + this.offerta + ", prenotazioni=" + this.prenotazioni + ", societa="
				+ this.societa.getNome() + ", guida=" + this.guida.getNome() + ", opzione="+this.opzioni.toString()+", ";
	}
	
}
