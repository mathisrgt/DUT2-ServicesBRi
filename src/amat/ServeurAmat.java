package amat;

import auth.authentificate.Auth;
import auth.user.UserAmateur;
import auth.user.UserPro;
import servicesBRi.Accueil;
import servicesBRi.AccueilAmateur;

import java.io.IOException;
import java.net.ServerSocket;

public class ServeurAmat implements Runnable {
    private ServerSocket listen_socket;
    private Auth authentificate;

    /**@brief Constructeur du Serveur réservé aux amateurs;
     * @param   port : Le portdestiné aux amateurs;
     * @param authentificate, la classe qui permet de identifier les Utilisateur;
     * @return VOID
     */
    public ServeurAmat(int port, Auth authentificate) throws IOException {
        this.authentificate = authentificate;
        listen_socket = new ServerSocket(port);
    }


    /**@brief Lance le thread, identifie l'utilisateur, puis lance le Service Accueil;
     * @return VOID;
     */
    public void run() {
        try {
            System.err.println("Lancement du serveur au port " + this.listen_socket.getLocalPort());
            while(true) {
                try {
                    this.authentificate.addUser(new UserAmateur("test1", "test1"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new Thread(new AccueilAmateur(listen_socket.accept(), authentificate)).start();
                System.out.println("Nouveau client ama");
            }

        }
        catch (IOException e) {
            try {this.listen_socket.close();} catch (IOException e1) {}
            System.err.println("Arrêt du serveur au port " + this.listen_socket.getLocalPort());
        }
    }

    // restituer les ressources --> finalize
    protected void finalize() throws Throwable {
        try {this.listen_socket.close();} catch (IOException e1) {}
    }
}
