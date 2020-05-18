package kurssi.model;

public class Kurssi {

	private int kurssiNro;
	private String kurssiNimi;
	private int kurssipisteet;
	
	public Kurssi() {
		
	}
	
	public Kurssi(int kn, String nimi, int kp) {
		this.kurssiNimi = nimi;
		this.kurssiNro = kn;
		this.kurssipisteet = kp;
	}

	public int getKurssiNro() {
		return kurssiNro;
	}

	public void setKurssiNro(int kurssiNro) {
		this.kurssiNro = kurssiNro;
	}

	public String getKurssiNimi() {
		return kurssiNimi;
	}

	public void setKurssiNimi(String kurssiNimi) {
		this.kurssiNimi = kurssiNimi;
	}

	public int getKurssipisteet() {
		return kurssipisteet;
	}

	public void setKurssipisteet(int kurssipisteet) {
		this.kurssipisteet = kurssipisteet;
	}

	@Override
	public String toString() {
		return "Kurssi [kurssiNro=" + kurssiNro + ", kurssiNimi=" + kurssiNimi + ", kurssipisteet=" + kurssipisteet
				+ "]";
	}
	
	
	
}
