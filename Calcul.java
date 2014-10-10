package lepackage;
import java.util.List;

/**
 * Calcul est la classe comprenant le calcul du remboursement.
 *
 * @author Clément Sébillet
 * @version 1.0
 */

public class Calcul {

	/**
     * Retourne le montant du remboursement.
     *
     * @return un décimal montantRemb, qui correspond au montant du remboursement.
     */

	public static double calculer(int i, List<AR> AR, List<AS> AS , Saisie maSaisie)
	{
		double montantRemb = 0;

		//Calcul remboursement
				//Si c'est un aller simple
				if(maSaisie.getTrajet().equals("AS")){
					//Si c'est en semaine de jour
					if(maSaisie.getDateDep().equals("S") && maSaisie.getHeureDep().equals("J")){
						montantRemb = AS.get(i).getPriseEnCharge() + (maSaisie.getNbKm() * AS.get(i).getTarifASJS());

						//Si le trajet dépasse 1h
						if(maSaisie.getHeurePar() > 1){
							montantRemb += montantRemb + (maSaisie.getHeurePar() * AS.get(i).getHoraireJS());
						}
					}else
					//Sinon c'est de semaine de nuit ou en WE
					if((maSaisie.getDateDep().equals("S") && maSaisie.getHeureDep().equals("N")) || maSaisie.getDateDep().equals("WE")){
						montantRemb = AS.get(i).getPriseEnCharge() + (maSaisie.getNbKm() * AS.get(i).getTarifASNW());

						//Si le trajet dépasse 1h
						if(maSaisie.getHeurePar() > 1){
							montantRemb += montantRemb + (maSaisie.getHeurePar() * AS.get(i).getHoraireNW());
						}
					}
				}else
					//Si c'est en semaine de jour
					if(maSaisie.getDateDep().equals("S") && maSaisie.getHeureDep().equals("J")){
						montantRemb = AR.get(i).getPriseEnCharge() + (maSaisie.getNbKm() * AR.get(i).getTarifARJS());

						//Si le trajet dépasse 1h
						if(maSaisie.getHeurePar() > 1){
							montantRemb += montantRemb + (maSaisie.getHeurePar() * AR.get(i).getHoraireJS());
						}
					}else
					//Sinon c'est de semaine de nuit ou en WE
					if((maSaisie.getDateDep().equals("S") && maSaisie.getHeureDep().equals("N")) || maSaisie.getDateDep().equals("WE")){
						montantRemb = AR.get(i).getPriseEnCharge() + (maSaisie.getNbKm() * AR.get(i).getTarifARNW());

						//Si le trajet dépasse 1h
						if(maSaisie.getHeurePar() > 1){
							montantRemb += montantRemb + (maSaisie.getHeurePar() * AR.get(i).getHoraireNW());
						}
				}

				return montantRemb;
	}
}