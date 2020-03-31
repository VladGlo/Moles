import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Garden {

    private int width, height;

    private ArrayList<Mole> moles = new ArrayList<>();
    private ArrayList<Point> foodMap = new ArrayList<>();
    private ArrayList<Mole> youngMoles = new ArrayList<>();
    private Gardener gardener;
    private Random random = new Random();

    public Garden(int width, int height, int moleCnt, int foodCnt) {
        this.width = width;
        this.height = height;
        gardener = new Gardener(getRandomPoint(), this);
        for (int i = 0; i < moleCnt; ++i)
            moles.add(new Mole(
                    getRandomPoint(),
                    random.nextBoolean(),
                    this
                    )
            );

        for (int i = 0; i < foodCnt; ++i)
            foodMap.add(getRandomPoint());
    }

    Point getRandomPoint() {
        return new Point(
                random.nextInt(width),
                random.nextInt(height)
        );
    }

    void addMole(Mole mole) {
        youngMoles.add(mole);
    }


    boolean isXYCorrect(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public void removeFood(Point targetXY) {
        foodMap.remove(targetXY);
    }

    public Point getNearestFood(Point point) {
        int minDist = height + width + 1;
        Point nearestFood = null;
        for (Point food: foodMap) {
            int dist = Math.abs(food.x - point.x) + Math.abs(food.y - point.y);
            if (dist < minDist) {
                minDist = dist;
                nearestFood = food;
            }
        }
        return nearestFood;
    }

    public Mole getNearestMole(Point point) {
        int minDist = height + width + 1;
        Mole nearestMole = null;
        for (Mole mole: moles) {
            int dist = Math.abs(mole.curXY.x - point.x) + Math.abs(mole.curXY.y - point.y);
            if (dist < minDist) {
                minDist = dist;
                nearestMole = mole;
            }
        }
        return nearestMole;
    }

    public void removeMole(Mole mole) {
        moles.remove(mole);
    }

    public boolean isFemaleInPoint(Point point) {
        return moles.stream().anyMatch(mole -> !mole.isMale() && mole.curXY.equals(point));
    }

    public void makeMoveByAllActors() {
        gardener.move();
        for (Mole mole: moles)
            mole.move();
        addYoungMoles();
    }

    private void addYoungMoles() {
        moles.addAll(youngMoles);
        youngMoles.clear();
    }

    /*
        e - food
        f - female mole
        m - male mole
        g - gardener

     */
    public void logGarden() {
        String[][] objects = new String[width][height];
        for (int i = 0; i < width; ++i)
            for (int j = 0; j < height; ++j)
                objects[i][j] = "0";

        for (int i = 0; i < width; ++i)
            for (int j = 0; j < height; ++j) {
                for (Point food: foodMap) {
                    if (food.x == i && food.y == j)
                        objects[i][j] = addObject(objects[i][j],"E");
                }
                for (Mole mole: moles) {
                    if (mole.curXY.x == i && mole.curXY.y == j)
                        if (mole.isMale())
                            objects[i][j] = addObject(objects[i][j],"M");
                        else
                            objects[i][j] = addObject(objects[i][j],"F");

                }
                if (gardener.curXY.x == i && gardener.curXY.y == j)
                    objects[i][j] = addObject(objects[i][j],"G");
            }

        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                System.out.print(objects[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private String addObject(String oldValue, String newParam) {
        if (oldValue.equals("0"))
            return newParam;
        else
            return oldValue + newParam;
    }
}
