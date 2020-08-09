package beetbox;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;

public class AlarmClock {
    public static interface Action {
        void act();
    }
    
    public class Alarm implements Comparable<Alarm> {
        private final int id;
        private final Instant time;
        private final Action action;
        
        private Alarm(int id, Instant time, Action action) {
            this.id = id;
            this.time = time;
            this.action = action;
        }

        @Override
        public int compareTo(Alarm o) {
            int c = time.compareTo(o.time);
            if (c != 0)
                return c;
            return o.id - id;
        }
    }
    
    private final AtomicInteger counter = new AtomicInteger();
    private final SortedSet<Alarm> alarms = new ConcurrentSkipListSet<>(); 

    public Alarm setAlarm(Instant time, Action action) {
        int id = counter.incrementAndGet();
        Alarm alarm = new Alarm(id, time, action);
        alarms.add(alarm);
        return alarm;
    }
    
    public void unsetAlarm(Alarm alarm) {
        alarms.remove(alarm);
    }
    
    public boolean isEmpty() {
        return alarms.isEmpty();
    }
    
    public void update(Instant now) {
        for (Alarm alarm : alarms) {
            if (alarm.time.isAfter(now)) {
                return;
            }
            alarms.remove(alarm);
            alarm.action.act();
        }
    }
    
    public static void unittest() {
        AlarmClock clock = new AlarmClock();
        Instant now = Clock.systemDefaultZone().instant();
        clock.setAlarm(now.plus(Duration.ofMinutes(2)), ()->{System.out.println("睡着了...");});
        clock.setAlarm(now.plus(Duration.ofMinutes(20)), ()->{System.out.println("醒来了...");});
        clock.setAlarm(now.plus(Duration.ofMinutes(15)), ()->{System.out.println("下课了!!!");});
        while (!clock.isEmpty()) {
            System.out.println("当前时间：" + now);
            clock.update(now);
            now = now.plus(Duration.ofMinutes(1));
        }
    }
}

