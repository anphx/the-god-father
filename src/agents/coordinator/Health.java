package agents.coordinator;


import java.net.InetSocketAddress;
import java.net.Socket;


public class Health {
    static double RTT;
    static int health;
    private static Health single_instance = null;

    private Health() {
        RTT = 2000;
        health = 100;
    }

    public static Health getInstance() {
        if (single_instance == null)
            single_instance = new Health();

        return single_instance;
    }

    public static void calcHealth() {
        double alpha = 0.1;
        double sampleRTT = getRTT();
        double newRTT = (alpha * sampleRTT) + ((1 - alpha) * RTT);
        RTT = newRTT;
//        System.out.println("RTT = " + RTT);
        // Assume the range in which server is responding: 10-50ms
        health = (int) (newRTT / (50-10));
    }

    public static long getRTT() {
        try {
            Socket sock = new Socket();
            Long start = System.currentTimeMillis();
            sock.connect(new InetSocketAddress("Agent-classic-balancer-public-890787002.eu-west-3.elb.amazonaws.com", 80), 2000);
            Long end = System.currentTimeMillis();
            Long duration = end - start;
            sock.close();
            return duration;

        } catch (Exception e) {
            return 2000;
        }
    }

    public int getHealth() {
        calcHealth();
        return (100 - health);
    }
}