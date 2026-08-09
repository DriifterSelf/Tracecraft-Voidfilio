package com.mojang.blaze3d.vertex;

import java.util.Collections;
import java.util.List;

public class VertexFormat {
    public enum IndexType {
        SHORT, INT;

        public static IndexType least(int vertexCount) {
            return SHORT;
        }
    }

    public static class Builder {
        public Builder add(String name, VertexFormatElement element) { return this; }
        public Builder add(VertexFormatElement element) { return this; }
        public Builder padding(int padding) { return this; }
        public VertexFormat build() { return new VertexFormat(); }
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getOffset(Object element) { return 0; }
    public String getElementName(Object element) { return ""; }
    public List<String> getElementAttributeNames() { return Collections.emptyList(); }
    public int getVertexSize() { return 36; }
    public int getElementsMask() { return 0xFFFF; }
    public int[] getOffsetsByElement() { return new int[32]; }
    public boolean contains(Object element) { return true; }
}
