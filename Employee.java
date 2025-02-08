import java.time.ZoneId;
import java.util.Date;

class Employee {
    String empId, firstName, lastName, designation, event;
    Date onboardDate, exitDate, salaryDate;
    double totalSalary, salary, totalBonus, bonus, totalReimbursement, reimbursement;

    public Employee(String empId, String firstName, String lastName, String designation, double salary) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.designation = designation;
        this.totalSalary = 0.0;
        this.salary = salary;
    }

    public void addSalary(double amount) {
        this.totalSalary += amount;
    }

    public Date getOnboardDate() {
        return onboardDate;
    }

    public Date getExitDate() {
        return exitDate;
    }

    public void setOnboardDate(Date onboardDate) {
        this.onboardDate = onboardDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Date getSalaryDate() {
        return salaryDate;
    }

    public void setSalaryDate(Date salaryDate) {
        this.salaryDate = salaryDate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId='" + empId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", designation='" + designation + '\'' +
                ", onboardDate=" + onboardDate +
                ", exitDate=" + exitDate +
                ", totalSalary=" + totalSalary +
                ", event='" + event + '\'' +
                '}';
    }

//    public String monthlySalaryReport(){
////        Month, Total Salary, Total employees
//
//        return salaryDate.toInstant()
//                .atZone(ZoneId.systemDefault())
//                .toLocalDate().getMonth() +
//    }
}