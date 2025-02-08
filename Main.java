import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        PayrollProcessing processor = new PayrollProcessing();
        try {
            processor.processFile("D:\\New folder\\Hello\\src\\employee_details.txt");
            processor.getTotalEmployeeCount();
            processor.generateMonthlyReports();
            processor.generateMonthlyReports("ONBOARD ");
            processor.generateMonthlyReports("EXIT");
            processor.generateEmployeeReports();
            processor.generateMonthlyEmployeeReports();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

}
