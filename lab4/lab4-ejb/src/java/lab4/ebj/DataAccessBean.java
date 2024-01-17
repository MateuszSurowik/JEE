package lab4.ebj;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Singleton;

@Singleton
public class DataAccessBean {

    private String ostatnieDane;
    private List<String> additionalDataList; // Store processed additional data

    public DataAccessBean() {
        additionalDataList = new ArrayList<>();
    }

    public String getOstatnieDane() {
        return ostatnieDane;
    }

    public void setOstatnieDane(String userAgent, Date currentDate) {
        // Your existing code to set last access data
        this.ostatnieDane = "Last Access Data: " + userAgent + " at " + formatDate(currentDate);

        // Let's add a new business method
        processAdditionalData(userAgent, currentDate);
    }

    // New business method
    public String getAdditionalData() {
        // Return the processed additional data (time of last access)
        if (additionalDataList.isEmpty()) {
            return "No additional data available.";
        }
        return "Last Access Time: " + formatDate(new Date());
    }

    private void processAdditionalData(String userAgent, Date currentDate) {
        // Log user agent and timestamp
        String additionalData = "User Agent: " + userAgent + ", Last Access Time: " + formatDate(currentDate);

        // Store the processed additional data
        additionalDataList.add(additionalData);
    }

    private String formatDate(Date date) {
        // Format the date for better readability
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
