package game.difficulty;

public class Hard implements GameDifficulty {

    @Override
    public int getNumberOfEnemies() {
        return 10;
    }

    @Override
    public int getDifficultyPoints() {
        return 500;
    }

    @Override
    public int getFruitsPoints() {
        return 150;
    }

    @Override
    public int getTimePoints() {
        return 25;
    }

    @Override
    public int getNumberOfFruits() {
        return 2;
    }
}
