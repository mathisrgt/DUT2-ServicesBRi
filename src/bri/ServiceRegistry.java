package bri;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Vector;


public class ServiceRegistry {
	// cette classe est un registre de services
	// partagée en concurrence par les clients et les "ajouteurs" de services,
	// un Vector pour cette gestion est pratique

	static {
		servicesClasses = new Vector<Class<? extends Service>>();
	}
	private static List<Class<? extends Service>> servicesClasses;
	/**@brief Service de mise ajour d'un Service present deja dans le vector, methodde synchronized car le vector est une ressource commune;
	 * @param runnableClass : La classe qui doit extends Service;
	 * @throws ValidationException : Si la classe ne respecte pas les criteres de validation;
	 * @return VOID;
	 */
	public synchronized static void miseAjour(Class<? extends Service> runnableClass) throws ValidationException {
		validation(runnableClass);
		System.out.println("Function mise a jour");
		synchronized (servicesClasses) {
			System.out.println("block syncro, taille " + servicesClasses.size());
			for (int i =0; i< servicesClasses.size();i++) {

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
	/**@brief Service d'ajout de services dans le vector, methodde synchronized car le vector est une ressource commune;
	 * @param runnableClass : La classe qui doit extends Service;
	 * @throws ValidationException : Si la classe ne respecte pas les criteres de validation;
	 * @return VOID;
	 */
	public synchronized static void addService(Class<? extends Service> runnableClass) throws ValidationException {
		// vérifier la conformité par introspection
		// si non conforme --> exception
		validation(runnableClass);
		servicesClasses.add(runnableClass);
	}

	/**@brief Souleve une exception si la classe n'est pas conforme, sinon elle ne fais rien ( surtout pas de boolean pour le retour );
	 * @param classe : La classe qui doit extends Service;
	 * @throws ValidationException : Si la classe ne respecte pas les criteres de validation;
	 * @return VOID;
	 */
	private static void validation(Class<? extends Service> classe) throws ValidationException {
		// cette partie pourrait être déléguée à un objet spécialisé
		// le constructeur avec Socket

		try {
			Constructor<? extends Service> c = classe.getConstructor(java.net.Socket.class);
			int modifiers = c.getModifiers();
			if (!Modifier.isPublic(modifiers))
				throw new ValidationException("Le constructeur (Socket) doit être public");
			if (c.getExceptionTypes().length != 0)
				throw new ValidationException("Le constructeur (Socket) ne doit pas lever d'exception");
			// etc... avec tous les tests nécessaires
		} catch (NoSuchMethodException e) {
			// transformation du type de l'exception quand l'erreur est détectée par ce biais
			throw new ValidationException("Il faut un constructeur avec Socket");
		}

		
	
	}
	/**@brief Get de la classe de indice numService dans le vecteur;
	 * @param numService : La classe qui doit extends Service;
	 * @return Class;
	 */
	public synchronized static Class<? extends Service> getServiceClass(int numService) {
			return servicesClasses.get(numService-1);
	}

	/**@brief Methode qui permet de recupere les classes du vecteur en String, de maière un peu "jolie";
	 * @return String;
	 */
	public static String toStringue() {
		String result = "Activités présentes :#";
		int i = 1;
		// foreach n'est qu'un raccourci d'écriture 
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
					e.printStackTrace(); // ??? - normalement déjà testé par validation()
				}
			}
		}
		return result;
	}

}
