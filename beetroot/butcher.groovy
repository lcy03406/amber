import beet.*

BeetBox.init(world)

def config = [
    ['^gfx/invobjs/lettucehead$', 'Split'],
    ['^gfx/invobjs/fish-.*', 'Butcher'],
    ['^gfx/invobjs/(hen|rooster|squirrel|hedeghog|rabbit-buck|rabbit-doe)$', 'Wring neck'],
    ['^gfx/invobjs/(hen|rooster)-dead$', 'Pluck'],
    ['^gfx/invobjs/rabbit-dead(|-doe)$', 'Flay'],
    ['^gfx/invobjs/.*-plucked$', 'Clean'],
    ['^gfx/invobjs/rabbit-carcass$', 'Clean'],
    ['^gfx/invobjs/chicken-cleaned$', 'Butcher'],
    ['^gfx/invobjs/rabbit-clean$', 'Butcher'],
]

for (conf in config) {
    def n = Inv.matchAndAct(null, conf[0], conf[1])
    if (n > 0)
        break;
}
