package beet

import static haven.MCache.tilesz

class Gob  {
    public static click(target) {
        click(target, 1, 0)
    }
    public static click(target, clickb, modflags) {
        click(target, clickb, modflags, 0, 0)
    }
    public static click(target, clickb, modflags, ol, part) {
        def mv = mv()
        def c = target.nc
        mv.wdgmsg("click", target.sc, c, clickb, modflags, 0, (int) target.id, c, ol, part);
    }
}
