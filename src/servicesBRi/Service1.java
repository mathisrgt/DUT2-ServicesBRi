package servicesBRi;

import auth.authentificate.Auth;
import auth.authentificate.IUser;
import auth.user.UserPro;

import java.io.*;
import java.net.Socket;

public class Service1 implements Runnable {
    private Socket s;
    private Auth ath;
    public Service1(Socket accept, Auth at) {
        ath = at;
        s=accept;
        //new Thread(this).start();
        System.out.println("lancement service 1");

    }

    @Override
    public void run() {
        ObjectOutputStream socketOut = null;
        ObjectInputStream socketIn = null;
        System.out.println("creation de la communication");
        try {
            System.out.println("debut try");

            System.out.println("apres scket in");
            socketOut = new ObjectOutputStream (s.getOutputStream ( ));
            socketIn =new ObjectInputStream(s.getInputStream());



            System.out.println("Après les new");
            IUser user = getUserFromSocket(socketIn);
            if(this.ath.hasAccount(user)){
                System.out.println(user);
            }else{
                String aa= "noUser";
                try {

                    socketOut.writeObject(aa);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
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


