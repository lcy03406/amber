import beet.*

BeetBox.init(world);

BeetBox.println('Select Start Place 0')
def t = MapView.select()
MapView.gobclick(t, 1, 0)

    