package socialNetwork.Pttrns;

class Mediator {
    public static void sendMessage(User user, String msg){
        System.out.println(user.getName() + ": " + msg);
    }
}
class User {
    private String name;
    public User(String name){
        this.name  = name;
    }
    public String getName() {
        return name;
    }
    public void sendMessage(String msg){
        Mediator.sendMessage(this, msg);
    }
}
