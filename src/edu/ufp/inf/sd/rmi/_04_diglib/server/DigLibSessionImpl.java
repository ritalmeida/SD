package edu.ufp.inf.sd.rmi._04_diglib.server;

import edu.ufp.inf.sd.rmi._01_helloworld.server.HelloWorldRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DigLibSessionImpl extends UnicastRemoteObject implements DigLibSessionRI {

    private DBMockup database;

    public DigLibSessionImpl(DBMockup database) throws RemoteException {

        super();
        this.database = database;
        //this.sessions = sessions;
    }

    @Override
    public Book[] search(String tittle, String author) throws RemoteException {

        return this.database.select(tittle, author);
    }
}
