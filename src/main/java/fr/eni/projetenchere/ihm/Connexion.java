package fr.eni.projetenchere.ihm;

import java.io.IOException;
import java.time.format.DateTimeParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projetenchere.bll.UtilisateurManager;
import fr.eni.projetenchere.bll.UtilisateurManagerFact;
import fr.eni.projetenchere.bo.Utilisateur;

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/Connexion")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UtilisateurManager manager = UtilisateurManagerFact.getInstance();
       
    public Connexion() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// Création des paramètres à récupérer
		String user = null;
		String password = null;
		
		// Lecture des paramètres
		user = request.getParameter("user");
		password = request.getParameter("password");
		
		// Vérification de l'existance en base
		Utilisateur theUser;
		try {
			theUser = manager.getUtilisateurByPseudoAndMDP(user, password);
		
			System.out.println("ok");
			//RequestDispatcher rd = request.getRequestDispatcher("/");
			//rd.forward(request, response);
			
		} catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
			// Transfert de l'affichage à la JSP
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
			rd.forward(request, response);
			System.out.println("ko");
			
			e.printStackTrace();
		}
		
	}

}
