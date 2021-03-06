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
import javax.servlet.http.HttpSession;

import fr.eni.projetenchere.bll.article.ArticleManager;
import fr.eni.projetenchere.bll.article.ArticleManagerFact;
import fr.eni.projetenchere.bll.categorie.CategorieManager;
import fr.eni.projetenchere.bll.categorie.CategorieManagerFact;
import fr.eni.projetenchere.bll.enchere.EnchereManager;
import fr.eni.projetenchere.bll.enchere.EnchereManagerFact;
import fr.eni.projetenchere.bll.retrait.RetraitManager;
import fr.eni.projetenchere.bll.retrait.RetraitManagerFact;
import fr.eni.projetenchere.bll.utilisateur.UtilisateurManager;
import fr.eni.projetenchere.bll.utilisateur.UtilisateurManagerFact;
import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.Categorie;
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
	private CategorieManager categorieManager = CategorieManagerFact.getInstance();
	private UtilisateurManager utilisateurManager = UtilisateurManagerFact.getInstance();
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
       
    public EnchereServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur u = (Utilisateur) request.getSession().getAttribute("utilisateur");
		if(u != null) {
			try {
				request.setAttribute("u",u);
				
				// R??cup??ration de l'article
				int id = Integer.parseInt(request.getParameter("id"));
				Article a = articleManager.getById(id);	
				request.setAttribute("a",a);
				
				// R??cup??ration des cat??gories
				List<Categorie> listeCategories =  new ArrayList<>();
				try {
					listeCategories = categorieManager.getAll();
				} catch (Exception e) {
				}
				request.setAttribute("listeCategories",listeCategories);
				
				// R??cup??ration retrait
				Retrait r = retraitManager.getRetraitByIdArticle(id);
				request.setAttribute("r",r);
				
				// R??cup??ration de l'ench??risseur actuel
				Utilisateur best = enchereManager.getDernierEnrechisseur(id);
				if(best != null) {
					request.setAttribute("best",best);
				}
				
				Date now = new Date();
				
				// V??rification si la vente est termin??e
				Utilisateur win = enchereManager.getUtilisateurWhoWin(a);
				request.setAttribute("win",win);
				boolean termine = true;
				if(a.getDate_fin_encheres().after(now)) {
					termine = false;
				}
				if(win != null) {
					// On update l'utilisateur
					u = utilisateurManager.getUtilisateurById(u.getNo_utilisateurs());
					HttpSession session = request.getSession();
					session.setAttribute("utilisateur", u);
				}
				request.setAttribute("termine",termine);
				
				// Verification si la vente est commenc??e
				boolean commence = true;
				if(a.getDate_debut_encheres().after(now)) {
					commence = false;
				}
				request.setAttribute("commence",commence);
				
				if(request.getParameter("sup") != null) {
					// Suppression
					articleManager.deleteArticleAndRetrait(a, r);
					// On redirige vers l'index
					response.sendRedirect(request.getContextPath() + "/");
				}
				else {	
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/enchere.jsp");
					rd.forward(request, response);
				}
				
			} catch (Exception e) {
				request.setAttribute("erreur", e.getMessage());
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/enchere.jsp");
				rd.forward(request, response);
			}
		}
		else {
			response.sendRedirect(request.getContextPath() + "/connexion");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			
			Utilisateur u = (Utilisateur) request.getSession().getAttribute("utilisateur");
			request.setAttribute("u",u);
			
			// R??cup??ration de l'article
			int id = Integer.parseInt(request.getParameter("id"));
			Article a = articleManager.getById(id);
			request.setAttribute("a",a);
			
			// R??cup??ration des cat??gories
			List<Categorie> listeCategories =  new ArrayList<>();
			try {
				listeCategories = categorieManager.getAll();
			} catch (Exception e) {
			}
			request.setAttribute("listeCategories",listeCategories);
			
			// R??cup??ration retrait
			Retrait r = retraitManager.getRetraitByIdArticle(id);
			request.setAttribute("r",r);
			
			// R??cup??ration des champs
			if(u.getNo_utilisateurs() == a.getUtilisateur().getNo_utilisateurs()) {
				// Si je suis vendeur
				String nom = request.getParameter("article");
				String description = request.getParameter("description");
				Categorie categorie = categorieManager.getCategorieById(Integer.parseInt(request.getParameter("categorie")));
				int prix = Integer.parseInt(request.getParameter("prix"));
				Date dateDebut = formatter.parse(request.getParameter("debutEnchere"));
				Date dateFin = formatter.parse(request.getParameter("finEnchere"));
				String rue = request.getParameter("rue");
				String codePostal = request.getParameter("codePostal");
				String ville = request.getParameter("ville");
				
				Article new_a = new Article(id, nom, description, dateDebut, dateFin, prix, prix, u, categorie);
				Retrait new_r = new Retrait(id, rue, codePostal, ville);
				
				Object[] tab = articleManager.updateArticleAndRetrait(new_a, new_r);
				r = (Retrait) tab[1];
				a = articleManager.getById(id);
				request.setAttribute("a",a);
				request.setAttribute("r",r);
			}
			else {
				// Si je suis acheteur
				int proposition = Integer.parseInt(request.getParameter("proposition"));
				Enchere e = enchereManager.insertEnchere(proposition, a, u);
				request.setAttribute("success", "Ench??re r??ussite ! Vos cr??dits ont ??t?? d??bit??s");
			}
			
			// Verification si la vente est commenc??e
			boolean commence = true;
			Date now = new Date();
			if(a.getDate_debut_encheres().after(now)) {
				commence = false;
			}
			request.setAttribute("commence",commence);

		}
		catch(Exception e){
			e.printStackTrace();
			request.setAttribute("erreur", e.getMessage());
			
			try {
				// R??cup??ration de l'article
				int id = Integer.parseInt(request.getParameter("id"));
				Article a = articleManager.getById(id);
				request.setAttribute("a",a);
				
				// Verification si la vente est commenc??e
				boolean commence = true;
				Date now = new Date();
				if(a.getDate_debut_encheres().after(now)) {
					commence = false;
				}
				request.setAttribute("commence",commence);
			}
			catch(Exception e1) {
				e.printStackTrace();
				request.setAttribute("erreur", e1.getMessage());
			}
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/enchere.jsp");
		rd.forward(request, response);
	}

}
