import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named(value = "obliczenia")
@RequestScoped
public class obliczenia {
    private String znak;
    private int liczba1;
    private int liczba2;
    private int wynik;

    public String getZnak() {
        return znak;
    }

    public int getLiczba1() {
        return liczba1;
    }

    public int getLiczba2() {
        return liczba2;
    }

    public int getWynik() {
        return wynik;
    }

    public void setZnak(String znak) {
        this.znak = znak;
    }

    public void setLiczba1(int liczba1) {
        this.liczba1 = liczba1;
    }

    public void setLiczba2(int liczba2) {
        this.liczba2 = liczba2;
    }

    public void oblicz() {
        switch (znak) {
            case "+":
                wynik = liczba1 + liczba2;
                zapiszDoPlikuXML();
                break;
            case "-":
                wynik = liczba1 - liczba2;
                zapiszDoPlikuXML();
                break;
            case "*":
                wynik = liczba1 * liczba2;
                zapiszDoPlikuXML();
                break;
            case "/":
                if (liczba2 != 0) {
                    wynik = liczba1 / liczba2;
                    zapiszDoPlikuXML();
                } else {
                    // Handle division by zero error (optional)
                    // You can set an error message or take appropriate action.
                    // For simplicity, we just set the result to 0.
                    wynik = 0;
                    znak = "Error: Division by zero";
                }
                break;
            default:
                // Obsługa nieznanej operacji (opcjonalne)
                wynik = 0;
                znak = "Error: Unknown operator";
        }
    }

private void zapiszDoPlikuXML() {
    try {
        File file = new File("/home/student/NetBeansProjects/lab3Solo/web/calcData.xml");

        // If the file does not exist, create a new XML file
        if (!file.exists()) {
            try (FileWriter newFileWriter = new FileWriter(file)) {
                newFileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                newFileWriter.write("<rownania>\n");
                newFileWriter.write("</rownania>");
            }
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }

            // Find the position to insert the new equation
            int insertIndex = content.indexOf("</rownania>");

            // Add a new equation to the XML file at the appropriate position
            String newEquation =
                    "    <rownanie id=\"" + System.currentTimeMillis() + "\">\n" +
                    "        <liczba1>" + liczba1 + "</liczba1>\n" +
                    "        <znak>" + znak + "</znak>\n" +
                    "        <liczba2>" + liczba2 + "</liczba2>\n" +
                    "        <wynik>" + wynik + "</wynik>\n" +
                    "    </rownanie>\n";

            content.insert(insertIndex, newEquation);

            // Write the updated content back to the file
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content.toString());
            }

        } catch (Exception e) {
            // Handle or log the exception
            e.printStackTrace();
            System.out.println("Error writing to file: " + e.getMessage());
        }
    } catch (Exception e) {
        // Handle or log the exception
        e.printStackTrace();
        System.out.println("Error creating file: " + e.getMessage());
    }
}




    public obliczenia() {
    }
}
