package haven.pathfinder;


import haven.*;

import java.util.Iterator;


public class Pathfinder implements Runnable {
    private OCache oc;
    private MCache map;
    private MapView mv;
    private Coord src;
    private Coord dest;
    public boolean terminate = false;
    public boolean moveinterupted = false;

    public Pathfinder(MapView mv, Coord src, Coord dest) {
        this.src = src;
        this.dest = dest;
        this.oc = mv.glob.oc;
        this.map = mv.glob.map;
        this.mv = mv;
    }

    @Override
    public void run() {
        long starttotal = System.nanoTime();
        haven.pathfinder.Map m = new haven.pathfinder.Map(src, dest, map);

        long start = System.nanoTime();
        synchronized (oc) {
            for (Gob gob : oc) {
                if (gob.isplayer())
                    continue;
                m.addGob(gob);
            }
        }
        System.out.println("      Gobs Processing: " + (double) (System.nanoTime() - start) / 1000000.0 + " ms.");

        Iterable<Edge> path = m.main();
        System.out.println("--------------- Total: " + (double) (System.nanoTime() - starttotal) / 1000000.0 + " ms.");

        m.dbgdump();

        Iterator<Edge> it = path.iterator();
        while (it.hasNext() && !moveinterupted && !terminate) {
            Edge e = it.next();

            Coord mc = new Coord(src.x + e.dest.x - Map.origin, src.y + e.dest.y - Map.origin);
            mv.wdgmsg("click", Coord.z, mc, 1, 0); // ui.modflags();

            while (!moveinterupted && !terminate && !mv.player().rc.equals(mc)) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e1) {
                    return;
                }
            }
        }
    }

    public void moveStop(int step) {
        System.out.println("Movement interrupted " + step);
        moveinterupted = true;
    }

    public void moveStep(int step) {
    }
}
