package bri;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;


class ServiceBRi implements Runnable {
	
	private Socket client;
	
	ServiceBRi(Socket socket) {
		client = socket;
	}

	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
			out.println(ServiceRegistry.toStringue()+"Tapez le numéro de service désiré :");
			int choix = Integer.parseInt(in.readLine());
			Class<? extends Service> classe = ServiceRegistry.getServiceClass(choix);
			
			try {
				Constructor<? extends Service> niou = classe.getConstructor(Socket.class);
				Service service = niou.newInstance(this.client);
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
