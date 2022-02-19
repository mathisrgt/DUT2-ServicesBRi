package amat;

import auth.authentificate.Auth;
import servicesBRi.Service1;

import java.io.IOException;
import java.net.ServerSocket;

public class ServeurAmat implements Runnable {
    private ServerSocket listen_socket;
    private Auth authentificate;

    public ServeurAmat(int port, Auth authentificate) throws IOException {
        listen_socket = new ServerSocket(port);
    }

    public void run() {
        try {
            System.err.println("Lancement du serveur au port " + this.listen_socket.getLocalPort());
            while(true) {
                new Thread(new Service1(listen_socket.accept(), authentificate)).start();
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
