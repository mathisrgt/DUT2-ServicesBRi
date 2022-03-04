package auth.user;

import auth.authentificate.IUser;

import java.io.Serializable;

public class UserPro implements IUser, Serializable {
    private static final long serialVersionUID = 6529685098267757690L;

    private String nom;
    private String mdp;
    private String path;
    public UserPro(String nom, String mdp, String path) {
        this.nom = nom;
        this.mdp = mdp;
        this.path = path;
    }
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
    public void setNom(String nom) {
        this.nom = nom;
    }
    /**@brief Get le mot de passe de utilisateur
     * @return String : le mot de passe
     */
    public String getMdp() {
        return mdp;
    }
    /**@brief Set mot de passe de utilisateur;
     * @param mdp : Le mdp;
     * @return Void;
     */
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    /**@brief Get de la variable PATH des utilisateur pro, pour les amateurs elle souleve une exception de utilisateur;
     * @return Void;
     */
    public String getPath() {
        return path;
    }
    /**@brief Set de la variable PATH des utilisateur pro, pour les amateurs elle souleve une exception de utilisateur;
     * @param path : String URL ver;
     * @return Void;
     */
    public void setPath(String path) {
        this.path = path;
    }


    /**@brief toString;
     * @return String;
     */
    @Override
    public String toString() {
        return "UserPro{" +
                "nom='" + nom + '\'' +
                ", mdp='" + mdp + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
