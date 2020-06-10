import beet.*

BeetBox.init(world);

Auto.dropitems.add('snow')
Event.push(Auto.&drop)
Player.act('dig')
def area = MapView.area()
for(c in area) {
    def res = MapView.tileres(c)
    if (res == 'snow') {
        MapView.tileclick(c)
        Player.prog()
    }
}
Event.dispatch(0)
