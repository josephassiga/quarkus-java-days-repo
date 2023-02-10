package quarkus.online.summit.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Expense extends PanacheEntity {

    public String name;
    public BigDecimal amount;
    public String description;
    public LocalDateTime creationDate;

    public static List<Expense> findCurrent() {
        return list("creationDate", LocalDateTime.now());
    }
}
