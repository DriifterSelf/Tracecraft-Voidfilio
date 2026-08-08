package net.minecraft.server.packs.resources;

import java.io.InputStream;
import java.util.function.Supplier;

public class Resource {
    public Resource() {}
    public Resource(Object p1, Object p2, Object p3) {}
    public Resource(Object p1, Supplier<InputStream> p2, Object p3) {}
    public Resource(Object p1, Supplier<InputStream> p2) {}
    public InputStream open() { return null; }
}
