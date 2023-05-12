/**
 * <p>Title: Projecto SD</p>
 *
 * <p>Description: Projecto apoio aulas SD</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: UFP & INESC Porto</p>
 *
 * @author Rui Moreira
 * @version 1.0
 */
package edu.ufp.inf.sd.rmi._06_visitor.server;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingletonFolderOperationsBooks implements SingletonFoldersOperationsI {

    private static SingletonFolderOperationsBooks singletonFolderOperationsBooks;
    private final File folderBooks;

    /** private - Avoid direct instantiation */
    public SingletonFolderOperationsBooks(File folderBooks) {

        this.folderBooks = folderBooks;
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, " - contructor (@ {0}", this.folderBooks);
    }

    @Override
    public File createFile(String fname) {

        String path = new StringBuilder().append(this.folderBooks).append('/').append(fname).toString();

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"))){

            writer.write("Isto é um teste!");
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, " - createFile()");
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public File deleteFile(String fname) {

        String path = new StringBuilder().append(this.folderBooks).append('/').append(fname).toString();
        File f = new File(path);

        if (f.delete()) {

            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Delete file @ {0}", f.getName());
        } else {
            System.out.println("Delete operaton is failed.");
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Delete operation is failed");
        }
        return f;
    }
}