import java.util.ArrayList;
import java.util.List;

public class Person {
    public String id;
    public List<Message> inbox;
    
    public Person(String id) {
        this.id = id;
        this.inbox = new ArrayList<>();
    }
    
    public void receiveMessage(Message msg) {
        inbox.add(msg);
        System.out.println("[" + id + "] received message from " + msg.senderId);
    }
}
