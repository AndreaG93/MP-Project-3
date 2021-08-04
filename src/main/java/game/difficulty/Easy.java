package game.difficulty;

public class Easy implements GameDifficulty {

    @Override
    public int getNumberOfEnemies() {
        return 4;
    }

    @Override
    public int getDifficultyPoints() {
        return 50;
    }

    @Override
    public int getFruitsPoints() {
        return 50;
    }

    @Override
    public int getTimePoints() {
        return 5;
    }

    @Override
    public int getNumberOfFruits() {
        return 4;
    }
}
