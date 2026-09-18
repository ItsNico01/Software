package org.example.me.Plugin;
import org.bukkit.Chunk;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import com.google.gson.JsonObject;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.World;

public final class Plugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Plugin is enabled");
        //Events registrieren
        getServer().getPluginManager().registerEvents(this, this);

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        try{
            Client client = new Client();
            JsonObject res = client.startGetRequest();
            Coordinates coordinates = Coordinates.getCoordinates();
            coordinates.startBuilding(res);

        }catch (Exception e){
            getLogger().severe("Error in onJoin: " + e.getMessage());
        }

        Coordinates coordinates = Coordinates.getCoordinates();
        teleportPlayer(event, coordinates);

    }

    //Teleportiere den Spieler in die Nähe des Buildings
    public void teleportPlayer(PlayerJoinEvent event, Coordinates coordinates) {
        World world = getServer().getWorld("world");
        assert world != null;
        Player player = event.getPlayer();
        player.getAllowFlight();
        player.setFlying(true);
        player.teleport(new Location(world,coordinates.getTeleportCoordinates()[0], coordinates.getTeleportCoordinates()[1], coordinates.getTeleportCoordinates()[2]));
    }


}
