package fr.eni.projetenchere.ihm;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projetenchere.bll.achat.AchatManager;
import fr.eni.projetenchere.bll.achat.AchatManagerFact;
import fr.eni.projetenchere.bll.utilisateur.UtilisateurManager;
import fr.eni.projetenchere.bll.utilisateur.UtilisateurManagerFact;
import fr.eni.projetenchere.bo.Utilisateur;

/**
 * Servlet implementation class AchatServlet
 */
@WebServlet("/achat")
public class AchatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AchatManager achatManager = AchatManagerFact.getInstance();
	private UtilisateurManager utilisateurManager = UtilisateurManagerFact.getInstance();
       
    public AchatServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur u = (Utilisateur) request.getSession().getAttribute("utilisateur");
		if(u != null) {
			if(request.getParameter("success") != null) {
				request.setAttribute("success", "Merci pour votre achat, votre compte viens d'être crédité");
			}
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/achat.jsp");
			rd.forward(request, response);
		}
		else {
			response.sendRedirect(request.getContextPath() + "/connexion");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur u = (Utilisateur) request.getSession().getAttribute("utilisateur");
		try {
			// Récupération du formulaire
			int nbCredit = Integer.parseInt(request.getParameter("credit"));
			String ccn = request.getParameter("ccn");
			int cvv = Integer.parseInt(request.getParameter("cvv"));
			
			// on effectue l'achat
			u = achatManager.doAchat(u, ccn, nbCredit);
			
			// On update l'utilisateur
			u = utilisateurManager.getUtilisateurById(u.getNo_utilisateurs());
			HttpSession session = request.getSession();
			session.setAttribute("utilisateur", u);
			
			response.sendRedirect(request.getContextPath() + "/achat?success");
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("erreur", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/achat.jsp");
			rd.forward(request, response);
		}		
		
	}

}
