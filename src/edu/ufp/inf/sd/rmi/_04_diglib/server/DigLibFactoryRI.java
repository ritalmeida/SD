package edu.ufp.inf.sd.rmi._04_diglib.server;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface DigLibFactoryRI extends Remote {
    DigLibSessionRI login(String username, String password) throws RemoteException;

    boolean register(String username, String password) throws RemoteException;

    DigLibSessionRI logout(String username) throws RemoteException;
}
