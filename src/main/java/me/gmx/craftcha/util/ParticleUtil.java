// 
// Decompiled by Procyon v0.5.36
// 

package me.gmx.craftcha.util;

import me.gmx.craftcha.Craftcha;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class ParticleUtil
{
    public static ArrayList<Player> dna;
    private static int taskId = -69;

    public static void init() {
        if (taskId != -69)
            return;
        ParticleUtil.dna = new ArrayList<Player>();
        taskId = new BukkitRunnable() {
            public void run() {
                for (final Player p : ParticleUtil.dna) {
                    ParticleUtil.dnaParticle(p, 5);
                }
            }
        }.runTaskTimer((Plugin) Craftcha.getInstance(), 0L, 100L).getTaskId();
    }


    public static void stop(){
        Bukkit.getScheduler().cancelTask(taskId);
        taskId = -69;

    }
    public static void dnaParticle(final Player p, final int duration) {
        final int t = 3;
        new BukkitRunnable() {
            Particle particleHelix = Particle.FLAME;
            Color colorHelix = null;
            Particle particleBase1 = Particle.WATER_WAKE;
            Color colorBase1 = null;
            Particle particleBase2 = Particle.REDSTONE;
            Color colorBase2 = null;
            double radials = 0.39269908169872414;
            float radius = 0.5f;
            int particlesHelix = 5;
            int particlesBase = 8;
            float length = 3.0f;
            float grow = 0.3f;
            float baseInterval = 2.0f;
            int step = 0;
            int iterations = 0;
            
            public void run() {
                final Location location = p.getLocation().add(0.0, 2.0, 0.0).setDirection(new Vector(0, 1, 0));
                location.setYaw(0.0f);
                for (int j = 0; j < this.particlesHelix; ++j) {
                    if (this.step * this.grow > this.length) {
                        this.step = 0;
                    }
                    for (int i = 0; i < 2; ++i) {
                        final double angle = this.step * this.radials + 3.141592653589793 * i;
                        final Vector v = new Vector(Math.cos(angle) * this.radius, (double)(this.step * this.grow), Math.sin(angle) * this.radius);
                        ParticleUtil.drawParticle(location, v, this.particleHelix, this.colorHelix);
                    }
                    if (this.step % this.baseInterval == 0.0f) {
                        for (int i = -this.particlesBase; i <= this.particlesBase; ++i) {
                            if (i != 0) {
                                Particle particle = this.particleBase1;
                                Color color = this.colorBase1;
                                if (i < 0) {
                                    particle = this.particleBase2;
                                    color = this.colorBase2;
                                }
                                final double angle2 = this.step * this.radials;
                                final Vector v2 = new Vector(Math.cos(angle2), 0.0, Math.sin(angle2)).multiply(this.radius * i / this.particlesBase).setY(this.step * this.grow);
                                ParticleUtil.drawParticle(location, v2, particle, color);
                            }
                        }
                    }
                    ++this.step;
                }
                ++this.iterations;
                if (this.iterations > 20 / t * duration) {
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin) Craftcha.getInstance(), 0L, (long)t);
    }
    
    protected static void drawParticle(final Location location, final Vector v, final Particle particle, final Color color) {
        VectorUtils.rotateAroundAxisX(v, (location.getPitch() + 90.0f) * 0.017453292f);
        VectorUtils.rotateAroundAxisY(v, -location.getYaw() * 0.017453292f);
        location.add(v);
        location.getWorld().spawnParticle(particle, location, 0);
        location.subtract(v);
    }
    
    public static void sphereParticles(final Player p, final int size, final int layers, final int duration) {
        new BukkitRunnable() {
            double radius = size;
            int timer = 0;
            
            public void run() {
                final Location l = p.getLocation();
                final double r = size;
                for (double phi = 0.0; phi <= 3.141592653589793; phi += 3.141592653589793 / layers) {
                    final double y = r * Math.cos(phi) + 1.5;
                    for (double theta = 0.0; theta <= 6.283185307179586; theta += 0.10471975511965977) {
                        final double x = r * Math.cos(theta) * Math.sin(phi);
                        final double z = r * Math.sin(theta) * Math.sin(phi);
                        l.add(x, y, z);
                        l.getWorld().spawnParticle(Particle.FLAME, l, 1, 0.0, 0.019999999552965164, 0.0, 0.002);
                        l.subtract(x, y, z);
                    }
                }
                if (this.timer > duration * 10) {
                    this.cancel();
                }
                ++this.timer;
            }
        }.runTaskTimer((Plugin)Craftcha.getInstance(), 0L, 2L);
    }
}
