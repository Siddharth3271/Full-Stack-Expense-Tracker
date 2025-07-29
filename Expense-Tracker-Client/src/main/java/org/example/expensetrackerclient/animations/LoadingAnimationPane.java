package org.example.expensetrackerclient.animations;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LoadingAnimationPane extends Pane {
    private final Rectangle rectangle;

    private final Label loadingLabel;

    public LoadingAnimationPane(Double screenWidth,Double screenHeight){
        rectangle=new Rectangle(screenWidth,screenHeight, Color.BLACK);

        loadingLabel=new Label("Loading.....");
        loadingLabel.setLayoutX(screenWidth/2);
        loadingLabel.setLayoutY(screenHeight/2);

        setMinSize(screenWidth,screenHeight);
        getChildren().addAll(rectangle,loadingLabel);
        setVisible(false);
        setManaged(false);

    }
}
