package agents.coordinator;

import jade.core.behaviours.TickerBehaviour;

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
        myAgent.updateHealthStatus(h.getHealth());
//        ResponseHandler res = new ResponseHandler(myAgent, h);
    }

    class ResponseHandler implements Runnable {
        ArchitectAgent agent;
        Health msg;

        ResponseHandler(ArchitectAgent a, Health m) {
            agent = a;
            msg = m;
        }

        public void run() {
            agent.updateHealthStatus(msg.getHealth());
        }
    }
}
