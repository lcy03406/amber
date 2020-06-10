package beet

import haven.WItem
import haven.Coord

class Item  {
    public static res(WItem wi) {
        return wi.item.res?.name - 'gfx/invobjs/'
    }
    
    public static iact(WItem wi, String act) {
        wi.item.wdgmsg('iact', wi.c, 0)
        if (act == null)
            return
        FlowerMenu.openselect(act)
    }
    
    public static drop(WItem wi) {
        wi.item.wdgmsg('drop', Coord.z)
    }
}
