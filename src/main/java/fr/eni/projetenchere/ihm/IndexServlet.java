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
			
			request.setAttribute("affType1","");
			request.setAttribute("affType2","disabled");
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		Utilisateur u = (Utilisateur) request.getSession().getAttribute("utilisateur");
		request.setAttribute("u", u);
		
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
		String affType1 = "";
		String affType2 = "disabled";
		String affParam1 = "";
		String affParam2 = "";
		String affParam3 = "";
		String affParam4 = "";
		String affParam5 = "";
		String affParam6 = "";
		if(type != null) {
			if(type.equals("achats")) {
				if(request.getParameter("encheresOuvertes") != null) {
					param1 = true;
					affParam1 = "checked";
				}		
				if(request.getParameter("mesEncheres") != null) {
					param2 = true;
					affParam2 = "checked";
				}
				if(request.getParameter("mesEncheresRemportees") != null) {
					param3 = true;
					affParam3 = "checked";
				}
				affType1 = "";
				affType2 = "disabled";
			}
			else {
				if(request.getParameter("mesVentesEnCours") != null) {
					param1 = true;
					affParam4 = "checked";
				}
				if(request.getParameter("ventesNonDebutees") != null) {
					param2 = true;
					affParam5 = "checked";
				}
				if(request.getParameter("ventesTerminees") != null) {
					param3 = true;
					affParam6 = "checked";
				}
				affType1 = "disabled";
				affType2 = "";
			}
		}
		
		request.setAttribute("nom",nom);
		request.setAttribute("cat",no_categorie);
		request.setAttribute("affParam1",affParam1);
		request.setAttribute("affParam2",affParam2);
		request.setAttribute("affParam3",affParam3);
		request.setAttribute("affParam4",affParam4);
		request.setAttribute("affParam5",affParam5);
		request.setAttribute("affParam6",affParam6);
		request.setAttribute("affType1",affType1);
		request.setAttribute("affType2",affType2);
		
		List<Article> listeArticles;
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
