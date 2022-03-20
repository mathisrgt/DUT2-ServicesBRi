package accueil;

import auth.authentificate.Auth;
import auth.authentificate.IUser;
import auth.user.UserPro;
import bri.Service;
import bri.ServiceRegistry;
import bri.ValidationException;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.URLClassLoader;

import static java.lang.Integer.valueOf;

public class Accueil implements Runnable {
    private Socket s;
    private Auth ath;
    /**@brief Constructeur de l'Accueil;
     * @param accept : Socket de communication;
     * @param at : Objet qui permet de identifier les nouveux arrivants;
     * @return void;
     */
    public Accueil(Socket accept, Auth at) {
        ath = at;
        s=accept;
        //new Thread(this).start();
        System.out.println("lancement service 1");

    }

    /**@brief Thread qui s'occupe de dispacher vers le service souhaité par le client pro;
     * @return void;
     */
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
                boolean err=false;
                while(true){
                    String sb="Bienvenu vous etes connecte ! # Choissiez un service # 1 - Fournir un nouveau Service # 2 - Mettre à jour un service # 3 - Déclarer un changement d'adresse FTP#";
                    if(!err){
                        PWin.println(sb);
                    }

                    try{
                        int val = valueOf(BFin.readLine());
                        lanceService(val, user, BFin, PWin);
                        err=false;
                        if(val>3 || val < 0){
                            throw new NumberFormatException();
                        }
                    }catch(NumberFormatException | SocketException a){
                        err=true;
                        PWin.println("#n'est pas un nombre possible, ou n'est meme pas un nombre, entrez une valeur possible : ");
                    }

                }



            }else{
                String aa= "noUser";

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    /**@brief Lance le service;
     * @param service : int que reprense le service en question;
     * @param user : L'utilisateur qui est connecté;
     * @param BFin : Le buffer de lecture ;
     * @param PWin : Le Print pour écrire ;
     * @return void;
     */
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
                        thereIsError=!classe.equals("q");
                        Message="La classe doit implémenter bri.Service ou press q to quit#";
                    }catch (ValidationException e) {
                        thereIsError=!classe.equals("q");
                        Message = e.getMessage() + '#';
                    } catch (ClassNotFoundException e) {
                        thereIsError=!classe.equals("q");
                        Message="La classe n'est pas sur le serveur ftp dans home ou press q to quit#";
                        System.out.println("Erreur 3 HIRE");
                    }catch(NoClassDefFoundError e){
                        thereIsError=!classe.equals("q");
                        Message="NoClassDefFoundError ou press q to quit#";
                        e.printStackTrace();
                    }
                    System.out.println("Alors il y a : " + thereIsError);
                    if(thereIsError){
                        PWin.println(Message);
                        System.out.println("Message sent" + Message);
                    }

                }while(thereIsError);
                PWin.println("IGNORE Service 1 terminé#");
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
                        thereIsError=!classe.equals("q");
                        Message="La classe doit implémenter bri.Service ou press q to quit#";
                    }catch (ValidationException e) {
                        thereIsError=!classe.equals("q");
                        Message = e.getMessage() + '#';
                    } catch (ClassNotFoundException e) {
                        thereIsError=!classe.equals("q");
                        Message="La classe n'est pas sur le serveur ftp dans home ou press q to quit#";
                        System.out.println("Erreur 3 HIRE");
                    }catch(NoClassDefFoundError e){
                        thereIsError=!classe.equals("q");
                        Message="NoClassDefFoundError ou press q to quit#";
                    }
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
                PWin.println("Entrez votre nouvelle URL ou press q to quit#");
                String path=BFin.readLine();
                if(path.equals("q")){
                    throw new Exception();
                }
                user.setPath(path);
                PWin.println("IGNORE Votre nouvelle PATH " + path + "#");
                BFin.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**@brief get un Objet de la socket;
     * @param socketIn :ObjectInputStream permentant de lire un objet dans une socket ;
     * @return iUser;
     */
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


