package examples;

import java.io.*;
import java.net.*;

import bri.Service;


public class ServiceDoubleEspaces implements Service {
	
	private final Socket client;
	
	public ServiceDoubleEspaces(Socket socket) {
		client = socket;
	}

	@Override
	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);

			out.println("Tapez un texte à corriger");
		
			String line = in.readLine();
			line = line.replaceAll("( ){2,}", " ");
			
			out.println(" Votre reponse :" + line + " appuyez entree pour continuer");
			
		}
		catch (IOException e) {
			//Fin du service d'inversion
		}
	}
	
	protected void finalize() throws Throwable {
		 client.close(); 
	}

	public static String toStringue() {
		return "Correction des doubles espaces";
	}
}
