package utils;

import dao.ProjectDAO;
import java.util.Base64;
import dao.TaskDAO;
import java.util.logging.Logger;
import model.Project;

public class SecurityUtil {
    private static final String SALT = "your-secret-salt";
    private static final Logger LOGGER = Logger.getLogger(SecurityUtil.class.getName());

    public static String encodeProjectId(String projectID) {
        if (projectID == null || projectID.trim().isEmpty()) {
            LOGGER.warning("ProjectID is null or empty");
            return null;
        }

        try {
            String encodedId = Base64.getUrlEncoder()
                    .encodeToString((SALT + projectID).getBytes());
            LOGGER.info("Successfully encoded project ID: " + projectID);
            return encodedId;
        } catch (Exception e) {
            LOGGER.severe("Error encoding project ID: " + e.getMessage());
            return null;
        }
    }

    public static String decodeProjectId(String encodedProjectID) {
        if (encodedProjectID == null || encodedProjectID.trim().isEmpty()) {
            LOGGER.warning("Encoded ProjectID is null or empty");
            return null;
        }

        try {
            String projectID = ProjectDAO.getInstance().getOriginalProjectId(encodedProjectID);
            LOGGER.info("Successfully decoded project ID from: " + encodedProjectID + " to: " + projectID);
            return projectID;
        } catch (Exception e) {
            LOGGER.severe("Error decoding project ID: " + encodedProjectID + " - " + e.getMessage());
            return null;
        }
    }

    public static String encodeTaskId(String taskId) {
        return TaskDAO.getInstance().getEncodedTaskId(taskId);
    }

    public static String decodeTaskId(String encodedTaskId) {
        return TaskDAO.getInstance().getOriginalTaskId(encodedTaskId);
    }
}