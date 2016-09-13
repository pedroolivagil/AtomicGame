package cat.olivadevelop.atomicspacewar.tools;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by OlivaDevelop on 05/08/2015.
 */
public class IntersectorGame {

    // Check if Polygon intersects Rectangle
    public static boolean overlaps(Polygon p, Rectangle r) {
        Polygon rPoly = new Polygon(new float[]{0, 0, r.width, 0, r.width,
                r.height, 0, r.height});
        rPoly.setPosition(r.x, r.y);
        return Intersector.overlapConvexPolygons(rPoly, p);
    }

    // Check if Polygon intersects Circle
    public static boolean overlaps(Polygon p, Circle c) {
        float[] vertices = p.getTransformedVertices();
        Vector2 center = new Vector2(c.x, c.y);
        float squareRadius = c.radius * c.radius;
        for (int i = 0; i < vertices.length; i += 2) {
            if (i == 0) {
                if (Intersector.intersectSegmentCircle(new Vector2(
                        vertices[vertices.length - 2],
                        vertices[vertices.length - 1]), new Vector2(
                        vertices[i], vertices[i + 1]), center, squareRadius))
                    return true;
            } else {
                if (Intersector.intersectSegmentCircle(new Vector2(
                        vertices[i - 2], vertices[i - 1]), new Vector2(
                        vertices[i], vertices[i + 1]), center, squareRadius))
                    return true;
            }
        }
        return false;
    }

    public static boolean overlaps(Circle c1, Circle c2) {
        return Intersector.overlaps(c1, c2);
    }

    public static boolean overlaps(Circle c1, Rectangle r1) {
        return Intersector.overlaps(c1, r1);
    }

    public static boolean overlaps(Rectangle r1, Rectangle r2) {
        return Intersector.overlaps(r1, r2);
    }

    public static boolean overlaps(Polygon p1, Polygon p2) {
        return Intersector.overlapConvexPolygons(p1, p2);
    }
}
