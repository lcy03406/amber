package haven;

import javax.media.opengl.GL2;
import java.awt.*;

public class GobHitbox extends Sprite {
    private final static float center = 0.5f;
    private static final States.ColState clrstate = new States.ColState(new Color(88, 139, 194, 255));
    private Coordf a, b, c, d;
    private int mode;

    public GobHitbox(Gob gob, Coord ac, Coord bc, boolean fill) {
        super(gob, null);
        mode = fill ? GL2.GL_QUADS : GL2.GL_LINE_LOOP;

        // rotate around map pixel's center
        double cos = Math.cos(-gob.a);
        double sin = Math.sin(-gob.a);

        a = rotate(ac.x, ac.y, center, -center, cos, sin);
        b = rotate(ac.x, bc.y, center, -center, cos, sin);
        c = rotate(bc.x, bc.y, center, -center, cos, sin);
        d = rotate(bc.x, ac.y, center, -center, cos, sin);

        // because overlay is rotated according to gob.a during rendering
        // we rotate it in the opposite direction first to negate the effect
        sin = -sin; // reverse the angle. cos(a) == cos(-a) hence no need to touch it.
        a = rotate(a.x, a.y, cos, sin);
        b = rotate(b.x, b.y, cos, sin);
        c = rotate(c.x, c.y, cos, sin);
        d = rotate(d.x, d.y, cos, sin);
    }

    public boolean setup(RenderList rl) {
        rl.prepo(clrstate);
        // rl.prepo(States.xray);
        return true;
    }

    public void draw(GOut g) {
        g.apply();
        BGL gl = g.gl;
        gl.glLineWidth(2.0F);
        gl.glBegin(mode);
        gl.glVertex3f(a.x, a.y, 1);
        gl.glVertex3f(b.x, b.y, 1);
        gl.glVertex3f(c.x, c.y, 1);
        gl.glVertex3f(d.x, d.y, 1);
        gl.glEnd();
    }

    private Coordf rotate(float x, float y, double cos, double sin) {
        return new Coordf((float) (x * cos - y * sin), (float) (x * sin + y * cos));
    }

    private Coordf rotate(float x, float y, float pivotx, float pivoty, double cos, double sin) {
        x -= pivotx;
        y -= pivoty;
        return new Coordf((float) ((x * cos - y * sin) + pivotx), (float) ((x * sin + y * cos) + pivoty));
    }
}
