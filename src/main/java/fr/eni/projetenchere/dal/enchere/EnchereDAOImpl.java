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
	private String GET_ENCHERES_BY_UTILISATEUR = "SELECT * FROM ENCHERES WHERE no_utilisateur = ?";
	
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
	public List<Enchere> getEncheresByUtilisateur(Connection cnx, Utilisateur utilisateur) throws Exception {
		Enchere enchere = null;
		List<Enchere> result = new ArrayList<Enchere>();
		
		try(cnx){
			PreparedStatement stmt = cnx.prepareStatement(GET_ENCHERES_BY_UTILISATEUR);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Article article = articleDAO.getArticleById(rs.getInt("no_article"));
				enchere = new Enchere(rs.getInt("no_enchere"), rs.getDate("date_enchere"), rs.getInt("montant_enchere"), article, utilisateur);
				result.add(enchere);
			}
		}
		catch(Exception e) {
			throw new Exception(GET_ENCHERES_BY_UTILISATEUR);
		}	
		return result;
	}

}
