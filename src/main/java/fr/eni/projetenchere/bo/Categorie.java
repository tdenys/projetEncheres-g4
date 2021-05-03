package fr.eni.projetenchere.bo;

public class Categorie {
	private int no_categorie;
	private String libelle;
	
	public Categorie() {
	}

	public Categorie(int no_categorie, String libelle) {
		this.no_categorie = no_categorie;
		this.libelle = libelle;
	}

	public int getNo_categorie() {
		return no_categorie;
	}

	public void setNo_categorie(int no_categorie) {
		this.no_categorie = no_categorie;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Override
	public String toString() {
		return "Categorie [no_categorie=" + no_categorie + ", libelle=" + libelle + "]";
	}
	
}
