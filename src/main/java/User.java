import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Column(name = "accounts_id")
    private List<Account> accounts = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    @Id
    @GeneratedValue
    private long id;

    public long getId() {
        return id;
    }

    private String name;
    private String phone;



    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public void addAccount(Account account) {
        account.setUser(this);
        accounts.add(account);
    }

    public Account getAccount(Currency currency) {
        Account result = null;
        for (Account account : accounts) {
            if (account.getCurrency().equals(currency)) result=account;
        }
        return result;

    }



}
