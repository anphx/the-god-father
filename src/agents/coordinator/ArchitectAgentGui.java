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
    private JTextField targetIP;
    private JTextField nbAgents;
    private JButton btnCreate;
    private JTable agentStatus;
    private JButton terminateAllAgentsButton;
    private JButton restartAllAgentsButton;
    private JTextField txtHost;
    private JTextField txtPort;
    private JPanel appPanel;
    private JTextField targetPort;
    private JTextField tickerPeriod;
    private JTextField fbNr;


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

            ge.addParameter(targetIP.getText());
            ge.addParameter(targetPort.getText());
            ge.addParameter(tickerPeriod.getText());
            ge.addParameter(fbNr.getText());

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
