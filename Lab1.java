import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Lab1 {
    private static boolean check(String text) {
        String Text = text.trim();
        return Text.indexOf("\"") == Text.lastIndexOf("\"") && Text.endsWith("\"");
    }
    private static double tarif(double in, double out, int sms) {
        double price = 0.0;
        if (out <= 10.0) price+=out*2;
        else price+=10.0*2;
        price+=in*4;
        if(sms>10) price+=(sms-10)*5;
        return price;
    }
    public static void main(String[] args) throws IOException {
        String csvfilename = args[0];
        double incomingcall=0.0, outgoingcall=0.0;
        int smsnumber=0;
        List<String> csvLines = Files.readAllLines(Paths.get(csvfilename));
        for (String line : csvLines) {
            String[] text = line.split(",");
            ArrayList<String> column = new ArrayList<>();
            for (int i=0; i<text.length; i++) {
                if (check(text[i])) {
                    String last = column.get(column.size() - 1);
                    column.set(column.size() - 1, last + "," + text[i]);
                } else column.add(text[i]);
            }
            if (column.get(1).equals("933156729")) {
                outgoingcall = Double.parseDouble(column.get(3));
                smsnumber = Integer.parseInt(column.get(4));
            }
            if (column.get(2).equals("933156729")) incomingcall = Double.parseDouble(column.get(3));
        }
        double price = tarif(incomingcall, outgoingcall, smsnumber);
        System.out.println(price);
    }
}
