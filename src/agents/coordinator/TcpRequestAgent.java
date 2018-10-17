package agents.coordinator;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpRequestAgent extends Agent {
    Socket server = null;
    String targethost = "Agent-classic-balancer-public-890787002.eu-west-3.elb.amazonaws.com";
    BufferedReader in;
    PrintWriter out;
    int port = 1234;
    private int tickerPeriod = 1000; //1sec
    private int fbNr = 10;

    protected void setup() {
        //Take host and port from argument
        Object[] args = getArguments();
        if (args != null && args.length >= 4) {
            targethost = (String) args[0];
            port = Integer.parseInt((String) args[1]);
            tickerPeriod = Integer.parseInt((String) args[2]);
            fbNr = Integer.parseInt((String) args[3]);
        }

        //Define agent
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Smith");
        sd.setName(getName());
        sd.addOntologies("SmithAgent");
        dfd.setName(getAID());
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            System.err.println(getLocalName()
                    + " registration with DF did not succeed. Reason: "
                    + e.getMessage());
            doDelete();
        }
        addBehaviour(new SendRequest(this, tickerPeriod));
    }

    public class SendRequest extends TickerBehaviour {

        public SendRequest(Agent a, int period) {
            super(a, period);
        }

        @Override
        protected void onTick() {
            try {
                //Open socket to TCP server
//                System.out.println("INFO:\t Opening socket to "+ targethost +":"+ port);
                server = new Socket(targethost, port);
//                System.out.println("INFO:\t Successfully opened socket to "+ targethost +":"+ port);

                //write on the output stream (count of element in fibonacci series)
                out = new PrintWriter(server.getOutputStream(), true);
                out.println(fbNr);
                out.flush();
//                System.out.println("INFO:\t Successfully communicated with TCP server at " +
//                        targethost +":"+ port + "--->" + tickerPeriod + "--->" + fbNr + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
