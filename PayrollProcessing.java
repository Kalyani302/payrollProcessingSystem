import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

public class PayrollProcessing {
    private final Map<String, Employee> employees = new HashMap<>();
    private final List<PayrollEvent> events = new ArrayList<>();

    public void processFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void processLine(String line) throws ParseException {
        String[] parts = line.split(",");
        if (parts.length < 6) return;

        String eventType = List.of("ONBOARD", "EXIT").contains(parts[5].trim()) ? parts[5].trim() : parts[2].trim();
        String empId = parts[1].trim();

        switch (eventType) {
            case "ONBOARD" -> {
                Employee employee = new Employee(empId, parts[2].trim(), parts[3].trim(), parts[4].trim(), 0.0);

                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                employee.setOnboardDate(sdf.parse(parts[6].trim()));
                employee.setEvent("ONBOARD");
                employees.put(empId, employee);
            }
            case "SALARY" -> {
                if (employees.containsKey(empId)) {
                    double value = Double.parseDouble(parts[3].trim());
                    System.out.println("salary = " + value);
                    Employee employee = employees.get(empId);
                    employee.addSalary(value);
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                    employee.setSalaryDate(sdf.parse(parts[4].trim()));
                    employee.salary = value;
                    employees.put(empId, employee);
                }
            }
            case "BONUS" -> {
                if (employees.containsKey(empId)) {
                    double value = Double.parseDouble(parts[3].trim());
                    Employee employee = employees.get(empId);
                    employee.bonus = value;
                    employee.totalBonus = employee.totalBonus + value;
                    employees.put(empId, employee);
                }
            }
            case "REIMBURSEMENT" -> {
                if (employees.containsKey(empId)) {
                    double value = Double.parseDouble(parts[3].trim());
                    Employee employee = employees.get(empId);
                    employee.reimbursement = value;
                    employee.totalReimbursement = employee.totalReimbursement + value;
                    employees.put(empId, employee);
                }
            }
            case "EXIT" -> {
                if (employees.containsKey(empId)) {
                    Employee employee = employees.get(empId);
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                    employee.setExitDate(sdf.parse(parts[3].trim()));
                    employee.setEvent("EXIT");
                    employees.put(empId, employee);
                }
            }
        }
    }

    public void getTotalEmployeeCount() {
        System.out.println("\n\nTotal Employees: " + employees.size());
    }

    public void generateMonthlyReports(String eventType) {
        Map<Month, List<Employee>> employeeMap = new HashMap<>();
        employees.forEach((k, v) -> {
            if (v.getEvent().equalsIgnoreCase(eventType)) {
                List<Employee> employeeList = employeeMap.get(v.getOnboardDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate().getMonth());
                if (isNull(employeeList) || employeeList.isEmpty()) {
                    employeeList = new ArrayList<>();
                }
                employeeList.add(v);
                employeeMap.put(v.getOnboardDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate().getMonth(), employeeList);
            }
        });
        System.out.println("\n\nMonthly Employee Report: \n" + employeeMap);
    }

    public void generateMonthlyReports() {
        Map<Month, List<Employee>> employeeMap = new HashMap<>();
        employees.forEach((k, v) -> {
            List<Employee> employeeList = employeeMap.get(v.getOnboardDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate().getMonth());

            if (isNull(employeeList) || employeeList.isEmpty()) {
                employeeList = new ArrayList<>();
            }
            if (!v.getEvent().equalsIgnoreCase("EXIT")) {
                employeeList.add(v);
                employeeMap.put(v.getOnboardDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate().getMonth(), employeeList);
            }
        });
        System.out.println("\n\nMonthly Employee Report: \n");

        employeeMap.forEach((k, v) -> {
            System.out.println(k + " " + v.stream().mapToDouble(it -> it.totalSalary).sum() + " " + v.size());
        });
    }

    public void generateEmployeeReports() {
        System.out.println("\n\nEmployee Report: \n");

        employees.forEach((k, v) ->
                System.out.println(k + " " + v.firstName + " " + v.lastName + " " + v.totalSalary + v.totalBonus + v.totalReimbursement));
    }

    public void generateMonthlyEmployeeReports() {
        Map<Month, List<Employee>> employeeMap = new HashMap<>();
        employees.forEach((k, v) -> {
            List<Employee> employeeList = employeeMap.get(v.getOnboardDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate().getMonth());

            if (isNull(employeeList) || employeeList.isEmpty()) {
                employeeList = new ArrayList<>();
            }
            if (!v.getEvent().equalsIgnoreCase("EXIT")) {
                employeeList.add(v);
                employeeMap.put(v.getOnboardDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate().getMonth(), employeeList);
            }
        });
        System.out.println("\n\nMonthly Employee Report: \n");

        employeeMap.forEach((k, v) ->
                System.out.println(k + " " + v.stream().mapToDouble(it -> (it.salary + it.reimbursement + it.bonus)).sum() + " " + v.size()));
    }
}
