package fr.eni.projetenchere.ihm;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projetenchere.bll.article.ArticleManager;
import fr.eni.projetenchere.bll.article.ArticleManagerFact;
import fr.eni.projetenchere.bll.categorie.CategorieManager;
import fr.eni.projetenchere.bll.categorie.CategorieManagerFact;
import fr.eni.projetenchere.bll.retrait.RetraitManager;
import fr.eni.projetenchere.bll.retrait.RetraitManagerFact;
import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.Categorie;
import fr.eni.projetenchere.bo.Retrait;
import fr.eni.projetenchere.bo.Utilisateur;

/**
 * Servlet implementation class VenteServlet
 */
@WebServlet("/vente")
public class VenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CategorieManager categorieManager = CategorieManagerFact.getInstance();
	private ArticleManager articleManager = ArticleManagerFact.getInstance();
	private RetraitManager retraitManager = RetraitManagerFact.getInstance();
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
       
    public VenteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur u = (Utilisateur) request.getSession().getAttribute("utilisateur");
		if(u != null) {
			request.setAttribute("u", u);
			
			// Récupération des catégories
			List<Categorie> listeCategories =  new ArrayList<>();
			try {
				listeCategories = categorieManager.getAll();
			} catch (Exception e) {
			}
			request.setAttribute("listeCategories",listeCategories);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/vente.jsp");
			rd.forward(request, response);
		}
		else {
			response.sendRedirect(request.getContextPath() + "/connexion");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			
			// Récupération des champs
			Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
			String nom = request.getParameter("article");
			String description = request.getParameter("description");
			Categorie categorie = categorieManager.getCategorieById(Integer.parseInt(request.getParameter("categorie")));
			int prix = Integer.parseInt(request.getParameter("prix"));
			Date dateDebut = formatter.parse(request.getParameter("debutEnchere"));
			Date dateFin = formatter.parse(request.getParameter("finEnchere"));
			String rue = request.getParameter("rue");
			String codePostal = request.getParameter("codePostal");
			String ville = request.getParameter("ville");
		
			Article a = new Article(nom, description, dateDebut, dateFin, prix, prix, utilisateur, categorie);
			a = articleManager.insertArticle(a, rue, codePostal, ville);
			
			response.sendRedirect(request.getContextPath() + "/");
		}
		catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
			try {
				doGet(request, response);
			} catch (ServletException e1) {
			} catch (IOException e1) {
			}
		}
		
		
	}

}
