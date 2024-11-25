package bowling;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe a pour but d'enregistrer le nombre de quilles abattues lors des
 * lancers successifs d'<b>un seul et même</b> joueur, et de calculer le score
 * final de ce joueur.
 */
public class PartieMonoJoueur {

	private List<TourJoueur> tours;
	private int tourCourant;
	/**
	 * Constructeur
	 */
	public PartieMonoJoueur() {
		tours = new ArrayList<>();
		tourCourant = 0;
		for (int i = 0; i < 10; i++) {
			tours.add(new TourJoueur(i + 1));
		}
	}

	/**
	 * Cette méthode doit être appelée à chaque lancer de boule
	 *
	 * @param nombreDeQuillesAbattues le nombre de quilles abattues lors de ce lancer
	 * @throws IllegalStateException si la partie est terminée
	 * @return vrai si le joueur doit lancer à nouveau pour continuer son tour, faux sinon	
	 */
	public boolean enregistreLancer(int nombreDeQuillesAbattues) {
		if (estTerminee()) {
			throw new IllegalStateException("La partie est terminée");
		}
		LancerJoueur lancer = new LancerJoueur(nombreDeQuillesAbattues);
		boolean tourContinue = tours.get(tourCourant).enregistreLancer(lancer);
		if (!tourContinue && tours.get(tourCourant).estTermine()) {
			tourCourant++;
		}
		return tourContinue;
	}

	/**
	 * Cette méthode donne le score du joueur.
	 * Si la partie n'est pas terminée, on considère que les lancers restants
	 * abattent 0 quille.
	 * @return Le score du joueur
	 */
	public int score() {

		int score = 0;
		for (int i = 0; i < 10; i++) {
			TourJoueur tour = tours.get(i);
			List<LancerJoueur> lancers = tour.getLancers();
			if (tour.estUnStrike()) {
				score += 10 + prochainLancer(i, 1) + prochainLancer(i, 2);
			} else if (tour.estUnSpare()) {
				score += 10 + prochainLancer(i, 1);
			} else {
				score += lancers.get(0).getNombreQuille() + lancers.get(1).getNombreQuille();
			}
		}
		return score;
	}
	/**
	 * @return vrai si la partie est terminée pour ce joueur, faux sinon
	 */
	private int prochainLancer(int tourIndex, int lancerIndex) {
		if (tourIndex + 1 < 10) {
			List<LancerJoueur> lancers = tours.get(tourIndex + 1).getLancers();
			if (lancerIndex < lancers.size()) {
				return lancers.get(lancerIndex).getNombreQuille();
			}
		}
		return 0;
	}
	
	public boolean estTerminee() {
		return tourCourant >= 10 && tours.get(9).estTermine();	
	}


	/**
	 * @return Le numéro du tour courant [1..10], ou 0 si le jeu est fini
	 */
	public int numeroTourCourant() {
		return estTerminee() ? 0 : tourCourant + 1;	
	}

	/**
	 * @return Le numéro du prochain lancer pour tour courant [1..3], ou 0 si le jeu
	 *         est fini.
	 */
	public int numeroProchainLancer() {
		return estTerminee() ? 0 : tours.get(tourCourant).getLancers().size() + 1;
	}

}
