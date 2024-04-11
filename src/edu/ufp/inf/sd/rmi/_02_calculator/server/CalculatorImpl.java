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
    public double add(double a, double b) {

        double soma = a + b;
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Sona (Servidor) = {0}", soma);
        return soma;
    }

    @Override
    public double add(ArrayList<Double> list) throws RemoteException {

        return 0;
    }

    @Override
    public double sub(double a, double b) {

        double subtracao = a - b;
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Subtração (Servidor) = {0}", subtracao);
        return subtracao;
    }

    @Override
    public double mult(double a, double b) {

        double multiplicacao = a * b;
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Multiplicação (Servidor) = {0}", multiplicacao);
        return multiplicacao;
    }

    @Override
    public double div(double a, double b) throws RemoteArithmeticException {

        double divisao = 0;

        if (b == 0) {

            throw new RemoteArithmeticException("Erro! Não é possível dividir por zero!");
        } else {

            divisao = a / b;
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Divisão (Servidor) = {0}", divisao);
        }
        return divisao;
    }
}
