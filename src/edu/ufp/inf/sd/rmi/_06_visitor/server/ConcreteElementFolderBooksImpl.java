package edu.ufp.inf.sd.rmi._06_visitor.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ConcreteElementFolderBooksImpl extends UnicastRemoteObject implements ElementFolderRI {

    private final SingletonFoldersOperationsI stateBooksFolder;

    public ConcreteElementFolderBooksImpl(String bookfolders) throws RemoteException {

        super();
        this.stateBooksFolder = SingletonFolderOperationsBooks.createSingletonFolderOperationsBooks(bookfolders);
    }

    public SingletonFoldersOperationsI getStateBooksFolder() {

        return this.stateBooksFolder;
    }

    @Override
    public Object acceptVisitor(VisitorFolderOperationI visitor) throws RemoteException {

        return visitor.visitConcreteElementStateBooks(this);
    }
}
