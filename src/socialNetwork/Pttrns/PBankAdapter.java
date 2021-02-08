package socialNetwork.Pttrns;

class PBankAdapter extends PBank {
    private ABank abank;
    public PBankAdapter(ABank abank) {
        this.abank = abank;
    }
    public void getBalance() {
        abank.getBalance();
    }
}

class PBank {
    private int balance;
    public PBank() { balance = 100; }
    public void getBalance() {
        System.out.println("PBank balance = " + balance);
    }
}

class ABank {
    private int balance;
    public ABank() { balance = 200; }
    public void getBalance() {
        System.out.println("ABank balance = " + balance);
    }
}