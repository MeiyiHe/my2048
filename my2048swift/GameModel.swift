//
//  GameModel.swift
//  my2048swift
//
//  Created by Meiyi He on 12/28/16.
//  Copyright © 2016 Meiyi He. All rights reserved.
//

import Foundation

class GameModel{
    
    var dimension:Int = 0
    
    var tiles:[[Int]]
    //var tiles:Array<Int>
    
    init(dimension:Int, tiles:[[Int]]){
        self.dimension = dimension
       // self.tiles = Array<Int>( repeating: 0, count: self.dimension*self.dimension)
        self.tiles = Array(repeating: Array(repeating: 0, count: self.dimension), count: self.dimension)
        
    }
    /*
    func setPosition(row:Int, col:Int, value:Int) -> Bool{
        assert(row >= 0 && row < dimension)
        assert(col >= 0 && col < dimension)
        
        var index = self.dimension*row + col
        var val = tiles[index]
        
        print("row ",row)
        print("col ",col)
        
        print("value ",value)
        
        if(val > 0){
            print("this position contains number already")
            return false
        }
        tiles[index] = val
        return true
    
    }
    */
    
    func setPosition(row:Int, col:Int, value:Int) -> Bool{
        
        assert(row >= 0 && row < dimension)
        assert(col >= 0 && col < dimension)
        
        
        var value = tiles[row][col]
        print("row ",row)
        print("col ",col)
        
        print("value ",value)
        
        if(tiles[row][col] > 0){
            print("this position contains number already")
            return false
        }
        
        tiles[row][col] = value
        
        return true
    }
    
    /*
    func emptyPosition() -> [Int]{
        var empty:Array<Int> = []
        
        for i in 0...dimension*dimension-1{
            if(tiles[i] == 0){
                empty.append(i)
            }
        }
        return empty
    }
    
    func checkFull() -> Bool{
        print("count how many position available", emptyPosition().count)
        if(emptyPosition().count == 0){
            return true
        }
        return false
    }
 */
 
    
   
    
    // check if the grid full or not --> if full then return true
    func checkFull() -> Bool{
        print("in check full method")
        
        var count:Int = 0
        var check = true
        
        for row in 0 ... self.dimension-1{
            for col in 0 ... self.dimension-1{
                if( self.tiles[row][col] == 0){
                    // there is position available
                    count = count+1
                    
                }
            }
        }
            print("count how many position available", count)
        if(count > 0){
            check = false
        }
        
        return check
    }
   

}
