package auth.user;

import auth.authentificate.IUser;

public class UserAmateur implements IUser {
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
}
