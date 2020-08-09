import beet.*

BeetBox.init(world);

def gobs = Search.matchSquare('^gfx/terobjs/dreca$', 10)
for (gob in gobs) {
    gob.highlight()
    Gob.click(gob, 3, 0, 0, -1)
    if (FlowerMenu.waitopen(1000)) {
        FlowerMenu.select('Harvest')
        Event.expect('ItemAdd')
        Gob.click(gob, 3, 0, 0, -1)
        if (FlowerMenu.waitopen(1000)) {
            FlowerMenu.select('Harvest')
            Event.expect('ItemAdd')
        }
    }
}