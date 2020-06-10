package beet

import java.util.List

class FlowerMenu  {
    public static def waitopen() {
        return Event.expect('FlowerMenu')
    }
    
    public static void select(w, int idx) {
        w.wdgmsg('cl', idx, 0)
    }
    
    public static void select(w, String name) {
        def petal = w.opts.find({it.name == name})
        w.wdgmsg('cl', petal.num, 0)
    }
    
    public static void openselect(name) {
        def (w, options) = Event.expect('FlowerMenu')
        def idx = options.indexOf(name)
        w.wdgmsg('cl', idx, 0)
    }
}
