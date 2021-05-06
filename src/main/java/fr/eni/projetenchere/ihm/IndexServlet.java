package fr.eni.projetenchere.ihm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projetenchere.bll.article.ArticleManager;
import fr.eni.projetenchere.bll.article.ArticleManagerFact;
import fr.eni.projetenchere.bll.categorie.CategorieManager;
import fr.eni.projetenchere.bll.categorie.CategorieManagerFact;
import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.Categorie;
import fr.eni.projetenchere.bo.Utilisateur;

/**
 * Servlet implementation class Index
 */
@WebServlet("/")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CategorieManager categorieManager = CategorieManagerFact.getInstance();
	private ArticleManager articleManager = ArticleManagerFact.getInstance();
       
    public IndexServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dc = null;
		String co = null;
		dc = request.getParameter("dc");
		co = request.getParameter("co");
		if(dc != null) {
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect(request.getContextPath() + "/");
		}else if("1".equals(co)) {
			response.sendRedirect(request.getContextPath() + "/connexion");
		}else {
			Utilisateur u = (Utilisateur) request.getSession().getAttribute("utilisateur");
			request.setAttribute("u", u);
			
			// Get All Catégories
			List<Categorie> listeCategories =  new ArrayList<>();
			try {
				listeCategories = categorieManager.getAll();
			} catch (Exception e) {
			}
			request.setAttribute("listeCategories",listeCategories);
			
			// Get All Articles
			List<Article> listeArticles =  new ArrayList<>();
			try {
				listeArticles = articleManager.getAll();
			} catch (Exception e) {
			}
			request.setAttribute("listeArticles",listeArticles);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// Get All Catégories
		List<Categorie> listeCategories =  new ArrayList<>();
		try {
			listeCategories = categorieManager.getAll();
		} catch (Exception e) {
		}
		request.setAttribute("listeCategories",listeCategories);
		
		// Récupération des champs
		String nom = request.getParameter("rechercheNom");
		int no_categorie = Integer.parseInt(request.getParameter("categorie"));
		String type = request.getParameter("type");
		boolean param1 = false;
		boolean param2 = false;
		boolean param3 = false;
		if(type != null) {
			if("Achats".equals(type)) {		
				if(!request.getParameter("encheresOuvertes").isEmpty()) {
					param1 = true;
				}		
				if(!request.getParameter("mesEncheres").isEmpty()) {
					param2 = true;
				}
				if(!request.getParameter("mesEncheresRemportees").isEmpty()) {
					param3 = true;
				}
			}
			else {
				if(!request.getParameter("mesVentesEnCours").isEmpty()) {
					param1 = true;
				}
				if(!request.getParameter("ventesNonDebutees").isEmpty()) {
					param2 = true;
				}
				if(!request.getParameter("ventesTerminees").isEmpty()) {
					param3 = true;
				}
			}
		}
		
		List<Article> listeArticles;
		System.out.println(nom+no_categorie+type+param1+param2+param3);
		try {
			listeArticles = articleManager.getAllWithFilter(nom, no_categorie, type, param1, param2, param3);
			request.setAttribute("listeArticles",listeArticles);
		} catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
		rd.forward(request, response);
		
	}

}
