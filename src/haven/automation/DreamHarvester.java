package haven.automation;


import haven.*;

import static haven.OCache.posres;

public class DreamHarvester implements Runnable {
    private GameUI gui;

    public DreamHarvester(GameUI gui) {
        this.gui = gui;
    }

    @Override
    public void run() {
        //Gob dreca = null;
        int count = 0;
        synchronized (gui.map.glob.oc) {
            for (Gob gob : gui.map.glob.oc) {
                try {
                    Resource res = gob.getres();
                    if (res != null && res.name.startsWith("gfx/terobjs/dreca")) {
                    	count = count+1;
                        //Coord2d plc = gui.map.player().rc;
                        //if ((dreca == null || gob.rc.dist(plc) < dreca.rc.dist(plc)))
                            //dreca = gob;
                    }
                } catch (Loading l) {
                }
            }
        }
        Gob[] drecas = new Gob[count];
        count = 0;
        synchronized (gui.map.glob.oc) {
            for (Gob gob : gui.map.glob.oc) {
                try {
                    Resource res = gob.getres();
                    if (res != null && res.name.startsWith("gfx/terobjs/dreca")) {
                    	drecas[count] = gob;
                    	count = count+1;
                    	
                        //Coord2d plc = gui.map.player().rc;
                        //if ((dreca == null || gob.rc.dist(plc) < dreca.rc.dist(plc)))
                            //dreca = gob;
                    }
                } catch (Loading l) {
                }
            }
        }
        
        Gob temp1 = null;
        Gob temp2 = null;
    	Coord2d plc = gui.map.player().rc;
        for(int x=0;x<drecas.length;x++){
        	for(int y=x+1;y<drecas.length;y++){
        	temp1 = drecas[x];
        	temp2 = drecas[y];
            if ((temp1.rc.dist(plc) > temp2.rc.dist(plc))){
            	drecas[x] = temp2;
            	drecas[y] = temp1;
            }
        	}
        }
        
        
        
        for(Gob dreca:drecas){
        	try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
	        if (dreca == null)
	            return;
        
	        int initialSpace = gui.maininv.getFreeSpace();
	
	        FlowerMenu.setNextSelection("Harvest");
	        gui.map.wdgmsg("click", dreca.sc, dreca.rc.floor(posres), 3, 0, 0, (int) dreca.id, dreca.rc.floor(posres), 0, -1);
	
	        long now = System.currentTimeMillis();
	        while (initialSpace <= gui.maininv.getFreeSpace() && System.currentTimeMillis() - now < 500) {
	            try {
	                Thread.sleep(40);
	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	            }
	        }

	        FlowerMenu.setNextSelection("Harvest");
	        gui.map.wdgmsg("click", dreca.sc, dreca.rc.floor(posres), 3, 0, 0, (int) dreca.id, dreca.rc.floor(posres), 0, -1);
        }
		
		gui.msg("END");
    }
}
