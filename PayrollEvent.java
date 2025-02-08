import java.util.Date;

class PayrollEvent {
    String empId, event;
    double value;
    Date eventDate;
    String notes;

    public PayrollEvent(String empId, String event, double value, Date eventDate, String notes) {
        this.empId = empId;
        this.event = event;
        this.value = value;
        this.eventDate = eventDate;
        this.notes = notes;
    }
}