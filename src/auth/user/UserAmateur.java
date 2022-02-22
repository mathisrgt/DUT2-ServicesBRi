package auth.user;

import auth.authentificate.IUser;

import java.io.Serializable;

public class UserAmateur implements IUser, Serializable {
    private static final long serialVersionUID = 6529685L;

    private String nom;
    private String mdp;

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String getMdp() {
        return mdp;
    }

    @Override
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Override
    public String getPath() throws Exception {
        throw new Exception("User is Amateur");
    }

    @Override
    public void setPath(String path) throws Exception {
        throw new Exception("User is Amateur");
    }

    public UserAmateur(String nom, String mdp) {
        this.nom = nom;
        this.mdp = mdp;
    }

    @Override
    public String  toString() {
        return "UserAmateur{" +
                "nom='" + nom + '\'' +
                ", mdp='" + mdp + '\'' +
                '}';
    }
}
