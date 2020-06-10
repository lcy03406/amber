package beet

import haven.Inventory
import haven.Loading
import haven.WItem
import haven.Widget

class Inv  {
    public static Inventory maininv() {
        return BeetBox.world.gui.maininv
    }
    
    public static def matchItem(Inventory inv, res) {
        inv ?= maininv()
        def items = [];
        for (Widget wdg = inv.child; wdg != null; wdg = wdg.next) {
            if (wdg instanceof WItem) {
                WItem witem = ((WItem) wdg);
                try {
                    def name = witem.item.getres().name
                    if (name ==~ res) {
                        items.add(witem);
                    }
                } catch (Loading l) {
                }
            }
        }
        return items;
    }
    
    public static def matchAndAct(Inventory inv, res, iact) {
        inv ?= maininv()
        def items = matchItem(inv, res)
        items.each{wi -> Item.iact(wi, iact)}
        return items.size()
    }
}
