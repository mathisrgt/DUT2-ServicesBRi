package auth.user;

import auth.authentificate.IUser;

import java.io.Serializable;

public class UserAmateur implements IUser, Serializable {
    private static final long serialVersionUID = 6529685L;

    private String nom;
    private String mdp;

    public UserAmateur(String nom, String mdp) {
        this.nom = nom;
        this.mdp = mdp;
    }
    @Override
    /**@brief Get Nom de utilisateur;
     * @return String : Le nom;
     */
    public String getNom() {
        return nom;
    }
    /**@brief Set Nom de utilisateur;
     * @param nom : Le nom;
     * @return Void;
     */
    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }
    /**@brief Get le mot de passe de utilisateur;
     * @return String : le mot de passe;
     */
    @Override
    public String getMdp() {
        return mdp;
    }
    /**@brief Set mot de passe de utilisateur;
     * @param mdp : Le mdp;
     * @return Void;
     */
    @Override
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    /**@brief Get de la variable PATH des utilisateur pro, pour les amateurs elle souleve une exception de utilisateur;
     * @return Void;
     */
    @Override
    public String getPath() throws Exception {
        throw new Exception("User is Amateur");
    }
    /**@brief Set de la variable PATH des utilisateur pro, pour les amateurs elle souleve une exception de utilisateur;
     * @param path : String URL ver;
     * @return Void;
     */
    @Override
    public void setPath(String path) throws Exception {
        throw new Exception("User is Amateur");
    }


    /**@brief toString;
     * @return String;
     */
    @Override
    public String  toString() {
        return "UserAmateur{" +
                "nom='" + nom + '\'' +
                ", mdp='" + mdp + '\'' +
                '}';
    }
}
