package haven;

import beetbox.Engine;
import java.util.Arrays;

public class BeetBox {
    static public void event(String name, Object ... args) {
        if (haven.Config.msglog) {
            System.err.printf("event %s %s\n", name, Arrays.toString(args));
        }
        Engine.inst().enqueEvent(name, args);
    }
    
    static public void gobevent(String name, Object ... args) {
        Gob gob = (Gob)args[0];
        if (gob.isplayer()) {
            name = "Player" + name;
        } else {
            name = "Gob" + name;
        }
        event(name, args);
    }
}
