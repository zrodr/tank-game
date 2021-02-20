package edu.proj.tankgame.model;

import java.util.Arrays;

public class TankWallHandler extends DefaultHandler {
    public TankWallHandler() {
        super();
    }

    @Override
    public void performCollision(GameState gameState, Entity e1, Entity e2){
        if(e1 instanceof Tank && e2 instanceof Wall){
            entities[0] = e1;
            entities[1] = e2;
        } else if (e1 instanceof Wall && e2 instanceof Tank){
            entities[1] = e1;
            entities[0] = e2;
        } else return;

        double[] distances = new double[4];
        distances[0] = entities[0].getXBound() - entities[1].getX();
        distances[1] = entities[1].getXBound() - entities[0].getX();
        distances[2] = entities[0].getYBound() - entities[1].getY();
        distances[3] = entities[1].getYBound() - entities[0].getY();

        double dist = Arrays.stream(distances).min().getAsDouble();

        if(dist == distances[0]) {
            entities[0].setX(entities[0].getX() - dist);
        }
        else if(dist == distances[1]){
            entities[0].setX(entities[0].getX() + dist);
        }
        else if(dist == distances[2]){
            entities[0].setY(entities[0].getY() - dist);
        }
        else{
            entities[0].setY(entities[0].getY() + dist);
        }
    }
}
