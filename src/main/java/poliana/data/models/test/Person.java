package poliana.data.models.test;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "test")
public class Person {

    @Id
    private String myId;

    public Person(String id) {
        this.myId = id;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }
}