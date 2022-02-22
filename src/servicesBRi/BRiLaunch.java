package servicesBRi;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;

import bri.ServeurBRi;
import bri.Service;
import bri.ServiceRegistry;
import bri.ValidationException;

public class BRiLaunch {

	// chargement dynamique d'un jarfile contenant une seule classe
	// cette classe implémente l'interface VerySimple

	private static final int PORT_SERVICE = 3000;

	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
		Scanner clavier = new Scanner(System.in);

		// URLClassLoader sur ftp
		String fileNameURL = "ftp://localhost:2121/";  //
		URLClassLoader urlcl = URLClassLoader.newInstance(new URL[]{new URL(fileNameURL)});

		System.out.println("Bienvenue dans votre gestionnaire dynamique d'activité BRi");
		System.out.println("Pour ajouter une activité, celle-ci doit être présente sur votre serveur ftp");
		System.out.println("A tout instant, en tapant le nom de la classe, vous pouvez l'intégrer");
		System.out.println("Les clients se connectent au serveur 3000 pour lancer une activité");

		new Thread(new ServeurBRi(PORT_SERVICE)).start();

		while (true) {
			try{
			String classeName = clavier.next();
			ServiceRegistry.addService(urlcl.loadClass(classeName).asSubclass(Service.class));
			} catch (ClassCastException e) {
				System.out.println("La classe doit implémenter bri.Service");;
			}catch (ValidationException e) {
				System.out.println(e.getMessage());;
			} catch (ClassNotFoundException e) {
				System.out.println("La classe n'est pas sur le serveur ftp dans home"); 
			}
		}
		}


}
