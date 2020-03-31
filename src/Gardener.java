import java.awt.*;
import java.util.Random;

public class Gardener extends Actor {

    private Mole target;
    private Random random = new Random();

    public Gardener(Point point, Garden garden) {
        curXY = point;
        this.garden = garden;
    }

    @Override
    public void move() {
        if (target == null) {
            findMole();
        }


        Point nextMove;
        if (target == null) {
            do {
                int possibleMoveId = random.nextInt(4);
                nextMove = randMoves[possibleMoveId];
            } while (!garden.isXYCorrect(curXY.x + nextMove.x, curXY.y + nextMove.y));
        }
        else {
            Point targetXY = target.curXY;
            int divX = targetXY.x - curXY.x;
            int divY = targetXY.y - curXY.y;
            nextMove = new Point(Integer.signum(divX), Integer.signum(divY));
            if (nextMove.x != 0 && nextMove.y != 0) {
                if (random.nextBoolean()) {
                    nextMove.x = 0;
                } else {
                    nextMove.y = 0;
                }
            }
        }
        curXY.x += nextMove.x;
        curXY.y += nextMove.y;
        if (target != null)
             tryToKillMole();
    }

    void tryToKillMole() {
        Point targetXY = target.curXY;
        double dist = Math.sqrt(Math.pow(curXY.x - targetXY.x, 2) + Math.pow(curXY.y - targetXY.y, 2));
        if (dist <= Math.sqrt(2)) {
            garden.removeMole(target);
            target = null;
        }
    }

    void findMole() {
        target = garden.getNearestMole(curXY);
    }
}
