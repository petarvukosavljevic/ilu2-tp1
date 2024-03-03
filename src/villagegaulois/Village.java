package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
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

	public String afficherVillageois() {
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
	
	private static class Marche {
		private Etal[] etals;
		
		private Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for(int i = 0; i<nbEtals; i++) {
				etals[i] = new Etal();
			}
		}
		
		 private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			 etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		 }
		 
		 private int trouverEtalLibre() {
			 int etalLibre = -1;
			 for(int i = 0; i<etals.length && etalLibre == -1; i++) {
				 if(etals[i].isEtalOccupe() == false) {
					 etalLibre = i;
				 }
			 }
			 return etalLibre;
		 }
		 
		 private Etal[] trouverEtals(String produit) {
			 int nbEtalsAvecProduit = 0;
			 for(int i = 0; i<etals.length; i++) {
				 if(etals[i].contientProduit(produit) && etals[i].isEtalOccupe()) {
					 nbEtalsAvecProduit++;
				 }
			 }
			 Etal[] etalsAvecProduit = new Etal[nbEtalsAvecProduit];
			 int indiceEtal = 0;
			 for(int i = 0; i<etals.length; i++) {
				 if(etals[i].contientProduit(produit) == true) {
					 etalsAvecProduit[indiceEtal] = etals[i];
					 indiceEtal++;
				 }
			 }
			 return etalsAvecProduit;
		 }
		 
		 private Etal trouverVendeur(Gaulois gaulois) {
			 Etal etalDuVendeur = null;
			 for(int i = 0; i<etals.length && etalDuVendeur == null; i++) {
				 if(gaulois.getNom() == etals[i].getVendeur().getNom()) {
					 etalDuVendeur = etals[i];
				 }
			 }
			 return etalDuVendeur;
		 }
		 
		 private String afficherMarche() {
			 int nbEtalVide = 0;
			 for (int i = 0; i<etals.length; i++) {
				 if(etals[i].isEtalOccupe()) {
					 etals[i].afficherEtal();
				 }else {
					nbEtalVide++; 
				 }
			 }
			 return  "Il reste " + nbEtalVide + " �tals non utilis�s dans le march�.\n";
		 }
	
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int etalLibre = marche.trouverEtalLibre();
		if (etalLibre == -1) {
			chaine.append("Il n'y a pas d'etals libres!\n");
		} else {
			marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " a l'etal numero " + (etalLibre + 1) + ".\n");
		}
		return chaine.toString();
	}
	
	 public String rechercherVendeursProduit(String produit) {
		 StringBuilder chaine = new StringBuilder();
		 Etal[] etalsAvecProduit = marche.trouverEtals(produit);
		 if (etalsAvecProduit != null) {
			 chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
				for (int i = 0; i < etalsAvecProduit.length; i++) {
					chaine.append(" - " + etalsAvecProduit[i].getVendeur().getNom() + "\n");
				}
		 } else {
			 chaine.append("Aucun vendeur propose des " + produit + "!\n");
		 }
		 return chaine.toString();
	 }
	 
	 public Etal rechercherEtal(Gaulois vendeur) {
		 Etal etalDuVendeur = marche.trouverVendeur(vendeur);
		 System.out.println(etalDuVendeur.afficherEtal());
		 return etalDuVendeur;
	 }
	 
	 public String partirVendeur(Gaulois vendeur) {
		 Etal etalOccuper = rechercherEtal(vendeur);
		 return etalOccuper.libererEtal();
	 }
	 
	 public String afficherMarche() {
		 StringBuilder chaine = new StringBuilder();
		 chaine.append("Le marche du village '" + this.getNom() + "' possede plusieurs etals :\n");
		 chaine.append(marche.afficherMarche());
		 return chaine.toString();
	 }


}