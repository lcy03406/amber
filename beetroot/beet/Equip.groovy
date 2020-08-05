package beet

import haven.Loading
import haven.WItem
import haven.Widget

class Equip  {
    public static def eq() {
        return BeetBox.world.gui.equipory
    }
    
    public static def slot(i) {
        return eq().quickslots[i];
    }
    
    public static def drop(i) {
        return eq().wdgmsg('drop', i);
    }
}
