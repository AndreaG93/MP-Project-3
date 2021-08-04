package game.difficulty;

public class VeryHard implements GameDifficulty {

    @Override
    public int getNumberOfEnemies() {
        return 12;
    }

    @Override
    public int getDifficultyPoints() {
        return 1000;
    }

    @Override
    public int getFruitsPoints() {
        return 200;
    }

    @Override
    public int getTimePoints() {
        return 30;
    }

    @Override
    public int getNumberOfFruits() {
        return 1;
    }
}
