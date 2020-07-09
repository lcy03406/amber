package haven.automation;


import haven.*;

public class Shoo implements Runnable {
    private GameUI gui;

    public Shoo(GameUI gui) {
        this.gui = gui;
    }

    @Override
    public void run() {
        Gob animal = null;
        synchronized (gui.map.glob.oc) {
            for (Gob gob : gui.map.glob.oc) {
                try {
                    Resource res = gob.getres();
                    if (res != null && (res.name.startsWith("gfx/kritter/horse") ||
                            res.name.startsWith("gfx/kritter/sheep") ||
                            res.name.startsWith("gfx/kritter/cattle") ||
                            res.name.startsWith("gfx/kritter/pig") ||
                            res.name.startsWith("gfx/kritter/goat")))
                    {
                        Coord2d plc = gui.map.player().rc;
                        if ((animal == null || gob.rc.dist(plc) < animal.rc.dist(plc)) && gob.knocked == false)
                            animal = gob;
                    }
                } catch (Loading l) {
                }
            }
        }

        if (animal == null)
            return;

        FlowerMenu.setNextSelection("Shoo");
        gui.map.wdgmsg("click", animal.sc, animal.getnc(), 3, 0, 0, (int) animal.id, animal.getnc(), 0, -1);
    }
}
