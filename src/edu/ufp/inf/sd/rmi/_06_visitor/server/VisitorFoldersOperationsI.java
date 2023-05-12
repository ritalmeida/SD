package edu.ufp.inf.sd.rmi._06_visitor.server;

import java.rmi.RemoteException;

public interface VisitorFoldersOperationsI {
    Object visitConcreteElementStateBooks(ElementFolderRI element) throws RemoteException;
}