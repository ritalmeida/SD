package edu.ufp.inf.sd.rmi._04_diglib.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public class RemoteSessionExpiredException extends RemoteException {

    public RemoteSessionExpiredException() {
    }

    public RemoteSessionExpiredException(String s) {
        super(s);
    }

    public RemoteSessionExpiredException(String s, Throwable cause) {
        super(s, cause);
    }
}
