package edu.ufp.inf.sd.rmi._04_diglib.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class DigLibSessionImpl extends UnicastRemoteObject implements DigLibSessionRI {

    private DBMockup database;
    private HashMap<String, DigLibSessionRI> sessions;

    public DigLibSessionImpl(DBMockup db, HashMap<String, DigLibSessionRI> sessions) throws RemoteException {

        this.database = db;
        this.sessions = sessions;
    }
    @Override
    public Book[] search(String tittle, String author) throws RemoteException {

        return database.select(tittle, author);
    }
}
