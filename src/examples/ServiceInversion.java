package examples;

import java.io.*;
import java.net.*;

import bri.Service;


public class ServiceInversion implements Service {

    private final Socket client;

    public ServiceInversion(Socket socket) {
        client = socket;
    }

    @Override
    public void run() {
        try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
            PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);

            out.println("Tapez un texte à inverser#");

            String line = in.readLine();
            StringBuilder sb = new StringBuilder(line);
            line = "Reponse : " + sb.reverse().toString() + " appuyez entree pour continuer";

            out.println(line);

        }
        catch (IOException e) {
            //Fin du service d'inversion
        }
    }


    public static String toStringue() {
        return "Service de Inversion de texte";
    }
}
