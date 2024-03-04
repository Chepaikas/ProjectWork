package factory.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class LoggerManager {
    private static final Logger logger = (Logger) LogManager.getLogger("ProjectWork");

    public static Logger getLogger() {
        return logger;
    }
}