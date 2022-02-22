package servicesBRi;

import auth.authentificate.Auth;
import auth.authentificate.IUser;
import auth.user.UserPro;
import bri.Service;
import bri.ServiceRegistry;
import bri.ValidationException;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;

import static java.lang.Integer.valueOf;

public class Accueil implements Runnable {
    private Socket s;
    private Auth ath;
    public Accueil(Socket accept, Auth at) {
        ath = at;
        s=accept;
        //new Thread(this).start();
        System.out.println("lancement service 1");

    }

    @Override
    public void run() {
        ObjectOutputStream socketOut = null;
        ObjectInputStream socketIn = null;
        BufferedReader BFin=null;
        PrintWriter PWin = null;
        System.out.println("creation de la communication");
        try {
            System.out.println("debut try");

            System.out.println("apres scket in");
            socketOut = new ObjectOutputStream (s.getOutputStream ( ));
            socketIn =new ObjectInputStream(s.getInputStream());
            BFin = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PWin = new PrintWriter(s.getOutputStream(), true);



            System.out.println("Après les new");
            IUser user = getUserFromSocket(socketIn);
            if(this.ath.hasAccount(user)){
                System.out.println(user);
                String sb="Bienvenu vous êtes connecté ! # Choissiez un service # 1 - Fournir un nouveau Service # 2 - Mettre à jour un service # 3 - Déclarer un changement d'adresse FTP#";

                PWin.println(sb);
                lanceService(valueOf(BFin.readLine()), user, BFin, PWin);


            }else{
                String aa= "noUser";

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

private void lanceService(int service, IUser user, BufferedReader BFin, PrintWriter PWin){
    System.out.println("Service choisi " + service);
    if(service==1){
    try {
        URLClassLoader urlcl = URLClassLoader.newInstance(new URL[]{new URL(user.getPath())});
        try{
            PWin.println("Nom du service ? #");
            ServiceRegistry.addService(urlcl.loadClass(BFin.readLine()).asSubclass(Service.class));
            System.out.println(ServiceRegistry.toStringue());
        } catch (ClassCastException e) {
            System.out.println("La classe doit implémenter bri.Service");;
        }catch (ValidationException e) {
            System.out.println(e.getMessage());;
        } catch (ClassNotFoundException e) {
            System.out.println("La classe n'est pas sur le serveur ftp dans home");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}

private IUser getUserFromSocket(ObjectInputStream socketIn){
    System.out.println("function getUserFromSocket");

    try {
        return (UserPro) socketIn.readObject();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    return null;
}

}


