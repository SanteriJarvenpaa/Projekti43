package jaakaappi.model.dao;

import java.sql.*;
import java.util.ArrayList;

import jaakaappi.model.Jaakaappi;

public class Dao {

	private Connection con = null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep = null;
	private String sql;
	private String db = "Jaakaapit.sqlite";
	
	public Dao() {
		
	}
	
	private Connection yhdista(){
    	Connection con = null;    	
    	String path = System.getProperty("catalina.base");    	
    	path = path.substring(0, path.indexOf(".metadata")).replace("\\", "/"); //Eclipsessa
    	//System.out.println("Polku on: " + path);
    	//path += "/webapps/"; //Tuotannossa. Laita kanta webapps-kansioon.
    	String url = "jdbc:sqlite:"+path+db;    	
    	try {	       
    		Class.forName("org.sqlite.JDBC");
	        con = DriverManager.getConnection(url);	
	        System.out.println("Yhteys avattu.");
	     }catch (Exception e){	
	    	 System.out.println("Yhteyden avaus ep�onnistui.");
	        e.printStackTrace();	         
	     }
	     return con;
	}
	
	public ArrayList<Jaakaappi> listaaKaikki(){
		ArrayList<Jaakaappi> jaakaapit = new ArrayList<Jaakaappi>();
		sql = "SELECT * FROM JAAKAAPPI";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);        		
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //jos kysely onnistui									
					while(rs.next()){
						Jaakaappi jaakaappi = new Jaakaappi();
						jaakaappi.setId(rs.getInt(1));
						jaakaappi.setNimi(rs.getString(2));
						System.out.println(rs.getString(3));
						jaakaappi.setAvaus(rs.getString(3));
						jaakaappi.setValmistus(rs.getString(4));
						jaakaappi.setParastaennen(rs.getString(5));
						jaakaappi.setKategoria(rs.getString(6));
						jaakaapit.add(jaakaappi);
						}					
				}				
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return jaakaapit;
	}
	
	public ArrayList<Jaakaappi> listaaKaikki(String hakusana){
		ArrayList<Jaakaappi> jaakaapit = new ArrayList<Jaakaappi>();
		sql = "SELECT * FROM JAAKAAPPI WHERE elintarvike_id LIKE ? or nimi LIKE ? or kategoria LIKE ?";
		try {
			con = yhdista();
			if(con != null) {
				stmtPrep = con.prepareStatement(sql);
				stmtPrep.setString(1, "%" + hakusana + "%");
				stmtPrep.setString(2, "%" + hakusana + "%");
				stmtPrep.setString(3, "%" + hakusana + "%");
				rs = stmtPrep.executeQuery();
				if(rs!=null) {
					while(rs.next()) {
						Jaakaappi jaakaappi = new Jaakaappi();
						jaakaappi.setId(rs.getInt(1));
						jaakaappi.setNimi(rs.getString(2));
						jaakaappi.setAvaus(rs.getString(3));
						jaakaappi.setValmistus(rs.getString(4));
						jaakaappi.setParastaennen(rs.getString(5));
						jaakaappi.setKategoria(rs.getString(6));
						jaakaapit.add(jaakaappi);
					}
				}
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jaakaapit;
	}
	
	public boolean lisaaJaakaappi(Jaakaappi jaakaappi){
		boolean paluuArvo=true;
		sql="INSERT INTO JAAKAAPPI(nimi, parastaennenpv, valmistuspv, kategoria) VALUES(?,?,?,?)";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, jaakaappi.getNimi());
			stmtPrep.setString(2, jaakaappi.getParastaennen());
			stmtPrep.setString(3, jaakaappi.getValmistus());
			stmtPrep.setString(4, jaakaappi.getKategoria());
			stmtPrep.executeUpdate();
			//System.out.println("Uusin id on " + stmtPrep.getGeneratedKeys().getInt(1));
	        con.close();
		} catch (SQLException e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public boolean poistaJaakaappi(int elintarvike_id) {
		boolean paluuArvo = true;
		sql = "DELETE FROM JAAKAAPPI WHERE elintarvike_id=?";
		try {
			con = yhdista();
			stmtPrep = con.prepareStatement(sql);
			stmtPrep.setInt(1, elintarvike_id);
			stmtPrep.executeUpdate();
			con.close();
		}catch (SQLException e) {
			e.printStackTrace();
			paluuArvo = false;
		}
		return paluuArvo;
	}
	
	public Jaakaappi etsiJaakaappi(int elintarvike_id) {
		Jaakaappi jaakaappi = null;
		sql = "SELECT * FROM JAAKAAPPI WHERE elintarvike_id=?";
		try {
			con = yhdista();
			if(con!=null) {
				stmtPrep = con.prepareStatement(sql);
				stmtPrep.setInt(1, elintarvike_id);
				rs = stmtPrep.executeQuery();
				if(rs.isBeforeFirst()) {
					jaakaappi = new Jaakaappi(rs.getInt("elintarvike_id"), rs.getString("nimi"), rs.getString("avattu_pvm"), rs.getString("valmistuspv"), rs.getString("parasta_ennen"), rs.getString("kategoria"));
				}
				con.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return jaakaappi;
	}
	
	public boolean muutaJaakaappi(Jaakaappi jaakaappi){
		boolean paluuArvo=true;
		sql="UPDATE JAAKAAPPI SET nimi=?, avattu_pvm=?, valmistuspv=?, parasta_ennen=?, kategoria=? WHERE elintarvike_id=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, jaakaappi.getNimi());
			stmtPrep.setString(2, jaakaappi.getAvaus());
			stmtPrep.setString(3, jaakaappi.getValmistus());
			stmtPrep.setString(4, jaakaappi.getParastaennen());
			stmtPrep.setString(5, jaakaappi.getKategoria());
			stmtPrep.setInt(5, jaakaappi.getId());
			stmtPrep.executeUpdate();
	        con.close();
		} catch (SQLException e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}

}
