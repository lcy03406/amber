package haven.automation;


import haven.*;

public class ButcherFish implements Runnable, WItemDestroyCallback {
    private GameUI gui;
    private boolean fishdone;
    private static final int TIMEOUT = 2000;
    private static final int DELAY = 8;

    public ButcherFish(GameUI gui) {
        this.gui = gui;
    }

    @Override
    public void run() {
        boolean good = true;
        good = good && findAndAct("^gfx/invobjs/fish-.*$", "Butcher");
        good = good && findAndAct("^gfx/invobjs/(hen|rooster|squirrel|hedeghog|rabbit-buck|rabbit-doe)$", "Wring neck");
        good = good && findAndAct("^gfx/invobjs/(squirrel|hedeghog|rabbit)-dead(|-buck|-doe)$", "Flay");
        good = good && findAndAct("^gfx/invobjs/(hen|rooster)-dead$", "Pluck");
        good = good && findAndAct("^gfx/invobjs/.*-plucked$", "Clean");
        good = good && findAndAct("^gfx/invobjs/bat-dead$", "Clean");
        good = good && findAndAct("^gfx/invobjs/.*-(carcass|plucked)$", "Clean");
        good = good && findAndAct("^gfx/invobjs/.*-clean(ed)?$", "Butcher");
    }
    
    private boolean findAndAct(String find, String act) {
        WItem fish;
        while ((fish = Utils.findItemByMatchInInv(gui.maininv, find)) != null) {
            fishdone = false;
            fish.registerDestroyCallback(this);

            FlowerMenu.setNextSelection(act);
            gui.ui.lcc = fish.rootpos();
            fish.item.wdgmsg("iact", fish.c, 0);

            int timeout = 0;
            while (!fishdone) {
                timeout += DELAY;
                if (timeout >= TIMEOUT)
                    return false;
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    return false;
                }
            }
        }
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }

    @Override
    public void notifyDestroy() {
        fishdone = true;
    }
}
