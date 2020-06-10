import beet.*

BeetBox.init(world);

def area = MapView.area()
for(c in area.lines()) {
    BeetBox.println("move $c")
    MapView.tileclick(c)
    Event.expect('GobMoveStop')
}
Event.dispatch(0)
