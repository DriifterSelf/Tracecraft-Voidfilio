package com.mojang.blaze3d.vertex;

import java.util.Collections;
import java.util.List;

public class VertexFormatElement {
    public static Object POSITION = new Object();

    public enum Type {
        FLOAT, UINT, INT, SHORT, USHORT, BYTE, UBYTE
    }

    public enum Usage {
        POSITION, NORMAL, COLOR, UV, GENERIC
    }

    public int id() { return 0; }
    public int mask() { return 1; }

    public static VertexFormatElement register(int id, int index, Object type, Object usage, int count) {
        return new VertexFormatElement();
    }

    public static List<VertexFormatElement> elementsFromMask(int mask) {
        return Collections.emptyList();
    }
}
