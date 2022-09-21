package view.tm;

public class EmployeeTm {
    private String employeeId;
    private String employeeName;
    private String employeeAddress;
    private String nic;
    private String birthday;
    private String contact;
    private String post;

    public EmployeeTm() {
    }

    public EmployeeTm(String employeeId, String employeeName, String employeeAddress, String nic, String birthday, String contact, String post) {
        this.setEmployeeId(employeeId);
        this.setEmployeeName(employeeName);
        this.setEmployeeAddress(employeeAddress);
        this.setNic(nic);
        this.setBirthday(birthday);
        this.setContact(contact);
        this.setPost(post);
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "AddNewEmployeeTm{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", employeeAddress='" + employeeAddress + '\'' +
                ", nic='" + nic + '\'' +
                ", birthday='" + birthday + '\'' +
                ", contact='" + contact + '\'' +
                ", post='" + post + '\'' +
                '}';
    }
}
