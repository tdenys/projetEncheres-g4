package fr.eni.projetenchere.dal.enchere;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.Categorie;
import fr.eni.projetenchere.bo.Enchere;
import fr.eni.projetenchere.bo.Retrait;
import fr.eni.projetenchere.bo.Utilisateur;
import fr.eni.projetenchere.dal.ConnectionProvider;
import fr.eni.projetenchere.dal.article.ArticleDAO;
import fr.eni.projetenchere.dal.article.ArticleDAOFactory;
import fr.eni.projetenchere.dal.utilisateur.UtilisateurDAO;
import fr.eni.projetenchere.dal.utilisateur.UtilisateurDAOFactory;

public class EnchereDAOImpl implements EnchereDAO {
	
	private UtilisateurDAO utilisateurDAO = UtilisateurDAOFactory.getUtilisateurDAO();
	private ArticleDAO articleDAO = ArticleDAOFactory.getArticleDAO();

	private String INSERT_ENCHERE = "INSERT INTO ENCHERES(date_enchere, montant_enchere, no_article, no_utilisateur) VALUES (?,?,?,?)";
	private String GET_ENCHERE_BY_ID = "SELECT * FROM ARTICLES_VENDUS WHERE NO_ARTICLE = ?";
	private String GET_LAST_ENCHERE_BY_ARTICLE = "SELECT TOP 1 * FROM ENCHERES WHERE no_article = ? ORDER BY no_enchere DESC";
	private String GET_NB_ENCHERES_BY_UTILISATEUR = "SELECT COUNT(*) FROM ENCHERES WHERE no_utilisateur = ?";
	private String GET_UTILISATEUR_WHO_WIN = "SELECT E.no_utilisateur, MAX(E.montant_enchere) max_montant_enchere FROM ENCHERES E INNER JOIN ARTICLES_VENDUS AV ON AV.no_article = E.no_article WHERE E.no_article = ? AND AV.date_fin_encheres < GetDate() GROUP BY E.no_utilisateur";
	private String GET_DERNIER_ENRICHISSEUR = "SELECT E.no_utilisateur, MAX(E.montant_enchere) max_montant_enchere FROM ENCHERES E INNER JOIN ARTICLES_VENDUS AV ON AV.no_article = E.no_article WHERE E.no_article = ?";
	
	@Override
	public Enchere insertEnchere(Enchere e) throws Exception {
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(INSERT_ENCHERE, Statement.RETURN_GENERATED_KEYS);
			stmt.setDate(1, new java.sql.Date(e.getDate_enchere().getTime()));
			stmt.setInt(2, e.getMontant_enchere());
			stmt.setInt(3, e.getArticle().getNo_article());
			stmt.setInt(4, e.getUtilisateur().getNo_utilisateurs());
			int nbRows = stmt.executeUpdate();
			if (nbRows == 1) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					e.setNo_enchere(rs.getInt(1));
				}
			}
		}
		catch(Exception exception) {
			throw new Exception(INSERT_ENCHERE);
		}
		return e;
	}
	

	@Override
	public Enchere insertEnchere(Connection cnx, Enchere e) throws Exception {
		try{
			PreparedStatement stmt = cnx.prepareStatement(INSERT_ENCHERE, Statement.RETURN_GENERATED_KEYS);
			stmt.setDate(1, new java.sql.Date(e.getDate_enchere().getTime()));
			stmt.setInt(2, e.getMontant_enchere());
			stmt.setInt(3, e.getArticle().getNo_article());
			System.out.println(e.getUtilisateur().getNo_utilisateurs());
			stmt.setInt(4, e.getUtilisateur().getNo_utilisateurs());
			int nbRows = stmt.executeUpdate();
			if (nbRows == 1) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					e.setNo_enchere(rs.getInt(1));
				}
			}
		}
		catch(Exception exception) {
			//throw new Exception(INSERT_ENCHERE);
			throw exception;
		}
		return e;
	}

	@Override
	public Enchere getEnchereById(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Utilisateur getUtilisateurLastEnchere(Connection cnx, int no_article) throws Exception {
		Utilisateur u = null;
		try{
			PreparedStatement stmt = cnx.prepareStatement(GET_LAST_ENCHERE_BY_ARTICLE);
			stmt.setInt(1, no_article);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				u = utilisateurDAO.getUtilisateurById(rs.getInt("no_utilisateur"));
			}
		}
		catch(Exception exception) {
			throw new Exception(GET_LAST_ENCHERE_BY_ARTICLE);
		}
		return u;
	}


	@Override
	public int getNbEncheresByUtilisateur(Connection cnx, Utilisateur utilisateur) throws Exception {
		int nb = 0;
		
		try {
			PreparedStatement stmt = cnx.prepareStatement(GET_NB_ENCHERES_BY_UTILISATEUR);
			stmt.setInt(1, utilisateur.getNo_utilisateurs());
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				nb = rs.getInt(1);
			}	
		}
		catch(Exception e) {
			throw new Exception(GET_NB_ENCHERES_BY_UTILISATEUR);
		}	
		return nb;
	}
	
	@Override
	public Utilisateur getUtilisateurWhoWin(Article a) throws Exception {
		Utilisateur u = null;
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(GET_UTILISATEUR_WHO_WIN);
			stmt.setInt(1, a.getNo_article());
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				u = utilisateurDAO.getUtilisateurById(rs.getInt("no_utilisateur"));
			}
		}
		catch(Exception exception) {
			throw new Exception(GET_UTILISATEUR_WHO_WIN);
		}
		return u;
	}
	
	@Override
	public Utilisateur getDernierEncherisseur(int no_article) throws Exception {
		Utilisateur u = null;
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(GET_DERNIER_ENRICHISSEUR);
			stmt.setInt(1, no_article);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				u = utilisateurDAO.getUtilisateurById(rs.getInt("no_utilisateur"));
			}
		}
		catch(Exception exception) {
			throw new Exception(GET_DERNIER_ENRICHISSEUR);
		}
		return u;
	}
}
