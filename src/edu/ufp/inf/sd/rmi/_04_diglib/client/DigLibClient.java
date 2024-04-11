package edu.ufp.inf.sd.rmi._04_diglib.client;

import edu.ufp.inf.sd.rmi._01_helloworld.server.HelloWorldRI;
import edu.ufp.inf.sd.rmi._04_diglib.server.Book;
import edu.ufp.inf.sd.rmi._04_diglib.server.DigLibFactoryRI;
import edu.ufp.inf.sd.rmi._04_diglib.server.DigLibSessionImpl;
import edu.ufp.inf.sd.rmi._04_diglib.server.DigLibSessionRI;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * Title: Projecto SD</p>
 * <p>
 * Description: Projecto apoio aulas SD</p>
 * <p>
 * Copyright: Copyright (c) 2017</p>
 * <p>
 * Company: UFP </p>
 *
 * @author Rui S. Moreira
 * @version 3.0
 */
public class DigLibClient {

    /**
     * Context for connecting a RMI client MAIL_TO_ADDR a RMI Servant
     */
    private SetupContextRMI contextRMI;
    /**
     * Remote interface that will hold the Servant proxy
     */
    private DigLibFactoryRI digLibFactoryRI;

    public static void main(String[] args) {
        if (args != null && args.length < 2) {
            //System.err.println("usage: java [options] edu.ufp.sd.inf.rmi._04_diglib.server.DigLibClient <rmi_registry_ip> <rmi_registry_port> <service_name>");
            System.exit(-1);
        } else {
            assert args != null;
            //1. ============ Setup client RMI context ============
            DigLibClient clt = new DigLibClient(args);
            //2. ============ Lookup service ============
            clt.lookupService();
            //3. ============ Play with service ============
            clt.playService();
        }
    }

    public DigLibClient(String args[]) {
        try {
            //List ans set args
            SetupContextRMI.printArgs(this.getClass().getName(), args);
            String registryIP = args[0];
            String registryPort = args[1];
            String serviceName = args[2];
            //Create a context for RMI setup
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(DigLibClient.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private Remote lookupService() {
        try {
            //Get proxy MAIL_TO_ADDR rmiregistry
            Registry registry = contextRMI.getRegistry();
            //Lookup service on rmiregistry and wait for calls
            if (registry != null) {
                //Get service url (including servicename)
                String serviceUrl = contextRMI.getServicesUrl(0);
                //Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going MAIL_TO_ADDR lookup service @ {0}", serviceUrl);
                
                //============ Get proxy MAIL_TO_ADDR HelloWorld service ============
                digLibFactoryRI = (DigLibFactoryRI) registry.lookup(serviceUrl);
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
                //registry = LocateRegistry.createRegistry(1099);
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return digLibFactoryRI;
    }
    
    private void playService() {

        try {
            DigLibSessionRI sessionRI = this.digLibFactoryRI.login("guest", "ufp");

            if (sessionRI != null) {

                Book[] books = sessionRI.search("Distributed", "Tanenbaum");

                for (Book b : books) {

                    System.out.println(books.toString());
                }
            }

            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going MAIL_TO_ADDR finish, bye. ;)");
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    private DigLibSessionRI login (String username, String password) throws RemoteException {

        return digLibFactoryRI.login(username, password);
    }

    private Book[] searchBooks(DigLibSessionRI session, String tittle, String author) throws RemoteException {

        return session.search(tittle, author);
    }
}
