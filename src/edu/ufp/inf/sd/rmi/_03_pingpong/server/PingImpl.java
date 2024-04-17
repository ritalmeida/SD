package edu.ufp.inf.sd.rmi._03_pingpong.server;

import edu.ufp.inf.sd.rmi._03_pingpong.client.PongRI;
import edu.ufp.inf.sd.rmi.util.threading.ThreadPool;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PingImpl extends UnicastRemoteObject implements PingRI, Runnable {

    private ThreadPool threadPool;
    private static final int numThreads = 10;
    private PongRI pongRI;
    private Ball ball;

    public class PingRunnable extends Thread {

        private  PongRI pongRI;
        private Ball ball;

        PingRunnable(PongRI clientPongRI, Ball ball) {

            this.pongRI = clientPongRI;
            this.ball = ball;
        }
    }

    public PingImpl() throws RemoteException {

        super();
        this.threadPool = new ThreadPool(numThreads);
    }

    @Override
    public void ping(Ball ball, PongRI pongRI) throws RemoteException {

        if (this.getClass().getAnnotatedInterfaces()[1].getType().getTypeName() == Runnable.class.getTypeName()) {

            this.pongRI = pongRI;
            this.ball = ball;
            this.threadPool.execute(this);
        }
        else {
            PingRunnable pt = new PingRunnable(pongRI, ball);
            pt.start();
        }
    }

    @Override
    public void run() {

        try {
            System.out.println("Ping: ball = " + this.ball.getPlayerID());
            this.pongRI.pong(this.ball);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
