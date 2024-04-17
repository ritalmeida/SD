package edu.ufp.inf.sd.rmi._04_diglib.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface DigLibSessionRI extends Remote {
    List<Book> search(String tittle, String author) throws RemoteException;
    public void logout() throws RemoteException;

}
