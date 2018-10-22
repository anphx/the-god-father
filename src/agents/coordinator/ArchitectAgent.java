package agents.coordinator;

import agents.utils.Helpers;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.Behaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.Vector;
import java.util.logging.Logger;

public class ArchitectAgent extends GuiAgent {

    private Vector<AgentContainer> allAgents = new Vector();
    private ArchitectAgentGui gui;
    private Logger logger;

    protected void setup() {
        gui = new ArchitectAgentGui();
        gui.setAgent(this);
        gui.frameInit();

        logger = Helpers.getLogger("ArchitectAgent", "logs/architectagent.log");

        System.out.println("===========Agent " + getLocalName() + " is up and running!===============");
        logger.info("===========Agent " + getLocalName() + " is up and running!===============");

        ///////////////////////
        // Add server health checking behaviour
        Behaviour b = new HealthCheckBehaviour(this, 500);
        addBehaviour(b);
    }

    @Override
    protected void onGuiEvent(GuiEvent ge) {
        // Handle all events fired from GuiAgent here
        // Process the events according to it's type
        switch (ge.getType()) {
            case Helpers.NEW_AGENTS:
                new Thread(new ThreadHandler(this, ge)).start();
            case Helpers.TERMINATE_AGENTS:
                for (int i = 0; i < allAgents.size(); i++) {
                    try {
                        allAgents.elementAt(i).kill();
                    } catch (StaleProxyException e) {
                        e.printStackTrace();
                    }
                }
            default:
                return;
        }
    }

    public void updateHealthStatus(int health) {
        // health is in % -> status of server
        gui.updateHealthStatus(health);
    }

    class ThreadHandler implements Runnable {
        ArchitectAgent agent;
        GuiEvent ge;

        ThreadHandler(ArchitectAgent a, GuiEvent g) {
            agent = a;
            ge = g;
        }

        public void run() {
            // Starting a number of agents in the same container by default
            try {
                ArchitectAgentGui gui = (ArchitectAgentGui) ge.getSource();
                int nbAgent = Integer.parseInt(ge.getParameter(0).toString());
                String host = ge.getParameter(1).toString();
                int port = Integer.parseInt(ge.getParameter(2).toString());
                String seedName = ge.getParameter(3).toString();
                String serverHost = ge.getParameter(4).toString();
                String serverPort = ge.getParameter(5).toString();
                String tickerPeriod = ge.getParameter(6).toString();
                String fiboNb = ge.getParameter(7).toString();

                Profile profile = new ProfileImpl(host, port, null);
                profile.setParameter(Profile.GUI, "true");
                Runtime rt = Runtime.instance();
                logger.info("Launching a whole in-process platform..." + profile);
                jade.wrapper.AgentContainer cont = rt.createAgentContainer(profile);

                for (int i = 0; i < nbAgent; i++) {
                    try {
                        // args: -host -port -tickerPeriod -fiboNb
                        AgentController a = cont.createNewAgent(
                                seedName + "-" + i,
                                TcpRequestAgent.class.getName(),
                                new String[]{serverHost, serverPort, tickerPeriod, fiboNb});
                        a.start();

                        logger.info(" - Created agent: " + i + " - " + a.getName() +
                                "====== on platform: " + cont.getPlatformName() +
                                "============ in container: " + cont.getContainerName() + "\n");

                        if (!allAgents.contains(cont)) allAgents.add(cont);
                        Thread.sleep(10);
                    } catch (Exception e) {
                        logger.throwing("ArchitectAgent", "onGuiEvent",e);
                        System.exit(-1);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return;
        }
    }
}
