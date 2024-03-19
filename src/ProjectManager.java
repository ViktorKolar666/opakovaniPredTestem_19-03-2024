import javax.swing.*;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProjectManager extends JFrame {
    public final String FILE_NAME = "projects.txt";
    public final List<Projekt> projects = new ArrayList<>();
    public ProjectManager(){
        readFile(FILE_NAME);
    }

    public void readFile(String fileName){
        try (Scanner sc = new Scanner(new File(fileName))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split("#");
                if (parts.length >= 6) {
                    String name = parts[0];
                    int workers = Integer.parseInt(parts[1]);
                    BigDecimal estimatedCost = new BigDecimal(parts[2]);
                    boolean isFinished = parts[3].equals("ano");
                    LocalDate beginDate = LocalDate.parse(parts[4]);
                    int rating = Integer.parseInt(parts[5]);
                    projects.add(new Projekt(name, workers, estimatedCost, isFinished, beginDate, rating));
                } else {
                    System.out.println("Chybný formát řádku: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveFile(String fileName){
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            for (Projekt projekt : projects) {
                pw.println(projekt.getName() + "#" + projekt.getWorkers()
                        + "#" + projekt.getEstimatedCost() + "#" + (projekt.isFinished() ? "ano" : "ne")
                        + "#" + projekt.getBeginDate() + "#" + projekt.getRating());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Chyba při zápisu do souboru: " +
                            e.getLocalizedMessage(),
                    "Chyba", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setProject(int index, Projekt projekt){
        projects.set(index, projekt);
        saveFile(FILE_NAME);
    }

    public void addProject(Projekt projekt){
        projects.add(projekt);
    }

    public int countProject(){
        return projects.size();
    }

    public Projekt getProject(int index){
        return projects.get(index);
    }

    public BigDecimal getTotalCost() {
        BigDecimal totalCost = new BigDecimal(0);
        for (Projekt projekt : projects) {
            totalCost = totalCost.add(projekt.getEstimatedCost());
        }
        return totalCost;
    }
}
