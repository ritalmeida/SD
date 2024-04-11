package edu.ufp.inf.sd.rmi._04_diglib.server;

import edu.ufp.inf.sd.rmi._01_helloworld.server.HelloWorldRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DigLibFactoryImpl extends UnicastRemoteObject implements DigLibFactoryRI {

    private HashMap<String, DigLibSessionRI> sessions = new HashMap<>();
    private DBMockup database;

    public DigLibFactoryImpl(DBMockup database) throws RemoteException {

        super();
        this.database = database;
    }

    @Override
    public boolean register(String username, String password) throws RemoteException {

        /*if (!database.exists(username, password)) {

            database.register(username, password);
            return true;
        }
        return false;*/

        boolean register = this.database.register(username, password);
        return register;
    }

    @Override
    public DigLibSessionRI login(String username, String password) throws RemoteException {

        if (this.sessions.containsKey(username)) {

            return this.sessions.get(username);
        }

        if (this.database.exists(username, password)) {

            DigLibSessionImpl libSession = new DigLibSessionImpl(this.database);
            this.sessions.put(username, libSession);

            return libSession;
        }

        return null;
    }

    public DigLibSessionRI logout (String username) throws RemoteException {

        this.sessions.remove(username);
        return null;
    }

}
