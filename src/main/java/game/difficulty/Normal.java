package game.difficulty;

public class Normal implements GameDifficulty {

    @Override
    public int getNumberOfEnemies() {
        return 7;
    }

    @Override
    public int getDifficultyPoints() {
        return 100;
    }

    @Override
    public int getFruitsPoints() {
        return 100;
    }

    @Override
    public int getTimePoints() {
        return 10;
    }

    @Override
    public int getNumberOfFruits() {
        return 3;
    }
}
