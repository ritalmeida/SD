package edu.ufp.inf.sd.rmi._04_diglib.client;

import edu.ufp.inf.sd.rmi._01_helloworld.server.HelloWorldRI;
import edu.ufp.inf.sd.rmi._04_diglib.server.*;
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
            System.err.println("usage: java [options] edu.ufp.sd.inf.rmi._04_diglib.client.DigLibClient <rmi_registry_ip> <rmi_registry_port> <service_name>");
            System.exit(-1);
        } else {
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
            this.contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(DigLibClient.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void lookupService() {

        //Get proxy MAIL_TO_ADDR rmiregistry
            Registry registry = contextRMI.getRegistry();
            //Lookup service on rmiregistry and wait for calls
            if (registry != null) {
                //Get service url (including servicename)
                //String serviceUrl = contextRMI.getServicesUrl(0);
                System.out.println("Registry is null");
            }
            String serviceUrl = contextRMI.getServicesUrl(0);
            try {
                this.digLibFactoryRI = (DigLibFactoryRI) registry.lookup(serviceUrl);
            } catch (RemoteException | NotBoundException ex) {
                ex.printStackTrace();
        }
    }
    
    private void playService() {

        User user = new User("rita", "1904");
        try {

            DigLibSessionRI session = this.digLibFactoryRI.login(user);
            session.search("Friends, Amantes e Aquela Coisa Terrivel", "Matthew Perry");

        } catch (RemoteException ex) {

            System.out.println("Creating new User...");
            try {
                DigLibSessionRI sessionRI = this.digLibFactoryRI.register(user);
                sessionRI.search("Friends, Amantes e Aquela Coisa Terrivel", "Matthew Perry").forEach(System.out::println);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
