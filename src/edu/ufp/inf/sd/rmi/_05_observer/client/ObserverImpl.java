package edu.ufp.inf.sd.rmi._05_observer.client;

import edu.ufp.inf.sd.rmi._01_helloworld.server.HelloWorldRI;
import edu.ufp.inf.sd.rmi._05_observer.server.State;
import edu.ufp.inf.sd.rmi._05_observer.server.SubjectRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {

    private String id;
    private State lastObserverState;
    protected SubjectRI subjectRI;
    protected ObserverGuiClient chatFrame;

    public ObserverImpl(String id, ObserverGuiClient chatFrame, SubjectRI subjectRI) throws RemoteException {

        super();
        this.id = id;
        this.subjectRI = subjectRI;
        this.chatFrame = chatFrame;
        this.lastObserverState = new State(id, "");
        this.subjectRI.attach(this);
    }

    @Override
    public void update() throws RemoteException {

        this.lastObserverState = subjectRI.getState();
        this.chatFrame.updateTextArea();
    }

    protected State getLastObserverState() {

        return this.lastObserverState;
    }
}
