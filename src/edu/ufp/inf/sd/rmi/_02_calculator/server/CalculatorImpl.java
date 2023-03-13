package edu.ufp.inf.sd.rmi._02_calculator.server;

import edu.ufp.inf.sd.rmi._01_helloworld.server.HelloWorldRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CalculatorImpl extends UnicastRemoteObject implements CalculatorRI {


    public CalculatorImpl() throws RemoteException {
        super();
    }

    @Override
    public double add(double a, double b) throws RemoteException {

        double soma = a+b;
        Logger.getLogger(this.getClass().getName()).log(Level.INFO,"Soma (Servidor) = {0}", soma);

        return soma;
    }

    @Override
    public double add(ArrayList<Double> list) throws RemoteException {
        return 0;
    }

    @Override
    public double sub(double a, double b) throws RemoteException {

        double subtracao = a-b;
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Subtracao (Servidor) = {0}", subtracao);

        return subtracao;
    }

    @Override
    public double mult(double a, double b) throws RemoteException {

        double mult = a*b;
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Multiplicacao (Servidor) = {0}", mult);

        return mult;
    }

    @Override
    public double div(double a, double b) throws RemoteException {

        double div = a/b;
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Divisao (Servidor) = {0}", div);

        return div;
    }
}
