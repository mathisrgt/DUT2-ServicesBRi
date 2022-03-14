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
	private BufferedReader in;
	private PrintWriter out;

	/**@brief Constructeur du Service qui gere le lancement des Serices disponibles;
	 * @param client : La socket de communication;
	 * @return VOID;
	 */
	public ServiceBRi(Socket client, BufferedReader in, PrintWriter out) {
		this.client = client;
		this.in = in;
		this.out = out;
	}

	/**@brief Thread qui s'occupe de savoir quel Service le client veut acceder, et aussi d'arreter après;
	 * @return VOID;
	 */
	public void run() {
		String message="";
		String tmp="";
		boolean thereisError=false;
		do{
			try {
				if(!thereisError){
					message = ServiceRegistry.toStringue()+"Tapez le numéro de service désiré(ou press 'q' to quit ) :";
				}
				System.out.println(message);
				out.println(message);
				tmp = in.readLine();
				int choix = Integer.parseInt(tmp);
				Class<? extends Service> classe = ServiceRegistry.getServiceClass(choix);
				System.out.println("Classe : " + classe);

				try {
					Constructor<? extends Service> niou = classe.getConstructor(Socket.class);
					System.out.println("Constructor : " + niou);
					Service service = niou.newInstance(this.client);
					System.out.println("Instance : " + service);
					service.run();
					in.readLine();
				/*out.println("Service terminé");
				System.out.println("Service Finis");*/
				/*Method runne = classe.getMethod("run");
				runne.invoke(service);*/
				} catch (SecurityException | InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
					System.out.println(e);
				}
			}
			catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
				if(tmp.equals("q")){
					break;
				}
				thereisError=true;
				message="Votre saisie n'est pas un numéro, ou n'est pas dans la liste";
			}
		}while(true);


	}


	// lancement du service
	public void start() {
		(new Thread(this)).start();		
	}

}
