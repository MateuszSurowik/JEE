package beans;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.http.Part;

@Named
@RequestScoped
public class uploadBean {
    private String name;
    private Part file;
    private String description;

    @EJB
    private ejb.PPTFileController pptFileController;

    public String getName() {
        return name;
    }

    public void setName(String fileName) {
        this.name = fileName;
    }

    // Getters and setters for filePath
    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    // Getters and setters for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void uploadFile() {
        pptFileController.setFile(file);
        pptFileController.setName(name);
        pptFileController.uploadPPTFile();
    }
}
