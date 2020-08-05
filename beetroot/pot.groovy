import beet.*

BeetBox.init(world);

def gobs = Search.match('^gfx/terobjs/gardenpot$')

def pick = gobs.findAll{it.ols.size() == 2}
if (pick.size() > 0) {
    for (gob in pick) {
        Gob.click(gob, 2, 0)
        FlowerMenu.openselect('Harvest')
        (inv, wi) = Event.expect('ItemAdd')
        Item.click(wi)
        Gob.itemact(gob)
        Event.expect('PlayerMoveStop')
    }
}

def water = gobs.findAll{(it.stage & 1) == 0}
if (water.size() > 0) {
    def bucket = Equip.slot(7) //right hand
    Item.take(bucket)
    for (gob in water) {
        Gob.itemact(gob, 1, 0, -1)
        Event.expect('PlayerMoveStop')
    }
    Equip.drop(7)
}

def soil = gobs.findAll{(it.stage & 2) == 0}
if (soil.size() > 0) {
    Inv.takeOne(null, '^gfx/invobjs/mulch')
    Event.expect('ItemDel')
    for (gob in soil) {
        Gob.itemact(gob, 1, 0, -1)
        Event.expect('ItemDel')
        Gob.itemact(gob, 1, 0, -1)
        Event.expect('ItemDel')
        Gob.itemact(gob, 1, 0, -1)
        Event.expect('ItemDel')
        Gob.itemact(gob, 1, 0, -1)
        Event.expect('ItemDel')
    }
}

