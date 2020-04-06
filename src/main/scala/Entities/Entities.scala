package Entities

sealed trait Orientation
case object Up extends Orientation
case object Down extends Orientation
case object Left extends Orientation
case object Right extends Orientation

case class Position(x: Int, y: Int)
case class Rover(orientation: Orientation, position: Position)
case class Grid(maxX: Int, maxY: Int)