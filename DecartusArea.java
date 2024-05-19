import javax.swing.*;
import java.awt.*;

public class DecartusArea extends JPanel {
    double a;
    double b;
    double tMin;
    double tMax;
    double step;
    double scale;

    /**
     * A JPanel that can display functions
     * @param a First parameter of a function
     * @param b Second parameter of a function
     * @param tMin Begging of a diapason in which to calculate function
     * @param tMax Ending of a diapason in which to calculate function
     * @param step Determines in what intervals function will be calculated (the smaller - the higher precision)
     */
    public DecartusArea(double a, double b, double tMin, double tMax, double step) {
        this.setPreferredSize(new Dimension(500, 500));
        setFunction(a, b, tMin, tMax, step);
    }

    /**
     * Sets parameters of a function
     * @param a First parameter of a function
     * @param b Second parameter of a function
     * @param tMin Begging of a diapason in which to calculate function
     * @param tMax Ending of a diapason in which to calculate function
     * @param step Determines in what intervals function will be calculated (the smaller - the higher precision)
     */
    void setFunction(double a, double b, double tMin, double tMax, double step) {
        this.a = a;
        this.b = b;
        this.tMin = tMin;
        this.tMax = tMax;
        this.step = step;
        getScale();
    }

    /**
     * Changes parameters of a function by adding some value
     * @param a First parameter of a function
     * @param b Second parameter of a function
     * @param tMin Begging of a diapason in which to calculate function
     * @param tMax Ending of a diapason in which to calculate function
     * @param step Determines in what intervals function will be calculated (the smaller - the higher precision)
     */
    void changeFunction(double a, double b, double tMin, double tMax, double step) {
        this.a += a;
        this.b += b;
        this.tMin += tMin;
        this.tMax += tMax;
        this.step += step;
        getScale();
    }

    /**
     * Calculates scale of a function to the area in which it is drawn
     */
    private void getScale() {
        double max = 0;
        double temp;
        for (double i = tMin; i < tMax; i += step) {
            temp = a * (Math.cos(i) - Math.cos(i * b) / b);
            if (temp < 0) temp = -temp;
            if (temp > max) {
                max = temp;
            }
            temp = a * (Math.sin(i) - Math.sin(i * b) / b);
            if (temp < 0) temp = -temp;
            if (temp > max) {
                max = temp;
            }
        }
        scale = max;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < 46; i++) {
            g2D.drawLine(0, 25 * i, 500, 25 * i);
        }
        for (int j = 0; j < 46; j++) {
            g2D.drawLine(25 * j, 0, 25 * j, 500);
        }

        g2D.setColor(Color.BLACK);
        g2D.setStroke(new BasicStroke(2));
        g2D.drawLine(250, 10, 250, 490);
        g2D.drawLine(245, 20, 250, 10);
        g2D.drawLine(255, 20, 250, 10);
        g2D.drawLine(10, 250, 490, 250);
        g2D.drawLine(480, 245, 490, 250);
        g2D.drawLine(480, 255, 490, 250);
        if(a==0 || b==1) return;

        double aToScale =  a * (200 / scale);
        g2D.setStroke(new BasicStroke(5));
        for (double i = tMin; i <= tMax; i += step) {
            int startX = (int) Math.round(aToScale * (Math.cos(i) - Math.cos(i * b) / b));
            int startY = (int) Math.round(aToScale * (Math.sin(i) - Math.sin(i * b) / b));
            int endX = (int) Math.round(aToScale * (Math.cos(i + step) - Math.cos((i + step) * b) / b));
            int endY = (int) Math.round(aToScale * (Math.sin(i + step) - Math.sin((i + step) * b) / b));
            g2D.drawLine(startX + 250, startY + 250, endX + 250, endY + 250);
        }
    }
}
