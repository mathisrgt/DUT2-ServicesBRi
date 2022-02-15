package User;

import Authentificate.IUser;

public class UserPro implements IUser {
    private String nom;
    private String mdp;
    private String path;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public UserPro(String nom, String mdp, String path) {
        this.nom = nom;
        this.mdp = mdp;
        this.path = path;
    }
}
