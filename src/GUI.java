import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {
    private JPanel panel;
    private JTextField TFname;
    private JTextField TFworkers;
    private JCheckBox ChBfinished;
    private JTextField TFbeginDate;
    private JRadioButton RBworst;
    private JRadioButton RBmid;
    private JRadioButton RBbest;
    private JButton BTNprev;
    private JButton BTNnext;
    private JButton BTNsave;

    private int actualIndex = 0;
    private ProjectManager projectManager = new ProjectManager();

    public GUI(ProjectManager projectManager) {
        this.projectManager = projectManager;
        components();
        menu();
        loadProject();
        BTNprev.addActionListener(e -> prevProject());
        BTNnext.addActionListener(e -> nextProject());
        BTNsave.addActionListener(e -> saveProject());
        ButtonGroup group = new ButtonGroup();
        group.add(RBworst);
        group.add(RBmid);
        group.add(RBbest);
    }

    private void components() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setBounds(100, 100, 500, 500);
        setTitle("Projekty");
    }

    private void menu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Projekt");
        JMenuItem menuItem1 = new JMenuItem("Přidej další");
        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectManager.addProject(new Projekt("", 0, new BigDecimal("0"), false, LocalDate.now(), 1));
                actualIndex = projectManager.countProject() - 1;
                loadProject();
            }
        });
        menu.add(menuItem1);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        JMenuItem menuItem2 = new JMenuItem("Statistika");
        menuBar.add(menuItem2);
        menuItem2.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Celkové náklady: " + projectManager.getTotalCost(), "Statistika", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public void saveProject(){
        String name = TFname.getText();
        int workers = Integer.parseInt(TFworkers.getText());
        boolean isFinished = ChBfinished.isSelected();
        LocalDate beginDate = LocalDate.parse(TFbeginDate.getText());
        int rating = 1;
        if (RBmid.isSelected()) {
            rating = 2;
        } else if (RBbest.isSelected()) {
            rating = 3;
        }
        Projekt actualProject = projectManager.getProject(actualIndex);
        actualProject.setName(name);
        actualProject.setWorkers(workers);
        actualProject.setFinished(isFinished);
        actualProject.setBeginDate(beginDate);
        actualProject.setRating(rating);
        projectManager.setProject(actualIndex, actualProject);
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            projectManager.saveFile(file.getPath());
        }
    }

    public void prevProject(){
        if (actualIndex > 0) {
            actualIndex--;
            loadProject();
        }
        BTNprev.setEnabled(actualIndex > 0);
    }

    public void nextProject(){
        if (actualIndex < projectManager.countProject() - 1) {
            actualIndex++;
            loadProject();
        }
    }
    public void loadProject()
    {
        BTNprev.setEnabled(actualIndex > 0);
        BTNnext.setEnabled(actualIndex < projectManager.countProject() - 1);

        if (projectManager.countProject() == 0) {
            TFname.setText("");
            TFworkers.setText("");
            ChBfinished.setSelected(false);
            TFbeginDate.setText("");
            RBworst.setSelected(true);
        }else {
            Projekt actualProject = projectManager.getProject(actualIndex);
            TFname.setText(actualProject.getName());
            TFworkers.setText(String.valueOf(actualProject.getWorkers()));
            ChBfinished.setSelected(actualProject.isFinished());
            TFbeginDate.setText(actualProject.getBeginDate().toString());

            switch (actualProject.getRating()) {
                case 1:
                    RBworst.setSelected(true);
                    break;
                case 2:
                    RBmid.setSelected(true);
                    break;
                case 3:
                    RBbest.setSelected(true);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        ProjectManager pm = new ProjectManager();

        pm.projects.add(new Projekt("Projekt 1", 5, new BigDecimal("100000"), false, LocalDate.now(), 1));
        pm.projects.add(new Projekt("Projekt 2", 3, new BigDecimal("50000"), true, LocalDate.now(), 2));
        pm.projects.add(new Projekt("Projekt 3", 7, new BigDecimal("200000"), false, LocalDate.now(), 3));

        GUI gui = new GUI(pm);
        gui.setVisible(true);
    }
}