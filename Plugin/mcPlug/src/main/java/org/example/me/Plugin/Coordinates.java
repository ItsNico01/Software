package org.example.me.Plugin;
import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.EntityType;

import java.io.FileNotFoundException;

import static org.bukkit.Bukkit.*;

public class Coordinates {
    private int x,y,z;

    private Coordinates() {
        //Konstruktor
    }

    //Singleton
    public static Coordinates coordinates;

    public static Coordinates getCoordinates() {
        if(coordinates == null) {
            coordinates = new Coordinates();
        }
        return coordinates;
    }

    //Platziere den Block bei den richtigen Koordinaten
    private <T> void placeMaterial(int x, int y, int z,T block) {
        World world = getServer().getWorld("world");
        assert world != null;

        //Türplatzierung funktioniert anders als normale Blöcke, da diese 2 Felder benötigen.
        if(block.toString().contains("DOOR")){

            Material mat = (Material) block;
            Door d = (Door) Bukkit.createBlockData(mat);
            d.setFacing(BlockFace.EAST);
            d.setHalf(Bisected.Half.BOTTOM);

            world.setBlockData(x, y, z, d);

            d.setHalf(Bisected.Half.TOP);
            world.setBlockData(x, y+1, z, d);

        }else if(block.toString().contains("COW_SPAWN_EGG")){
            Location location =  new Location(world,x,y,z);
            world.spawnEntity(location, EntityType.COW);

        }else{
            world.getBlockAt(x, y, z).setType((Material) block);
        }

    }

    //Starte Bauprozess durch Auslesen des Json + Platzierung der Blöcke
    public void startBuilding(JsonObject json) throws FileNotFoundException {
        JsonArray buildArray = json.getAsJsonArray("build");
        if (buildArray == null) {
            System.out.println("Build-Array fehlt!");
            System.out.println("RAW JSON: " + json.toString());
            return;
        }

        for (JsonElement element : buildArray) {
            JsonObject obj = element.getAsJsonObject();
            if (!obj.has("block")) continue;

            this.x = obj.get("x").getAsInt();
            this.y = obj.get("y").getAsInt();
            this.z = obj.get("z").getAsInt();
            Material block = Material.valueOf(obj.get("block").getAsString());
            placeMaterial(x,y,z, block);
            System.out.println("x=" + x + ", y=" + y + ", z=" + z + ", block=" + block);
        }

    }

    //Koordinaten zur Teleportation des Players in die Nähe des Buildings
    public int[] getTeleportCoordinates() {
        int[] coordinates = new int[3];
        coordinates[0] = this.x+20;
        coordinates[1] = this.y+20;
        coordinates[2] = this.z+20;
        return coordinates;
    }
}
