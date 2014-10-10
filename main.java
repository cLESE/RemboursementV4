package lepackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import lepackage.Calcul;

/**
 * main est le programme principal qui va permettre la création des liste pour aller retour et aller simple, la saisie de l'utilisateur et le calcul du remboursement.
 *
 * @author Clément Sébillet
 * @version 1.0
 */

public class main {

	public static void main(String[] args) {

		// création d'une liste
		List<AR> AR =  new ArrayList<AR>();
		List<AS> AS =  new ArrayList<AS>();

		try{
			Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
		    System.out.println("Driver PostgreSQL introuvable !!!");
		    System.exit(0);
		}

		//Création d'un objet de type Connection
    	Connection maConnect = null;

    	try{
    		String url = "jdbc:postgresql://172.16.99.2:5432/csebillet";
    		maConnect = DriverManager.getConnection(url, "c.sebillet", "passe");
    	}catch(Exception e){
    		System.out.println("Une erreur est survenue lors de la connexion à la base de donnée");
    	}

    	Statement maReq = null;

    	try{
    		maReq = maConnect.createStatement();
    	}catch(Exception e){

    	}

    	String texteRequete = "select * from \"taxi\".\"dept\"";


		// définition de l'objet qui récupérera le résultat de l'exécution de la requête :
		ResultSet curseurResultat = null;
		try {
			curseurResultat = maReq.executeQuery(texteRequete);

			// tant qu'il y a encore une ligne résultat à lire
			while(curseurResultat.next())
	         {
	        	AR.add(new AR (curseurResultat.getInt("dept"),curseurResultat.getDouble("priseCharge"), curseurResultat.getDouble("tarifKMARJS"),curseurResultat.getDouble("tarifHoraireJS"),curseurResultat.getDouble("tarifKMARND"),curseurResultat.getDouble("tarifHoraireND")));
	 			AS.add(new AS (curseurResultat.getInt("dept"),curseurResultat.getDouble("priseCharge"), curseurResultat.getDouble("tarifKMASJS"),curseurResultat.getDouble("tarifHoraireJS"),curseurResultat.getDouble("tarifKMASND"),curseurResultat.getDouble("tarifHoraireND")));
	         }

			// on ferme le flux résultat
			 curseurResultat.close();

			// on ferme l'objet lié à la connexion
			 maConnect.close();
		} catch (SQLException e) {
		    System.out.println("La requête ne retourne aucun résultat !!!");
		    System.exit(0);
		}

		int i;

		Saisie maSaisie = new Saisie();

		boolean saisieOK = false;

		do{

			boolean trouve = false;
			i = 0;

			while(!trouve && i<AR.size()){
				if(maSaisie.getNumDept()==AR.get(i).getDept()){
					trouve = true;
				}else{
					i++;
				}
			}

			if(trouve){
				saisieOK = true;
			}
			else{
				Scanner deptObjet = new Scanner(System.in);
				System.out.println("Département non trouvé; veuillez resaisir");
				maSaisie.setNumDept(deptObjet.nextInt());
			}
		}while(!saisieOK);

		System.out.println("Résultat : " + Calcul.calculer(i, AR, AS, maSaisie));

	}

}