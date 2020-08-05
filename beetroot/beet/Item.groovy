package beet

import haven.WItem
import haven.Coord

class Item  {
    public static res(WItem wi) {
        return wi.item.res?.name - 'gfx/invobjs/'
    }
    
    public static click(WItem wi) {
        wi.item.wdgmsg('click', wi.c, 1, 0)
    }
    
    public static iact(WItem wi, String act) {
        wi.item.wdgmsg('iact', wi.c, 0)
        if (act == null)
            return
        FlowerMenu.openselect(act)
    }
    
    public static take(WItem wi) {
        wi.item.wdgmsg('take', Coord.z)
    }
    
    public static drop(WItem wi) {
        wi.item.wdgmsg('drop', Coord.z)
    }
}
