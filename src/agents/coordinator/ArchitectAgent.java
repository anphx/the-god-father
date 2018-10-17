package agents.coordinator;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerProxy;

import java.util.Vector;

public class ArchitectAgent extends GuiAgent {

    private Vector<AgentController> allAgents = new Vector();
    private ArchitectAgentGui gui;

    protected void setup() {
        System.out.println("Hello World! My name is " + getLocalName());
        gui = new ArchitectAgentGui();

//        gui = new ArchitectAgentGui();
        gui.setAgent(this);
        gui.frameInit();


        // Make this agent terminate
        //doDelete();
    }

//    protected void onGuiEvent(GuiEvent ev) {
//        RA1.setTitle("My Agent Name is: " + this.getName());
//    }

    @Override
    protected void onGuiEvent(GuiEvent ge) {
        // Handle all events fired from GuiAgent here
        // Process the events according to it's type
        switch (ge.getType()) {
            case Helpers.NEW_AGENTS:
                // Starting a number of agents in the same container by default
//                AgentContainer c = getContainerController();
                try {
                    ArchitectAgentGui gui = (ArchitectAgentGui) ge.getSource();
                    int nbAgent = Integer.parseInt(ge.getParameter(0).toString());
                    String host = ge.getParameter(1).toString();
                    int port = Integer.parseInt(ge.getParameter(2).toString());
                    String seedName = ge.getParameter(3).toString();

                    Profile profile = new ProfileImpl(host, port, null);
                    profile.setParameter(Profile.GUI, "true");
                    Runtime rt = Runtime.instance();
                    System.out.println("Launching a whole in-process platform..." + profile);
                    jade.wrapper.AgentContainer cont = rt.createAgentContainer(profile);

                    for (int i=0; i<nbAgent; i++) {
                        // TODO: Replace with a more useful agent
                        System.out.println(">>>>>>>>>>>>>>> Starting up our Agent Smith...");
                        AgentController a = cont.createNewAgent(seedName + "-" + i, HelloWorldAgent.class.getName(),null);
                        a.start();

                        System.out.print("Created agent: " + a.getName() +
                                "====== on platform: " + cont.getPlatformName() +
                                "============ in container: " + cont.getContainerName() + "\n");
                        allAgents.add(a);
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                return;
            default:
                return;

        }
    }
}
