package fr.eni.projetenchere.ihm;

import java.io.IOException;
import java.time.format.DateTimeParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projetenchere.bll.utilisateur.UtilisateurManager;
import fr.eni.projetenchere.bll.utilisateur.UtilisateurManagerFact;
import fr.eni.projetenchere.bo.Utilisateur;

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/connexion")
public class ConnexionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UtilisateurManager manager = UtilisateurManagerFact.getInstance();
       
    public ConnexionServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("success") != null) {
			request.setAttribute("success", "Le mot de passe à été mis à jour");
		}
		String cookiePseudo = null;
		String cookieMdp = null;
		if(getCookie(request, "pseudo") != null & getCookie(request, "mdp") != null) {
			cookiePseudo = getCookie(request, "pseudo").getValue();
			cookieMdp = getCookie(request, "mdp").getValue();
		}
		String saveUserCookiesOK = "";
	    if(cookiePseudo != null && cookieMdp != null) {
	    	saveUserCookiesOK = "checked";
	    }
	    request.setAttribute("saveUserCookiesOK", saveUserCookiesOK);
	    request.setAttribute("pseudo", cookiePseudo);
		request.setAttribute("mdp", cookieMdp);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
		rd.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// Création des paramètres à récupérer
		String user = null;
		String password = null;
		String saveUserCookies = null;
		Cookie c;
		Cookie c2;
		
		// Lecture des paramètres
		user = request.getParameter("user");
		password = request.getParameter("password");
		saveUserCookies = request.getParameter("saveUserCookies");
		
		// Vérification de l'existance en base        
		Utilisateur theUser = null;
		String cookiePseudo = null;
		String cookieMdp = null;
		if(getCookie(request, "pseudo") != null & getCookie(request, "mdp") != null) {
			cookiePseudo = getCookie(request, "pseudo").getValue();
			cookieMdp = getCookie(request, "mdp").getValue();
		}
		try {
	        if((saveUserCookies != null && cookiePseudo == null && cookieMdp == null) || (!user.equals(cookiePseudo) && cookiePseudo != null)) {
	        	theUser = manager.getUtilisateurByPseudoAndMDP(user, password);
	        	c = new Cookie("pseudo", user);
	        	c2 = new Cookie("mdp", theUser.getMot_de_passe());
	        	response.addCookie(c);
	        	response.addCookie(c2);
	        }
	        else{
	        	try {
	        		theUser = manager.getUtilisateurByPseudoAndMDP(user, password);
	        	}catch(Exception e1) {
	        		try {
		        		theUser = manager.getUtilisateurByPseudoAndMDPWithCookies(user, password);
		        	}catch(Exception e2) {
		        		request.setAttribute("erreur", e2.getMessage());
		        		// Transfert de l'affichage à la JSP
		    			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
		    			rd.forward(request, response);
		        	}
	        	}
	        }
	        if(saveUserCookies == null && getCookie(request, "pseudo") != null & getCookie(request, "mdp") != null) {
	        	c = getCookie(request, "pseudo");
	        	c2 = getCookie(request, "mdp");
	        	c.setMaxAge(0);
	    		c2.setMaxAge(0);
	    		response.addCookie(c);
	        	response.addCookie(c2);
	        }
	        HttpSession session = request.getSession();
	        session.setAttribute("utilisateur", theUser);	
	        session.setMaxInactiveInterval(300);
	        response.sendRedirect(request.getContextPath() + "/"); 
	        
		} catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
			// Transfert de l'affichage à la JSP
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
			rd.forward(request, response);
		}		
	}
	
	private static Cookie getCookie( HttpServletRequest request, String nom ) {
        Cookie[] cookies = request.getCookies();
        if ( cookies != null ) {
            for ( Cookie cookie : cookies ) {
                if ( cookie != null && nom.equals( cookie.getName() ) ) {
                    return cookie;
                }
            }
        }
        return null;
    }

}
