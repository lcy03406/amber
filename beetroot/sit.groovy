import beet.*

BeetBox.init(world);

def gobs = Search.matchSquare('gfx/terobjs/chest', 1)
for(g in gobs) {
    g.highlight()
    Gob.click(g, 3, 0)
}