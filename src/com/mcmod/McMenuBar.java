package com.mcmod;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;

import com.mcmod.api.Mod;
import com.mcmod.debug.McDebug;

public class McMenuBar extends JMenuBar {

    private static final long serialVersionUID = 1L;
    private List<TogglableModMenuItem> togglableMods = new ArrayList<TogglableModMenuItem>();
    private List<ModMenuItem> mods = new ArrayList<ModMenuItem>();
    private Map<JCheckBoxMenuItem, McDebug> debugs = new HashMap<JCheckBoxMenuItem, McDebug>();
    private File modFolder;

    public McMenuBar() {
        JMenu menu = new JMenu("Mods");
        try {
            modFolder = new File(System.getProperty("user.home") + "/McMod/mods/");
            System.out.println(modFolder.mkdirs() ? "Created folder." : "Couldn't create D:");
            System.out.println(modFolder.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            loadMods();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (ModMenuItem mod : mods) {
            menu.add(mod);
        }
        menu.add(new JSeparator());

        for (TogglableModMenuItem mod : togglableMods) {
            menu.add(mod);
        }

        add(menu);

        JMenu debugs = new JMenu("Debug");
        for (McDebug debug : McDebug.getDebugs()) {
            debugs.add(createDebugMenu(debug));
        }

        add(debugs);
    }

    public JCheckBoxMenuItem createDebugMenu(McDebug debug) {
        JCheckBoxMenuItem item = new JCheckBoxMenuItem(debug.getName());

        debugs.put(item, debug);

        return item;
    }

    public void loadMods() throws Exception {
        CodeSource cs = getClass().getProtectionDomain().getCodeSource();
        if (cs == null) {
            throw new RuntimeException("CodeSource for JAR == null");
        }
        loadModsFrom(cs.getLocation(), getModNames(cs.getLocation()));
        loadModsFrom(modFolder.toURI().toURL(), modFolder.list());
    }

    public void loadModsFrom(URL url, String[] from) throws Exception {
        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});

        for (String name : from) {
            if (name.endsWith(".class")) {
                Class<?> c;
                c = classLoader.loadClass(name.replace(".class", ""));

                if (Mod.class.isAssignableFrom(c)) {
                    Mod m = (Mod) c.newInstance();

                    if (m.isTogglable()) {
                        togglableMods.add(new TogglableModMenuItem(m));
                    } else {
                        mods.add(new ModMenuItem(m));
                    }
                }
            }
        }
    }

    private String[] getModNames(URL url) throws Exception {
        // Stupid workaround for weird pathnames; see also:
        // http://weblogs.java.net/blog/2007/04/25/how-convert-javaneturl-javaiofile
        File f;
        try {
            f = new File(url.toURI());
        } catch (URISyntaxException e) {
            f = new File(url.getPath());
        }

        if (url.toString().endsWith("/")) {
            // Not running from JAR.
            return f.list();
        } else {
            // Running from JAR.
            JarFile jf = new JarFile(f);
            Enumeration<JarEntry> entries = jf.entries();
            List<String> files = new ArrayList<String>();
            while (entries.hasMoreElements()) {
                JarEntry je = entries.nextElement();
                String name = je.getName();
                if (!je.isDirectory() && name.split("/").length == 1) {
                    files.add(name);
                }
            }
            return files.toArray(new String[files.size()]);
        }
    }

    public List<TogglableModMenuItem> getActiveMods() {
        List<TogglableModMenuItem> items = new ArrayList<TogglableModMenuItem>();

        for (TogglableModMenuItem item : this.togglableMods) {
            if (item.isSelected()) {
                items.add(item);
            }
        }

        return items;
    }

    public List<McDebug> getActiveDebugs() {
        List<McDebug> debugs = new ArrayList<McDebug>();

        for (JCheckBoxMenuItem item : this.debugs.keySet()) {
            if (item.isSelected()) {
                debugs.add(this.debugs.get(item));
            }
        }

        return debugs;
    }
}
