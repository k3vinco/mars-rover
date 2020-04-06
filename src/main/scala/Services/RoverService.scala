package Services

import Entities._
import monocle.macros.syntax.lens._

object RoverService {

  def rotateClockwise(rover: Rover): Rover = {
    val newOrientation = rover.orientation match {
      case Up => Right
      case Down => Left
      case Left => Up
      case Right => Down
    }
    rover.copy(orientation = newOrientation)
  }

  def rotateAntiClockwise(rover: Rover): Rover = {
    val newOrientation = rover.orientation match {
      case Up => Left
      case Down => Right
      case Left => Down
      case Right => Up
    }
    rover.copy(orientation = newOrientation)
  }

  def moveForward(rover: Rover, grid: Grid): Rover = rover.orientation match {
    case Up => rover.lens(_.position.y).modify(prev => if (prev == grid.maxY) 0 else prev + 1)
    case Down => rover.lens(_.position.y).modify(prev => if (prev == 0) grid.maxY else prev - 1)
    case Left => rover.lens(_.position.x).modify(prev => if (prev == 0) grid.maxX else prev - 1)
    case Right => rover.lens(_.position.x).modify(prev => if (prev == grid.maxX) 0 else prev + 1)
  }

  def autopilot(rover: Rover, grid: Grid, destination: Position): List[Position] = {
    val leftToRightDistance = destination.x - rover.position.x
    val destXCoordInLeftGridMirror = destination.x - grid.maxX - 1
    val rightToLeftDistance = rover.position.x - destXCoordInLeftGridMirror

    val bottomToTopDistance = destination.y - rover.position.y
    val destYCoordInLeftGridMirror = destination.y - grid.maxY - 1
    val topToBottomDistance = rover.position.y - destYCoordInLeftGridMirror

    val xCoords = if (leftToRightDistance < rightToLeftDistance) {
      (rover.position.x to destination.x).toList
    } else {
      (rover.position.x to destXCoordInLeftGridMirror by -1).toList
    }

    val yCoords = if (bottomToTopDistance < topToBottomDistance) {
      (rover.position.y to destination.y).toList
    } else {
      (rover.position.x to destYCoordInLeftGridMirror by -1).toList
    }

    val adjustedXCoords = {
      val extraYMovesOverXMoves = yCoords.length - xCoords.length
      if (extraYMovesOverXMoves > 0) {
        List.fill(extraYMovesOverXMoves)(xCoords.head) ::: xCoords
      } else {
        xCoords
      }
    }

    val adjustedYCoords = {
      val extraXMovesOverYMoves = xCoords.length - yCoords.length
      if (extraXMovesOverYMoves > 0) {
        List.fill(extraXMovesOverYMoves)(yCoords.head) ::: yCoords
      } else {
        yCoords
      }
    }

    val xPath = adjustedXCoords.map(x => Position(x, rover.position.y))
    val yPath = adjustedYCoords.map(y => Position(destination.x, y))

    xPath ::: yPath
  }
}
