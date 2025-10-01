package se233.chapter6.model;

import javafx.geometry.Point2D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.chapter6.view.GameStage;

import java.util.Random;

public class Food {
    private Point2D position;
    private Random rn;
    private boolean isSpecial;
    private static final Logger logger = LogManager.getLogger(Food.class);

    public Food(Point2D position) {
        this.rn = new Random();
        this.position = position;
        this.isSpecial = rn.nextDouble() < 0.2;
    }

    public Food() { // ทำสุ่มอาหาร5คะแนน
        this.rn = new Random();
        this.position = new Point2D(rn.nextInt(GameStage.WIDTH), rn.nextInt(GameStage.HEIGHT));
        this.isSpecial = rn.nextDouble() < 0.2; // 20% สุ่มเกิด
    }

    public void respawn() {
        Point2D prev_position = this.position;
        do {
            this.position = new Point2D(rn.nextInt(GameStage.WIDTH), rn.nextInt(GameStage.HEIGHT));
        } while (prev_position.equals(this.position));
        logger.info("food: x:{} y:{}",this.position.getX(), this.position.getY());
        this.isSpecial = rn.nextDouble() < 0.2; // 20% chance
    }

    public Point2D getPosition() {
        return this.position;
    }
    public boolean isSpecial() {
        return isSpecial;
    }
}