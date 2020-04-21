import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class SecondLab {
    public static void main(String[] args) throws IOException {
        double price;
        int traffic = 0;
        ArrayList<Integer> traffics = new ArrayList<>();
        ArrayList<Integer> times = new ArrayList<>();
        String csvFile = args[0];
        BufferedReader br;
        String line;
        br = new BufferedReader(new FileReader(csvFile));
        while ((line = br.readLine()) != null) {
            String[] info = line.split(",");
            if (info.length > 1) {
                if (info[3].equals("77.74.181.52") || info[4].equals("77.74.181.52")) {
                    traffic += Integer.parseInt(info[12]);
                    traffics.add(traffic);
                    StringBuilder timestring = new StringBuilder(info[1]);
                    timestring.delete(0, timestring.indexOf(" ") + 1);
                    times.add(LocalTime.parse(timestring).toSecondOfDay()-43200);
                }
            }
        }
        int[] TIMES = new int[times.size()];
        for (int i = 0; i < times.size(); i++) TIMES[i] = times.get(i);
        int[] TRAFFICS = new int[traffics.size()];
        for (int i = 0; i < traffics.size(); i++) TRAFFICS[i] = traffics.get(i);
        price = Price(traffic);
        System.out.println("Price: " + price + "R.");
        new Draw(TIMES, TRAFFICS);
    }

    public static double Price(int trafficCount) {
        double price = 1.5 * (double) trafficCount / Math.pow(2.0, 10.0);
        price *= 100;
        price = Math.round(price) / 100.0;
        return price;
    }
}

class Draw extends javax.swing.JFrame {

    private int[] x;
    private int[] y;
    private int[] changedY;
    private Dimension size = new Dimension(1000, 700);
    private Dimension startPointXoY = new Dimension(50, 670);

    public Draw(int[] times, int[] trafficArr) {
        this.x = times;
        this.y = trafficArr;
        changedY = new int[y.length];
        arrForGraphic();
        init();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, size.width, size.height);

        g.setColor(Color.WHITE);

        LocalTime timemark = LocalTime.parse("12:00:00");

        for (int i = 1; i <= 18; i++) {
            if (i % 3 == 0)
                g.drawString(timemark.plusSeconds((long) i * 100).toString(), startPointXoY.width - 15 + startPointXoY.width * i, startPointXoY.height + (startPointXoY.width - 25));
            g.drawString(String.valueOf(i * 5000), 10, startPointXoY.height - startPointXoY.width * i);
        }

        g.drawLine(startPointXoY.width, startPointXoY.height - 650, startPointXoY.width, startPointXoY.height);
        g.drawLine(startPointXoY.width, startPointXoY.height, startPointXoY.width + 900, startPointXoY.height);
        g.drawPolyline(x, changedY, changedY.length-1);

    }

    private void init() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(size);
        setResizable(false);
        setTitle("График зависимости трафика от времени");
        setVisible(true);
    }

    private void arrForGraphic() {
        for (int i = 0; i < x.length; i++) {
            x[i] /= 2;
            x[i] += startPointXoY.width;
            y[i] /= 100;
            changedY[i] = startPointXoY.height - ((int) y[i]);
        }
    }
}
