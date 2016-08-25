package purus.farmer;

import java.awt.Color;

import haven.Button;
import haven.Coord;
import haven.GameUI;
import haven.Inventory;
import haven.UI;
import haven.Widget;
import haven.Window;
import purus.BotUtils;

public class Farmer extends Window implements AreaSelect {
	
	private BotUtils BotUtils;
	
	private UI ui;
	private Widget w;
	private Inventory i;
	
	// coords of area selected
	private Coord rc1;
	private Coord rc2;
	
		// This is main place to select farming bots 
	
	public Farmer(UI ui, Widget w, Inventory i) {
        super(new Coord(180, 300), "Farming Bots");
		this.ui = ui;
		this.w = w;
		this.i = i;
		this.BotUtils = new BotUtils(ui, w, i);
        
        int y = 0;
		
        Button carrotBtn = new Button(140, "Carrot") {
            @Override
            public void click() {
            	if(rc1!=null && rc2!=null) {
                	// Start carrot farmer and close this window
                	SeedCropFarmer bf = new SeedCropFarmer(rc1, rc2, BotUtils,
                			"gfx/terobjs/plants/carrot", "gfx/invobjs/carrot", 4);
                	GameUI gui = BotUtils.gui();
                	gui.add(bf, new Coord(gui.sz.x / 2 - bf.sz.x / 2, gui.sz.y / 2 - bf.sz.y / 2 - 200));
                	new Thread(bf).start();
                	this.parent.destroy();
            	} else 
            		BotUtils.sysMsg("Area not selected!", Color.WHITE);
            }
        };
        add(carrotBtn, new Coord(20, y));
        y += 35;
        
        Button onionBtn = new Button(140, "Yellow Onion") {
            @Override
            public void click() {
            	if(rc1!=null && rc2!=null) {
                	// Start yellow onion farmer and close this window
                	SeedCropFarmer bf = new SeedCropFarmer(rc1, rc2, BotUtils,
                			"gfx/terobjs/plants/yellowonion", "gfx/invobjs/yellowonion", 3);
                	GameUI gui = BotUtils.gui();
                	gui.add(bf, new Coord(gui.sz.x / 2 - bf.sz.x / 2, gui.sz.y / 2 - bf.sz.y / 2 - 200));
                	new Thread(bf).start();
                	this.parent.destroy();
            	} else 
            		BotUtils.sysMsg("Area not selected!", Color.WHITE);
            }
        };
        add(onionBtn, new Coord(20, y));
        y += 35;
        
        Button beetBtn = new Button(140, "Beetroot") {
            @Override
            public void click() {
            	if(rc1!=null && rc2!=null) {
                	// Start beetroot onion farmer and close this window
                	SeedCropFarmer bf = new SeedCropFarmer(rc1, rc2, BotUtils,
                			"gfx/terobjs/plants/beet", "gfx/invobjs/beet", 3);
                	GameUI gui = BotUtils.gui();
                	gui.add(bf, new Coord(gui.sz.x / 2 - bf.sz.x / 2, gui.sz.y / 2 - bf.sz.y / 2 - 200));
                	new Thread(bf).start();
                	this.parent.destroy();
            	} else 
            		BotUtils.sysMsg("Area not selected!", Color.WHITE);
            }
        };
        add(beetBtn, new Coord(20, y));
        y += 35;
        
        Button barleyBtn = new Button(140, "Barley") {
            @Override
            public void click() {
            	if(rc1!=null && rc2!=null) {
                	// Start barley farmer and close this window
                	SeedCropFarmer bf = new SeedCropFarmer(rc1, rc2, BotUtils,
                			"gfx/terobjs/plants/barley", "gfx/invobjs/seed-barley", 3);
                	GameUI gui = BotUtils.gui();
                	gui.add(bf, new Coord(gui.sz.x / 2 - bf.sz.x / 2, gui.sz.y / 2 - bf.sz.y / 2 - 200));
                	new Thread(bf).start();
                	this.parent.destroy();
            	} else 
            		BotUtils.sysMsg("Area not selected!", Color.WHITE);
            }
        };
        add(barleyBtn, new Coord(20, y));
        y += 35;
        
        Button flaxBtn = new Button(140, "Flax") {
            @Override
            public void click() {
            	if(rc1!=null && rc2!=null) {
                	// Start flax farmer and close this window
                	SeedCropFarmer bf = new SeedCropFarmer(rc1, rc2, BotUtils,
                			"gfx/terobjs/plants/flax", "gfx/invobjs/seed-flax", 3);
                	GameUI gui = BotUtils.gui();
                	gui.add(bf, new Coord(gui.sz.x / 2 - bf.sz.x / 2, gui.sz.y / 2 - bf.sz.y / 2 - 200));
                	new Thread(bf).start();
                	this.parent.destroy();
            	} else 
            		BotUtils.sysMsg("Area not selected!", Color.WHITE);
            }
        };
        add(flaxBtn, new Coord(20, y));
        y += 35;
        
        Button poppyBtn = new Button(140, "Poppy") {
            @Override
            public void click() {
            	if(rc1!=null && rc2!=null) {
                	// Start poppy farmer and close this window
                	SeedCropFarmer bf = new SeedCropFarmer(rc1, rc2, BotUtils,
                			"gfx/terobjs/plants/poppy", "gfx/invobjs/seed-poppy", 4);
                	GameUI gui = BotUtils.gui();
                	gui.add(bf, new Coord(gui.sz.x / 2 - bf.sz.x / 2, gui.sz.y / 2 - bf.sz.y / 2 - 200));
                	new Thread(bf).start();
                	this.parent.destroy();
            	} else 
            		BotUtils.sysMsg("Area not selected!", Color.WHITE);
            }
        };
        add(poppyBtn, new Coord(20, y));
        y += 35;
        
        Button hempBtn = new Button(140, "Hemp") {
            @Override
            public void click() {
            	if(rc1!=null && rc2!=null) {
                	// Start hemp farmer and close this window
                	SeedCropFarmer bf = new SeedCropFarmer(rc1, rc2, BotUtils,
                			"gfx/terobjs/plants/hemp", "gfx/invobjs/seed-hemp", 4);
                	GameUI gui = BotUtils.gui();
                	gui.add(bf, new Coord(gui.sz.x / 2 - bf.sz.x / 2, gui.sz.y / 2 - bf.sz.y / 2 - 200));
                	new Thread(bf).start();
                	this.parent.destroy();
            	} else 
            		BotUtils.sysMsg("Area not selected!", Color.WHITE);
            }
        };
        add(hempBtn, new Coord(20, y));
        y += 35;
        
        Button areaSelBtn = new Button(140, "Select Area") {
            @Override
            public void click() {
            	// Select area
            	BotUtils.sysMsg("Hold shift to drag area", Color.WHITE);
            	ui.gui.map.areaSelectB = true;
            }
        };
        add(areaSelBtn, new Coord(20, y));
	}
	
	public void areaSelect(Coord rc1, Coord rc2) {
		this.rc1 = rc1;
		this.rc2 = rc2;
	}
	
    @Override
    public void wdgmsg(Widget sender, String msg, Object... args) {
        if (sender == cbtn)
            reqdestroy();
        else
            super.wdgmsg(sender, msg, args);
    }

}