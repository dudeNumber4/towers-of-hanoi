# Towers of Hanoi
Just a fun project to help learn java better.
Towers of Hanoi is the famous game where you move rings on "towers" from a start tower to a target tower by also using a temporary tower.  The game starts like this:
```
   |          |          |
   =          |          |
  ===         |          |
=======       |          |
 start       temp      target
```
And ends like this:  
```
   |          |          |
   |          |          =
   |          |         ===
   |          |       =======
 start       temp      target
```
 Play visually here: https://www.mathsisfun.com/games/towerofhanoi.html

## Dependencies
* https://mvnrepository.com/artifact/wtf.g4s8/tuples/0.1.2  Crazy strange that a language as old as java doesn't have tuples.
* Lombok  
Maven command: `mvn dependency:resolve -U`

## Running
I've just been running this in jshell.  From jshell location (Java 9+):  
`jshell 'towers.java' --class-path 'path_to/tuples-0.1.2.jar'`  
`new Towers().start()`  
Game essentially limited to 8 rings, otherwise it'll take a very long time and spew too much output.
