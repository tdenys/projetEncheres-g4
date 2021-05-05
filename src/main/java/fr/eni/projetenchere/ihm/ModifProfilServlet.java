package fr.eni.projetenchere.ihm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/profil/modifier")
public class ModifProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ModifProfilServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pseudo = null; 
		String nom = null; 
		String prenom = null; 
		String email = null; 
		String telephone = null; 
		String rue = null; 
		String ville = null; 
		String codePostal = null; 
		String motDePasseActuel = null; 
		String nouveauMotDePasse = null;
		String motDePasseConfirmation = null;
		
		pseudo = request.getParameter("pseudo");
		nom = request.getParameter("nom");
		prenom = request.getParameter("prenom");
		email = request.getParameter("email");
		telephone = request.getParameter("telephone");
		rue = request.getParameter("rue");
		ville = request.getParameter("ville");
		codePostal = request.getParameter("codePostal");
		motDePasseActuel = request.getParameter("motDePasseActuel");
		nouveauMotDePasse = request.getParameter("nouveauMotDePasse");
		motDePasseConfirmation = request.getParameter("motDePasseConfirmation");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
