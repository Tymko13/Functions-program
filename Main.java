public class Main {
    public static void main(String[] args) {

        MainWindow test = new MainWindow();
        test.setVisible(true);
        while (true) { //Animation loop
            try {
                Thread.sleep(17); // 1000 / 60 = 16.6
            } catch (InterruptedException ignored) {
            }
            if (test.animate) {
                test.da.changeFunction(test.sA, test.sB, test.sTMin, test.sTMax, test.sStep);
                test.functionWindow.repaint();
                if (test.animIsOver()) {
                    test.redraw(test.a, test.b, test.tMin, test.tMax, test.step);
                    test.animate = false;
                }
            }
        }
//        for(double i = 0; i < 10; i+=0.01){
//            redraw(i);
//            try {
//                Thread.sleep(20);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        double i = 0;
//        double speed;
//        int sign = 1;
//        while (true) {
//            redraw(i);
//            try {
//                Thread.sleep(16);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            if (i > 1 || i < 0) {
//                sign = -sign;
//                i = Math.round(i);
//            }
//            speed = sign * (0.15 - i * 0.145);
//            i += speed;
//        }
    }

}
