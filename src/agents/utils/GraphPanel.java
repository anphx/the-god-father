package agents.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Arrays;
import java.util.Vector;

public class GraphPanel extends JPanel {

    final int PAD = 20;
    private Integer[] data;
    private Graphics gr;

    public GraphPanel(Vector<Integer> vect) {
        data = vect.toArray(new Integer[vect.size()]);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Integer[] data = {
                21, 14, 18, 03, 86, 88, 74, 87, 54, 77,
                61, 55, 48, 60, 49, 36, 38, 27, 20, 18
        };

        Vector<Integer> vect = new Vector<Integer>(Arrays.asList(data));
        f.add(new GraphPanel(vect));
        f.setSize(400, 400);
        f.setLocation(200, 200);
        f.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        // Draw ordinate.
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h - PAD));
        // Draw abcissa.
        g2.draw(new Line2D.Double(PAD, h - PAD, w - PAD, h - PAD));
        // Draw labels.
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);
        float sh = lm.getAscent() + lm.getDescent();

        // Ordinate label.
        int[] yAxis = new int[]{0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};
//        SortedSet<Integer> unique = new TreeSet<Integer>(Arrays.asList(yAxis));


//        unique.addAll(Arrays.asList(data));
//        String s = unique.toString();

        float sy = PAD + ((h - 2 * PAD) - yAxis.length * sh) / 2 + lm.getAscent();
//        Iterator<Integer> iter = ((TreeSet<Integer>) unique).descendingIterator();
//        while (iter.hasNext()) {
        for (int iter = yAxis.length - 1; iter >= 0; iter--) {
//            String letter = iter.next().toString();
            String letter = String.valueOf(yAxis[iter]);
            float sw = (float) font.getStringBounds(letter, frc).getWidth();
            float sx = (PAD - sw) / 2;
            g2.drawString(letter, sx, sy);
            sy += sh;
        }

//        for(int i = 0; i < iter.size(); i++) {
////            String letter = String.valueOf(s.charAt(i));
//            String letter = unique.[i].toString;
//
//            float sw = (float)font.getStringBounds(letter, frc).getWidth();
//            float sx = (PAD - sw)/2;
//            g2.drawString(letter, sx, sy);
//            sy += sh;
//        }
        // Abcissa label.
        String s = "timestamp";
        sy = h - PAD + (PAD - sh) / 2 + lm.getAscent();
        float sw = (float) font.getStringBounds(s, frc).getWidth();
        float sx = (w - sw) / 2;
        g2.drawString(s, sx, sy);
        // Draw lines.
        double xInc = (double) (w - 2 * PAD) / (data.length - 1);
        double scale = (double) (h - 2 * PAD) / getMax();
        g2.setPaint(Color.green.darker());
        for (int i = 0; i < data.length - 1; i++) {
            double x1 = PAD + i * xInc;
            double y1 = h - PAD - scale * data[i];
            double x2 = PAD + (i + 1) * xInc;
            double y2 = h - PAD - scale * data[i + 1];
            g2.draw(new Line2D.Double(x1, y1, x2, y2));
        }
        // Mark data points.
        g2.setPaint(Color.red);
        for (int i = 0; i < data.length; i++) {
            double x = PAD + i * xInc;
            double y = h - PAD - scale * data[i];
            g2.fill(new Ellipse2D.Double(x - 2, y - 2, 4, 4));
        }
        gr = g2;
    }

    private int getMax() {
        int max = -Integer.MAX_VALUE;
        for (int i = 0; i < data.length; i++) {
            if (data[i] > max)
                max = data[i];
        }
        return max;
    }

    public void refresh(Vector<Integer> vect) {
        data = vect.toArray(new Integer[vect.size()]);
        this.paintComponent(gr);
    }
}
