package edu.ufp.inf.sd.rmi._06_visitor.server;

import java.io.File;

public interface SingletonFoldersOperationsI {
    public File createFile(String fname);
    public File deleteFile(String fname);
}