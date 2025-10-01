package se233.chapter6.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.chapter6.model.Food;
import se233.chapter6.model.Snake;
import static javafx.scene.paint.Color.*; // Import all colors

public class GameStage extends Pane {
    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;
    public static final int TILE_SIZE = 10;
    private Canvas canvas;
    private KeyCode key;

    public GameStage() {
        this.setHeight(TILE_SIZE * HEIGHT);
        this.setWidth(WIDTH * TILE_SIZE);
        canvas = new Canvas(WIDTH * TILE_SIZE, TILE_SIZE * HEIGHT);
        this.getChildren().add(canvas);
    }

    public void render(Snake snake, Food food) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, WIDTH * TILE_SIZE, TILE_SIZE * HEIGHT);

        gc.setFill(BLUE);
        snake.getBody().forEach(p -> {
            gc.fillRect(p.getX() * TILE_SIZE, p.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        });

        if (food.isSpecial()) {
            gc.setFill(GREEN); // พิเศษสีเขียว
        } else {
            gc.setFill(RED);   // ธรรมดาสีแดง
        }
        gc.fillRect(food.getPosition().getX() * TILE_SIZE, food.getPosition().getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    public KeyCode getKey() {
        return key;
    }

    public void setKey(KeyCode key) {
        this.key = key;
    }
}