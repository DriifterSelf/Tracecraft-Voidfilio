package com.mojang.blaze3d.vertex;

import java.util.stream.Stream;

public class VertexFormatElement {
    public enum Type {
        FLOAT, UINT, INT, SHORT, USHORT, BYTE, UBYTE
    }

    public enum Usage {
        GENERIC, POSITION, NORMAL, COLOR, UV
    }

    public static VertexFormatElement register(int id, int index, Type type, Usage usage, int count) {
        return new VertexFormatElement();
    }

    public int mask() { return 1; }
    public int id() { return 0; }
    public static Stream<VertexFormatElement> elementsFromMask(int mask) { return Stream.empty(); }
}
