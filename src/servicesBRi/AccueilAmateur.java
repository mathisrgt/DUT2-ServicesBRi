package servicesBRi;

import auth.authentificate.Auth;
import auth.authentificate.IUser;
import auth.user.UserPro;
import bri.*;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;

import static java.lang.Integer.valueOf;

public class AccueilAmateur implements Runnable {
    private Socket s;
    private Auth ath;
    public AccueilAmateur(Socket accept, Auth at) {
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
                new ServiceBRi(s).start();
            }else{
                String aa= "noUser";

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }



private IUser getUserFromSocket(ObjectInputStream socketIn){
    System.out.println("function getUserFromSocket");

    try {
        return (IUser) socketIn.readObject();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    return null;
}

}

