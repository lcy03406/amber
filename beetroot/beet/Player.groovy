package beet

import haven.Coord

class Player  {
    private static menu() {
        return BeetBox.world.gui.menu
    }
    
    public static act(name) {
        menu().wdgmsg('act', name)
    }
    
    public static prog() {
        Event.expect('ProgStart')
        Event.expect('ProgStop')
    }
}
