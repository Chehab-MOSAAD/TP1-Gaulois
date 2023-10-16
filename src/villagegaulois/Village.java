package villagegaulois;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
    private Marche marche;
	private Etal[] etals;
    
	public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtal);
	}

	public String getNom() {
		return nom;
	}
    public void setNom(String nom) {
        this.nom = nom;
    }

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
	    if (chef == null) {
	        throw new VillageSansChefException("Le village " + nom + " n'a pas de chef !");
	    }
	    StringBuilder chaine = new StringBuilder();
	    if (nbVillageois < 1) {
	        chaine.append("Il n'y a encore aucun habitant au village du chef "
	                + chef.getNom() + ".\n");
	    } else {
	        chaine.append("Au village du chef " + chef.getNom()
	                + " vivent les légendaires gaulois :\n");
	        for (int i = 0; i < nbVillageois; i++) {
	            chaine.append("- " + villageois[i].getNom() + "\n");
	        }
	    }
	    return chaine.toString();
	}
	
	private class Marche {
		private Etal[] etals;
		
        public Marche(int nbEtals) {
            etals = new Etal[nbEtals];
            for (int i = 0; i < nbEtals; i++) {
                etals[i] = new Etal();
            }
        }
        
        public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
            if (indiceEtal >= 0 && indiceEtal < etals.length) {
                Etal etal = etals[indiceEtal];
                if (!etal.isEtalOccupe()) {
                	etal.occuperEtal(vendeur, produit, nbProduit);
                    System.out.println(vendeur.getNom() + " s'installe à l'étal " + indiceEtal);
                } else {
                    System.out.println("L'étal " + indiceEtal + " est déjà occupé.");
                }
            } else {
                System.out.println("L'étal " + indiceEtal + " n'existe pas.");
            }
        }
        
        public int trouverEtalLibre() {
            for (int i = 0; i < etals.length; i++) {
                if (!etals[i].isEtalOccupe()) {
                    return i;
                }
            }
            return -1;
        }
        
        public Etal[] trouverEtals(String produit) {
            int nbEtals = 0;
			Etal[] etalsProduit = new Etal[nbEtals];
            int index = 0;
            for (Etal etal : etals) {
                if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
                    etalsProduit[index++] = etal;
                }
            }
            return Arrays.copyOf(etalsProduit, index);
        }

        public Etal trouverVendeur(Gaulois gaulois) {
            for (Etal etal : etals) {
                if (etal.isEtalOccupe() && etal.getVendeur().equals(gaulois)) {
                    return etal;
                }
            }
            return null;
        }

        public String afficherMarche() {
            StringBuilder sb = new StringBuilder();
            int nbEtalVide = 0;
            for (Etal etal : etals) {
                if (etal.isEtalOccupe()) {
                    sb.append(etal.afficherEtal());
                } else {
                    nbEtalVide++;
                }
            }
            if (nbEtalVide > 0) {
                sb.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
            }
            return sb.toString();
        }

		public Etal[] getEtals() {
			return this.etals;
		}
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
	    // Chercher un étal libre pour le vendeur
	    int numEtal = marche.trouverEtalLibre();
	    if (numEtal == -1) {
	        return "Aucun étal libre n'est disponible pour le vendeur " + vendeur.getNom() + ".";
	    }
	    
	    marche.utiliserEtal(numEtal, vendeur, produit, nbProduit);
	    
	    // Construire la chaîne de retour
	    StringBuilder sb = new StringBuilder();
	    sb.append(vendeur.getNom())
	      .append(" cherche un endroit pour vendre ")
	      .append(nbProduit)
	      .append(" ")
	      .append(produit)
	      .append(".\n");
	    sb.append("Le vendeur ")
	      .append(vendeur.getNom())
	      .append(" vend des ")
	      .append(produit)
	      .append(" à l'étal n°")
	      .append(numEtal)
	      .append(".\n");
	    return sb.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Les vendeurs qui proposent des ").append(produit).append(" sont :").append(System.lineSeparator());
	    for (Etal etal : marche.getEtals()) {
	        if (etal.contientProduit(produit)) {
	            sb.append("- ").append(etal.getVendeur().getNom()).append(System.lineSeparator());
	        }
	    }
	    return sb.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
	    Etal[] etals = this.marche.getEtals();
	    for (Etal etal : etals) {
	        if (etal != null && etal.getVendeur() == vendeur) {
	            return etal;
	        }
	    }
	    return null;
	}
	
	public String partirVendeur(Gaulois vendeur) {
	    StringBuilder sb = new StringBuilder();
	    Etal[] etals = this.marche.getEtals();
	    for (Etal etal : etals) {
	        if (etal.getVendeur() == vendeur) {
	            sb.append("Le vendeur ")
	              .append(vendeur.getNom())
	              .append(" quitte son étal, il a vendu ")
	              .append(etal.getQuantiteInitiale())
	              .append(" ")
	              .append(etal.getProduit())
	              .append(" parmi les ")
	              .append(etal.getQuantiteInitiale())
	              .append(" qu'il voulait vendre.")
	              .append("\n");
	            etal.libererEtal();
	            break;
	        }
	    }
	    return sb.toString();
	}
	
	public String afficherMarche() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Le marché du village \"le village des irréductibles\" possède plusieurs étals :\n");
	    Etal[] etals = this.marche.getEtals();
	    int nbEtalsUtilises = 0;
	    for (Etal etal : etals) {
	    	if (etal.getVendeur() == null) {
	    		
	    	} else if (etal.isEtalOccupe()) {
	            sb.append(etal.getVendeur().getNom())
	              .append(" vend ")
	              .append(etal.getQuantiteInitiale())
	              .append(" ")
	              .append(etal.getProduit())
	              .append("\n");
	            nbEtalsUtilises++;
	        }
	    }
	    if (nbEtalsUtilises == 0) {
	        sb.append("Aucun étal n'est utilisé dans le marché.\n");
	    } else if (nbEtalsUtilises < etals.length) {
	        int nbEtalsNonUtilises = etals.length - nbEtalsUtilises;
	        sb.append("Il reste ")
	          .append(nbEtalsNonUtilises)
	          .append(" étal");
	        if (nbEtalsNonUtilises > 1) {
	            sb.append("s");
	        }
	        sb.append(" non utilisé");
	        if (nbEtalsNonUtilises > 1) {
	            sb.append("s");
	        }
	        sb.append(" dans le marché.\n");
	    }
	    return sb.toString();
	}
}