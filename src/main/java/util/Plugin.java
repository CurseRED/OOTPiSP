package main.java.util;

import java.io.File;

public interface Plugin {

    public void encode(File file, String key);
    public void decode(File file, String key);
}
