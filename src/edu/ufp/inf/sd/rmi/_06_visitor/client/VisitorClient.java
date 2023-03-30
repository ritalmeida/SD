package edu.ufp.inf.sd.rmi._06_visitor.client;

import edu.ufp.inf.sd.rmi._06_visitor.server.ElementFolderRI;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;
import edu.ufp.inf.sd.rmi._06_visitor.server.VisitorFoldersOperationCreateFile;
import edu.ufp.inf.sd.rmi._06_visitor.server.VisitorFoldersOperationsI;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VisitorClient {


    private SetupContextRMI contextRMI;
    private ElementFolderRI myRI;

    public static void main(String[] args) {
        if (args != null && args.length < 2) {
            System.exit(-1);
        } else {
            VisitorClient clt = new VisitorClient(args);
            clt.lookupService();
            clt.playService();
        }
    }

    public VisitorClient(String[] args) {
        try {
            String registryIP   = args[0];
            String registryPort = args[1];
            String serviceName  = args[2];
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(VisitorClient.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private Remote lookupService() {
        try {
            Registry registry = contextRMI.getRegistry();
            if (registry != null) {
                String serviceUrl = contextRMI.getServicesUrl(0);
                myRI = (ElementFolderRI) registry.lookup(serviceUrl);
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return myRI;
    }

    private void playService() {
        try {
            VisitorFoldersOperationsI v1 = new VisitorFoldersOperationCreateFile("create1.txt");
            myRI.acceptVisitor(v1);
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
}