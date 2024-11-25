package bowling;

import java.util.ArrayList;
import java.util.List;

public class TourJoueur {
	private int tour;
	private ArrayList<LancerJoueur> lancers;
	private int coup;
	private boolean termine;

	public TourJoueur(int tour) {
		this.tour = tour;
		this.lancers = new ArrayList<>();
		this.coup = 0;
		this.termine = false;
	}

	public boolean enregistreLancer(LancerJoueur lancer) {
		lancers.add(lancer);
		coup++;
		if (coup >= 2 || (coup == 1 && lancer.getNombreQuille() == 10)) {
			termine = true;
		}
		return !termine;
	}

	public boolean estUnStrike() {
		return !lancers.isEmpty() && lancers.getFirst().getNombreQuille() == 10;
	}

	public boolean estUnSpare() {
		return lancers.size() > 1 && lancers.get(0).getNombreQuille() + lancers.get(1).getNombreQuille() == 10;
	}

	public boolean estTermine() {
		return termine;
	}

	public int getTour() {
		return tour;
	}

	public List<LancerJoueur> getLancers() {
		return lancers;
	}
}
