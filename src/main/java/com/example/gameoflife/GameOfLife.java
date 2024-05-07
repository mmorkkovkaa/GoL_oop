package com.example.gameoflife;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameOfLife extends Application {

    private static final int CELL_SIZE = 10;
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private static final int ROWS = HEIGHT / CELL_SIZE;
    private static final int COLUMNS = WIDTH / CELL_SIZE;

    private boolean[][] grid = new boolean[ROWS][COLUMNS];

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.setCenter(canvas);

        initializeGrid();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            updateGrid();
            drawGrid(gc);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        // Установка голубого цвета фона
        scene.setFill(Color.LIGHTBLUE);

        primaryStage.setTitle("Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeGrid() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                grid[i][j] = Math.random() < 0.1; // initial random setup
            }
        }
    }

    private void updateGrid() {
        boolean[][] newGrid = new boolean[ROWS][COLUMNS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                int neighbors = countNeighbors(i, j);
                if (grid[i][j]) {
                    newGrid[i][j] = (neighbors == 2 || neighbors == 3);
                } else {
                    newGrid[i][j] = (neighbors == 3);
                }
            }
        }

        grid = newGrid;
    }

    private int countNeighbors(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int neighborRow = row + i;
                int neighborCol = col + j;
                if (neighborRow >= 0 && neighborRow < ROWS && neighborCol >= 0 && neighborCol < COLUMNS) {
                    if (!(i == 0 && j == 0) && grid[neighborRow][neighborCol]) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private void drawGrid(GraphicsContext gc) {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (grid[i][j]) {
                    // Розовый цвет для живых клеток
                    gc.setFill(Color.DEEPPINK);

                } else {
                    gc.setFill(Color.WHITE);
                }
                gc.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

