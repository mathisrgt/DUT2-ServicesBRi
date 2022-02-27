package bri;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Vector;

public class ServiceRegistry {
	// cette classe est un registre de services
	// partag�e en concurrence par les clients et les "ajouteurs" de services,
	// un Vector pour cette gestion est pratique

	static {
		servicesClasses = new Vector<Class<? extends Service>>();
	}
	private static List<Class<? extends Service>> servicesClasses;

	public static void miseAjour(Class<? extends Service> runnableClass) throws ValidationException {
		validation(runnableClass);
		System.out.println("Function mise a jour");
		synchronized (servicesClasses) {
			System.out.println("block syncro, taille " + servicesClasses.size());
			for (int i =0; i<= servicesClasses.size();i++) {

				if(servicesClasses.size()!=0 && servicesClasses.get(i).getName().equals(runnableClass.getName())){
					System.out.println("for each index :" + i);
					System.out.println("getname :" + servicesClasses.get(i).getName());
					System.out.println("class runnalbe :" + runnableClass.getName());
					servicesClasses.remove(i);
					servicesClasses.add(runnableClass);
				}else{
					throw new ValidationException("Cette classe n'existe pas ! press q to quit");
				}
			}
		}
	}

	public static void addService(Class<? extends Service> runnableClass) throws ValidationException {
		// v�rifier la conformit� par introspection
		// si non conforme --> exception
		validation(runnableClass);
		servicesClasses.add(runnableClass);
	}

	// une m�thode de validation renvoie void et l�ve une exception si non validation
	// surtout pas de retour boolean !
	private static void validation(Class<? extends Service> classe) throws ValidationException {
		// cette partie pourrait �tre d�l�gu�e � un objet sp�cialis�
		// le constructeur avec Socket
		Constructor<? extends Service> c = null;
		try { 
			c = classe.getConstructor(java.net.Socket.class); 
		} catch (NoSuchMethodException e) {
			// transformation du type de l'exception quand l'erreur est d�tect�e par ce biais
			throw new ValidationException("Il faut un constructeur avec Socket");
		}
		int modifiers = c.getModifiers();
		if (!Modifier.isPublic(modifiers)) 
			throw new ValidationException("Le constructeur (Socket) doit �tre public");
		if (c.getExceptionTypes().length != 0)
			throw new ValidationException("Le constructeur (Socket) ne doit pas lever d'exception");
		// etc... avec tous les tests n�cessaires
		
	
	}

	public static Class<? extends Service> getServiceClass(int numService) {
			return servicesClasses.get(numService-1);
	}
	
// toStringue liste les activit�s pr�sentes
	public static String toStringue() {
		String result = "Activit�s pr�sentes :#";
		int i = 1;
		// foreach n'est qu'un raccourci d'�criture 
		// donc il faut prendre le verrou explicitement sur la collection
		synchronized (servicesClasses) { 
			for (Class<? extends Service> s : servicesClasses) {
				try {
					Method toStringue = s.getMethod("toStringue");
					String string = (String) toStringue.invoke(s);
					result = result + i + " " + string+"#";
					i++;
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace(); // ??? - normalement d�j� test� par validation()
				}
			}
		}
		return result;
	}

}
