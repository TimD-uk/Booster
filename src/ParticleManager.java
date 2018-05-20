import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.living.player.Player;

public class ParticleManager
{
    private static float random(double randFrom, double randTo)
    {
        randTo = Math.abs(randFrom) + randTo;
        return (float) randFrom + (float) (Math.random() * (float) randTo);
    }
    private static float random(double randFrom, double randTo, boolean center)
    {
        randFrom = randFrom / 2;
        randTo = randTo / 2;
        if (center)
        {
            randTo = Math.abs(randFrom) + randTo;
            return (float) randFrom + (float) (Math.random() * (float) randTo);
        }
        return 0;
    }

    private static ParticleEffect effect(ParticleType particleType)
    {
        return Sponge.getRegistry().createBuilder(ParticleEffect.Builder.class).type(particleType).build();
    }

    public static void black(Player p) {
        for (int i = 0; i < 5; i++)
        {
            p.spawnParticles(effect(ParticleTypes.SMOKE), p.getLocation().getPosition().add(
                    random(-1,1),
                    random(-0.2,0.2),
                    random(-1, 1)
            ));
            p.spawnParticles(effect(ParticleTypes.SMOKE), p.getLocation().getPosition().add(
                    random(-1,1, true),
                    random(-0.2,0.2),
                    random(-1, 1, true)
            ));
            p.spawnParticles(effect(ParticleTypes.SMOKE), p.getLocation().getPosition().add(
                    random(-1,1, true),
                    random(-0.2,0.2),
                    random(-1, 1, true)
            ));
        }
    }

    public static void red(Player p) {
        for (int i = 0; i < 5; i++)
        {
            p.spawnParticles(effect(ParticleTypes.FLAME), p.getLocation().getPosition().add(
                    random(-1,1),
                    random(-0.2,0.4),
                    random(-1, 1)
            ));
            p.spawnParticles(effect(ParticleTypes.FLAME), p.getLocation().getPosition().add(
                    random(-1,1, true),
                    random(-0.2,0.4),
                    random(-1, 1, true)
            ));
            p.spawnParticles(effect(ParticleTypes.FLAME), p.getLocation().getPosition().add(
                    random(-1,1, true),
                    random(-0.2,0.4),
                    random(-1, 1, true)
            ));
        }
    }

    public static void cloud(Player p)
    {
        for (int i = 0; i < 3; i++)
        {
            p.spawnParticles(effect(ParticleTypes.CLOUD), p.getLocation().getPosition().add(
                    random(-1,1),
                    random(-0.2,0.4),
                    random(-1, 1)
            ));
            p.spawnParticles(effect(ParticleTypes.CLOUD), p.getLocation().getPosition().add(
                    random(-1,1, true),
                    random(-0.2,0.4),
                    random(-1, 1, true)
            ));
            p.spawnParticles(effect(ParticleTypes.CLOUD), p.getLocation().getPosition().add(
                    random(-1,1, true),
                    random(-0.2,0.4),
                    random(-1, 1, true)
            ));
        }
    }
}