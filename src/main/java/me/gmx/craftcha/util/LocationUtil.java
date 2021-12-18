package me.gmx.craftcha.util;

import me.gmx.craftcha.objects.BlockStorage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class LocationUtil {


    public static List<Block> getSquareAroundLocation(Location location, int radius){
        List<Block> blocks = new ArrayList<Block>();
        for (int x = location.getBlockX()-radius;x<location.getBlockX()+radius;x++){
            for (int y = location.getBlockY()-radius;y<location.getBlockY()+radius;y++){
                if ((x == location.getBlockX()-radius || x == location.getBlockX()+radius) && (y ==location.getBlockY()-radius || y == location.getBlockY()+radius)){
                    blocks.add(location.getWorld().getHighestBlockAt(x,y));
                }
            }
        }
        return blocks;
    }

    public static List<BlockStorage> getSquareAround(Location location, int radius){
        Location bl,tr;
        int y = location.getBlockY();
        List<BlockStorage> store = new ArrayList<BlockStorage>();
        bl = location.clone().subtract(radius,0,radius);
        tr = location.clone().add(radius,0,radius);
        for (int x = bl.getBlockX();x<=tr.getBlockX();x++){
            for (int z = bl.getBlockZ();z<=tr.getBlockZ();z++){
                if ((x == bl.getBlockX() || x == tr.getBlockX()) || (z == bl.getBlockZ() || z == tr.getBlockZ())) {
                    Block b = location.getWorld().getBlockAt(new Location(location.getWorld(),x,y,z));
                    store.add(new BlockStorage(b.getLocation(),b.getState()));
                }
            }
        }
        return store;
    }


    public static List<Block> getBorders(Location bl, Location tr){
        List<Block> blocks = new ArrayList<Block>();
        int y = (bl.getBlockY());
        for (int x = bl.getBlockX();x<=tr.getBlockX();x++){
            for (int z = bl.getBlockZ();z<=tr.getBlockZ();z++){
                if ((x == bl.getBlockX() || x == tr.getBlockX()) || (z == bl.getBlockZ() || z == tr.getBlockZ()))
                blocks.add(bl.getWorld().getBlockAt(x,y,z));
            }
        }

        return blocks;
    }

    public static Location getHighestBock(World world, int x, int z){
        int i = 255;
        while(i>0){
            if(new Location(world, x, i, z).getBlock().getType()!= Material.AIR)
                return new Location(world, x, i, z).add(0,1,0);
            i--;
        }
        return new Location(world, x, 1, z);
    }


    public static Location getMiddleBlock(Location botLeft, Location topRight){
        Location loc = new Location(botLeft.getWorld(),
                (botLeft.getBlockX()+topRight.getBlockX())/2,
                (botLeft.getBlockY()+topRight.getBlockY())/2,
                (botLeft.getBlockZ()+topRight.getBlockZ())/2);
        return loc;
    }
}
