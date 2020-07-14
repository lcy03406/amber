package beet

import haven.Coord

class Area implements Iterable<Coord> {
    public class Iter implements Iterator<Coord> {
        public def x, y, d;
        public Iter() {
            this.x = Area.this.x1
            this.y = Area.this.y1
            d = y%2
        }
        public boolean hasNext() {
            return y <= Area.this.y2
        }
        public Coord next() {
            def c = new Coord(x, y)
            if (y%2 == d) {
                if (x == Area.this.x2) {
                    y = y + 1
                } else {
                    x = x + 1
                }                
            } else {
                if (x == Area.this.x1) {
                    y = y + 1
                } else {
                    x = x - 1
                }                
            }
            return c
        }
    }
    
    public x1, x2, y1, y2
    
    public Area(Coord c1, Coord c2) {
        x1 = Math.min(c1.x, c2.x)
        x2 = Math.max(c1.x, c2.x)
        y1 = Math.min(c1.y, c2.y)
        y2 = Math.max(c1.y, c2.y)
    }
    
    public Area(x1, x2, y1, y2) {
        this.x1 = Math.min(x1, x2)
        this.x2 = Math.max(x1, x2)
        this.y1 = Math.min(y1, y2)
        this.y2 = Math.max(y1, y2)
    }
    
    public Iter iterator() {
        return new Iter()
    }
    
    public lines() {
        def l = []
        def d = y1%2
        for (y in y1..y2) {
            if (y%2 == d) {
                l.add(new Coord(x1, y))
                l.add(new Coord(x2, y))
            } else {
                l.add(new Coord(x2, y))
                l.add(new Coord(x1, y))                
            }
        }
        return l
    }
    
    public contains(c) {
        return c.x >= x1 && c.x <= x2 && c.y >= y1 && c.y <= y2
    }
}
