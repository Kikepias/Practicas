import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class IncidentManager {
    private HashMap<String, User> users;
    private ArrayList<Incident> incidents;
    private User currentUser;
    private Scanner scanner;
    private int nextIncidentId;

    public IncidentManager() {
        users = new HashMap<>();
        incidents = new ArrayList<>();
        scanner = new Scanner(System.in);
        nextIncidentId = 1;
        // Add some demo users
        users.put("admin", new User("admin", "admin123", "Admin", "User", User.ADMIN));
        users.put("user1", new User("user1", "user123", "Normal", "User", User.USER));
    }

    public void start() {
        while (true) {
            if (currentUser == null) {
                if (!login()) {
                    System.out.println("Login failed. Please try again.");
                    continue;
                }
            }

            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createIncident();
                    break;
                case 2:
                    viewIncidents();
                    break;
                case 3:
                    if (currentUser.getType() == User.ADMIN) {
                        manageIncident();
                    } else {
                        System.out.println("Unauthorized access");
                    }
                    break;
                case 4:
                    logout();
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private boolean login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("Welcome, " + user.getName() + "!");
            return true;
        }
        return false;
    }

    private void logout() {
        currentUser = null;
        System.out.println("Logged out successfully");
    }

    private void showMenu() {
        System.out.println("\n=== Incident Management System ===");
        System.out.println("1. Create new incident");
        System.out.println("2. View incidents");
        if (currentUser.getType() == User.ADMIN) {
            System.out.println("3. Manage incidents");
        }
        System.out.println("4. Logout");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private void createIncident() {
        System.out.println("\n=== Create New Incident ===");
        System.out.print("Enter incident description: ");
        String description = scanner.nextLine();

        Incident incident = new Incident(nextIncidentId++, description, currentUser);
        incidents.add(incident);
        System.out.println("Incident created successfully with ID: " + incident.getId());
    }

    private void viewIncidents() {
        System.out.println("\n=== Incidents ===");
        boolean found = false;

        for (Incident incident : incidents) {
            if (currentUser.getType() == User.ADMIN || 
                incident.getReporter().getUsername().equals(currentUser.getUsername())) {
                displayIncident(incident);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No incidents found.");
        }
    }

    private void manageIncident() {
        System.out.print("Enter incident ID to manage: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Incident incident = findIncidentById(id);
        if (incident == null) {
            System.out.println("Incident not found.");
            return;
        }

        System.out.println("\nCurrent status: " + incident.getStatusString());
        System.out.println("1. Mark as In Progress");
        System.out.println("2. Mark as Resolved");
        System.out.println("3. Mark as Closed");
        System.out.println("4. Add Resolution");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                incident.setStatus(Incident.STATUS_IN_PROGRESS);
                break;
            case 2:
                incident.setStatus(Incident.STATUS_RESOLVED);
                break;
            case 3:
                incident.setStatus(Incident.STATUS_CLOSED);
                break;
            case 4:
                System.out.print("Enter resolution: ");
                String resolution = scanner.nextLine();
                incident.setResolution(resolution);
                break;
            default:
                System.out.println("Invalid option");
                return;
        }
        System.out.println("Incident updated successfully");
    }

    private Incident findIncidentById(int id) {
        for (Incident incident : incidents) {
            if (incident.getId() == id) {
                return incident;
            }
        }
        return null;
    }

    private void displayIncident(Incident incident) {
        System.out.println("\nIncident ID: " + incident.getId());
        System.out.println("Description: " + incident.getDescription());
        System.out.println("Status: " + incident.getStatusString());
        System.out.println("Reporter: " + incident.getReporter().getName() + 
                         " " + incident.getReporter().getSurname());
        System.out.println("Created: " + incident.getCreatedAt());
        System.out.println("Last Updated: " + incident.getLastUpdated());
        if (!incident.getResolution().isEmpty()) {
            System.out.println("Resolution: " + incident.getResolution());
        }
    }

    public static void main(String[] args) {
        IncidentManager manager = new IncidentManager();
        manager.start();
    }
}