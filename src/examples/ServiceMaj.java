package examples;

import java.io.*;
import java.net.*;

import bri.Service;


public class ServiceMaj implements Service {
	
	private final Socket client;
	
	public ServiceMaj(Socket socket) {
		client = socket;
	}

	@Override
	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);

			out.println("Tapez un texte à corriger");
		
			String line = in.readLine();

			char[] caracts = line.toCharArray();
			caracts[0] = Character.toUpperCase(caracts[0]);
			for(int i = 1; i < caracts.length; ++i)
				if (((i+2) < caracts.length) && (caracts[i]=='.'))
					caracts[i+2] = Character.toUpperCase(caracts[i+2]);

			String corrLine = new String (caracts);
			
			out.println("Reponse :" + corrLine + " appuyez entree pour continuer");
			
		}
		catch (IOException e) {
			//Fin du service d'inversion
		}
	}
	
	protected void finalize() throws Throwable {
		 client.close(); 
	}

	public static String toStringue() {
		return "Correction de maj en début de ligne";
	}
}
