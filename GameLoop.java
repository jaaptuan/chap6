package se233.chapter6.controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import se233.chapter6.model.Direction;
import se233.chapter6.model.Food;
import se233.chapter6.model.Snake;
import se233.chapter6.view.GameStage;

public class GameLoop implements Runnable {
    private final GameStage gameStage;
    private final Snake snake;
    private final Food food;
    private final Stage stage; // TASK 2: Added Stage to update the title with the score.
    private final float interval = 1000.0f / 10;
    private boolean running;

    public GameLoop(GameStage gameStage, Snake snake, Food food, Stage stage) {
        this.snake = snake;
        this.gameStage = gameStage;
        this.food = food;
        this.stage = stage; // TASK 2
        running = true;
    }

    private void keyProcess() {
        KeyCode curKey = gameStage.getKey();
        if (curKey == null) return; // Do nothing if no key is pressed

        Direction curDirection = snake.getDirection();
        if (curKey == KeyCode.UP && curDirection != Direction.DOWN)
            snake.setDirection(Direction.UP);
        else if (curKey == KeyCode.DOWN && curDirection != Direction.UP)
            snake.setDirection(Direction.DOWN);
        else if (curKey == KeyCode.LEFT && curDirection != Direction.RIGHT)
            snake.setDirection(Direction.LEFT);
        else if (curKey == KeyCode.RIGHT && curDirection != Direction.LEFT)
            snake.setDirection(Direction.RIGHT);
    }

    private void update() {
        snake.move();
    }

    private void checkCollision() {
        if (snake.collided(food)) {
            // TASK 3: Pass the food object to grow to handle scoring correctly.
            snake.grow(food);
            food.respawn();
        }

        // โชว์ให้ขึ้น game over
        if (snake.checkDead()) {
            running = false;
            // Show Game Over dialog on JavaFX Application Thread
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText(null);
                alert.setContentText("Game Over! Final Score: " + snake.getScore());
                alert.showAndWait();
                Platform.exit();
            });
        }
    }

    private void redraw() {
        // TASK 2: Update window title with the current score.
        Platform.runLater(() -> stage.setTitle("Snake Game | Score: " + snake.getScore()));
        gameStage.render(snake, food);
    }

    @Override
    public void run() {
        while (running) {
            keyProcess();
            update();
            checkCollision();
            redraw();
            try {
                Thread.sleep((long) interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}