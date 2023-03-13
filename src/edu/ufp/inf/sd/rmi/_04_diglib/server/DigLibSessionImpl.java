package edu.ufp.inf.sd.rmi._04_diglib.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DigLibSessionImpl extends UnicastRemoteObject implements DigLibSessionRI {

    private DBMockup database;

    public DigLibSessionImpl(DBMockup db) throws RemoteException {

        super();
        this.database = db;
    }
    @Override
    public Book[] search(String tittle, String author) throws RemoteException {

        return this.database.select(tittle, author);
    }
}
