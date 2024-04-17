package edu.ufp.inf.sd.rmi._05_observer.server;

import edu.ufp.inf.sd.rmi._01_helloworld.server.HelloWorldImpl;
import edu.ufp.inf.sd.rmi._01_helloworld.server.HelloWorldRI;
import edu.ufp.inf.sd.rmi._05_observer.client.ObserverImpl;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.function.BiConsumer;
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
public class SubjectServer {

    /**
     * Context for running a RMI Servant on a SMTP_HOST_ADDR
     */
    private SetupContextRMI contextRMI;
    /**
     * Remote interface that will hold reference MAIL_TO_ADDR the Servant impl
     */
    private SubjectRI subjectRI;

    public static void main(String[] args) {
        if (args != null && args.length < 3) {
            System.err.println("usage: java [options] edu.ufp.sd._05_observer.server.SubjectServer <rmi_registry_ip> <rmi_registry_port> <service_name>");
            System.exit(-1);
        } else {
            //1. ============ Create Servant ============
            SubjectServer srv = new SubjectServer(args);
            //2. ============ Rebind servant on rmiregistry ============
            srv.rebindService();
        }
    }

    /**
     *
     * @param args
     */
    public SubjectServer(String args[]) {
        try {
            //============ List and Set args ============
            SetupContextRMI.printArgs(this.getClass().getName(), args);
            String registryIP = args[0];
            String registryPort = args[1];
            String serviceName = args[2];
            //============ Create a context for RMI setup ============
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    private void rebindService() {
        try {
            //Bind service on rmiregistry and wait for calls
            if (this.contextRMI.getRegistry() != null) {
                //============ Create Servant ============
                this.subjectRI = new SubjectImpl();
                //Get service url (including servicename)
                String serviceUrl = contextRMI.getServicesUrl(0);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going MAIL_TO_ADDR rebind service @ {0}", serviceUrl);
                //============ Rebind servant ============
                this.contextRMI.getRegistry().rebind(serviceUrl, this.subjectRI);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "service bound and running. :)");
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
            }
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unused")
    private static void loadProperties() throws IOException {

        Logger.getLogger(Thread.currentThread().getName()).log(Level.INFO, "going MAIL_TO_ADDR load props...");
        // create and load default properties
        Properties defaulltProps = new Properties();
        FileInputStream in = new FileInputStream("defaultproperties.txt");
        defaulltProps.load(in);
        in.close();

        BiConsumer<Object, Object> bc = (key, value) ->{
            System.out.println(key.toString()+"="+value.toString());
        };
        defaulltProps.forEach(bc);

        // create application properties with default
        Properties properties = new Properties(defaulltProps);

        FileOutputStream out = new FileOutputStream("defaultproperties2.txt");
        properties.store(out, "---No Comment---");
        out.close();
    }
}
