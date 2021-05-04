package fr.eni.projetenchere.ihm;

import java.io.IOException;

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
 * Servlet implementation class Inscription
 */
@WebServlet("/inscription")
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UtilisateurManager manager = UtilisateurManagerFact.getInstance();
	
    public Inscription() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/inscription.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// Création des champs a recuperer
		String pseudo = null; 
		String nom = null; 
		String prenom = null; 
		String email = null; 
		String telephone = null; 
		String rue = null; 
		String ville = null; 
		String codePostal = null; 
		String motDePasse = null; 
		String motDePasseConfirmation = null;
		
		// Lecture des parametres
		pseudo = request.getParameter("pseudo");
		nom = request.getParameter("nom");
		prenom = request.getParameter("prenom");
		email = request.getParameter("email");
		telephone = request.getParameter("telephone");
		rue = request.getParameter("rue");
		ville = request.getParameter("ville");
		codePostal = request.getParameter("codePostal");
		motDePasse = request.getParameter("motDePasse");
		motDePasseConfirmation = request.getParameter("motDePasseConfirmation");
		
		// Verification de l'existence en base
		try {
			manager.insertUtilisateur(pseudo, nom, prenom, email, telephone, rue, ville, codePostal, motDePasse, motDePasseConfirmation);	
		} catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
			// Transfert de l'affichage a la JSP
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/inscription.jsp");
			rd.forward(request, response);
			
			e.printStackTrace();
		}
		
		//Transfert vers l'accueil
		//RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
		rd.forward(request, response);
	}
	
}
