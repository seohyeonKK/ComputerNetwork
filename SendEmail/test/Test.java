package SendEmail.test;

import SendEmail.Receiver;

import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        String receiverID = "     asdff@nwwenf.com     ,      woe@fwjeio.com";
        System.out.println(Arrays.stream(receiverID.split(",")).toList());

        List<String> receivers = Arrays.stream(receiverID.split(",")).toList();

        for(String r : receivers){
            Receiver receiver = new Receiver();
            receiver.setId(r.trim());
        }
    }
}
