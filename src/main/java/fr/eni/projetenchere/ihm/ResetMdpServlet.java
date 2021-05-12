package fr.eni.projetenchere.ihm;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projetenchere.bll.utilisateur.UtilisateurManager;
import fr.eni.projetenchere.bll.utilisateur.UtilisateurManagerFact;

/**
 * Servlet implementation class ResetMdpServlet
 */
@WebServlet("/resetMdp")
public class ResetMdpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UtilisateurManager utilisateurManager = UtilisateurManagerFact.getInstance();
       
    public ResetMdpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pseudo = request.getParameter("pseudo");
		String mdpCrypt = request.getParameter("token");
		
		if(pseudo != null && mdpCrypt != null) {
			try {
				// On vérifie si le pseudo coorespond au mdp crypté
				if(mdpCrypt.equals(utilisateurManager.getUtilisateurByPseudo(pseudo).getMot_de_passe())) {
					request.setAttribute("pseudo", pseudo);
					request.setAttribute("mdpCrypt", mdpCrypt);
				}
				else {
					request.setAttribute("erreur", "Login incorrect");
				}
			} catch(Exception e) {
				request.setAttribute("erreur", e.getMessage());
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/motdepasseoublie.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pseudo = request.getParameter("pseudo");
		String mdpCrypt = request.getParameter("token");
		
		if(!mdpCrypt.equals("")) {
			try {
				String nouveauMotDePasse = request.getParameter("nouveauMotDePasse");
				String motDePasseConfirmation = request.getParameter("motDePasseConfirmation");
				
				request.setAttribute("pseudo", pseudo);
				request.setAttribute("mdpCrypt", mdpCrypt);
				
				utilisateurManager.updateUtilisateurMDP(pseudo, mdpCrypt, nouveauMotDePasse, motDePasseConfirmation);
				response.sendRedirect(request.getContextPath() + "/connexion?success");
				
			} catch(Exception e) {
				request.setAttribute("erreur", e.getMessage());
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/motdepasseoublie.jsp");
				rd.forward(request, response);
			}
		} else if(pseudo.equals("")) {
			try {
				String pseudoSaisi = request.getParameter("pseudoSaisi");
				String email = utilisateurManager.getUtilisateurByPseudo(pseudoSaisi).getEmail();
				String mdpCryptUtilisateur =utilisateurManager.getUtilisateurByPseudo(pseudoSaisi).getMot_de_passe();
				
				String lien = "http://localhost:8080/projetEncheres-g4/resetMdp?pseudo=" + pseudoSaisi + "&token=" + mdpCryptUtilisateur;
				
				// Envoi du mail
				request.setAttribute("mail", lien);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/motdepasseoublie.jsp");
				rd.forward(request, response);
				
			} catch(Exception e) {
				request.setAttribute("erreur", "Login incorrect");
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/motdepasseoublie.jsp");
				rd.forward(request, response);
			}
		}
		else {
			response.sendRedirect(request.getContextPath() + "/connexion");
		}	
	}

}
