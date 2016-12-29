module Main where

import Day1

main :: IO ()
main = do day1 <- readFile "day1part1.txt"
          putStrLn . show . day1a $ day1
          putStrLn . show . day1b $ day1
