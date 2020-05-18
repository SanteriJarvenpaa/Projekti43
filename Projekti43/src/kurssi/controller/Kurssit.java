package kurssi.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import java.util.ArrayList;

import kurssi.model.Kurssi;
import kurssi.model.dao.Dao;

/**
 * Servlet implementation class Kurssit
 */
@WebServlet("/Kurssit")
public class Kurssit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Kurssit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Kurssi.doGet()");
		
		String strJSON = "";
		ArrayList<Kurssi> kurssit;
		Dao dao = new Dao();
		
		kurssit = dao.listaaKaikki();
		strJSON = new JSONObject().put("kurssit", kurssit).toString();
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(strJSON);
	}

}
