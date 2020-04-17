module MICHEL.SURIN.main {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;

    opens controller;
    opens engine;
    opens gui;
    opens items;
    opens tools;
    opens world;
}