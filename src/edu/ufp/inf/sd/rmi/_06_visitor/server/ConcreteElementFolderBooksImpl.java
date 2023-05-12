package edu.ufp.inf.sd.rmi._06_visitor.server;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConcreteElementFolderBooksImpl extends UnicastRemoteObject implements ElementFolderRI {

    public SingletonFolderOperationsBooks stateBooksFolder;


    public ConcreteElementFolderBooksImpl() throws RemoteException{

        super();
        this.stateBooksFolder = new SingletonFolderOperationsBooks(new File("/Users/Ritaa/Documents/FACULDADE/SISTEMASDISTRIBUIDOS/SD/src/edu/ufp/inf/sd/rmi/_06_visitor/server/books/"));
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, " - constructor()");
    }
    @Override
    public Object acceptVisitor(VisitorFoldersOperationsI visitor) throws RemoteException {
        return visitor.visitConcreteElementStateBooks(this);
    }

    public SingletonFolderOperationsBooks getStateBooksFolder() {
        return this.stateBooksFolder;
    }
}