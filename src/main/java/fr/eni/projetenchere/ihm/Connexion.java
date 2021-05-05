package fr.eni.projetenchere.ihm;

import java.io.IOException;
import java.time.format.DateTimeParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projetenchere.bll.UtilisateurManager;
import fr.eni.projetenchere.bll.UtilisateurManagerFact;
import fr.eni.projetenchere.bo.Utilisateur;

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/connexion")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UtilisateurManager manager = UtilisateurManagerFact.getInstance();
       
    public Connexion() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
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
		
	        HttpSession session = request.getSession();
	        session.setAttribute("pseudo", theUser.getPseudo());
	        session.setAttribute("estAdministrateur", theUser.isAdministrateur());		
	
	        response.sendRedirect(request.getContextPath() + "/"); 
	        
		} catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
			// Transfert de l'affichage à la JSP
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
			rd.forward(request, response);
			
			e.printStackTrace(); 
		}
		
	}

}
