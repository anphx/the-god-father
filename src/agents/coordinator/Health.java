package agents.coordinator;


import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.InetSocketAddress;


public class Health {
    static double RTT;
    static int health;
    private static Health single_instance = null;
    public static Health getInstance()
    {
        if (single_instance == null)
            single_instance = new Health();

        return single_instance;
    }

    private Health(){
        RTT=2000;
        health=100;
    }

    public int getHealth(){
        calcHealth();
        return (100-health);
    }
    public static void calcHealth(){
        double alpha=0.1;
        double sampleRTT = getRTT();
        double newRTT= (alpha*sampleRTT )+ ((1-alpha)*RTT);
        RTT=newRTT;
        health=(int)(newRTT/20);
    }

    public static long getRTT(){
        try {
            Socket sock = new Socket();
            Long start = System.currentTimeMillis();
            sock.connect(new InetSocketAddress("Agent-classic-balancer-public-890787002.eu-west-3.elb.amazonaws.com", 80),2000);
            Long end= System.currentTimeMillis();
            Long duration = end-start;
            return duration;

        }catch (Exception e){
            return 2000;
        }
    }
}