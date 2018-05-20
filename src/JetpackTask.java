import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;

public class JetpackTask
{
    public static void start(Player p)
    {
        p.setVelocity(p.getVelocity().add(new Vector3d(0, 5, 0)));
        ParticleManager.cloud(p);
        Task task = Task.builder()
                .execute(() -> p.offer(Keys.IS_ELYTRA_FLYING, true))
                .delayTicks(10)
                .submit(Booster.instance);
        p.clearTitle();
    }
}