package fr.eni.projetenchere.dal.article;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.Categorie;
import fr.eni.projetenchere.bo.Utilisateur;
import fr.eni.projetenchere.dal.ConnectionProvider;
import fr.eni.projetenchere.dal.categorie.CategorieDAO;
import fr.eni.projetenchere.dal.categorie.CategorieDAOFactory;
import fr.eni.projetenchere.dal.utilisateur.UtilisateurDAO;
import fr.eni.projetenchere.dal.utilisateur.UtilisateurDAOFactory;

public class ArticleDAOImpl implements ArticleDAO {
	
	private String GET_ARTICLE_BY_ID = "SELECT * FROM ARTICLES_VENDUS WHERE NO_ARTICLE = ?";
	private String GET_ALL_ARTICLE = "SELECT * FROM ARTICLES_VENDUS";
	private String GET_ARTICLES_BY_UTILISATEUR = "SELECT * FROM ARTICLES_VENDUS WHERE NO_UTILISATEUR = ?";
	private String GET_NB_ARTICLES_BY_UTILISATEUR = "SELECT COUNT(*) FROM ARTICLES_VENDUS WHERE NO_UTILISATEUR = ?";
	private String INSERT_ARTICLE = "INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES (?,?,?,?,?,?,?,?)";
	private String UPDATE_ARTICLE = "UPDATE ARTICLES_VENDUS SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, prix_initial = ?, prix_vente = ?, no_utilisateur = ?, no_categorie = ? WHERE no_article = ?";
	private String UPDATE_ARTICLE_VENDU = "UPDATE ARTICLES_VENDUS SET vendu = ? WHERE NO_ARTICLE = ?";
	private String DELETE_ARTICLE = "DELETE FROM ARTICLES_VENDUS WHERE NO_ARTICLE = ?";
	
	private UtilisateurDAO utilisateurDAO = UtilisateurDAOFactory.getUtilisateurDAO(); 
	private CategorieDAO categorieDAO = CategorieDAOFactory.getCategorieDAO(); 
	
	@Override
	public Article getArticleById(int id) throws Exception {
		Article a = null;
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(GET_ARTICLE_BY_ID);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				Utilisateur utilisateur = utilisateurDAO.getUtilisateurById(rs.getInt("no_utilisateur"));
				Categorie categorie = categorieDAO.getCategorieById(rs.getInt("no_categorie"));
				a = new Article(id, rs.getString("nom_article"), rs.getString("description"), rs.getDate("date_debut_encheres"), rs.getDate("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"), utilisateur, categorie);
			}
		}
		catch(Exception e) {
			throw new Exception(GET_ARTICLE_BY_ID);
		}
		
		return a;
	}
	
	public List<Article> getAll() throws Exception {
		Article a = null;
		List<Article> result = new ArrayList<Article>();
		
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(GET_ALL_ARTICLE);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Utilisateur utilisateur = utilisateurDAO.getUtilisateurById(rs.getInt("no_utilisateur"));
				Categorie categorie = categorieDAO.getCategorieById(rs.getInt("no_categorie"));
				a = new Article(rs.getInt("no_article"), rs.getString("nom_article"), rs.getString("description"), rs.getDate("date_debut_encheres"), rs.getDate("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"), utilisateur, categorie);
				result.add(a);
			}
		}
		catch(Exception e) {
			throw new Exception(GET_ALL_ARTICLE);
		}	
		return result;
	}
	
	public List<Article> getAllWithFilter(String filtres, int categorie, String type, boolean param1, boolean param2, boolean param3, Utilisateur u) throws Exception {
		Article a = null;
		List<Article> result = new ArrayList<Article>();
		String reqWithFilter = "";
		if(u == null) {
			reqWithFilter = "SELECT DISTINCT AV.nom_article, AV.no_utilisateur, AV.no_categorie, AV.no_article, AV.nom_article, AV.description, AV.date_debut_encheres, AV.date_fin_encheres, AV.prix_initial, AV.prix_vente FROM ARTICLES_VENDUS AV "
					+ "LEFT JOIN ENCHERES E ON E.no_article = AV.no_article "
					+ "WHERE (? = ' ' OR nom_article LIKE ?) "
					+ "AND (? = 0 OR no_categorie = ?);";
		} else if("achats".equals(type)) {
			reqWithFilter = "SELECT DISTINCT AV.nom_article, AV.no_utilisateur, AV.no_categorie, AV.no_article, AV.nom_article, AV.description, AV.date_debut_encheres, AV.date_fin_encheres, AV.prix_initial, AV.prix_vente FROM ARTICLES_VENDUS AV "
				+ "LEFT JOIN ENCHERES E ON E.no_article = AV.no_article "
				+ "WHERE (? = ' ' OR nom_article LIKE ?) "
				+ "AND (? = 0 OR no_categorie = ?) "
				+ "AND (AV.no_utilisateur != ?) "
				+ "AND (? = 1 OR date_fin_encheres > GetDate()) "
				+ "AND (? = 1 OR E.no_utilisateur = ?) "
				+ "AND (? = 1 OR AV.date_fin_encheres < GetDate() AND E.no_utilisateur = ?);";
		} else {
			reqWithFilter = "SELECT DISTINCT AV.nom_article, AV.no_utilisateur, AV.no_categorie, AV.no_article, AV.nom_article, AV.description, AV.date_debut_encheres, AV.date_fin_encheres, AV.prix_initial, AV.prix_vente FROM ARTICLES_VENDUS AV "
				+ "LEFT JOIN ENCHERES E ON E.no_article = AV.no_article "
				+ "WHERE (? = ' ' OR nom_article LIKE ?) "
				+ "AND (? = 0 OR no_categorie = ?) "
				+ "AND (AV.no_utilisateur = ?) "
				+ "AND (? = 1 OR (AV.no_utilisateur = ? AND date_fin_encheres > GetDate())) "
				+ "AND (? = 1 OR date_debut_encheres > GetDate()) "
				+ "AND (? = 1 OR date_fin_encheres < GetDate());";
		}
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(reqWithFilter);
			if(u == null) {
				stmt.setString(1, filtres);
				stmt.setString(2, "%"+filtres+"%");
				stmt.setInt(3, categorie);
				stmt.setInt(4, categorie);
			}
			else if("achats".equals(type)) {
				stmt.setString(1, filtres);
				stmt.setString(2, "%"+filtres+"%");
				stmt.setInt(3, categorie);
				stmt.setInt(4, categorie);
				stmt.setInt(5, u.getNo_utilisateurs());
				stmt.setBoolean(6, !param1);
				stmt.setBoolean(7, !param2);
				stmt.setInt(8, u.getNo_utilisateurs());
				stmt.setBoolean(9, !param3);
				stmt.setInt(10, u.getNo_utilisateurs());
			} else {
				stmt.setString(1, filtres);
				stmt.setString(2, "%"+filtres+"%");
				stmt.setInt(3, categorie);
				stmt.setInt(4, categorie);
				stmt.setInt(5, u.getNo_utilisateurs());
				stmt.setBoolean(6, !param1);
				stmt.setInt(7, u.getNo_utilisateurs());
				stmt.setBoolean(8, !param2);
				stmt.setBoolean(9, !param3);
			}
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Utilisateur utilisateur = utilisateurDAO.getUtilisateurById(rs.getInt("no_utilisateur"));
				Categorie categorie1 = categorieDAO.getCategorieById(rs.getInt("no_categorie"));
				a = new Article(rs.getInt("no_article"), rs.getString("nom_article"), rs.getString("description"), rs.getDate("date_debut_encheres"), rs.getDate("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"), utilisateur, categorie1);
				result.add(a);
			}
		} 
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception(reqWithFilter);
		}	
		return result;
	}
	
	@Override
	public Article insertArticle(Article a) throws Exception {	
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(INSERT_ARTICLE,Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, a.getNom_article());
			stmt.setString(2, a.getDescription());
			stmt.setDate(3, new java.sql.Date(a.getDate_debut_encheres().getTime()));
			stmt.setDate(4, new java.sql.Date(a.getDate_fin_encheres().getTime()));
			stmt.setInt(5, a.getPrix_initial());
			stmt.setInt(6, a.getPrix_vente());
			stmt.setInt(7, a.getUtilisateur().getNo_utilisateurs());
			stmt.setInt(8, a.getCategorie().getNo_categorie());
			int nbRows = stmt.executeUpdate();
			if (nbRows == 1) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					a.setNo_article(rs.getInt(1));
				}
			}
		}
		catch(Exception e) {
			throw new Exception(INSERT_ARTICLE);
		}
		return a;
	}
	
	@Override
	public Article insertArticle(Connection cnx, Article a) throws Exception {	
		try{
			PreparedStatement stmt = cnx.prepareStatement(INSERT_ARTICLE,Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, a.getNom_article());
			stmt.setString(2, a.getDescription());
			stmt.setDate(3, new java.sql.Date(a.getDate_debut_encheres().getTime()));
			stmt.setDate(4, new java.sql.Date(a.getDate_fin_encheres().getTime()));
			stmt.setInt(5, a.getPrix_initial());
			stmt.setInt(6, a.getPrix_vente());
			stmt.setInt(7, a.getUtilisateur().getNo_utilisateurs());
			stmt.setInt(8, a.getCategorie().getNo_categorie());
			int nbRows = stmt.executeUpdate();
			if (nbRows == 1) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					a.setNo_article(rs.getInt(1));
				}
			}
		}
		catch(Exception e) {
			throw new Exception(INSERT_ARTICLE);
		}
		return a;
	}
	
	@Override
	public Article updateArticle(Article a) throws Exception {
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(UPDATE_ARTICLE);
			stmt.setString(1, a.getNom_article());
			stmt.setString(2, a.getDescription());
			stmt.setDate(3, new java.sql.Date(a.getDate_debut_encheres().getTime()));
			stmt.setDate(4, new java.sql.Date(a.getDate_fin_encheres().getTime()));
			stmt.setInt(5, a.getPrix_initial());
			stmt.setInt(6, a.getPrix_vente());
			stmt.setInt(7, a.getUtilisateur().getNo_utilisateurs());
			stmt.setInt(8, a.getCategorie().getNo_categorie());
			stmt.setInt(9, a.getNo_article());
			stmt.executeUpdate();
		}
		catch(Exception e) {
			throw new Exception(UPDATE_ARTICLE);
		}
		return a;
	}
	
	@Override
	public Article updateArticle(Connection cnx, Article a) throws Exception {
		try{
			PreparedStatement stmt = cnx.prepareStatement(UPDATE_ARTICLE);
			stmt.setString(1, a.getNom_article());
			stmt.setString(2, a.getDescription());
			stmt.setDate(3, new java.sql.Date(a.getDate_debut_encheres().getTime()));
			stmt.setDate(4, new java.sql.Date(a.getDate_fin_encheres().getTime()));
			stmt.setInt(5, a.getPrix_initial());
			stmt.setInt(6, a.getPrix_vente());
			stmt.setInt(7, a.getUtilisateur().getNo_utilisateurs());
			stmt.setInt(8, a.getCategorie().getNo_categorie());
			stmt.setInt(9, a.getNo_article());
			stmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception(UPDATE_ARTICLE);
		}
		return a;
	}
	
	@Override
	public void removeArticle(Article a) throws Exception {
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(DELETE_ARTICLE);
			stmt.setInt(1, a.getNo_article());
			stmt.executeUpdate();
		}
		catch(Exception e) {
			throw new Exception(DELETE_ARTICLE);
		}
	}

	@Override
	public int getNbArticlesByUtilisateur(Connection cnx, Utilisateur u) throws Exception {
		int nb = 0;
		
		try {
			PreparedStatement stmt = cnx.prepareStatement(GET_NB_ARTICLES_BY_UTILISATEUR);
			stmt.setInt(1, u.getNo_utilisateurs());
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				nb = rs.getInt(1);
			}	
		}
		catch(Exception e) {
			throw new Exception(GET_NB_ARTICLES_BY_UTILISATEUR);
		}	
		return nb;
	}

	@Override
	public void updateArticleVendu(Article article, boolean vendu) throws Exception {
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(UPDATE_ARTICLE_VENDU);
			stmt.setBoolean(1, vendu);
			stmt.setInt(2, article.getNo_article());
			stmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception(UPDATE_ARTICLE_VENDU);
		}
	}
	
	
}
