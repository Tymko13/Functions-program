import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Form extends JPanel {
    private double spinnerValue = 3.1;

    /**
     * A form with parameters needed to create a function
     * @param mw A JFrame with an area to draw functions
     */
    public Form(MainWindow mw) {
        this.setPreferredSize(new Dimension(150, 500));
        this.setBorder(new LineBorder(Color.BLACK, 2, true));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 16));

        JLabel vars = new JLabel("---Змінні---");
        vars.setFont(new Font("sans-serif", Font.BOLD, 18));
        vars.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(vars);

        JPanel aPanel = new JPanel();
        aPanel.add(new JLabel("a:"));
        JSpinner aSpinner = new JSpinner();
        aSpinner.setModel(new SpinnerNumberModel(6.2, -999, 999, 0.1));

        aPanel.add(aSpinner);
        this.add(aPanel);

        JPanel bPanel = new JPanel();
        bPanel.add(new JLabel("b:"));
        JSpinner bSpinner = new JSpinner();
        bSpinner.setModel(new SpinnerNumberModel(3.1, -999, 999, 0.1));
        bSpinner.addChangeListener(e -> {
            if ((double) bSpinner.getValue() == 0) {
                if (spinnerValue == 0.1) {
                    bSpinner.setValue(-0.1);
                } else if (spinnerValue == -0.1) {
                    bSpinner.setValue(0.1);
                } else {
                    bSpinner.setValue(spinnerValue);
                    JOptionPane.showMessageDialog(null, "Ця змінна не може бути нульовою!");
                }
            }
            spinnerValue = (double) bSpinner.getValue();
        });
        bPanel.add(bSpinner);
        this.add(bPanel);

        JLabel range = new JLabel("---Діапазон t---");
        range.setFont(new Font("sans-serif", Font.BOLD, 18));
        range.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(range);

        JPanel fromPanel = new JPanel();
        fromPanel.add(new JLabel("від"));
        JSpinner fromSpinner = new JSpinner();
        fromSpinner.setModel(new SpinnerNumberModel(0, -100, 100, 3.14));
        fromPanel.add(fromSpinner);
        this.add(fromPanel);

        JPanel toPanel = new JPanel();
        toPanel.add(new JLabel("до"));
        JSpinner toSpinner = new JSpinner();
        toSpinner.setModel(new SpinnerNumberModel(62.8, -100, 100, 3.14));
        toPanel.add(toSpinner);
        this.add(toPanel);

        fromSpinner.addChangeListener(e ->{
            if((double)fromSpinner.getValue() > (double)toSpinner.getValue()){
                toSpinner.setValue(fromSpinner.getValue());
            }
        });
        toSpinner.addChangeListener(e ->{
            if((double)fromSpinner.getValue() > (double)toSpinner.getValue()){
                fromSpinner.setValue(toSpinner.getValue());
            }
        });

        JPanel stepPanel = new JPanel();
        JLabel stepLabel = new JLabel("Крок:");
        stepPanel.add(stepLabel);
        JSpinner stepSpinner = new JSpinner();
        stepSpinner.setModel(new SpinnerNumberModel(0.1, 0.01, 3.14, 0.1));
        stepPanel.add(stepSpinner);
        this.add(stepPanel);

        JButton draw = new JButton("Намалювати");
        draw.setAlignmentX(CENTER_ALIGNMENT);
        this.add(draw);
        draw.addActionListener(e -> {
            mw.redraw((double) aSpinner.getValue(), (double) bSpinner.getValue(), (double) fromSpinner.getValue(), (double) toSpinner.getValue(), (double) stepSpinner.getValue());
        });

        JLabel animation = new JLabel("---Анімація---");
        animation.setFont(new Font("sans-serif", Font.BOLD, 18));
        animation.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(animation);

        JPanel timePanel = new JPanel();
        JLabel timeLabel = new JLabel("Тривалість:");
        timePanel.add(timeLabel);
        JSpinner timeSpinner = new JSpinner();
        timeSpinner.setModel(new SpinnerNumberModel(1, 0.5, 10, 0.5));
        timePanel.add(timeSpinner);
        this.add(timePanel);

        JButton animate = new JButton("Анімувати");
        animate.setAlignmentX(CENTER_ALIGNMENT);
        animate.addActionListener(e -> {
            mw.prepareAnimation((double) aSpinner.getValue(), (double) bSpinner.getValue(), (double) fromSpinner.getValue(), (double) toSpinner.getValue(), (double) stepSpinner.getValue(), (double) timeSpinner.getValue());
        });
        this.add(animate);

        JButton save = new JButton("Зберегти фото");
        save.setAlignmentX(CENTER_ALIGNMENT);
        save.addActionListener(e -> {
            Rectangle image = mw.getBounds();
            BufferedImage bufferedImage = new BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_ARGB);
            mw.paint(bufferedImage.getGraphics());

            File imageFile = new File(System.currentTimeMillis() + "_function.png");
            try {
                ImageIO.write(bufferedImage, "png", imageFile);
                JOptionPane.showMessageDialog(null, "Фото функції успішно збережено!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Виникла помилка під час збереження фото!");
            }
        });
        this.add(save);
    }
}
