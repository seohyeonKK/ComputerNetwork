package SendEmail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Receiver {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    static List<Receiver> parse(String ids){
        List<String> receiverIds = Arrays.stream(ids.split(",")).toList();
        List<Receiver> receivers = new ArrayList<>();

        for(String r : receiverIds){
            Receiver receiver = new Receiver();
            receiver.setId(r.trim());
            receivers.add(receiver);
        }

        return receivers;
    }
}
