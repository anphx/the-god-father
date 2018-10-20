package agents.coordinator;

import agents.utils.GraphPanel;
import agents.utils.Helpers;
import jade.gui.GuiEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

public class ArchitectAgentGui {

    private ArchitectAgent myAgent;
    private JPanel mainPanel;
    private JTextField targetIP;
    private JTextField nbAgents;
    private JButton btnCreate;
    private JButton terminateAllAgentsButton;
    private JTextField txtHost;
    private JTextField txtPort;
    private JPanel appPanel;
    private JTextField targetPort;
    private JTextField tickerPeriod;
    private JTextField fbNr;
    private GraphPanel graph;

    private JFrame frame;

    //    public static Boolean isDebugging = false;
//    private int[] serverStatus;
    Vector<Integer> serverStatus = new Vector<Integer>();


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

        terminateAllAgentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiEvent ge = new GuiEvent(this, Helpers.TERMINATE_AGENTS);
                myAgent.postGuiEvent(ge);
            }
        });
//        isDebug.addItemListener(e -> isDebugging = (e.getStateChange() == ItemEvent.SELECTED));
    }

    protected void frameInit() {
        frame = new JFrame("a.p. The-God-Father Agency");
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout(2, 1));

        graph = new GraphPanel(serverStatus);
        graph.setPreferredSize(new Dimension(400, 400));

        frame.setContentPane(content);
        content.add(this.appPanel);
        content.add(graph, BorderLayout.PAGE_END);

//        frame.add(this.appPanel);
//        frame.add(new GraphPanel());

//        this.appPanel.add(new GraphPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void setAgent(ArchitectAgent a) {
        myAgent = a;
    }

    public void updateHealthStatus(int health) {
        serverStatus.add(health);
        int size = serverStatus.size();

        // Keep up to 50 latest values of health data
        if (size >= 50) {
            Integer[] temp = new Integer[]{serverStatus.elementAt(size-2), serverStatus.elementAt(size-1)};
            serverStatus = new Vector<Integer>(Arrays.asList(temp));
        }
//        System.out.println(serverStatus);
        graph = new GraphPanel(serverStatus);
        graph.setPreferredSize(new Dimension(400, 400));
        frame.getContentPane().add(graph, BorderLayout.PAGE_END);
        frame.pack();

//        graph.refresh(serverStatus);
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        // Fire gui event in agent
//        // GuiEvent ge = new GuiEvent(this, NEW_ACCOUNT);
//        // myAgent.postGuiEvent(ge);
//    }

}
