import javax.persistence.*;
import java.util.List;

/**
 * Created by user-pc on 17.07.2016.
 */
public class MenuHelper {
    public EntityManager em = Persistence.createEntityManagerFactory("Bank").createEntityManager();

    public void addUser(User user, Account acc1, Account acc2, Account acc3) {
        em.getTransaction().begin();
        user.addAccount(acc1);
        em.persist(acc1);
        user.addAccount(acc2);
        em.persist(acc2);
        user.addAccount(acc3);
        em.persist(acc3);
        em.persist(user);
        em.getTransaction().commit();
    }

    public void addRates(Rate... rates) {
        em.getTransaction().begin();
        for (Rate rate : rates) {
            em.persist(rate);
        }
        em.getTransaction().commit();
    }

    public void addMoneyToAccount(String name, Currency currency, float money) {
        System.out.println("\nсчета до пополнения:");
        User user = getUser("Name1");
        viewAccounts(user);
        em.getTransaction().begin();
        try {
            User userDB = em.find(User.class, user.getId());
            Account account = userDB.getAccount(currency);
            if (money >= 0) account.addMoney(money);
            else account.withdrawMoney(money);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
        System.out.println("\nсчета после пополнения:");
        viewAccounts(user);
    }
    public void convert(String name, Currency cur1, Currency cur2, float amount) {
        User user = getUser(name);
        Account from = user.getAccount(cur1);
        Account to = user.getAccount(cur2);
        System.out.println("Счета до конвертации");
        System.out.println(from);
        System.out.println(to);
        Rate rate=getRate(cur1, cur2);
        System.out.println(rate);
        em.getTransaction().begin();
        try {
            from.withdrawMoney(amount);
            to.addMoney(amount*rate.getValue());
            Transaction transaction1 = new Transaction(from, to, cur1, -amount);
            Transaction transaction2 = new Transaction(from, to, cur2, amount*rate.getValue());
            transaction1.setUserFrom(user.getName());
            transaction2.setUserFrom(user.getName());
            transaction1.setUserTo(user.getName());
            transaction2.setUserTo(user.getName());
            System.out.println(transaction1);
            System.out.println(transaction2);
            em.persist(transaction1);
            em.persist(transaction2);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
        System.out.println("счета после конвертации:");
        System.out.println(from);
        System.out.println(to);

    }

    private Rate getRate(Currency cur1, Currency cur2) {
        Rate result = null;
        try {
            Query query = em.createQuery("SELECT r FROM Rate r WHERE ((r.currency1 = :cur1) AND (r.currency2 = :cur2))", Rate.class);
            query.setParameter("cur1", cur1);
            query.setParameter("cur2", cur2);
            result = (Rate) query.getSingleResult();
        } catch (NoResultException ex) {
            System.err.println("User not found");
        } catch (NonUniqueResultException ex) {
            System.err.println("Non unique result!");
        }
        return result;
    }

    public void transaction(String out, String in, Currency currency, float amount) {
        System.out.println("счета до перевода:");
        User userFrom = getUser(out);
        User userTo = getUser(in);
        System.out.println(userFrom.getAccount(currency));
        System.out.println(userTo.getAccount(currency));
        Account from = userFrom.getAccount(currency);
        Account to = userTo.getAccount(currency);
        em.getTransaction().begin();
        try {
            from.withdrawMoney(amount);
            to.addMoney(amount);
            Transaction transaction = new Transaction(from, to, currency, amount);
            transaction.setUserFrom(userFrom.getName());
            transaction.setUserTo(userTo.getName());
            System.out.println(transaction);
            em.persist(transaction);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
        System.out.println("счета после перевода:");
        System.out.println(userFrom.getAccount(currency));
        System.out.println(userTo.getAccount(currency));
    }

    public void delete(long id) {
        em.getTransaction().begin();
        em.remove(get(id));
        em.getTransaction().commit();
    }

    public User get(long id) {
        return em.find(User.class, id);
    }

    public List<User> getAll(){
        TypedQuery<User> userList = em.createQuery("SELECT u FROM User u", User.class);
        return userList.getResultList();
    }


    public User getUser(String name) {
        User user = null;
        try {
            Query query = em.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class);
            query.setParameter("name", name);
            user = (User) query.getSingleResult();
        } catch (NoResultException ex) {
            System.err.println("User not found");
        } catch (NonUniqueResultException ex) {
            System.err.println("Non unique result!");
        }
        return user;
    }

    public void viewAccounts(User user) {
        for (Account account : user.getAccounts()) {
            System.out.println(account);
        }
    }


    public void getBalance(String name) {
        User user = getUser(name);
        viewAccounts(user);
        float balance = 0f;
        Rate usdUah = getRate(Currency.USD, Currency.UAH);
        Rate eurUah = getRate(Currency.EUR, Currency.UAH);
        for (Account acc : user.getAccounts()) {
            if (acc.getCurrency().equals(Currency.UAH)) balance += acc.getBalance();
            if (acc.getCurrency().equals(Currency.USD)) balance += acc.getBalance() * usdUah.getValue();
            if (acc.getCurrency().equals(Currency.EUR)) balance += acc.getBalance() * eurUah.getValue();
        }
        System.out.println(String.format("%.2f UAH", balance));
    }
}
