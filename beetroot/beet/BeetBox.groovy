package beet

import java.awt.Color;

class BeetBox  {
    private static final Color logColor = new Color(0xB404AE);
    private static final int waitTime = 10000;
    def static world;
    
    static void init(w) {
        world = w;
        assert(world != null)
        assert(world.gui != null)
    }

    static void println(String x) {
        synchronized (world.gui) {
            world.gui.msg(x, logColor);
        }
    }
}
