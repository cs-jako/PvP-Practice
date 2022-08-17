package net.crazy.pvplib.library;

import net.crazy.pvplib.Library;

public class MCLogger {
    public enum LogType {
        INFO("§7[§9INFO§7] §9"),
        DEBUG("§7[§eDEBUG§7] §e"),
        WARNING("§f[§cWARNING§7] §c"),
        ERROR("§7[§4ERROR§7] §4");

        public final String prefix;
        LogType(String prefix) {
            this.prefix = prefix;
        }

    }

    public static void log(LogType logType, String log) {
        Library.instance.getServer().getConsoleSender().sendMessage(logType.prefix + log);
    }
}
