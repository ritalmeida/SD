package edu.ufp.inf.sd.rmi._06_visitor.server;

import java.io.Serializable;
import java.rmi.RemoteException;

public class VisitorFoldersOperationDeleteFile implements VisitorFoldersOperationsI, Serializable {

    private String fileToDelete;
    private String fileToDeleteWithPrefix;
    public VisitorFoldersOperationDeleteFile(String newFolder) {

        setFileToDelete(newFolder);
        //this.fileToDelete = newFolder;
    }
    @Override
    public Object visitConcreteElementStateBooks(ElementFolderRI element) throws RemoteException {

        ((ConcreteElementFolderBooksImpl)element).getStateBooksFolder().createFile(this.fileToDelete);
        /*SingletonFolderOperationsBooks s = ((ConcreteElementFolderBooksImpl)element).getStateBooksFolder();
        fileToDeleteWithPrefix = "VisitorBook_"+fileToDelete;
        System.out.println("VisitorStateFolderOperationDeleteFile - visitCOncreteElementStateBooks() : going to delete");
        return s.deleteFile(fileToDeleteWithPrefix);*/
        return element;
    }

    public String getFileToDelete(){

        return this.fileToDelete;
    }

    public void setFileToDelete(String fileToDelete){

        this.fileToDelete = fileToDelete;
    }

}