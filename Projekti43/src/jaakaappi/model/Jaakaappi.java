package jaakaappi.model;

public class Jaakaappi {
	
	private int id;
	private String nimi;
	private String avaus;
	private String valmistus;
	private String parastaennen;
	private String kategoria;

	public Jaakaappi() {
		
	}
	
	public Jaakaappi(int id, String nimi, String avaus, String valmistus, String parastaennen, String kategoria) {
		this.id = id;
		this.nimi = nimi;
		this.avaus = avaus;
		this.valmistus = valmistus;
		this.parastaennen = parastaennen;
		this.kategoria = kategoria;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNimi() {
		return nimi;
	}

	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	public String getAvaus() {
		return avaus;
	}

	public void setAvaus(String avaus) {
		this.avaus = avaus;
	}

	public String getValmistus() {
		return valmistus;
	}

	public void setValmistus(String valmistus) {
		this.valmistus = valmistus;
	}

	public String getParastaennen() {
		return parastaennen;
	}

	public void setParastaennen(String parastaennen) {
		this.parastaennen = parastaennen;
	}

	public String getKategoria() {
		return kategoria;
	}

	public void setKategoria(String kategoria) {
		this.kategoria = kategoria;
	}

	@Override
	public String toString() {
		return "Jaakaappi [id=" + id + ", nimi=" + nimi + ", avaus=" + avaus + ", valmistus=" + valmistus
				+ ", parastaennen=" + parastaennen + ", kategoria=" + kategoria + "]";
	}
	
	

}
