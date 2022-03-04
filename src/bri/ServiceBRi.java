package bri;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;


public class ServiceBRi implements Runnable {
	
	private Socket client;


	/**@brief Constructeur du Service qui gere le lancement des Serices disponibles;
	 * @param socket : La socket de communication;
	 * @return VOID;
	 */
	public ServiceBRi(Socket socket) {
		client = socket;
	}

	/**@brief Thread qui s'occupe de savoir quel Service le client veut acceder, et aussi d'arreter après;
	 * @return VOID;
	 */
	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
			String message = ServiceRegistry.toStringue()+"Tapez le numéro de service désiré :";
			System.out.println(message);
			out.println(message);
			int choix = Integer.parseInt(in.readLine());
			Class<? extends Service> classe = ServiceRegistry.getServiceClass(choix);
			System.out.println("Classe : " + classe);
			
			try {
				Constructor<? extends Service> niou = classe.getConstructor(Socket.class);
				System.out.println("Constructor : " + niou);
				Service service = niou.newInstance(this.client);
				System.out.println("Instance : " + service);
				service.run();
				/*Method runne = classe.getMethod("run");
				runne.invoke(service);*/
			} catch (SecurityException | InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
				System.out.println(e);
			}
			}
		catch (IOException e) {
			//Fin du service
		}

		try {client.close();} catch (IOException e2) {}
	}
	
	protected void finalize() throws Throwable {
		 client.close(); 
	}

	// lancement du service
	public void start() {
		(new Thread(this)).start();		
	}

}
