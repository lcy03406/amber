package beet

import haven.Coord

class Auto  {
    public static dropitems = [];
    
    public static drop(ev) {
        if (ev.name == 'ItemAdd') {
            def (inv, wi) = ev.args
            def name = Item.res(wi)
            if (inv == Inv.maininv() && name in dropitems) {
                Item.drop(wi)
            }
        }
        return false
    }
}
