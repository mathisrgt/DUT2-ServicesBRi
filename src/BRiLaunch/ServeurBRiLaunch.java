package BRiLaunch;

import amat.ServeurAmat;
import auth.authentificate.Auth;
import prog.ServeurProg;

import java.io.IOException;

class ServeurBRiLaunch {
    private final static int PORT_PROG = 1111;
    private final static int PORT_AMAT = 2222;

    public static void main(String[] args) {
        try {
            new Thread(new ServeurProg(PORT_PROG, new Auth())).start();
            new Thread(new ServeurAmat(PORT_AMAT, new Auth())).start();
        } catch (IOException e) {
            System.err.println("Erreur lors de la création des serveurs : " +  e);
        }
    }
}
