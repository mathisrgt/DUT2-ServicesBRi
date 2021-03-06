package auth.authentificate;

import java.util.ArrayList;

public class Auth {
    private ArrayList<IUser> users = new ArrayList<>();

    /**@brief Ajoute un utilisateur, fonction synchronized;
     * @param   usr : Le nouveau utilisater;
     * @return VOID;
     */
    public synchronized void addUser(IUser usr) throws Exception {
        if(!users.contains(usr)){
            this.users.add(usr);
        }else{
            throw new Exception("user already exists");
        }

    }
    /**@brief Ajoute un utilisateur, fonction synchronized;
     * @param   usr : Le nouveau utilisater;
     * @return VOID;
     */
    public synchronized boolean hasAccount(IUser usr){
         for( IUser u : this.users){
             if(u.getNom().equals(usr.getNom()) && u.getMdp().equals(usr.getMdp()))
                 return true;
         }
         return false;
    }

}
