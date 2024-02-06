/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.servlet.http.Part;
import jpa.Emotes;
import sun.misc.IOUtils;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PPTFileController {
    @Inject
    private restful.FileResource fileResource;
    
    private Part file;
    private String fileName;
    private String description;
    
    private byte[] fileContent;
    
    public void setFile(Part file) {
        this.file = file;
    }

    public void setName(String fileName) {
        this.fileName = fileName;
    }

   

    public void uploadPPTFile() {
        jpa.Emotes Emotes = new jpa.Emotes();
            
        String fileName = Paths.get(file.getSubmittedFileName()).getFileName().toString();
        try (InputStream inputStream = file.getInputStream()) {
            fileContent = IOUtils.readFully(inputStream, -1, true);
            Emotes.setEmote(fileContent);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Emotes.setName(fileName);

        try {
            // You might want to trigger the RESTful service here if needed
            fileResource.uploadPPTFile(Emotes);

            // Log or print a success message
            System.out.println("File uploaded successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            // Print any exception during file upload
            System.out.println("Error during file upload: " + e.getMessage());
        }
    }
}


