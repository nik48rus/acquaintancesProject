package socialNetwork.Pttrns;

public class Patterns {
    // порождающий
    public static void singleton(){
        Singleton singleton = Singleton.getInstance();
        singleton.setUp();
    }
    // структурный
    public static void adapter() {
        PBank pbank = new PBank();
        pbank.getBalance();
        PBankAdapter abank = new PBankAdapter(new ABank());
        abank.getBalance();
    }
    // поведенческий
    public static void mediator() {
        User user1 = new User("user1");
        User user2 = new User("user2");
        user1.sendMessage("message1");
        user2.sendMessage("message2");
    }
}
