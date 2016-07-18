import javax.persistence.*;

@Entity
@Table(name = "Transactions")
public class Transaction {

    @Id
    @GeneratedValue
    private long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acc_from_id")
    private Account from;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acc_to_id")
    private Account to;


    private String userFrom;
    private String userTo;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private float amount;

    public Transaction(Account from, Account to, Currency currency, float amount) {
        this.from = from;
        this.to = to;
        this.currency = currency;
        this.amount = amount;
    }
    public Transaction() {

    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;

    }

    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "" + from.getUser().getName() +
                "==>>" + to.getUser().getName() +
                ", " + amount +
                ", " + currency +
                '}';
    }

    public Account getFrom() {
        return from;
    }

    public void setFrom(Account from) {
        this.from = from;
    }

    public Account getTo() {
        return to;
    }

    public void setTo(Account to) {
        this.to = to;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }


}
