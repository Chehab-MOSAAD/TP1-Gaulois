package villagegaulois;

import personnages.Gaulois;

public class Etal {
	private Gaulois vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;

	public boolean isEtalOccupe() {
		return etalOccupe;
	}

	public Gaulois getVendeur() {
		return vendeur;
	}
	
	public String getProduit() {
	    return produit;
	}
	
	public int getQuantiteInitiale() {
	    return this.quantiteDebutMarche;
	}

	public int getQuantiteDispo() {
	    return quantite;
	}
	
	public void setQuantiteDispo(int quantiteDispo) {
	    this.quantite = quantiteDispo;
	}

	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}

	public String libererEtal() {
	    int quantiteInitiale = 0;
	    int quantiteDispo = 0;
	    try {
	        if (!isEtalOccupe()) {
	            throw new IllegalStateException("L'étal n'a pas été occupé par un vendeur.");
	        }
	        String message = "Le vendeur " + vendeur.getNom() + " quitte son étal, il a vendu " + (quantiteInitiale - quantiteDispo) + " " + produit + " parmi les " + quantiteInitiale + " qu'il voulait vendre.";
	        vendeur = null;
	        quantiteDispo = 0;
	        return message;
	    } catch (IllegalStateException e) {
	        return "Impossible de libérer l'étal : " + e.getMessage();
	    }
	}


	public String afficherEtal() {
		if (etalOccupe) {
			return "L'étal de " + vendeur.getNom() + " est garni de " + quantite
					+ " " + produit + "\n";
		}
		return "L'étal est libre";
	}
	
	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) {
	    try {
		    // Vérifier que l'étal est occupé
	        if (!isEtalOccupe()) {
	            throw new IllegalStateException("L'étal n'est pas occupé");
	        }
	        // Vérifier que la quantité à acheter est positive
	        if (quantiteAcheter <= 0) {
	            throw new IllegalArgumentException("La quantité à acheter doit être positive");
	        }
	        // Vérifier que l'acheteur n'est pas null
	        if (acheteur == null) {
	            throw new NullPointerException("L'acheteur ne peut pas être null");
	        }

	        String produit = getProduit();
	        int quantiteDispo = getQuantiteDispo();
	        if (quantiteAcheter > quantiteDispo) {
	            setQuantiteDispo(0);
	            return acheteur.getNom() + " veut acheter " + quantiteAcheter + " " + produit + " à " + getVendeur().getNom() + ", mais il n'y en a plus que " + quantiteDispo + ". " + getVendeur().getNom() + " vide l'étal de " + produit + ".";
	        } else {
	            setQuantiteDispo(quantiteDispo - quantiteAcheter);
	            return acheteur.getNom() + " veut acheter " + quantiteAcheter + " " + produit + " à " + getVendeur().getNom() + ". " + getVendeur().getNom() + ", est ravi de tout trouver sur l'étal de " + produit + ".";
	        }
	    } catch (IllegalStateException e) {
	        return "Impossible d'acheter, l'étal n'est pas occupé";
	    } catch (IllegalArgumentException e) {
	        return "Impossible d'acheter, la quantité à acheter doit être positive";
	    } catch (NullPointerException e) {
	        return "Impossible d'acheter, l'acheteur ne peut pas être null";
	    }
	}
	
	public boolean contientProduit(String produit) {
	    return produit.equals(this.produit);
	}

}