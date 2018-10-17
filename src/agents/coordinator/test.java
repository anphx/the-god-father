package agents.coordinator;

import jade.gui.GuiEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class test {
    private JPanel contentP;
    private JPanel mainPanel;
    private JTable agentsList;
    private JTextField targetIP;
    private JButton sayHelloButton;
    private JTextField nbAgents;
    private JButton btnCreate;
    private JSpinner timer;
    private JButton resetTimerButton;
    private JComboBox agentFilters;
    private JButton terminateSelectedAgentButton;
    private JButton terminateAllAgentsButton;
    private JTextField agentToKill;
    private JButton terminateThisAgentButton;
    private JButton restartAllAgentsButton;
    private JButton restartRandomAgentsButton;
    private JTextField textField2;
    private JTextField txtHost;
    private JTextField txtPort;
    private JTable agentStatus;

    private ArchitectAgent myAgent;


    public test() {
        btnCreate.addActionListener(e -> {
            int numbOfAgents = Integer.parseInt(nbAgents.getText());
            int port = Integer.parseInt(txtPort.getText());
            String host = txtHost.getText();
            String name = "agent-" + new Date().getTime();

            GuiEvent ge = new GuiEvent(this, Helpers.NEW_AGENTS);
            ge.addParameter(numbOfAgents);
            ge.addParameter(host);
            ge.addParameter(port);
            ge.addParameter(name);

            myAgent.postGuiEvent(ge);
        });
    }

    public void frameInit() {
        JFrame frame = new JFrame("a.p. The-God-Father Agency");
        frame.setContentPane(this.contentP);
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
}
