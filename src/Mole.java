import java.awt.*;
import java.util.Random;

public class Mole extends Actor{

    private boolean isMale;
    private int eatTimer = 0;
    private Random random = new Random();
    private Point targetXY = null;

    public Mole(Point startPoint, boolean isMale, Garden garden) {
        curXY = startPoint;
        this.isMale = isMale;
        this.garden = garden;
    }

    @Override
    public void move() {
        //check if no food target or food was eaten by other mole
        if (targetXY == null || targetXY != findFood()) {
            targetXY = findFood();
        }

        if (isMale) {
            tryToMakeNewMole();
        }

        Point nextMove;
        //eat food
        if (targetXY != null && targetXY.x == curXY.x && targetXY.y == curXY.y) {
            ++eatTimer;
            if (eatTimer == 3) {
                garden.removeFood(targetXY);
                targetXY = null;
            }
            return;
        }
        //no food in garden
        if (targetXY == null) {
            do {
                int possibleMoveId = random.nextInt(4);
                nextMove = randMoves[possibleMoveId];
            } while (!garden.isXYCorrect(curXY.x + nextMove.x, curXY.y + nextMove.y));
        }
        //go for food
        else {
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
    }

    private void tryToMakeNewMole() {
        boolean isFemaleInCurXY = garden.isFemaleInPoint(curXY);
        if (isFemaleInCurXY) {
            garden.addMole(new Mole(
                    curXY,
                    random.nextBoolean(),
                    garden));
        }
    }

    private Point findFood() {
        return garden.getNearestFood(curXY);
    }

    public boolean isMale() {
        return isMale;
    }
}
