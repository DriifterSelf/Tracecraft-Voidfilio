package net.minecraft.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;

public class GuiGraphics {
    private final PoseStack pose = new PoseStack();

    public PoseStack pose() {
        return pose;
    }

    public void drawString(Object font, Component text, int x, int y, int color) {}
    public void drawString(Object font, String text, int x, int y, int color) {}
    public void drawString(Object font, Object text, int x, int y, int color) {}
    public void drawCenteredString(Object font, Object text, int x, int y, int color) {}
    public void fill(int minX, int minY, int maxX, int maxY, int color) {}
    public void blit(Object p1, Object p2, int p3, int p4, float p5, float p6, int p7, int p8, int p9, int p10) {}
}
