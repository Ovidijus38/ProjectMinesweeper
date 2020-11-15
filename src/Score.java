import java.sql.Time;
import java.util.ArrayList;

public class Score {

    ArrayList<Time> bestTimes;

    int gamesPlayed;
    int gamesWon;

    int longestWinningStreak;
    int longestLosingStreak;

    int currentStreak;

    int currentWinningStreak;
    int currentLosingStreak;

    public Score()
    {
        gamesPlayed = gamesWon = currentStreak = longestLosingStreak = longestWinningStreak = currentWinningStreak = currentLosingStreak = 0;
        bestTimes = new ArrayList();
    }
}
