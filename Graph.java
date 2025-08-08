import java.util.HashMap;
import java.util.Map;

public class Graph {
    private Map<String, Person> people = new HashMap<>();

    public void addPerson(Person p) {
        people.put(p.id, p);
    }

    public void sendMessage(Message m) {
        if (people.containsKey(m.receiverId)) {
            Person receiver = people.get(m.receiverId);
            receiver.receiveMessage(m);
        } else {
            System.out.println("Receiver not found.");
        }
    }
}
