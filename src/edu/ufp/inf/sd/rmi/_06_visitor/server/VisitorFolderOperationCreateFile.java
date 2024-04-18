package edu.ufp.inf.sd.rmi._06_visitor.server;


import java.rmi.RemoteException;

public class VisitorFolderOperationCreateFile implements VisitorFolderOperationI {

    private String fileToCreate;
    private String fileToCreateWithPrefix;

    public VisitorFolderOperationCreateFile(String newFolder) throws RemoteException {

        //this.fileToCreate = newFolder;
        SingletonFolderOperationsBooks.createSingletonFolderOperationsBooks(newFolder);
    }

    @Override
    public Object visitConcreteElementStateBooks(ElementFolderRI element) throws RemoteException {

        /*ConcreteElementFolderBooksImpl elementFolder = (ConcreteElementFolderBooksImpl) element;
        return elementFolder.getStateBooksFolder().createFile(this.fileToCreate);*/

        ((ConcreteElementFolderBooksImpl)element).getStateBooksFolder().createFile(this.fileToCreate);
        return element;
    }

    @Override
    public Object visitCreateElementsStateMagazines(ElementFolderRI element) {

        return null;
    }

    public String getFileToCreate() {
        return fileToCreate;
    }

    public void setFileToCreate(String fileToCreate) {
        this.fileToCreate = fileToCreate;
    }
}
