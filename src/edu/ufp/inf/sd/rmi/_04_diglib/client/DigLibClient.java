package edu.ufp.inf.sd.rmi._04_diglib.client;

import edu.ufp.inf.sd.rmi._04_diglib.server.Book;
import edu.ufp.inf.sd.rmi._04_diglib.server.DigLibFactoryRI;
import edu.ufp.inf.sd.rmi._04_diglib.server.DigLibSessionRI;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DigLibClient {


    private SetupContextRMI contextRMI;
    private DigLibFactoryRI digLibFactoryRI;

    public static void main(String[] args) {
        if (args != null && args.length < 2) {
            System.exit(-1);
        } else {
            assert args != null;
            DigLibClient clt = new DigLibClient(args);
            clt.lookupService();
            clt.playService();
        }
    }

    public DigLibClient(String[] args) {
        try {
            String registryIP = args[0];
            String registryPort = args[1];
            String serviceName = args[2];
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(DigLibClient.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void lookupService() {
        try {
            Registry registry = contextRMI.getRegistry();
            if (registry != null) {
                String serviceUrl = contextRMI.getServicesUrl(0);
                digLibFactoryRI = (DigLibFactoryRI) registry.lookup(serviceUrl);
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void playService() {
        try {
            DigLibSessionRI session = login("guest", "ufp");

            if (session != null) {

                Book[] book = searchBooks(session,"Distributed","Tanenbaum");

                if(book.length > 0)
                {
                    for(Book books: book)
                    {
                        System.out.println("books = " + books + "\n");
                    }
                }else{
                    System.out.println("[session] - 1.Book not found\n");
                }


                Book[] b = searchBooks(session, "Teste", "Rui");

                if(b.length > 0)
                {
                    for(Book books: b)
                    {
                        System.out.println(books);

                    }
                }else{
                    System.out.println("[session] - 2.Book not found\n");
                }
            } else {
                System.out.println("[session] - No Session, Error credentials\n");
            }

            DigLibSessionRI error_session = login("gues", "uf");

            if (error_session != null) {

                Book[] book = searchBooks(error_session, "Distributed Systems: principles and paradigms", "Tanenbaum");

                System.out.println("book.length = " + book.length + "\n");

                if(book.length > 0)
                {
                    for(Book books: book)
                    {
                        System.out.println("books = " + books + "\n");
                    }
                }else{
                    System.out.println("[error_session] - 1.Book not found\n");
                }

                Book[] b = searchBooks(error_session, "Teste", "Rui");

                if(b.length > 0)
                {
                    for(Book books: b)
                    {
                        System.out.println(books);

                    }
                }else{
                    System.out.println("[error_session] - 2.Book not found\n");
                }
            } else {
                System.out.println("[error_session] - No Session, Error credentials\n");
            }
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    private DigLibSessionRI login (String username, String password) throws RemoteException {
        return digLibFactoryRI.login(username, password);
    }

    private Book[] searchBooks(DigLibSessionRI session, String title, String author) throws RemoteException {
        return session.search(title, author);
    }
}
