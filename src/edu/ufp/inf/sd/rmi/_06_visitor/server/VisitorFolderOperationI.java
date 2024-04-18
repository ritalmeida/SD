package edu.ufp.inf.sd.rmi._06_visitor.server;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface VisitorFolderOperationI extends Serializable {
    public Object visitConcreteElementStateBooks(ElementFolderRI element) throws RemoteException;

    public Object visitCreateElementsStateMagazines(ElementFolderRI element);
}
