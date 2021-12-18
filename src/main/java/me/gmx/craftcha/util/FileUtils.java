package me.gmx.craftcha.util;

import me.gmx.craftcha.Craftcha;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileUtils {

    public static void copy(InputStream input, File file){

        try{
            FileOutputStream output = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int i;
            while ((i = input.read(b)) > 0) {
                output.write(b,0,i);
            }
            output.close();
            input.close();


        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void mkDir(File file){
        try{
            file.mkdir();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }





}
