package view.tm;

public class CustomerTm {
    private String guestId;
    private String guestName;
    private String guestAddress;
    private String nic;
    private String contact;

    public CustomerTm() {
    }

    public CustomerTm(String guestId, String guestName, String guestAddress, String nic, String contact) {
        this.setGuestId(guestId);
        this.setGuestName(guestName);
        this.setGuestAddress(guestAddress);
        this.setNic(nic);
        this.setContact(contact);
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestAddress() {
        return guestAddress;
    }

    public void setGuestAddress(String guestAddress) {
        this.guestAddress = guestAddress;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "AddNewGuestTm{" +
                "guestId='" + guestId + '\'' +
                ", guestName='" + guestName + '\'' +
                ", guestAddress='" + guestAddress + '\'' +
                ", nic='" + nic + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
