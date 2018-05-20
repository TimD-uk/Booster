import com.flowpowered.math.vector.Vector3d;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.equipment.EquipmentTypes;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.util.Direction;

import java.util.HashMap;
import java.util.Optional;

@Plugin(id = "flybooster", name = "FlyBooster", version = "1.0", authors = {"hybrid"})
public class Booster
{
    public static HashMap<String, Integer> playersWithParticle = new HashMap<>();
    public static HashMap<String, Boolean> playerSaidFly = new HashMap<>();
    public static Booster instance;
    public static int ticksJump = 50;

    @Listener
    public void init(GameInitializationEvent e) {
        instance = this;
    }

    @Inject
    private Logger logger;

    public Logger getLogger()
    {
        return logger;
    }
    @Listener
    public void onStart(GameStartingServerEvent e)
    {
        Task task = Task.builder()
                .delayTicks(1)
                .intervalTicks(1)
                .name("Test scheduler")
                .execute((task1 -> {
                    for (Player p : Sponge.getServer().getOnlinePlayers())
                    {
                        playerEvent(p);
                    }
                }))
                .submit(this);
        this.getLogger().info("Plugin loaded");
    }


    public void playerEvent(Player p)
    {
        if (p.get(Keys.IS_ELYTRA_FLYING).orElse(false))
        {
            if (!p.hasPermission("booster.fly"))
                return;
            Vector3d rotate = p.getRotation();
            Direction direction = Direction.getClosest(rotate);

            Vector3d vector = new Vector3d(0, direction.asOffset().getY(), 0);
            p.setVelocity(p.getVelocity().add(vector.mul(0,0.025,0)));
        }


        Optional<ItemStack> optChestPlateItem = p.getEquipped(EquipmentTypes.CHESTPLATE);
        if (!optChestPlateItem.isPresent())
            return;
        ItemStack ChestPlateItem = optChestPlateItem.get();

        if (ChestPlateItem.getType() != ItemTypes.ELYTRA)
            return;

        if (!p.hasPermission("booster.start"))
            return;

        if (p.get(Keys.IS_SNEAKING).orElse(false) && p.isOnGround())
        {
            if (playersWithParticle.containsKey(p.getName()))
            {
                if (playersWithParticle.get(p.getName()) >= ticksJump) {
                    if (!playerSaidFly.containsKey(p.getName()))
                    {
                        playerSaidFly.put(p.getName(), true);
                        p.sendTitle(Title.builder()
                                .title(Text.of(TextColors.GOLD, "Вы готовы к полёту!"))
                                .subtitle(Text.of(TextColors.YELLOW, "Отпустите клавишу Shift, чтобы взлететь"))
                                .fadeIn(5)
                                .stay(40)
                                .fadeOut(15)
                                .build());
                        p.playSound(SoundTypes.BLOCK_ANVIL_USE, p.getPosition(),0.5);
                    }
                    ParticleManager.red(p);
                } else {
                    playersWithParticle.put(p.getName(), playersWithParticle.get(p.getName()) + 1);
                    ParticleManager.black(p);
                }
            } else {
                playersWithParticle.put(p.getName(), 1);
                p.playSound(SoundTypes.ENTITY_TNT_PRIMED, p.getPosition(),0.5);
                ParticleManager.black(p);
            }
        } else {
            if (playersWithParticle.containsKey(p.getName()) && playersWithParticle.get(p.getName()) >= ticksJump)
            {
                JetpackTask.start(p);
                playersWithParticle.remove(p.getName());
                playerSaidFly.remove(p.getName());
            } else if(playersWithParticle.containsKey(p.getName()))
            {
                playersWithParticle.remove(p.getName());
                p.stopSounds();
            } else {
                p.stopSounds();
            }
        }
    }
}