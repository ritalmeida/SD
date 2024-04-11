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
            //SetupContextRMI.printArgs(this.getClass().getName(), args);
            String registryIP = args[0];
            String registryPort = args[1];
            String serviceName = args[2];
            //Create a context for RMI setup
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(DigLibClient.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void lookupService() {
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
    }
    
    private void playService() {

        try {

            DigLibSessionRI session = this.digLibFactoryRI.login("guest", "ufp");

            if (session != null) {

                Book[] books = session.search("Distributed", "Colouris");

                for (Book b : books) {

                    System.out.println(books.toString());
                }

            /*DigLibSessionRI session = login("guest", "ufp");

            if (session != null) {

                Book[] book = searchBooks(session, "Distributed", "Tanenbaum");

                if (book.length > 0) {

                    for (Book books : book) {

                        System.out.println("books = " + books + "\n");
                    }
                } else {
                    System.out.println("[session] - 1. Book not found\n");
                }

                Book[] b = searchBooks(session, "Math", "Perry");

                if (b.length > 0) {

                    for (Book books : b) {

                        System.out.println(books);
                    }
                } else {
                    System.out.println("[session] - 2. Book not found\n");
                }
            } else {
                System.out.println("[session] - No Session, Error\n");
            }

            DigLibSessionRI error = login("gue", "fpu");

            if (error != null) {

                Book[] book = searchBooks(error, "Distributed Systems: principles and paradigms", "Tanenbaum");

                System.out.println("book.length = " + book.length + "\n");

                if (book.length > 0) {

                    for (Book books : book) {

                        System.out.println("books = " + books + "\n");
                    }
                } else {
                    System.out.println("[error] - 1. Book not found");
                }
                Book[] b = searchBooks(error, "Math", "Perry");

                if (b.length > 0) {

                    for (Book books : b) {

                        System.out.println(books);
                    }
                } else {

                    System.out.println("[error] - 2. Book not foundn");
                }
            } else {

                System.out.println("[error] - No session. Error!\n");*/
            }
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
