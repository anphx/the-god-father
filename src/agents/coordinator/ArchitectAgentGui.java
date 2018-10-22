package agents.coordinator;

import agents.utils.GraphPanel;
import agents.utils.Helpers;
import jade.gui.GuiEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

public class ArchitectAgentGui {

    //    public static Boolean isDebugging = false;
    Vector<Integer> serverStatus = new Vector<Integer>();
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
    private JTextArea txtHistory;
    private GraphPanel graph;
    private JFrame frame;


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
            updateHistory("Started " + numbOfAgents + " agents with 1req/" + tickerPeriod.getText() + "ms - fibo: " + fbNr.getText());
        });

        terminateAllAgentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiEvent ge = new GuiEvent(this, Helpers.TERMINATE_AGENTS);
                myAgent.postGuiEvent(ge);
                updateHistory("Terminated all current agents!");
            }
        });
//        isDebug.addItemListener(e -> isDebugging = (e.getStateChange() == ItemEvent.SELECTED));
    }

    protected void frameInit() {
        frame = new JFrame("a.p. The-God-Father Agency");
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout(2, 2));

        graph = new GraphPanel(serverStatus);
        graph.setPreferredSize(new Dimension(400, 400));

//        historyPanel = new JScrollPane();
//        historyPanel.setPreferredSize(new Dimension(200, 400));
//        txtHistory = new JTextArea();
//        historyPanel.add(txtHistory);

        frame.setContentPane(content);
        content.add(this.appPanel, BorderLayout.PAGE_START);
        content.add(graph, BorderLayout.PAGE_END);
//        content.add(historyPanel, BorderLayout.LINE_END);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void setAgent(ArchitectAgent a) {
        myAgent = a;
    }

    public void updateHistory(String text) {
        txtHistory.append(text + "\n");
    }

    public void updateHealthStatus(int health) {
        serverStatus.add(health);
        int size = serverStatus.size();

        // Keep up to 50 latest values of health data
        if (size >= 50) {
            Integer[] temp = new Integer[]{
                    serverStatus.elementAt(size - 3),
                    serverStatus.elementAt(size - 2),
                    serverStatus.elementAt(size - 1)
            };
            serverStatus = new Vector<>(Arrays.asList(temp));
        }
        graph = new GraphPanel(serverStatus);
        graph.setPreferredSize(new Dimension(400, 400));
        frame.getContentPane().add(graph, BorderLayout.PAGE_END);
        frame.pack();
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        // Fire gui event in agent
//        // GuiEvent ge = new GuiEvent(this, NEW_ACCOUNT);
//        // myAgent.postGuiEvent(ge);
//    }

}
