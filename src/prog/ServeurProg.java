package prog;

import auth.authentificate.Auth;
import auth.user.UserPro;
import accueil.Accueil;

import java.io.IOException;
import java.net.ServerSocket;

public class ServeurProg implements Runnable {
    private ServerSocket listen_socket;
    private Auth authentificate;
    /**@brief Constructeur du serveur Pro;
     * @param port : port du Service;
     * @param authentificate : Objet qui permet de identifier les nouveux arrivants;
     * @return void;
     */
    public ServeurProg(int port, Auth authentificate) throws IOException {
        listen_socket = new ServerSocket(port);
        this.authentificate = authentificate;
        try {
            this.authentificate.addUser(new UserPro("test", "test", "test"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**@brief Thread qui s'occupe de dispacher les arrivants de type PRO dans leur accuil respectif;
     * @return void;
     */
    public void run() {
        try {
            System.err.println("Lancement du serveur au port " + this.listen_socket.getLocalPort());
            while(true) {
                new Thread(new Accueil(listen_socket.accept(), authentificate)).start();
                System.out.println("Nouveau client prog");
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
