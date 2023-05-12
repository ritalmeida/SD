package edu.ufp.inf.sd.rmi._06_visitor.server;

import jdk.jfr.consumer.RecordedMethod;

import java.io.Serializable;
import java.rmi.RemoteException;

public class VisitorFoldersOperationCreateFile implements VisitorFoldersOperationsI, Serializable {
    public String fileToCreate;
    public String fileToCreateWithPrefix;
    public VisitorFoldersOperationCreateFile(String newFolder){

        SingletonFolderOperationsBooks.createSingletonFolderOperationsBooks(newFolder);
    }

    @Override
    public Object visitConcreteElementStateBooks(ElementFolderRI element) throws RemoteException {

        ((ConcreteElementFolderBooksImpl)element).getStateBooksFolder().createFile(this.fileToCreate);
        return element;
        /*SingletonFolderOperationsBooks s = ((ConcreteElementFolderBooksImpl)element).getStateBooksFolder();
        fileToCreateWithPrefix = "VisitorBook_"+fileToCreate;
        System.out.println("VisitorStateFolderOperationDeleteFile - visitCOncreteElementStateBooks() : going to create file");
        return s.createFile(fileToCreateWithPrefix);*/
    }

    public String getFileToCreate(){
        return this.fileToCreate;
    }

    public void setFileToCreate(String fileToCreate){
        this.fileToCreate = fileToCreate;
    }

}