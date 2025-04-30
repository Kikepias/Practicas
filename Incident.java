public class Incident {
    // Constants for incident status
    public static final int STATUS_UNSOLVED = 0;
    public static final int STATUS_PENDING = 1;
    public static final int STATUS_SOLVED = 2;

    // Private attributes
    private int id;
    private String description;
    private String computer;
    private int status;
    private User sender;
    private String resolution;

    // Constructor
    public Incident(int id, String description, String computer, String resolution, int status, User sender) {
        this.id = id;
        this.description = description;
        this.computer = computer;
        this.resolution = resolution;
        this.status = status;
        this.sender = sender;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        updateLastModified();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        updateLastModified();
    }

    public String getComputer() {
        return computer;
    }

    public void setComputer(String computer) {
        this.computer = computer;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    // Method to get status as string
    public String getStatusString() {
        switch(status) {
            case STATUS_UNSOLVED:
                return "Unsolved";
            case STATUS_PENDING:
                return "Pending";
            case STATUS_SOLVED:
                return "Solved";
            default:
                return "Unknown";
        }
    }
}