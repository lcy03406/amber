package beet

class Event  {
   
    static def filters = []
    
    static private queue() {
        return BeetBox.world.sensor.queue()
    }
    
    static def push(f) {
        filters.push(f)
    }

    static def pop() {
        filters.pop()
    }
    
    static def remove(f) {
        filters.remove(f)
    }
    
    static def filter(ev) {
        def r = false
        for ( f in filters) {
            r = f(ev) || r
        }
        return r
    }
    
    static def dispatch(ms) {
        ms ?= 0
        def q = queue()
        def ev = null
        def t = ms
        for (;;) {
            synchronized (q) {
                while (q.size() == 0) {
                    def d = new Date().getTime()
                    try {
                        if (ms <= 0) {
                            q.wait(0);
                        } else {
                            q.wait(t);
                        }
                    } catch (InterruptedException ex) {
                    }
                    if (ms > 0) {
                        t -= new Date().getTime() - d
                        if (t <= 0)  {
                            break;
                        }
                    }
                }
                ev = q.poll();
            }
            if (ev == null)
                return null;
            //:BeetBox.println("ev:${ev?.name}:${ev?.args}")
            if (filter(ev)) {
                synchronized (q) {
                    q.clear()
                }
                return ev;
            }
        }
    }

    public static waituntil(ms, f) {
        push(f)
        def ev = dispatch(ms)
        pop()
        return ev
    }

    public static waitexpect(ms, name) {
        return waituntil(ms, {ev -> ev.name == name})?.args
    }

    public static waitexpect(ms, name, f) {
        return waituntil(ms, {ev -> ev.name == name && f(ev)})?.args
    }

    
    public static until(f) {
        return waituntil(0, f);
    }
    
    public static expect(name) {
        return until({ev -> ev.name == name}).args
    }

    public static expect(name, f) {
        return until({ev -> ev.name == name && f(ev)}).args
    }
}
