package codes.jcli.achievementRace;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class AdvancementScoreboard {
    private final Objective advancementObjective;

    private final String OBJECTIVE_ID = "advancement";
    private final String OBJECTIVE_NAME = "advancement";

    public AdvancementScoreboard(Scoreboard scoreboard) {
        Objective advancementObjective = scoreboard.getObjective(OBJECTIVE_NAME);
        if (advancementObjective == null) {
            Criteria advancementCriteria = Criteria.create(OBJECTIVE_NAME);
            Component displayComponent = Component.text(OBJECTIVE_ID).color(TextColor.fromCSSHexString("orange"));
            advancementObjective = scoreboard.registerNewObjective(OBJECTIVE_ID, advancementCriteria, displayComponent);
        }

        advancementObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
        advancementObjective.setAutoUpdateDisplay(true);

        this.advancementObjective = advancementObjective;
    }

    public Objective getAdvancementObjective() {
        return this.advancementObjective;
    }

    public void incrementForPlayer(Player player) {
        Score score = this.getAdvancementObjective().getScoreFor(player);
        score.setScore(score.getScore() + 1);
    }
}
