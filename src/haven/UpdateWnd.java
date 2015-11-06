package haven;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URI;

public class UpdateWnd extends Window {

    public UpdateWnd(String durl, String version) {
        super(Coord.z, "Update");
        final String url = durl;
        add(new Label("New update is available - v" + version), new Coord(20, 40));
        add(new Button(200, "Download Update") {
            public void click() {
                Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        desktop.browse(new URI(url.substring(0, url.length()-4) + "-upd.zip"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Coord(0, 80));
        add(new Button(200, "Full Client Download") {
            public void click() {
                Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        desktop.browse(new URI(url));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Coord(0, 125));
        pack();
        this.c = new Coord(HavenPanel.w/2-sz.x/2, HavenPanel.h/2-sz.y/2);
    }

    @Override
    public void wdgmsg(Widget sender, String msg, Object... args) {
        if (sender == cbtn) {
            ui.destroy(this);
        } else {
            super.wdgmsg(sender, msg, args);
        }
    }

    @Override
    public boolean type(char key, KeyEvent ev) {
        if(key == KeyEvent.VK_ESCAPE) {
            wdgmsg(cbtn, "click");
            return(true);
        }
        return(super.type(key, ev));
    }
}