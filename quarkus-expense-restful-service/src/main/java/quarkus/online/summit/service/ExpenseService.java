package quarkus.online.summit.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import quarkus.online.summit.entity.Expense;

@ApplicationScoped
public class ExpenseService {

    public List<Expense> list() {
        return Expense.listAll();
    }

    public Expense create(final Expense expense) {
        expense.persist();
        return expense;
    }

    public Boolean delete(final UUID uuid) {
        return Expense.deleteById(uuid);
    }

    public void update(final Expense expense) {
        final Map<String, Object> params = new HashMap<>();
        params.put("name", expense.name);
        params.put("amount", expense.amount);
        params.put("description", expense.description);
        params.put("creationDate", expense.creationDate);
        Expense.update(
                "update expense e set e.name = :name, e.amount = :amount, e.description = :description, e.creationDate = :creationDate where e.id = :id",
                params);
    }
}
