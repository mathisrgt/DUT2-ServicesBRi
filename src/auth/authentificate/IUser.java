package auth.authentificate;

public interface IUser {

    /**@brief Get Nom de utilisateur;
     * @return String : Le nom;
     */
    String getNom();

    /**@brief Set Nom de utilisateur;
     * @param nom : Le nom;
     * @return Void
     */
    void setNom(String nom);

    /**@brief Get le mot de passe de utilisateur;
     * @return String : le mot de passe;
     */
    String getMdp();
    /**@brief Set mot de passe de utilisateur;
     * @param mdp : Le mdp;
     * @return Void;
     */
    void setMdp(String mdp);

    /**@brief Get de la variable PATH des utilisateur pro, pour les amateurs elle souleve une exception de utilisateur;
     * @return Void;
     */
    String getPath() throws Exception;
    /**@brief Set de la variable PATH des utilisateur pro, pour les amateurs elle souleve une exception de utilisateur;
     * @param path : String URL ver;
     * @return Void;
     */
    void setPath(String path) throws Exception;
}
