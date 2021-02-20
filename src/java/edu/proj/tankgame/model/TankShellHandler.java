package edu.proj.tankgame.model;

public class TankShellHandler extends DefaultHandler  {

    public TankShellHandler() {
        super();
    }

    @Override
    public void performCollision(GameState gameState, Entity e1, Entity e2) {
        if(e1 instanceof Tank && e2 instanceof Shell){
            entities[0] = e1;
            entities[1] = e2;
        } else if (e1 instanceof Shell && e2 instanceof Tank){
            entities[1] = e1;
            entities[0] = e2;
        } else return;

        if( !((Shell)entities[1]).getOwner().equals(entities[0].getId()) ){
            ((Tank) entities[0]).takeDamage(gameState);
            gameState.markEntityForRemoval(entities[1].getId());
        }
    }
}
