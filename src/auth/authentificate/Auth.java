package auth.authentificate;

import java.util.ArrayList;

public class Auth {
    private ArrayList<IUser> users = new ArrayList<>();


    public synchronized void addUser(IUser usr) throws Exception {
        if(!users.contains(usr)){
            this.users.add(usr);
        }else{
            throw new Exception("user already exists");
        }

    }

    public synchronized boolean hasAccount(IUser usr){
         return users.contains(usr);
    }
}
