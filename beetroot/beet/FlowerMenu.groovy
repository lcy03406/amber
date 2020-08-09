package beet

import java.util.List

class FlowerMenu  {
    private static w;
    public static def waitopen(ms) {
        def args = Event.waitexpect(ms, 'FlowerMenu')
        if (args) {
            w = args?[0]
        }
        return args
    }
    
    public static void select(int idx) {
        w.wdgmsg('cl', idx, 0)
        w = null
    }
    
    public static void select(String name) {
        def petal = w.opts.find({it.name == name})
        w.wdgmsg('cl', petal.num, 0)
        w = null
    }
    
    public static void openselect(name) {
        def (w, options) = Event.expect('FlowerMenu')
        def idx = options.indexOf(name)
        w.wdgmsg('cl', idx, 0)
    }
}
