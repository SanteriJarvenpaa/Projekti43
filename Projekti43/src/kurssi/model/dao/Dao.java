package kurssi.model.dao;

import java.sql.*;
import java.util.ArrayList;

import kurssi.model.Kurssi;

public class Dao {

	private Connection con = null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep = null;
	private String sql;
	private String db = "Kurssit.sqlite";
	
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
	
	public ArrayList<Kurssi> listaaKaikki(){
		ArrayList<Kurssi> kurssit = new ArrayList<Kurssi>();
		sql = "SELECT * FROM Kurssit";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);        		
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //jos kysely onnistui									
					while(rs.next()){
						Kurssi kurssi = new Kurssi();
						kurssi.setKurssiNro(rs.getInt(1));
						kurssi.setKurssiNimi(rs.getString(2));
						kurssi.setKurssipisteet(rs.getInt(3));
						kurssit.add(kurssi);
						}					
				}				
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return kurssit;
	}

}
