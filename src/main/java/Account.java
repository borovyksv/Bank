import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Accounts")
public class Account {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    User user;

    @Override
    public String toString() {
        return "" +
                "" + user.getName() +
                ", " + balance +
                ", " + currency;
    }

    @Id
    @GeneratedValue
    private long id;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private float balance;

    @OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
    List<Transaction> out = new ArrayList<>();

    @OneToMany(mappedBy = "to", cascade = CascadeType.ALL)
    List<Transaction> in = new ArrayList<>();



    public Account() {
    }
    public Account(Currency currency, float balance) {
        this.currency = currency;
        this.balance = balance;
    }

    public void addMoney(float amount) {
        balance += amount;
    }
    public void withdrawMoney(float amount) {
        balance -= amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Currency getCurrency() {
        return currency;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float money) {
        this.balance = money;
    }


}
