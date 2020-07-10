import beet.*

BeetBox.init(world);

def gobs = Search.match('^gfx/terobjs/dreca$')
for (gob in gobs) {
    Gob.click(gob, 2, 0)
    FlowerMenu.openselect('Harvest')
    Event.expect('ItemAdd')
}