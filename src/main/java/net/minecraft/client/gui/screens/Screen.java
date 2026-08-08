package net.minecraft.client.gui.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Font;
import java.util.List;
import java.util.ArrayList;

public abstract class Screen {
    public int width;
    public int height;
    public Font font = new Font();
    public final List<Object> renderables = new ArrayList<>();

    public Screen(Object title) {}

    protected void init() {}
    protected void clearWidgets() {}
    protected <T> T addRenderableWidget(T widget) { return widget; }
    public void onClose() {}
    public void tick() {}
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {}
    public void renderBackground(GuiGraphics context, int mouseX, int mouseY, float delta) {}
    public boolean mouseClicked(double mouseX, double mouseY, int button) { return false; }
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) { return false; }
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) { return false; }
}
