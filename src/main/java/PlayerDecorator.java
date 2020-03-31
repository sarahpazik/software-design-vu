public abstract class PlayerDecorator implements Player{
    protected Player decoratedPlayer;

    public PlayerDecorator(Player p){
        this.decoratedPlayer = p;
    }

    public boolean checkTime() {
        return this.decoratedPlayer.checkTime();
    }

}
