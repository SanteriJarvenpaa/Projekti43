package jaakaappi.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import java.util.ArrayList;

import jaakaappi.model.Jaakaappi;
import jaakaappi.model.dao.Dao;

/**
 * Servlet implementation class Jaakaapit
 */
@WebServlet("/jaakaapit/*")
public class Jaakaapit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Jaakaapit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Jaakaappi.doGet()");
		String pathInfo = request.getPathInfo();
		System.out.println("polku: " + pathInfo);
		String strJSON = "";
		ArrayList<Jaakaappi> jaakaapit;
		Dao dao = new Dao();
		if(pathInfo == null) {
			jaakaapit = dao.listaaKaikki();
			strJSON = new JSONObject().put("jaakaapit", jaakaapit).toString();
		}else if(pathInfo.indexOf("haeyksi")!=-1){
			int elintarvike_id = Integer.parseInt(pathInfo.replace("/haeyksi/", ""));
			Jaakaappi jaakaappi = dao.etsiJaakaappi(elintarvike_id);
			if(jaakaappi == null) {
				strJSON = "{}";
			}else {
				JSONObject JSON = new JSONObject();
				JSON.put("elintarvike_id", jaakaappi.getId());
				JSON.put("nimi", jaakaappi.getNimi());
				JSON.put("avattu_pvm", jaakaappi.getAvaus());
				JSON.put("valmistuspv", jaakaappi.getValmistus());
				JSON.put("parasta_ennen", jaakaappi.getParastaennen());
				JSON.put("kategoria", jaakaappi.getKategoria());
				strJSON = JSON.toString();
			}
		}else {
			String hakusana = pathInfo.replace("/", "");
			jaakaapit = dao.listaaKaikki(hakusana);
			strJSON = new JSONObject().put("jaakaapit", jaakaapit).toString();
		}
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(strJSON);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Jaakaapit.doPost");
		JSONObject jsonObj = new JsonStrToObj().convert(request);
		Jaakaappi jaakaappi = new Jaakaappi();
		jaakaappi.setNimi(jsonObj.getString("nimi"));
		jaakaappi.setParastaennen(jsonObj.getString("parasta_ennen"));
		jaakaappi.setValmistus(jsonObj.getString("valmistuspv"));
		jaakaappi.setKategoria(jsonObj.getString("kategoria"));
		jaakaappi.setAvaus(jsonObj.getString("avattu_pvm"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();
		if(dao.lisaaJaakaappi(jaakaappi)) {
			out.println("{\"response\":1}");
		}else {
			out.println("{\"response\":0}");
		}
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("Jaakaapit.doPut()");
		JSONObject jsonObj = new JsonStrToObj().convert(request);
		Jaakaappi jaakaappi = new Jaakaappi();
		jaakaappi.setId(Integer.parseInt(jsonObj.getString("elintarvike_id")));
		jaakaappi.setNimi(jsonObj.getString("nimi"));
		jaakaappi.setParastaennen(jsonObj.getString("parasta_ennen"));
		jaakaappi.setValmistus(jsonObj.getString("valmistuspv"));
		jaakaappi.setKategoria(jsonObj.getString("kategoria"));
		jaakaappi.setAvaus(jsonObj.getString("avattu_pvm"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();
		if(dao.muutaJaakaappi(jaakaappi)) {
			out.println("{\"response\":1}"); 
		}else {
			out.println("{\"response\":0}");
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Jaakaapit.doDelete()");
		String pathInfo = request.getPathInfo();
		int elintarvike_id = Integer.parseInt(pathInfo.replace("/", ""));
		Dao dao = new Dao();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		if(dao.poistaJaakaappi(elintarvike_id)) {
			out.println("{\"response\":1}");
		}else {
			out.println("{\"response\":0}");
		}
	}
}
