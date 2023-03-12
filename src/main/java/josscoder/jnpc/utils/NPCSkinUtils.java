package josscoder.jnpc.utils;

import cn.nukkit.entity.data.Skin;
import josscoder.jnpc.exception.NPCException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class NPCSkinUtils {

    public static Skin from(final Path skinPath) {
        Skin skin = new Skin();

        if (Files.notExists(skinPath) || !Files.isRegularFile(skinPath)) {
            throw new NPCException("Invalid Skin Data");
        }

        BufferedImage skinData = null;

        try {
            skinData = ImageIO.read(skinPath.toFile());
        } catch (IOException e) {
            System.out.println("Invalid Skin");
        }

        skin.setSkinData(skinData);
        skin.setPremium(true);
        skin.setSkinId("Standard_Custom");
        skin.setGeometryName("geometry.humanoid.custom");
        skin.setTrusted(true);

        return skin;
    }

    public static Skin from(Path skinPath, Path geometryPath, String geometryName) {
        Skin skin = new Skin();

        if (Files.notExists(skinPath) || !Files.isRegularFile(skinPath) || Files.notExists(geometryPath) || !Files.isRegularFile(geometryPath)) {
            throw new NPCException("Invalid Skin Data");
        }

        BufferedImage skinData;
        String geometry;

        try {
            skinData = ImageIO.read(skinPath.toFile());
            geometry = new String(Files.readAllBytes(geometryPath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new NPCException("Invalid Skin Data: " +  e.getMessage());
        }

        skin.setSkinData(skinData);
        skin.setPremium(true);
        skin.setSkinId("Standard_Custom");
        skin.setGeometryName(geometryName);
        skin.setGeometryData(geometry);
        skin.setTrusted(true);

        return skin;
    }
}