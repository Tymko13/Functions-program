import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.text.MessageFormat;

public class MainWindow extends JFrame {
    DecartusArea da;
    JLabel function;
    JPanel functionWindow;
    JLabel scale;

    boolean animate = false;

    double sA;
    double sB;
    double sTMin;
    double sTMax;
    double sStep;

    double a;
    double b;
    double tMin;
    double tMax;
    double step;

    /**
     * A main window of this program that display a function
     */
    public MainWindow() {
        this.setTitle("Калькулятор графіків");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        function = new JLabel("x = 6.2(cos(t)−cos(3.1t) / 3.1); y = 6.2(sin(t)−sin(3.1t) / 3.1)");
        function.setHorizontalAlignment(SwingConstants.CENTER);
        function.setFont(new Font("sans-serif", Font.BOLD, 14));
        function.setBorder(new LineBorder(Color.BLACK, 2, true));

        da = new DecartusArea(6.2, 3.1, 0.0, 62.8, 0.1);

        scale = new JLabel("Масштаб: 1:" + Math.round(da.scale / 8 * 100) / 100.0);
        scale.setFont(new Font("sans-serif", Font.BOLD, 14));

        JPanel downPanel = new JPanel();
        downPanel.setLayout(new GridLayout(1,0));
        downPanel.add(scale);
        downPanel.setBorder(new LineBorder(Color.BLACK, 2, true));
        JTextField author = new JTextField("Автор графіка");
        author.setFont(new Font("sans-serif", Font.BOLD, 14));
        downPanel.add(author);

        functionWindow = new JPanel();
        functionWindow.setLayout(new BorderLayout());
        functionWindow.add(da, BorderLayout.CENTER);
        functionWindow.add(function, BorderLayout.NORTH);
        functionWindow.add(downPanel, BorderLayout.SOUTH);

        this.add(functionWindow, BorderLayout.WEST);
        this.add(new Form(this), BorderLayout.EAST);
        this.pack();
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
    }

    /**
     * Returns true if the function has reached it`s destination yet in an animation loop
     */
    boolean animIsOver() {
        if (sA < 0) {
            if (a < da.a) return false;
        } else {
            if (a > da.a) return false;
        }
        if (sB < 0) {
            if (b < da.b) return false;
        } else {
            if (b > da.b) return false;
        }
        if (sTMin < 0) {
            if (tMin < da.tMin) return false;
        } else {
            if (tMin > da.tMin) return false;
        }
        if (sTMax < 0) {
            if (tMax < da.tMax) return false;
        } else {
            if (tMax > da.tMax) return false;
        }
        if (sStep < 0) {
            return !(step < da.step);
        } else {
            return !(step > da.step);
        }
    }

    /**
     * Updates function and repaints its panel, scale panel and name panel
     * @param a First parameter of a function
     * @param b Second parameter of a function
     * @param tMin Begging of a diapason in which to calculate function
     * @param tMax Ending of a diapason in which to calculate function
     * @param step Determines in what intervals function will be calculated (the smaller - the higher precision)
     */
    public void redraw(double a, double b, double tMin, double tMax, double step) {
        da.setFunction(a, b, tMin, tMax, step);
        functionWindow.repaint();
        function.setText(MessageFormat.format("x = {0}(cos(t)−cos({1}t) / {1}); y = {0}(sin(t)−sin({1}t) / {1})", a, b));
        scale.setText("Масштаб: 1:" + Math.round(da.scale / 8 * 100) / 100.0);
    }

    /**
     * Starts an animation of a function from its current state to a new state, specified by parameters of this method
     * @param a First parameter of a function
     * @param b Second parameter of a function
     * @param tMin Begging of a diapason in which to calculate function
     * @param tMax Ending of a diapason in which to calculate function
     * @param step Determines in what intervals function will be calculated (the smaller - the higher precision)
     */
    public void prepareAnimation(double a, double b, double tMin, double tMax, double step, double time) {
        if (b == da.b && tMin == da.tMin && tMax == da.tMax && step == da.step) {
            redraw(a, b, tMin, tMax, step);
            return;
        }
        this.a = a;
        this.b = b;
        this.tMin = tMin;
        this.tMax = tMax;
        this.step = step;

        time *= 60;
        sA = (a - da.a) / time;
        sB = (b - da.b) / time;
        sTMin = (tMin - da.tMin) / time;
        sTMax = (tMax - da.tMax) / time;
        sStep = (step - da.step) / time;

        this.animate = true;
    }
}
