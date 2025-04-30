import java.util.ArrayList;

public class IncidentDB {
    private static ArrayList<Incident> incidents = new ArrayList<>();

    public static ArrayList<Incident> findByUser(String username) {
        ArrayList<Incident> userIncidents = new ArrayList<>();
        for (Incident incident : incidents) {
            if (incident.getSender().getUsername().equals(username)) {
                userIncidents.add(incident);
            }
        }
        return userIncidents;
    }

    public static void save(Incident incident) {
        // Set the incident ID based on the current size of the ArrayList
        incident.setId(incidents.size());
        incidents.add(incident);
    }
}