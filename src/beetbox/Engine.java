package beetbox;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import haven.Console;
import haven.GameUI;
import haven.HavenPanel;
import haven.Session;
import haven.UI;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.codehaus.groovy.runtime.StackTraceUtils;

public class Engine {
    private static Engine inst;
    
    GroovyScriptEngine engine = null;
    private Thread running = null;
    private World world = new World();
    
    public static Engine init() {
        inst = new Engine();
        return inst;
    }
    
    public static Engine inst() {
        return inst;
    }
    
    public Engine() {
        inst = this;
        try {
            String path = "beetroot";
            if (Files.exists(Paths.get("../beetroot")))
                path = "../beetroot";
            engine = new GroovyScriptEngine(path, this.getClass().getClassLoader());
        } catch (IOException ex) {
        }
    }
    
    public void newui(HavenPanel p, UI ui, Session sess) {
        world.p = p;
        world.ui = ui;
        world.gui = null;
        ui.cons.setcmd("beet", this::runScript);
        ui.cons.setcmd("beetkill", this::killScript);
    }
    
    public void newgameui(GameUI gui) {
        world.gui = gui;
    }
    
    private static final Color logColor = new Color(0xB40422);
    public void println(String text) {
        if (world != null && world.gui != null) {
            world.gui.msg(text, logColor);
        }
    }
    
    public void runScript(Console cons, String[] args) {
        scriptStop();
        running = new Thread(()->{scriptRun(args);});
        running.start();
    }
    
    public void killScript(Console cons, String[] args) {
        if (running == null) {
            println("beetbox is not running!");
            return;
        }
        scriptStop();
    }
   
    private void scriptRun(String[] args) {
        try {
            String name = args[1] + ".groovy";
            println("beetbox run " + name);
            Binding binding = new Binding();
            binding.setVariable("world", world);
            binding.setVariable("args", args);
            world.sensor.clear();
            engine.run(name, binding);
            println("beetbox finish " + name);
        } catch (ThreadDeath td) {
            println("beetbox death");
            //throw(td);
        } catch (Exception ex) {
            println(ex.toString());
            StackTraceUtils.printSanitizedStackTrace(ex, world.ui.cons.out);
        } finally {
            if (running == Thread.currentThread())
                running = null;
        }
    }

    private void scriptStop() {
        if (running != null) {
	    Thread r = running;
            r.stop();
            try {
                r.join();
            } catch (Exception ex) {
            }
            running = null;
        }
    }
    
    public void enqueEvent(String name, Object ... args) {
        if (running != null) {
            world.sensor.enque(name, args);
        }
    }
}
