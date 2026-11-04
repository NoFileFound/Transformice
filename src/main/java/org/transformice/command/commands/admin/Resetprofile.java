package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.data.objects.PlayerTitleInfo;
import org.transformice.data.objects.ShamanType;
import org.transformice.database.data.PlayerEntity;

@Command(name = "resetprofile", description = "Resets the player's progress.", permission = Command.CommandPermission.ADMINISTRATOR, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Resetprofile implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        PlayerEntity playerAccount = server.getDatabase().loadPlayerByName(args.getFirst());
        if (playerAccount == null) {
            CommandHandler.sendServerMessage(player, "The supplied argument isn't a valid nickname.");
            return;
        }

        playerAccount.currentTitle = new PlayerTitleInfo((short) 0, (byte) 0);
        playerAccount.getFriendList().clear();
        playerAccount.getIgnoredList().clear();
        playerAccount.playerLook = "1;0,0,0,0,0,0,0,0,0,0,0,0";
        playerAccount.soulmate = "";
        playerAccount.savesNormalWithSkill = 0;
        playerAccount.savesHardWithSkill = 0;
        playerAccount.savesDivineWithSkill = 0;
        playerAccount.savesNormalWithoutSkill = 0;
        playerAccount.savesHardWithoutSkill = 0;
        playerAccount.savesDivineWithoutSkill = 0;
        playerAccount.getShamanBadges().clear();
        playerAccount.currentShamanBadge = 0;
        playerAccount.shamanColor = 9820630;
        playerAccount.getShamanSkills().clear();
        playerAccount.shamanSkillsEnabled = true;
        playerAccount.shamanLevel = 1;
        playerAccount.shamanNextLevelXp = 32;
        playerAccount.shamanType = ShamanType.NORMAL;
        playerAccount.shopCheese = 0;
        playerAccount.shopStrawberries = 0;
        playerAccount.getShopEmojis().clear();
        playerAccount.getShopAdventureBanners().clear();
        playerAccount.getShopItems().clear();
        playerAccount.getUnlockedTitles().clear();
        playerAccount.getUnlockedTitles().add(playerAccount.currentTitle);
        playerAccount.totemInfo = new Object[]{0, ""};
        playerAccount.petInfo = new long[]{0, 0};
        playerAccount.cheeseCount = 0;
        playerAccount.firstCount = 0;
        playerAccount.shamanCheeseCount = 0;
        playerAccount.bootcampCount = 0;
        playerAccount.getInventory().clear();
        playerAccount.getEquippedConsumables().clear();
        playerAccount.getPlayerAdventures().clear();
        playerAccount.adventurePoints = 0;
        playerAccount.currentAdventureBanner = 1;
        playerAccount.getPlayerBadges().clear();
        playerAccount.getRacingStats().roundsCount = 0;
        playerAccount.getRacingStats().roundsCompleted = 0;
        playerAccount.getRacingStats().roundsCompletedFirstSecondThird = 0;
        playerAccount.getRacingStats().roundsCompletedFirst = 0;
        playerAccount.getSurvivorStats().roundsSurvived = 0;
        playerAccount.getSurvivorStats().roundsCount = 0;
        playerAccount.getSurvivorStats().roundsShaman = 0;
        playerAccount.getSurvivorStats().roundsKilledMice = 0;
        playerAccount.getDefilanteStats().defilantePointsCount = 0;
        playerAccount.getDefilanteStats().roundsCount = 0;
        playerAccount.getDefilanteStats().roundsCompletedFirstSecondThird = 0;
        server.getDatabase().savePlayer(playerAccount);
        if (server.getPlayers().get(playerAccount.getUsername()) != null)
            server.getPlayers().get(playerAccount.getUsername()).disconnect(true);

        CommandHandler.sendServerMessage(player, String.format("Successfully reset the profile of the player %s", playerAccount.getUsername()));
    }
}