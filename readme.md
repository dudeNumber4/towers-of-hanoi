# Towers of Hanoi
Just a fun project to help learn java better.
Towers of Hanoi is the famous game where you move rings on "towers" from a start tower to a target tower by also using a temporary tower with the main rule that all rings must move onto either an empty tower or one with a larger ring than the one you're moving on it.  The game starts like this:
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
 
## Algorithm
* Consider that the rings are numbered from smallest = 1 to largest = total count of rings.
* Odd / even rings always travel in opposite directions.
* A ring can only move when it moves to a tower that has a larger ring size at the top.
* The direction each ring travels changes depending on the odd/even # of the ring.
  * Odd rings: next move is always to the immediate left (circling around when no more left).
  * Even rings: next move is always to the immediate right.
* One more rule: when deciding which piece to move next, scan the tops of each pile and move the largest ring which can legally move following the above rule.

## Dependencies
* https://mvnrepository.com/artifact/wtf.g4s8/tuples/0.1.2  Crazy strange that a language as old as java doesn't have tuples.
* Lombok  

## Running
Game currently limited to 8 rings, otherwise it'll take a very long time.
