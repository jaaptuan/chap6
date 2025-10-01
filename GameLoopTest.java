package se233.chapter6;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import se233.chapter6.controller.GameLoop;
import se233.chapter6.model.Direction;
import se233.chapter6.model.Food;
import se233.chapter6.model.Snake;
import se233.chapter6.view.GameStage;

// TASK 1 & 3: Corrected Mockito imports.
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.junit.jupiter.api.Assertions.*;

public class GameLoopTest {
    private GameStage gameStage;
    private Snake snake;
    private Food food;
    private GameLoop gameLoop;

    @BeforeEach
    public void setUp() {
        // Since GameLoop is now modified to take a Stage, we'll pass null for testing purposes
        // as we are not testing the GUI update part here directly. For tests that need it,
        // we might need to mock the Stage or use a more advanced setup.
        gameStage = new GameStage();
        snake = new Snake(new Point2D(0, 0));
        food = new Food(new Point2D(0, 1));
        gameLoop = new GameLoop(gameStage, snake, food, null); // Pass null for Stage in test
    }

    private void clockTickHelper() throws Exception {
        // We need to simulate the `update()` call as well now.
        ReflectionHelper.invokeMethod(gameLoop, "keyProcess", new Class<?>[0], new Object[0]);
        ReflectionHelper.invokeMethod(gameLoop, "update", new Class<?>[0], new Object[0]);
        ReflectionHelper.invokeMethod(gameLoop, "checkCollision", new Class<?>[0], new Object[0]);
        ReflectionHelper.invokeMethod(gameLoop, "redraw", new Class<?>[0], new Object[0]);
    }

    @Test
    public void keyProcess_pressRight_snakeTurnRight() throws Exception {
        ReflectionHelper.setField(gameStage, "key", KeyCode.RIGHT);
        // The snake's default direction is DOWN, which is not opposite to RIGHT.
        clockTickHelper();
        Direction currentDirection = (Direction) ReflectionHelper.getField(snake, "direction");
        assertEquals(Direction.RIGHT, currentDirection);
    }

    @Test
    public void collided_snakeEatFood_shouldGrow() throws Exception {
        // Move the snake down to eat the food at (0, 1)
        snake.setDirection(Direction.DOWN);
        clockTickHelper();
        // The snake should have grown. Initial length is 1.
        assertTrue(snake.getLength() > 1, "Snake length should be greater than 1 after eating.");

        assertNotEquals(new Point2D(0, 1), food.getPosition(), "Food should respawn at a new position.");
    }

    @Test
    public void collided_snakeHitBorder_shouldDie() throws Exception {
        snake.setDirection(Direction.LEFT);
        clockTickHelper();
        Boolean running = (Boolean) ReflectionHelper.getField(gameLoop, "running");
        assertFalse(running, "The 'running' flag should be false after hitting a wall.");
    }

    @Test
    public void redraw_calledThreeTimes_snakeAndFoodShouldRenderThreeTimes() throws Exception {
        GameStage mockGameStage = Mockito.mock(GameStage.class);
        Snake mockSnake = Mockito.mock(Snake.class);
        Food mockFood = Mockito.mock(Food.class);
        GameLoop gameLoopWithMocks = new GameLoop(mockGameStage, mockSnake, mockFood, null);

        ReflectionHelper.invokeMethod(gameLoopWithMocks, "redraw", new Class<?>[0], new Object[0]);
        ReflectionHelper.invokeMethod(gameLoopWithMocks, "redraw", new Class<?>[0], new Object[0]);
        ReflectionHelper.invokeMethod(gameLoopWithMocks, "redraw", new Class<?>[0], new Object[0]);
        verify(mockGameStage, times(3)).render(mockSnake, mockFood);
    }
}