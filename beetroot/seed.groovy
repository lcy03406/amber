import beet.*

BeetBox.init(world)

def step() {
    def items = Inv.matchItem(null, '^gfx/invobjs/seed-.*$')
    for (i1 in items) {
        if (i1.item.n < 50) {
            for (i2 in items) {
                if (i1 != i2 && i2.item.n < 50 && i1.item.getres().name == i2.item.getres().name && i1.item.q == i2.item.q) {
                    Item.take(i1)
                    Item.itemact(i2)
                    Event.expect('ItemTt', {ev.args[0] == i2})
                    return true
                }
            }
        }
    }
    return false
}

while (step()) {
    
}