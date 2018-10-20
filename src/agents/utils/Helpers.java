package agents.utils;

import java.io.IOException;
import java.util.logging.Logger;

public class Helpers {
    public static final int NEW_AGENTS = 1;
    public static final int TERMINATE_AGENTS = 2;

    public static Logger getLogger(String className, String fileName) {
        FileLogger fileLog = new FileLogger(className, fileName);
        try {
            fileLog.setup();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileLog.logger();
    }

}
