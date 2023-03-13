package edu.ufp.inf.sd.rmi._03_pingpong.server;

import edu.ufp.inf.sd.rmi._03_pingpong.client.PongRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class PingImpl extends UnicastRemoteObject implements PingRI {


    public PingImpl() throws RemoteException {
        super();
    }


    @Override
    public void ping(Ball ball, PongRI pongRI) {

        PingThread t1 = new PingThread(pongRI, ball);
        t1.start();
    }
}
