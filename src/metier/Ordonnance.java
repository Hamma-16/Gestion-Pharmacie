package metier;

import java.util.Date;

public class Ordonnance {
    private int idMedicament;
    private int idClient;
    private int quantite;
    private Date date;

    public Ordonnance(int idMedicament, int idClient, int quantite, Date date) {
        this.idMedicament = idMedicament;
        this.idClient = idClient;
        this.quantite = quantite;
        this.date = date;
    }

	public Ordonnance() {
		// TODO Auto-generated constructor stub
	}

	public int getIdMedicament() {
        return idMedicament;
    }

    public void setIdMedicament(int idMedicament) {
        this.idMedicament = idMedicament;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}