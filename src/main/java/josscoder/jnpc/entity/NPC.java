package josscoder.jnpc.entity;

import cn.nukkit.Player;
import cn.nukkit.level.Location;
import cn.nukkit.math.Vector3;
import josscoder.jnpc.factory.NPCFactory;
import josscoder.jnpc.settings.AttributeSettings;
import josscoder.jnpc.settings.HumanAttributes;
import josscoder.jnpc.settings.TagSettings;
import lombok.Getter;

@Getter
public class NPC extends Spawnable {

    private final TagSettings tagSettings;
    private final HumanAttributes humanSettings;

    public NPC(AttributeSettings attributeSettings,HumanAttributes humanSettings) {
        super(attributeSettings, humanSettings);
        this.humanSettings = humanSettings;

        this.tagSettings = new TagSettings();
        this.tagSettings.setLinkedNPC(this);
    }

    public static NPC create(AttributeSettings attributeSettings, HumanAttributes humanSettings) {
        NPC npc = new NPC(attributeSettings, humanSettings);

        NPCFactory.getInstance().store(npc);

        return npc;
    }

    public static NPC create(AttributeSettings attributeSettings) {
        return NPC.create(attributeSettings, null);
    }

    public void lookAt(Vector3 vector3) {
        lookAt(vector3, false);
    }

    public void lookAt(Vector3 vector3, boolean update) {
        Location location = attributeSettings.getLocation();

        double xDist = (vector3.getX() - location.getX());
        double zDist = (vector3.getZ() - location.getZ());

        location.yaw = Math.atan2(zDist, xDist) / Math.PI * 180 - 90;

        if (location.yaw < 0) {
            location.yaw += 360.0;
        }

        if (update) {
            move(location);
        }
    }

    @Override
    public void show(Player player) {
        super.show(player);
        spawnLines(player);
    }

    @Override
    public void hide(Player player) {
        super.hide(player);
        hideLines(player);
    }

    public void spawnLines(Player player) {
        tagSettings.getLines().forEach(line -> line.show(player));
    }

    public void hideLines(Player player) {
        tagSettings.getLines().forEach(line -> line.hide(player));
    }

    public void reloadLines() {
        playerList.forEach(player -> {
            hideLines(player);
            spawnLines(player);
        });
    }
}