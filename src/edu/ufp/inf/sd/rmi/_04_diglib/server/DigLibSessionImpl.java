package edu.ufp.inf.sd.rmi._04_diglib.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.List;

public class DigLibSessionImpl extends UnicastRemoteObject implements DigLibSessionRI, Runnable {

    private DBMockup database;
    private User user;
    private LocalDateTime sessionTimer;

    public DigLibSessionImpl(DBMockup database, User user) throws RemoteException {

        super();
        this.database = database;
        this.user = user;
        this.database.addSession(user.getUname(), this);
    }

    public DigLibSessionImpl(DBMockup database, User user, LocalDateTime sessionTimer) throws RemoteException {

        this(database, user);
        this.sessionTimer = sessionTimer;
    }


    @Override
    public List<Book> search(String tittle, String author) throws RemoteSessionExpiredException {

        if (this.database.hasSession(this.user.getUname())) {
            return this.database.select(tittle, author);
        }
        else throw new RemoteSessionExpiredException("Error 419: Session Expired");
    }

    @Override
    public synchronized void logout() throws RemoteException {

        this.database.removeSession(this.user.getUname());
    }

    @Override
    public void run() {

        System.out.println("Session ticking...");
        while (this.sessionTimer.isAfter(LocalDateTime.now())) {

        }
        System.out.println("Session Expired!");
        try {
            this.logout();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
