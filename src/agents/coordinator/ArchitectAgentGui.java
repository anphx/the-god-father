package agents.coordinator;

import jade.core.Agent;
import jade.gui.GuiEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;

public class ArchitectAgentGui {

    private ArchitectAgent myAgent;
    private JPanel mainPanel;
    private JTable agentsList;
    private JTextField targetIP;
    private JTextField nbAgents;
    private JButton sayHelloButton;
    private JComboBox agentFilters;
    private JButton terminateSelectedAgentButton;
    private JSpinner timer;
    private JButton resetTimerButton;
    private JButton btnCreate;
    private JTable agentStatus;
    private JButton terminateAllAgentsButton;
    private JTextField agentToKill;
    private JButton terminateThisAgentButton;
    private JButton restartAllAgentsButton;
    private JButton restartRandomAgentsButton;
    private JTextField textField2;
    private JTextField txtHost;
    private JTextField txtPort;
    private JPanel appPanel;


    public ArchitectAgentGui() {
        btnCreate.addActionListener(e -> {
            int numbOfAgents = Integer.parseInt(nbAgents.getText());
            int port = Integer.parseInt(txtPort.getText());
            String host = txtHost.getText();
            String seedName = "agent-" + new Date().getTime();

            GuiEvent ge = new GuiEvent(this, Helpers.NEW_AGENTS);
            ge.addParameter(numbOfAgents);
            ge.addParameter(host);
            ge.addParameter(port);
            ge.addParameter(seedName);

            myAgent.postGuiEvent(ge);
        });
    }

    protected void frameInit() {
        JFrame frame = new JFrame("a.p. The-God-Father Agency");
        frame.setContentPane(this.appPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void setAgent(ArchitectAgent a) {
        myAgent = a;
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        // Fire gui event in agent
//        // GuiEvent ge = new GuiEvent(this, NEW_ACCOUNT);
//        // myAgent.postGuiEvent(ge);
//    }

    /* Get/Set controls */

}
