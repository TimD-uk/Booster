import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;

public class JetpackTask
{
    public static void start(Player p)
    {
        p.setVelocity(p.getVelocity().add(new Vector3d(0, 10, 0)));
        p.offer(Keys.IS_ELYTRA_FLYING, true);
        ParticleManager.cloud(p);
        p.clearTitle();
    }
}