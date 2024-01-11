# Mancala Game

Created by Braedan Chappel of the University of Guelph

## Table of Contents
1. Introduction
2. Running the program

## Introduction

"Mancala GUI" is a graphical user interface (GUI) version of the classic board game, Mancala. 
This interactive game allows players to engage with the Mancala game in a visually appealing and user-friendly environment. 
The game follows the traditional rules of Mancala: players take turns picking up all of the stones in one of their pits, and "sowing" them, 
dropping one in each following pit counter-clockwise. The objective is to collect the most stones in your store by the end of the game.

## Running

Although gradle is not needed, java has to be intalled on the machine running the program. Below i've included the versions that I used to develop this game.

Here are the versions of Gradle and Java that are used for this project, I'm not entirely sure back/forward compatibility with other versions but they will probably work.

    Gradle: 
        ------------------------------------------------------------
        Gradle 8.1.1
        ------------------------------------------------------------

        Build time:   2023-04-21 12:31:26 UTC
        Revision:     1cf537a851c635c364a4214885f8b9798051175b

        Kotlin:       1.8.10
        Groovy:       3.0.15
        Ant:          Apache Ant(TM) version 1.10.11 compiled on July 10 2021
        JVM:          17.0.7 (Private Build 17.0.7+7-Ubuntu-0ubuntu120.04)
        OS:           Linux 5.15.133.1-microsoft-standard-WSL2 amd64

    Java:
        openjdk 17.0.7 2023-04-18
        OpenJDK Runtime Environment (build 17.0.7+7-Ubuntu-0ubuntu120.04)
        OpenJDK 64-Bit Server VM (build 17.0.7+7-Ubuntu-0ubuntu120.04, mixed mode, sharing)


The game can be run by using the .jar file

Ensure the present working directory is in the root project folder: /MancalaGame
Enter this command: 'java -cp build/classes/java/main/ ui.GUI'







