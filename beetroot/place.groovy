
import beet.*
import haven.Gob

BeetBox.init(world)

BeetBox.println('Select Start Place 0')
def p0 = MapView.select()
BeetBox.println('Select Start Place 1')
def p1 = MapView.select()

def a = p0.a
def rc0 = p0.rc
def rc = p1.rc
def d = rc.sub(rc0)

while (true) {
    BeetBox.println('Lift!')
    def target = Event.until({ev -> ev.name == 'GobFollowStart' && ev.args[1] == MapView.pl().id}).args[0]
    MapView.gobclick(target, 2, 0)
    Event.expect('PlaceStart')
    rc = rc.add(d)
    MapView.place(rc, a)
}
    
