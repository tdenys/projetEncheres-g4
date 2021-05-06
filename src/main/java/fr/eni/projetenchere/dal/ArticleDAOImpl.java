package fr.eni.projetenchere.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.Categorie;

public class ArticleDAOImpl implements ArticleDAO {
	
	private String GET_ARTICLE_BY_ID = "SELECT * FROM ARTICLES_VENDUS WHERE NO_ARTICLE = ?";
	private String GET_ALL_ARTICLE = "SELECT * FROM ARTICLES_VENDUS";
	private String INSERT_ARTICLE = "INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES (?,?,?,?,?,?,?,?)";
	private String UPDATE_ARTICLE = "UPDATE ARTICLES_VENDUS SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, prix_initial = ?, prix_vente = ?, no_utilisateur = ?, no_categorie = ? WHERE no_article = ?";
	private String DELETE_ARTICLE = "DELETE FROM ARTICLES_VENDUS WHERE pseudo = ?";
	
	@Override
	public Article getArticleById(int id) throws Exception {
		Article a = null;
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(GET_ARTICLE_BY_ID);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				a = new Article(id, rs.getString("nom_article"), rs.getString("description"), rs.getDate("date_debut_encheres"), rs.getDate("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"), rs.getInt("no_utilisateur"), rs.getInt("no_categorie"));
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
				a = new Article(rs.getInt("no_article"), rs.getString("nom_article"), rs.getString("description"), rs.getDate("date_debut_encheres"), rs.getDate("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"), rs.getInt("no_utilisateur"), rs.getInt("no_categorie"));
				result.add(a);
			}
		}
		catch(Exception e) {
			throw new Exception(GET_ALL_ARTICLE);
		}	
		return result;
	}
	
	@Override
	public Article insertArticle(Article a) throws Exception {
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(INSERT_ARTICLE,Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, a.getNom_article());
			stmt.setString(2, a.getDescription());
			stmt.setDate(3, (Date) a.getDate_debut_encheres());
			stmt.setDate(4, (Date) a.getDate_fin_encheres());
			stmt.setInt(5, a.getPrix_initial());
			stmt.setInt(6, a.getPrix_vente());
			stmt.setInt(7, a.getNo_utilisateur());
			stmt.setInt(8, a.getNo_categorie());
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
			stmt.setDate(3, (Date) a.getDate_debut_encheres());
			stmt.setDate(4, (Date) a.getDate_fin_encheres());
			stmt.setInt(5, a.getPrix_initial());
			stmt.setInt(6, a.getPrix_vente());
			stmt.setInt(7, a.getNo_utilisateur());
			stmt.setInt(8, a.getNo_categorie());
			stmt.setInt(9, a.getNo_article());
			stmt.executeUpdate();
		}
		catch(Exception e) {
			throw new Exception(UPDATE_ARTICLE);
		}
		return a;
	}
	
	@Override
	public void removeArticle(Article a) throws Exception {
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(DELETE_ARTICLE);
			stmt.setString(1, a.getNom_article());
			stmt.executeUpdate();
		}
		catch(Exception e) {
			throw new Exception(DELETE_ARTICLE);
		}
	}
	
	
}
