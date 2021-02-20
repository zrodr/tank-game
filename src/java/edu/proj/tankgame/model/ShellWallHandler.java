package edu.proj.tankgame.model;

public class ShellWallHandler extends DefaultHandler {
    public ShellWallHandler() {
        super();
    }

    @Override
    public void performCollision(GameState gameState, Entity e1, Entity e2) {
        if(e1 instanceof Shell && e2 instanceof Wall){
            entities[0] = e1;
            entities[1] = e2;
        } else if (e1 instanceof Wall && e2 instanceof Shell){
            entities[1] = e1;
            entities[0] = e2;
        } else return;

        gameState.markEntityForRemoval(entities[0].getId());
        ((Wall)entities[1]).takeDamage(gameState);
    }
}
