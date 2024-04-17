package edu.ufp.inf.sd.rmi._04_diglib.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;

public class DigLibFactoryImpl extends UnicastRemoteObject implements DigLibFactoryRI {

    static int SessionTimeInSeconds = 10;
    private DBMockup database;

    public DigLibFactoryImpl(DBMockup database) throws RemoteException {

        super();
        this.database = database;
    }

    @Override
    public DigLibSessionRI register(User user) throws RemoteException {

        this.database.register(user.getUname(), user.getPword());
        return this.login(user);

    }

    @Override
    public DigLibSessionRI login(User user) throws RemoteException {

        if (!this.database.exists(user.getUname(), user.getPword())) {
            throw new RemoteUserNotFoundException("User not found!");
        }

        return this.getSession(user);
    }

    /*public DigLibSessionRI logout (String username) throws RemoteException {

        this.sessions.remove(username);
        return null;
    }*/

    private DigLibSessionRI getSession(User user) throws RemoteException {

        DigLibSessionRI sessionRI = this.database.session(user.getUname()).orElse(new DigLibSessionImpl(database, user,
                LocalDateTime.now().plusSeconds(SessionTimeInSeconds)));

        Thread thread = new Thread((Runnable)sessionRI);
        thread.start();

        return sessionRI;
    }

}
