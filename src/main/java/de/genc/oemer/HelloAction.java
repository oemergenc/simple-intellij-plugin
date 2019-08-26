package de.genc.oemer;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import java.io.InputStream;

public class HelloAction extends AnAction {
    public HelloAction() {
        super("Hello");
    }

    public void actionPerformed(AnActionEvent event) {
        String classpathStr = System.getProperty("java.class.path");
        System.out.println(classpathStr);
        InputStream resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("my.properties");
        if (resourceStream != null) {
            System.out.println("Found the file");
        } else {
            System.out.println("Did not found the file");
        }
    }
}