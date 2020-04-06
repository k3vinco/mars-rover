package Services

import Entities._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ServicesTests extends AnyWordSpec with Matchers {

  private def testClockwiseRotation(initialOrientation: Orientation, expectedOrientation: Orientation) = {
    val origin = Position(0, 0)
    val testCase = Rover(initialOrientation, origin)
    val expected = Rover(expectedOrientation, origin)

    val actual = RoverService.rotateClockwise(testCase)

    actual shouldBe expected
  }

  private def testAntiClockwiseRotation(initialOrientation: Orientation, expectedOrientation: Orientation) = {
    val origin = Position(0, 0)
    val testCase = Rover(initialOrientation, origin)
    val expected = Rover(expectedOrientation, origin)

    val actual = RoverService.rotateAntiClockwise(testCase)

    actual shouldBe expected
  }

  "A rover" when {
    "facing up" should {
      "face right on turning clockwise" in {
        testClockwiseRotation(
          initialOrientation = Up,
          expectedOrientation = Right
        )
      }

      "face left on turning anticlockwise" in {
        testAntiClockwiseRotation(
          initialOrientation = Up,
          expectedOrientation = Left
        )
      }
    }

    "facing right" should {
      "face down on turning clockwise" in {
        testClockwiseRotation(
          initialOrientation = Right,
          expectedOrientation = Down
        )
      }

      "face up on turning anticlockwise" in {
        testAntiClockwiseRotation(
          initialOrientation = Right,
          expectedOrientation = Up
        )
      }
    }

    "facing down" should {
      "face left on turning clockwise" in {
        testClockwiseRotation(
          initialOrientation = Down,
          expectedOrientation = Left
        )
      }

      "face right on turning anticlockwise" in {
        testAntiClockwiseRotation(
          initialOrientation = Down,
          expectedOrientation = Right
        )
      }
    }
    "facing left" should {
      "face up on turning clockwise" in {
        testClockwiseRotation(
          initialOrientation = Left,
          expectedOrientation = Up)
      }

      "face down on turning anticlockwise" in {
        testAntiClockwiseRotation(
          initialOrientation = Left,
          expectedOrientation = Down)
      }
    }

    "on the right edge of the grid and facing right" should {
      "move to the left edge of the grid on moving forward" in {
        val grid = Grid(2, 2)
        val testCase = Rover(Right, Position(2, 0))
        val expected = Rover(Right, Position(0, 0))

        val actual = RoverService.moveForward(testCase, grid)

        actual shouldBe expected
      }
    }

    "on the left edge of the grid and facing left" should {
      "move to the right edge of the grid on moving forward" in {
        val grid = Grid(2, 2)
        val testCase = Rover(Left, Position(0, 0))
        val expected = Rover(Left, Position(2, 0))

        val actual = RoverService.moveForward(testCase, grid)

        actual shouldBe expected
      }
    }

    "on the top edge of the grid and facing up" should {
      "move to the bottom edge of the grid on moving forward" in {
        val grid = Grid(2, 2)
        val testCase = Rover(Up, Position(0, 2))
        val expected = Rover(Up, Position(0, 0))

        val actual = RoverService.moveForward(testCase, grid)

        actual shouldBe expected
      }
    }

    "on the bottom edge of the grid and facing down" should {
      "move to the top edge of the grid on moving forward" in {
        val grid = Grid(2, 2)
        val testCase = Rover(Down, Position(0, 0))
        val expected = Rover(Down, Position(0, 2))

        val actual = RoverService.moveForward(testCase, grid)

        actual shouldBe expected
      }
    }

    "in the middle of the grid and facing up" should {
      "move up one square on moving forward" in {
        val grid = Grid(2, 2)
        val testCase = Rover(Up, Position(1, 1))
        val expected = Rover(Up, Position(1, 2))

        val actual = RoverService.moveForward(testCase, grid)

        actual shouldBe expected
      }
    }

    "in the middle of the grid and facing down" should {
      "move down one square on moving forward" in {
        val grid = Grid(2, 2)
        val testCase = Rover(Down, Position(1, 1))
        val expected = Rover(Down, Position(1, 0))

        val actual = RoverService.moveForward(testCase, grid)

        actual shouldBe expected
      }
    }

    "in the middle of the grid and facing left" should {
      "move down left square on moving forward" in {
        val grid = Grid(2, 2)
        val testCase = Rover(Left, Position(1, 1))
        val expected = Rover(Left, Position(0, 1))

        val actual = RoverService.moveForward(testCase, grid)

        actual shouldBe expected
      }
    }

    "in the middle of the grid and facing right" should {
      "move right one square on moving forward" in {
        val grid = Grid(2, 2)
        val testCase = Rover(Right, Position(1, 1))
        val expected = Rover(Right, Position(2, 1))

        val actual = RoverService.moveForward(testCase, grid)

        actual shouldBe expected
      }
    }

    "on the opposite corner to the destination" should {
      "cross over grid edges when on autopilot" in {
        val grid = Grid(2, 2)
        val rover = Rover(Right, Position(0, 0))
        val destination = Position(2, 2)
        val possiblePaths = Set(
          List(Position(0, 0), Position(0, 2), Position(2, 2)),
          List(Position(0, 0), Position(2, 0), Position(2, 2)),
        )

        val actual = RoverService.autopilot(rover, grid, destination)

        possiblePaths should contain(actual)
      }
    }
  }
}