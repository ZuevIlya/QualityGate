import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int[][] data = new int[10][9];
        int i = 0;
        try(Scanner scanner = new Scanner(new File("/Users/ilyazuev/IdeaProjects/QualityGateTest/src/Data.csv"))){
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try (Scanner rowScanner = new Scanner(line)) {
                    rowScanner.useDelimiter(";");
                    while (rowScanner.hasNext()) {
                        System.out.print(rowScanner.next() + " ");
                    }
                }
                try (Scanner rowScanner2 = new Scanner(line)) {
                    rowScanner2.useDelimiter(";");
                    for (int j=0;j<9;j++) {
                        data[i][j] = Integer.parseInt(rowScanner2.next());
                    }
                }
                System.out.println();
                i = i + 1;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        SolarScan[] solarScans = new SolarScan[10];
        for (i=0;i< solarScans.length;i++) {
            solarScans[i] = new SolarScan(
                    data[i][0], // code_lines
                    data[i][1], // total
                    data[i][2], // critical
                    data[i][3], // high
                    data[i][4], // medium
                    data[i][5], // low
                    data[i][6], // sast_score
                    data[i][7], // datetime
                    data[i][8]); // scan
        }
        getResult(solarScans);
    }

    public static void getResult(SolarScan[] solarScans) {
        int F1 = getSASTScoreFlag(solarScans[0]) ? 1 : 0;
        int F2 = getRiskFactorFlag(solarScans[0], solarScans[1], solarScans[4]) ? 1 : 0;
        int F3 = getCritFactorFlag(solarScans[0], solarScans[1], solarScans[4]) ? 1 : 0;
        int F5 = getCritFreqFlag(solarScans[0], solarScans[1]) ? 1 : 0;
        int F6 = getAbsCritFlag(solarScans[0], solarScans[1]) ? 1 : 0;
        int F7 = getAbsCritHighFlag(solarScans[0], solarScans[1]) ? 1 : 0;

        double expression = getExpression(F2, F3, F5, F6, F7);
        System.out.println(getHardState(expression));
    }

    public static boolean getSASTScoreFlag(SolarScan self) {
        return self.sast_score > 4.0;
    }

    public static boolean getRiskFactorFlag(SolarScan self, SolarScan other1, SolarScan other5) {
        return ((self.getRiskFactor() - other1.getRiskFactor()) > 0) && ((self.getRiskFactor() - other5.getRiskFactor()) > 0.05);
    }

    public static boolean getCritFactorFlag(SolarScan self, SolarScan other1, SolarScan other5) {
        return ((self.getCritFactor() - other1.getCritFactor()) > 0) && ((self.getCritFactor() - other5.getCritFactor()) > 0.03);
    }

    public static boolean getFrequencyFlag() {
        // useless
        return false;
    }

    public static boolean getCritFreqFlag(SolarScan self, SolarScan other) {
        return (self.getCriticalHighDensity() - other.getCriticalHighDensity()) > 0;
    }

    public static boolean getAbsCritFlag(SolarScan self, SolarScan other) {
        return (self.critical - other.critical) > 0;
    }

    public static boolean getAbsCritHighFlag(SolarScan self, SolarScan other) {
        return (self.getCriticalHigh() - other.getCriticalHigh()) > 0;
    }

    public static boolean getAbsMediumLow(SolarScan self, SolarScan other10) {
        return (self.getMediumLow() - other10.getMediumLow()) > 0;
    }

    public static double getExpression(int F2, int F3, int F5, int F6, int F7) {
        return F2 * 0.2 + F3 * 0.2 + F5 * 0.1 + F6 * 0.3 + F7 * 0.2;
    }

    public static boolean getHardState(double expression) {
        return (expression - 0.5) > 0;
    }


}