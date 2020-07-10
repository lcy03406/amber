package beet

import static haven.MCache.tilesz
import haven.Coord
import haven.Coord2d

class Search  {
    
    private static mv() {
        return BeetBox.world.gui.map
    }
    
    private static oc() {
        return BeetBox.world.gui.map.glob.oc
    }
    
    private static pl() {
        return mv().player()
    }
    
    public static search(f) {
        def l = []
        def oc = oc()
        synchronized (oc) {
            for (gob in oc) {
                if (f(gob)) {
                    l.add(gob)
                }
            }
        }
        return l
    }
    
    public static match(name) {
        return search({gob -> gob.getres()?.name ==~ name})
    }
    
    public static matchArea(name, area) {
        return search({gob -> gob.getres()?.name ==~ name && area})
    }
}
