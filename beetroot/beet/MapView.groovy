package beet

import static haven.MCache.tilesz
import haven.Coord
import haven.Coord2d

class MapView  {
    
    public static mv() {
        return BeetBox.world.gui.map
    }
    
    public static area() {
        def mv = mv()
        mv.uimsg('sel', 1)
        def (c1, c2) = Event.expect('MapArea')
        mv.uimsg('sel', 0)
        BeetBox.println("Select Area {$c1},{$c2}")
        return new Area(c1, c2)
    }
    
    public static select() {
        def mv = mv()
        //Player.act('inspect')
        def (gob) = Event.expect('MapSelect')
        //mv.wdgmsg('click', Coord.z, Coord.z, 2, 0)
        BeetBox.println("Select Object {$gob.id}")
        return gob
    }
    
    public static tileres(c) {
        def map = BeetBox.world.ui.sess.glob.map
        int t = map.gettile(c)
        def res = map.tilesetr(t)
        return res?.name - 'gfx/tiles/'
    }
    
    public static tilecenter(tc) {
        return tc.mul(1024).add(512,512);
    }
    
    public static click(mc)  {
        click(mc, 1, 0)
    }

    public static click(mc, clickb, modflags) {
        def mv = mv()
        mv.wdgmsg('click', Coord.z, mc, clickb, modflags)
    }

    public static tileclick(c) {
        tileclick(c, 1, 0)
    }
    
    public static tileclick(tc, clickb, modflags) {
        def c = tilecenter(tc)
        click(c, clickb, modflags)
    }
        
    public static place(c, a) {
        wdgmsg("place", (int)Math.round(a * 32768 / Math.PI), 1, 0);
    }
}
