module Day1
    ( day1a, day1b )
where

import Data.Char (ord)
import Data.Maybe (mapMaybe)
import Control.Arrow (second)

data Turn = LEFT | RIGHT deriving (Eq, Bounded, Show, Enum)

parseDay1 :: [Char] -> [(Turn, Int)]
parseDay1 = let parseTurn [] = []
                parseTurn (c:cs)
                  | c == 'R' = parseDay1Number RIGHT 0 cs
                  | c == 'L' = parseDay1Number LEFT 0 cs
                  | otherwise = parseDay1 cs
                parseDay1Number t n [] = [(t, n)]
                parseDay1Number t n (x:xs)
                  | x >= '0' && x <= '9' = parseDay1Number t (n * 10 + (ord x - 48)) xs
                  | n > 0                = (t, n) : parseDay1 (x:xs)
                  | otherwise            = error "Missing number after turn"
            in parseTurn

data Direction = NORTH | EAST | SOUTH | WEST deriving (Eq, Bounded, Show, Enum)

shift :: (Enum a, Bounded a) => a -> Int -> a
shift enum amount = toEnum $ mod ((fromEnum enum) + amount) (fromEnum (maxBound `asTypeOf` enum) + 1)

changeDirection :: Turn -> Direction -> Direction
changeDirection RIGHT d = shift d 1
changeDirection LEFT d = shift d $ -1

data Orientation = HORIZONTAL | VERTICAL deriving (Eq, Bounded, Show, Enum)

orient :: Direction -> Orientation
orient NORTH = VERTICAL
orient SOUTH = VERTICAL
orient _ = HORIZONTAL

data Point = Point Int Int deriving (Eq, Show)

applySteps :: Point -> Direction -> Int -> Point
applySteps (Point x y) NORTH v = Point (x+v) y
applySteps (Point x y) EAST v = Point x (y+v)
applySteps (Point x y) SOUTH v = Point (x-v) y
applySteps (Point x y) WEST v = Point x (y-v)

distance :: Point -> Int
distance (Point x y) = (abs x) + (abs y)

step :: (Direction, Point) -> (Turn, Int) -> (Direction, Point)
step (d, p) (t, steps) = let newDir = (changeDirection t d)
                         in  (newDir, applySteps p newDir steps)

data Line = Line Orientation Int Int Int deriving (Eq, Show)

makeLine :: Point -> Point -> Line
makeLine (Point x1 y1) (Point x2 y2)
  | x1 == x2 = Line HORIZONTAL x1 y1 y2
  | y1 == y2 = Line VERTICAL y1 x1 x2
  | otherwise = error "Can't make a line object if the points are not in a straight line"

pointsToLines :: [Point] -> [Line]
pointsToLines (a:b:rest) = makeLine a b : pointsToLines (b:rest)
pointsToLines _ = []

cross :: Line -> Line -> Maybe Point
cross (Line HORIZONTAL x y1 y2) (Line VERTICAL y x1 x2)
  |    ((y1 < y) && (y < y2) || (y1 > y) && (y > y2))
    && ((x1 < x) && (x < x2) || (x1 > x) && (x > x2))
    = Just (Point x y)
  | otherwise = Nothing
cross a@(Line VERTICAL _ _ _) b@(Line HORIZONTAL _ _ _) = cross b a
cross _ _ = Nothing

day1a :: [Char] -> Int
day1a = distance . snd . foldl step (NORTH, Point 0 0) . parseDay1

findCrossings :: [Line] -> [Line] -> [Point]
findCrossings history (l:ls) = mapMaybe (cross l) history ++ findCrossings (l:history) ls
findCrossings _ [] = []

day1b = distance . head . findCrossings [] . pointsToLines . map snd . scanl step (NORTH, Point 0 0) . parseDay1
