package main.java.util;

import jdk.jfr.Description;
import main.java.util.Plugin;

import java.io.File;

@Description("Без обработки")
public class EmptyPlugin implements Plugin {
    @Override
    @Description("")
    public void encode(File file, String key) {

    }

    @Override
    public void decode(File file, String key) {

    }
}
