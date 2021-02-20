package edu.proj.tankgame.model;

public class ShellShellHandler extends DefaultHandler {

    public ShellShellHandler(){
        super();
    }

    @Override
    public void performCollision(GameState gameState, Entity e1, Entity e2) {
        if(e1 instanceof Shell && e2 instanceof Shell){
            entities[0] = e1;
            entities[1] = e2;

            gameState.markEntityForRemoval(entities[0].getId());
            gameState.markEntityForRemoval(entities[1].getId());
        }
    }
}
