
package railwayreservation;

public class Record {

    private final int id;

    private String name;
    private String contactNumber;
    private String category;
    private String status;

    public Record(int id, String name, String contact,
                  String category, String status) {

        this.id = id;
        this.name = name;
        this.contactNumber = contact;
        this.category = category;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String v) {
        name = v;
    }

    public void setContactNumber(String v) {
        contactNumber = v;
    }

    public void setCategory(String v) {
        category = v;
    }

    public void setStatus(String v) {
        status = v;
    }

    public void displayRecord() {

        System.out.println("Record ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Contact Number: " + contactNumber);
        System.out.println("Category: " + category);
        System.out.println("Status: " + status);
    }
}
