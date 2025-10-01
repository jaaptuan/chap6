package se233.chapter6;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.geometry.Point2D;
import se233.chapter6.model.Direction;
import se233.chapter6.model.Food;
import se233.chapter6.model.Snake;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeTest {
    private Snake snake;

    @BeforeEach
    public void setUp() {
        snake = new Snake(new Point2D(0, 0));
    }

    @Test
    public void initialPosition_shouldBe_atOrigin() {
        assertEquals(new Point2D(0, 0), snake.getHead());
    }

    @Test
    public void move_afterInitialized_headShouldBeInDownwardDirection() {
        snake.move();
        assertEquals(new Point2D(0, 1), snake.getHead());
    }

    @Test
    public void collided_withFood_shouldBeDetected() {
        Food food = new Food(new Point2D(0, 0));
        assertTrue(snake.collided(food));
    }


    @Test
    public void eatFood_shouldIncrementScoreByOne() { //อาหาร1คะแนน
        Food normalFood = new Food(new Point2D(1, 1));
        while(normalFood.isSpecial()) {
            normalFood.respawn();
        }
        assertEquals(0, snake.getScore()); //ก่อนกินควรเป็น 0
        snake.grow(normalFood); // กินและ
        assertEquals(1, snake.getScore()); // กินแล้วมี1คะแนน
    }

    @Test
    public void eatSpecialFood_shouldIncrementScoreByFive() { //อาหาร5คะแนน
        Food specialFood = new Food(new Point2D(1, 1));
        // Force the food to be special for this test
        try {
            java.lang.reflect.Field isSpecialField = Food.class.getDeclaredField("isSpecial");
            isSpecialField.setAccessible(true);
            isSpecialField.set(specialFood, true);
        } catch (Exception e) {
            fail("Reflection failed for isSpecial field.");
        }
        assertEquals(0, snake.getScore());
        snake.grow(specialFood);
        assertEquals(5, snake.getScore());
    }

    @Test
    public void setDirection_whenTryingToReverse_directionShouldNotChange() {
        // 1.เคลื่อนที่ลง
        assertEquals(1, snake.getLength());
        assertEquals(Direction.DOWN, snake.getDirection());

        // 2.เช็คให้เคลื่อนที่ขึ้น
        snake.setDirection(Direction.UP);
        assertEquals(Direction.UP, snake.getDirection(), "Should allow changing from DOWN to UP");

        // 3. ทำให้งูยาวกว่า 1 ช่อง
        Food testFood = new Food(new Point2D(99, 99));
        snake.grow(testFood);
        assertEquals(2, snake.getLength());

        // 4. พยายามย้อนกลับจาก UP → DOWN (ควรป้องกัน)
        snake.setDirection(Direction.DOWN);
        assertEquals(Direction.UP, snake.getDirection(), "Should prevent reversal from UP to DOWN");

        // 5. ทดสอบทิศทางอื่นที่ไมใช่การย้อนกลับ (ควรเปลี่ยนได้)
        snake.setDirection(Direction.LEFT);
        assertEquals(Direction.LEFT, snake.getDirection(), "Should allow changing to LEFT");
    }


}