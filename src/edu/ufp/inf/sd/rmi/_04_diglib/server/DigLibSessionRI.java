package edu.ufp.inf.sd.rmi._04_diglib.server;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface DigLibSessionRI extends Remote {
    Book[] search(String tittle, String author) throws RemoteException;
}
