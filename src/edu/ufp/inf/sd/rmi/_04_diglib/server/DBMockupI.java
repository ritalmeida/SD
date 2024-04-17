package edu.ufp.inf.sd.rmi._04_diglib.server;

import java.util.Optional;

public interface DBMockupI {
    boolean exists(String u, String p);

    void addSession(String username, DigLibFactoryRI session);

    void addSession(String username, DigLibSessionRI session);

    void removeSession(String username);

    Optional<DigLibSessionRI> session(String username);

    boolean hasSession(String username);
}
