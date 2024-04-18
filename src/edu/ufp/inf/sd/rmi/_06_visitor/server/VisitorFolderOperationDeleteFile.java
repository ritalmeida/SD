package edu.ufp.inf.sd.rmi._06_visitor.server;

import java.rmi.RemoteException;

public class VisitorFolderOperationDeleteFile implements VisitorFolderOperationI {

    private String fileToDelete;
    private String fileToDeleteWithPrefix;

    public VisitorFolderOperationDeleteFile(String newFolder) throws RemoteException {

        //this.fileToDelete = newFolder;
        setFileToDelete(newFolder);
    }

    @Override
    public Object visitConcreteElementStateBooks(ElementFolderRI element) {

        /*ConcreteElementFolderBooksImpl elementFolder = (ConcreteElementFolderBooksImpl) element;
        return elementFolder.getStateBooksFolder().deleteFile(this.fileToDelete);*/
        ((ConcreteElementFolderBooksImpl)element).getStateBooksFolder().deleteFile(this.fileToDelete);
        return element;
    }

    @Override
    public Object visitCreateElementsStateMagazines(ElementFolderRI element) {

        return null;
    }

    public String getFileToDelete() {
        return fileToDelete;
    }

    public void setFileToDelete(String fileToDelete) {
        this.fileToDelete = fileToDelete;
    }
}
