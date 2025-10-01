package se233.chapter6.model;

import javafx.geometry.Point2D;
import se233.chapter6.view.GameStage;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private Direction direction;
    private Point2D head;
    private Point2D prev_tail;
    private List<Point2D> body;
    private int score;

    public Snake(Point2D position) {
        direction = Direction.DOWN;
        body = new ArrayList<>();
        this.head = position;
        this.body.add(this.head);
        this.score = 0;
    }

    public void move() {
        head = head.add(direction.current);
        prev_tail = body.remove(body.size() - 1);
        body.add(0, head);
    }

    // ข้อ 2 & 3
    public void grow(Food food) {
        if (food.isSpecial()) {
            score += 5; // ข้อ 3 คะแนน 5
        } else {
            score += 1; // ข้อ 2 คนแนน 1
        }
        body.add(prev_tail);
    }

    public boolean collided(Food food) {
        return head.equals(food.getPosition());
    }

    public int getLength() {
        return body.size();
    }

    public List<Point2D> getBody() {
        return body;
    }
    public void setDirection(Direction newDirection) { //
        if (body.size() > 1 && newDirection == this.direction.opposite()) { // อันนี้ถ้าพึ่งเริ่มก็ย้อนได้
            return;
        }
        this.direction = newDirection;
    }

    public Direction getDirection() {
        return direction;
    }

    public Point2D getHead() {
        return head;
    }

    public int getScore() {
        return score;
    }

    public boolean checkDead() {
        boolean isOutOfBound = head.getX() < 0 || head.getY() < 0 ||
                head.getX() >= GameStage.WIDTH || head.getY() >= GameStage.HEIGHT;
        boolean isHitBody = body.lastIndexOf(head) > 0;
        return isOutOfBound || isHitBody;
    }
}