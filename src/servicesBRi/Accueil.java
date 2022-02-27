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
                while(true){
                    String sb="Bienvenu vous etes connecte ! # Choissiez un service # 1 - Fournir un nouveau Service # 2 - Mettre à jour un service # 3 - Déclarer un changement d'adresse FTP#";

                    PWin.println(sb);
                    lanceService(valueOf(BFin.readLine()), user, BFin, PWin);
                }



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
        boolean thereIsError=false;
        String Message="";
        URLClassLoader urlcl = URLClassLoader.newInstance(new URL[]{new URL(user.getPath())});
        PWin.println("Nom du service ? #");
        String classe="";
        do{
            try{

                    classe = BFin.readLine();
                    System.out.println("message " + classe);

                    ServiceRegistry.addService(urlcl.loadClass(classe).asSubclass(Service.class));
                    System.out.println(ServiceRegistry.toStringue());
                    thereIsError=false;
            } catch (ClassCastException e) {
                thereIsError=true;
                Message="La classe doit implémenter bri.Service ou press q to quit#";
            }catch (ValidationException e) {
                thereIsError=true;
                Message = e.getMessage() + '#';
            } catch (ClassNotFoundException e) {
                thereIsError=true;
                Message="La classe n'est pas sur le serveur ftp dans home ou press q to quit#";
                System.out.println("Erreur 3 HIRE");
            }catch(NoClassDefFoundError e){
                thereIsError=true;
                Message="NoClassDefFoundError ou press q to quit#";
            }
            if(classe.equals("q") || thereIsError){
                thereIsError=false;
                PWin.println(Message);
                System.out.println("Message sent" + Message);
            }

        }while(thereIsError);
        PWin.println("IGNORE Service a ete ajoute#");
        BFin.readLine();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }else if(service==2){
        try {
            boolean thereIsError=false;
            String Message="";
            URLClassLoader urlcl = URLClassLoader.newInstance(new URL[]{new URL(user.getPath())});
            PWin.println("Nom du service ? #");
            String classe="";
            do{
                try{

                    classe = BFin.readLine();
                    System.out.println("message " + classe);

                    ServiceRegistry.miseAjour(urlcl.loadClass(classe).asSubclass(Service.class));
                    System.out.println(ServiceRegistry.toStringue());
                    thereIsError=false;
                } catch (ClassCastException e) {
                    thereIsError=true;
                    Message="La classe doit implémenter bri.Service ou press q to quit#";
                }catch (ValidationException e) {
                    thereIsError=true;
                    Message = e.getMessage() + '#';
                } catch (ClassNotFoundException e) {
                    thereIsError=true;
                    Message="La classe n'est pas sur le serveur ftp dans home ou press q to quit#";
                    System.out.println("Erreur 3 HIRE");
                }catch(NoClassDefFoundError e){
                    thereIsError=true;
                    Message="NoClassDefFoundError ou press q to quit#";
                }
                thereIsError= !classe.equals("q");
                if(thereIsError){
                    PWin.println(Message);
                    System.out.println("Message sent" + Message);
                }


            }while(thereIsError);
            PWin.println("IGNORE Service a ete mis a jour#");
            BFin.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }else if(service==3){

        try {
            PWin.println("Entrez votre nouvelle URL #");
            String path=BFin.readLine();
            user.setPath(path);
            PWin.println("IGNORE Votre nouvelle PATH " + path + "#");
            BFin.readLine();
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


