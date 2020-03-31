public class Main {
    public static void main(String[] args) {
        Garden garden = new Garden(15,15, 3, 5);

        for (int i = 0; i < 10; ++i) {
            garden.makeMoveByAllActors();
            garden.logGarden();
        }
    }
}
