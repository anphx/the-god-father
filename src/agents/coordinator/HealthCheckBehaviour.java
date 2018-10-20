package agents.coordinator;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import javafx.scene.shape.Arc;

import javax.swing.*;

public class HealthCheckBehaviour extends TickerBehaviour {
    long currentTick = 0;
    private ArchitectAgent myAgent;

    public HealthCheckBehaviour(ArchitectAgent a, long period) {
        super(a, period);
        myAgent = a;
        currentTick = period;
    }

    @Override
    protected void onTick() {
        Health h = Health.getInstance();
//        SwingUtilities.invokeLater(new ResponseHandler((ArchitectAgent)myAgent, h));
        ResponseHandler res = new ResponseHandler(myAgent, h);
        new Thread(res).start();

    }

    class ResponseHandler implements Runnable {
        ArchitectAgent agent;
        Health msg;

        ResponseHandler(ArchitectAgent a, Health m) {
            agent = a;
            msg = m;
        }

        public void run() {
//            System.out.println(msg.getHealth());
            agent.updateHealthStatus(msg.getHealth());
        }
    }
}
