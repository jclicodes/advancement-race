package codes.jcli.advancementrace.ui;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class AdvancementScoreboard {
    private final Objective trackedObjective;

    private static final String OBJECTIVE_ID = "advancement";
    private static final Component OBJECTIVE_DISPLAY =  Component.text("Advancements").color(NamedTextColor.GOLD);

    public AdvancementScoreboard(Scoreboard scoreboard) {
        Objective advancementObjective = scoreboard.getObjective(OBJECTIVE_ID);
        // set up advancement objective if not already registered on server
        if (advancementObjective == null) {
            advancementObjective = scoreboard.registerNewObjective(OBJECTIVE_ID, Criteria.DUMMY, OBJECTIVE_DISPLAY);
            advancementObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
            advancementObjective.setAutoUpdateDisplay(true);
        }

        this.trackedObjective = advancementObjective;
    }

    public void incrementForPlayer(Player player) {
        Score score = this.trackedObjective.getScoreFor(player);
        score.setScore(score.getScore() + 1);
    }
}
