package entity;

import database.DBVisitaGuidata;

import java.util.ArrayList;

import database.DBGuidaTuristica;

public class EntityGuidaTuristica {

	private String cognome;
	private String nome;
	private int eta;
	private String sesso;
	private String lingue;
	private int annoAbilitazione;
	private boolean disponibile;
	private EntityVisitaGuidata visita;
	
	public EntityGuidaTuristica() {
		
		super();
		
	}
	
	public EntityGuidaTuristica(String cognome) {
		
			DBGuidaTuristica guida=new DBGuidaTuristica(cognome);
			this.cognome=guida.getCognome();
			this.nome=guida.getNome();
			this.eta=guida.getEta();
			this.sesso=guida.getSesso();
			this.lingue=guida.getLingue();
			this.annoAbilitazione=guida.getAnnoAbilitazione();
			
			guida.caricaVisitaGuidaDaDB();
			caricaVisitaGuidata(guida);
			
	}
	
	public EntityGuidaTuristica(DBGuidaTuristica guida) {
		
		this.cognome=guida.getCognome();
		this.nome=guida.getNome();
		this.eta=guida.getEta();
		this.sesso=guida.getSesso();
		this.lingue=guida.getLingue();
		this.annoAbilitazione=guida.getAnnoAbilitazione();
		
		guida.caricaVisitaGuidaDaDB();
		caricaVisitaGuidata(guida);
		
	}
	
	public void caricaVisitaGuidata(DBGuidaTuristica guida) {
		
		EntityVisitaGuidata visita=new EntityVisitaGuidata(guida.getVisita());
		this.visita=visita;
		
	}
	
	public EntityGuidaTuristica(String cognome, String nome, int eta, String sesso, String lingue, int annoAbilitazione, boolean disponibile,
			EntityVisitaGuidata visita) {
		super();
		this.cognome = cognome;
		this.nome = nome;
		this.eta = eta;
		this.sesso = sesso;
		this.lingue = lingue;
		this.annoAbilitazione = annoAbilitazione;
		this.disponibile = disponibile;
		this.visita = visita;
	}

	public int scriviSuDB() {
		
		DBGuidaTuristica guida=new DBGuidaTuristica();
		
		guida.setCognome(this.cognome);
		guida.setNome(this.nome);
		guida.setEta(this.eta);
		guida.setSesso(this.sesso);
		guida.setLingue(this.lingue);
		guida.setDisponibile(this.disponibile);
		guida.setAnnoAbilitazione(this.annoAbilitazione);
		DBVisitaGuidata visita=new DBVisitaGuidata(this.visita);
		guida.setVisita(visita);
		
		int i=guida.SalvaInDB();
		return i;
		
	}

	
	public static int TrovaGuideDisponibili() {
		int ret = 0;
		ArrayList<DBGuidaTuristica> guideDisponibili = new ArrayList<DBGuidaTuristica>();
		
		guideDisponibili = DBGuidaTuristica.CercaGuideDisponibili();
		
		for(int i = 0; i < guideDisponibili.size(); i++) {
			EntityGuidaTuristica guidaEntity = new EntityGuidaTuristica(guideDisponibili.get(i));
			
			guidaEntity.toString();
			System.out.println("\n");
			ret = 1;
		}
		return ret;
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

	public EntityVisitaGuidata getVisita() {
		return visita;
	}

	public void setVisita(EntityVisitaGuidata visita) {
		this.visita = visita;
	}
	
	public String toString() {
		return "EntityGuidaTuristica [nome=" + nome + ", cognome=" + cognome + ", eta'=" + eta + ", sesso="
		+sesso+ ", lingue="+lingue+", annoAbilitazione="+annoAbilitazione+", visita="+visita.toString()+ "]";
	}
	
}
