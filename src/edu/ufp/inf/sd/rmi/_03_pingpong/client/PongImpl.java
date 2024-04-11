package edu.ufp.inf.sd.rmi._03_pingpong.client;

import edu.ufp.inf.sd.rmi._03_pingpong.server.Ball;
import edu.ufp.inf.sd.rmi._03_pingpong.server.PingRI;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PongImpl implements PongRI, Serializable {

    SetupContextRMI contextRMI;
    PingRI pingRI;

    public PongImpl(SetupContextRMI contextRMI){
        this.contextRMI = contextRMI;
        lookupService();
    }

    public Remote lookupService() {
        try {
            //Get proxy MAIL_TO_ADDR rmiregistry
            Registry registry = contextRMI.getRegistry();
            //Lookup service on rmiregistry and wait for calls
            if (registry != null) {
                //Get service url (including servicename)
                String serviceUrl = contextRMI.getServicesUrl(0);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going MAIL_TO_ADDR lookup service @ {0}", serviceUrl);

                //============ Get proxy MAIL_TO_ADDR HelloWorld service ============
                pingRI = (PingRI) registry.lookup(serviceUrl);
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
                //registry = LocateRegistry.createRegistry(1099);
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return pingRI;
    }

    public void startService() {
        Ball ball = new Ball(1);
        try {
            //============ Call Ping remote service ============

            //Export client
            Remote exportObject = java.rmi.server.UnicastRemoteObject.exportObject(this, 0);
            this.pingRI.ping(ball, this);
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going MAIL_TO_ADDR finish, bye. ;)");

        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void pong(Ball ball) throws RemoteException {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "The ball was send");
    }
}
