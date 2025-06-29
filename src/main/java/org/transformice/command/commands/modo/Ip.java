package org.transformice.command.commands.modo;

// Imports
import com.maxmind.geoip2.record.Continent;
import com.maxmind.geoip2.record.Country;
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.libraries.GeoIP;
import org.transformice.utils.IPHex;

@Command(
        name = "ip",
        usage = "[playerName]",
        description = "Gives the IP and country of a connected player.",
        permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Ip implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = args.getFirst();
        Client playerClient = server.getPlayers().get(playerName);
        if (playerClient == null) {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("invalidusername"));
            return;
        }

        Country countryInfo = GeoIP.getCountry(playerClient.getIpAddress());
        Continent continentInfo = GeoIP.getContinent(playerClient.getIpAddress());

        CommandHandler.sendServerMessage(player, String.format("<BV>%s</BV>'s IP address: %s\n%s - %s (%s) - Community [%s]",
                playerClient.getPlayerName(),
                IPHex.encodeIP(playerClient.getIpAddress()),
                (countryInfo == null) ? "JP" : countryInfo.getIsoCode().toUpperCase(),
                (countryInfo == null) ? "Japan" : countryInfo.getName(),
                (continentInfo == null) ? "Asia" : continentInfo.getName(),
                playerClient.playerCommunity));
    }
}