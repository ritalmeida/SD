package edu.ufp.inf.sd.rmi._04_diglib.server;

import edu.ufp.inf.sd.rmi._01_helloworld.server.HelloWorldRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DigLibSessionImpl extends UnicastRemoteObject implements DigLibSessionRI {

    private DBMockup database;
    private HashMap<String, DigLibSessionRI> sessions;

    public DigLibSessionImpl(DBMockup database, HashMap<String, DigLibSessionRI> sessions) throws RemoteException {

        this.database = database;
        this.sessions = sessions;
    }

    @Override
    public Book[] search(String tittle, String author) throws RemoteException {

        return database.select(tittle, author);
    }
}
