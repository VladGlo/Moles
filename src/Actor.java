import java.awt.*;

public abstract class Actor {
    static Point[] randMoves = {
            new Point(0, 1),
            new Point(1, 0),
            new Point(0, -1),
            new Point(-1, 0)
    };

    public void move() {}

    Point curXY;

    Garden garden;
}
