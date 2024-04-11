package edu.ufp.inf.sd.rmi._05_observer.server;

import edu.ufp.inf.sd.rmi._01_helloworld.server.HelloWorldRI;
import edu.ufp.inf.sd.rmi._05_observer.client.ObserverRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubjectImpl extends UnicastRemoteObject implements SubjectRI {

    private State state;

    private ArrayList<ObserverRI> observers = new ArrayList<>();

    public SubjectImpl() throws RemoteException {

        super();
    }

    public SubjectImpl(State state) throws RemoteException {
        this.state = state;
    }

    @Override
    public State getState() {

        return this.state;
    }

    @Override
    public void setState(State state) {

        this.state = state;
        this.notifyAllObservers();
    }

    public void notifyAllObservers() {

        for (ObserverRI observer : observers) {

            try {
                observer.update();
            } catch (RemoteException ex) {

                System.out.println(ex.toString());
            }
        }
    }

    @Override
    public void attach(ObserverRI observerRI) {

        if (!this.observers.contains(observerRI))
            this.observers.add(observerRI);
    }

    @Override
    public void detach(ObserverRI observerRI) {

        this.observers.remove(observerRI);
    }

}
