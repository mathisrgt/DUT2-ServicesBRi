package auth.authentificate;

public interface IUser {
    String getNom();

    void setNom(String nom);

    String getMdp();

    void setMdp(String mdp);

    String getPath() throws Exception;
    void setPath(String path) throws Exception;
}
