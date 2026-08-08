package com.mojang.blaze3d.vertex;

import java.util.List;
import java.util.Collections;

public class VertexFormat {
    public enum IndexType {
        SHORT, INT;
        public static IndexType least(int vertexCount) { return SHORT; }
    }

    public static class Builder {
        public Builder add(String name, VertexFormatElement element) { return this; }
        public Builder padding(int bytes) { return this; }
        public VertexFormat build() { return new VertexFormat(); }
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<String> getElementAttributeNames() {
        return Collections.emptyList();
    }
    public int getVertexSize() {
        return 32;
    }
    public int getElementsMask() { return 0; }
    public int[] getOffsetsByElement() { return new int[32]; }
    public boolean contains(Object element) { return true; }
    public String getElementName(VertexFormatElement element) { return ""; }
}
