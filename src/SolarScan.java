public class SolarScan {
    private final int code_lines;
    private final int total;
    int critical;
    private final int high;
    private final int medium;
    private final int low;
    int sast_score;
    private final int datetime;
    private final int scan;

    final int release_type;

    public SolarScan(int code_lines, int total, int critical, int high, int medium, int low, int sast_score, int datetime, int scan, int release_type) {
        this.code_lines = code_lines;
        this.total = total;
        this.critical = critical;
        this.high = high;
        this.medium = medium;
        this.low = low;
        this.sast_score = sast_score;
        this.datetime = datetime;
        this.scan = scan;
        this.release_type = release_type;
    }

    public int getRiskFactor() {
        return total !=0 ? (critical+high) / total * 100 : 0;
    }

    public int getCritFactor() {
        return total !=0 ? critical / total * 100 : 0;
    }

    public int getTotalDensity() {
        return total != 0 ? code_lines / total : 0;
    }

    public int getCriticalHighDensity() {
        return (critical + high) != 0 ? code_lines / (critical + high) : 0;
    }

    public int getTotal() {
        return total;
    }

    public int getCode_lines() {
        return code_lines;
    }

    public int getRelease_type() {
        return release_type;
    }

    public int getCriticalHigh() {
        return critical + high;
    }

    public int getMediumLow() {
        return medium + low;
    }

}
