package edu.ufp.inf.sd.rmi._04_diglib.server;

import java.rmi.RemoteException;

public class RemoteUserNotFoundException extends RemoteException {

    public RemoteUserNotFoundException() {
    }

    public RemoteUserNotFoundException(String s) {
        super(s);
    }

    public RemoteUserNotFoundException(String s, Throwable cause) {
        super(s, cause);
    }
}
