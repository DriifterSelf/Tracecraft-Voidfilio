package net.minecraft.client.gui.font;

public class FontTexture {
    public static class Node {
        public int x = 0;
        public int y = 0;
        public Node insert(Object glyph) { return new Node(); }
    }
    public Node root = new Node();
}
