import java.math.BigDecimal;
import java.time.LocalDate;

public class Projekt {
    private String name;
    private int workers;
    private BigDecimal estimatedCost;
    private boolean isFinished;
    private LocalDate beginDate;
    private int rating;

    public Projekt(String name, int workers, boolean isFinished, LocalDate beginDate, int rating) {
        this.name = name;
        this.workers = workers;
        this.isFinished = isFinished;
        this.beginDate = beginDate;
        this.rating = rating;
    }
    public Projekt(String name, int workers,BigDecimal estimatedCost, boolean isFinished, LocalDate beginDate, int rating) {
        this.name = name;
        this.workers = workers;
        this.estimatedCost = estimatedCost;
        this.isFinished = isFinished;
        this.beginDate = beginDate;
        this.rating = rating;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWorkers() {
        return workers;
    }

    public void setWorkers(int workers) {
        this.workers = workers;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public BigDecimal getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(BigDecimal estimatedCost) {
        this.estimatedCost = estimatedCost;
    }
}