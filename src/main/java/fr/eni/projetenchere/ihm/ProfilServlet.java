package fr.eni.projetenchere.ihm;

import java.io.IOException;

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

@WebServlet("/profil")
public class ProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UtilisateurManager manager = UtilisateurManagerFact.getInstance();
	
    public ProfilServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String profil = null;
		HttpSession session = request.getSession();
		
		Utilisateur uSession = (Utilisateur) session.getAttribute("utilisateur");
		profil = request.getParameter("p");
		
		try {
			if(manager.isConnectedUser(uSession.getPseudo(), profil)) {
				request.setAttribute("u", uSession);
				request.setAttribute("isProfil", true);
			}else {
				request.setAttribute("u", manager.getUtilisateurByPseudo(profil));
				request.setAttribute("isProfil", false);
			}
		} catch (Exception e) {
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/profil.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
