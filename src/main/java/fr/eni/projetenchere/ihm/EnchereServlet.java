package fr.eni.projetenchere.ihm;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projetenchere.bll.article.ArticleManager;
import fr.eni.projetenchere.bll.article.ArticleManagerFact;
import fr.eni.projetenchere.bll.enchere.EnchereManager;
import fr.eni.projetenchere.bll.enchere.EnchereManagerFact;
import fr.eni.projetenchere.bll.retrait.RetraitManager;
import fr.eni.projetenchere.bll.retrait.RetraitManagerFact;
import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.Enchere;
import fr.eni.projetenchere.bo.Retrait;
import fr.eni.projetenchere.bo.Utilisateur;

/**
 * Servlet implementation class EnchereServlet
 */
@WebServlet("/enchere")
public class EnchereServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ArticleManager articleManager = ArticleManagerFact.getInstance();
	private EnchereManager enchereManager = EnchereManagerFact.getInstance();
	private RetraitManager retraitManager = RetraitManagerFact.getInstance();
       
    public EnchereServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur u = (Utilisateur) request.getSession().getAttribute("utilisateur");
		if(u != null) {
			try {
				request.setAttribute("u",u);
				
				// Récupération de l'article
				int id = Integer.parseInt(request.getParameter("id"));
				Article a = articleManager.getById(id);	
				request.setAttribute("a",a);
				
				// Récupération retrait
				Retrait r = retraitManager.getRetraitByIdArticle(id);
				request.setAttribute("r",r);
				
				// Vérification si la vente est terminée
				Utilisateur win = enchereManager.getUtilisateurWhoWin(id);
				request.setAttribute("win",win);
				boolean termine = true;
				if(win == null) {
					termine = false;
				}
				request.setAttribute("termine",termine);
				
			} catch (Exception e) {
				request.setAttribute("erreur", e.getMessage());
			}
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/enchere.jsp");
			rd.forward(request, response);
		}
		else {
			response.sendRedirect(request.getContextPath() + "/connexion");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			
			Utilisateur u = (Utilisateur) request.getSession().getAttribute("utilisateur");
			int id = Integer.parseInt(request.getParameter("id"));
			Article a = articleManager.getById(id);
			request.setAttribute("a",a);
			
			Retrait r = retraitManager.getRetraitByIdArticle(id);
			request.setAttribute("r",r);
			
			// Récupération des champs
			int proposition = Integer.parseInt(request.getParameter("proposition"));
			Enchere e = enchereManager.insertEnchere(proposition, a, u);
			request.setAttribute("success", "Enchère réussite ! Vos crédits ont été débités");

		}
		catch(Exception e){
			e.printStackTrace();
			request.setAttribute("erreur", e.getMessage());
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/enchere.jsp");
		rd.forward(request, response);
	}

}
