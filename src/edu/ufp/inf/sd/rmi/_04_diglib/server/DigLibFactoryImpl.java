package edu.ufp.inf.sd.rmi._04_diglib.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DigLibFactoryImpl extends UnicastRemoteObject implements DigLibFactoryRI {

    private HashMap<String, DigLibSessionRI> sessions = new HashMap<>();
    private DBMockup database;


    public DigLibFactoryImpl() throws RemoteException {

        super();
        database = new DBMockup();
        sessions = new HashMap<>();
    }

    @Override
    public boolean register(String username, String password) throws RemoteException {

        if (!database.exists(username, password)) {

            database.register(username, password);
            return true;
        }
        return false;
    }

    @Override
    public DigLibSessionRI login(String username, String password) throws RemoteException {

        if (database.exists(username, password)) {

            if (!this.sessions.containsKey(username)) {

                DigLibSessionRI digLibSessionRI = new DigLibSessionImpl(this.database, this.sessions);
                this.sessions.put(username, digLibSessionRI);
                return digLibSessionRI;
            }
        }
        return null;
    }

    @Override
    public DigLibSessionRI logout(String username) throws RemoteException {

        this.sessions.remove(username);
        return null;
    }

}
