package edu.ufp.inf.sd.rmi._03_pingpong.server;

import edu.ufp.inf.sd.rmi._03_pingpong.client.PongRI;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PingThread extends Thread {

    PongRI pongRI;
    Ball ball;


    public PingThread(PongRI pongRI, Ball ball) {

        this.pongRI = pongRI;
        this.ball = ball;
    }

    public void run() {

        try {
            pongRI.pong(ball);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "The ball was send");
    }
}
