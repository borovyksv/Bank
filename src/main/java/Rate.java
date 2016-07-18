import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Exchange_Rates")
public class Rate {

    @Override
    public String toString() {
        return "Rate{" +
                "" + currency1 +
                ", " + currency2 +
                ", " + value +
                '}';
    }

    @Id
    @GeneratedValue
    private long id;
    @Enumerated(EnumType.STRING)
    private Currency currency1;
    @Enumerated(EnumType.STRING)
    private Currency currency2;

    private float value;

    public Rate() {
    }

    public Rate(Currency currency1, Currency currency2, float value) {
        this.currency1 = currency1;
        this.currency2 = currency2;
        this.value = value;
    }

    public Currency getCurrency1() {
        return currency1;
    }

    public void setCurrency1(Currency currency1) {
        this.currency1 = currency1;
    }

    public Currency getCurrency2() {
        return currency2;
    }

    public void setCurrency2(Currency currency2) {
        this.currency2 = currency2;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
