public class Main {
    public static void main(String[] args) {

        MenuHelper helper = new MenuHelper();
        System.out.println("\n\n\n/////////////////////////////Добавляем клиентов");

        for (int i = 1; i < 6; i++) {
            helper.addUser(
                    new User("Name" + i, "+38 093 123 45 9" + i),
                    new Account(Currency.UAH, 10_000+i*1000),
                    new Account(Currency.EUR, 5_000+i*100),
                    new Account(Currency.USD, 8_000+i*100)
            );
        }

        System.out.println("\n\n\n/////////////////////////////Добавляем валютные пары");
            helper.addRates(new Rate(Currency.USD, Currency.UAH, 24.8601f),
            new Rate(Currency.USD, Currency.EUR, 0.9056f),
            new Rate(Currency.UAH, Currency.EUR, 0.0364f),
            new Rate(Currency.UAH, Currency.USD, 0.0402f),
            new Rate(Currency.EUR, Currency.UAH, 27.4493f),
            new Rate(Currency.EUR, Currency.USD, 1.1041f));

        System.out.println("\n\n/////////////////////////////пополнения счета <Name1> в нужной валюте <5_999 UAH>");
        helper.addMoneyToAccount("Name1", Currency.UAH, 5_999);



        System.out.println("\n\n/////////////////////////////перевод средств");
        System.out.println("\n/////#1 перевод Name1 --> Name2 : 777 USD");
        helper.transaction("Name1", "Name2", Currency.USD, 777);
        System.out.println("\n/////#2 перевод Name1 --> Name3 : 777 UAH");
        helper.transaction("Name1", "Name3", Currency.UAH, 777);
        System.out.println("\n/////#3 перевод Name3 --> Name2 : 777 EUR");
        helper.transaction("Name3", "Name2", Currency.EUR, 777);

        System.out.println("\n\n/////////////////////////////конвертация валюты по курсу");
        System.out.println("\n/////Name1 USD --> UAH  500");
        helper.convert("Name1", Currency.USD, Currency.UAH, 500);
        System.out.println("\n/////Name2 UAH --> USD  5000");
        helper.convert("Name2", Currency.UAH, Currency.USD, 5000);
        System.out.println("\n/////Name3 EUR --> UAH  300");
        helper.convert("Name3", Currency.EUR, Currency.UAH, 300);

        System.out.println("\n\n/////////////////////////////Суммарные средства на счету Name 1:");

        helper.getBalance("Name1");







    }
}
